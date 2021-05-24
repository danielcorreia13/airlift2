package Servers.GeneralRep;

import Common.Message;
import Servers.Common.ServerCom;


public class GeneralRepClientProxy extends Thread{

    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    private final ServerCom conn;

    private final GeneralRepInterface generalRep;


    public GeneralRepClientProxy( ServerCom conn, GeneralRepInterface generalRep) {
        super ("GeneralRep_" + GeneralRepClientProxy.getProxyId ());
        this.conn = conn;
        this.generalRep = generalRep;
    }

    @Override
    public void run() {

            Message msg = (Message) conn.readObject();
            if (msg == null) return;
            Message send = generalRep.handleRequest(msg);
            conn.writeObject(send);
            conn.close();

    }

    private static int getProxyId ()
    {
        Class<?> cl = null;
        int proxyId;

        try
        { cl = Class.forName ("Servers.GeneralRep.GeneralRepClientProxy");
        }
        catch (ClassNotFoundException e)
        { System.err.println ("Class was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }
}
