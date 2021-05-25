package Servers.GeneralRep;

import Common.Message;
import Servers.Common.ServerCom;


public class GeneralRepClientProxy extends Thread{

    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private final ServerCom conn;

    /**
     *  Interface to the General Repository
     */

    private final GeneralRepInterface generalRep;

    /**
     *  Instantiation of a General Repository Proxy
     *
     *     @param conn communication channel
     *     @param generalRep interface to Destination Airport
     */

    public GeneralRepClientProxy( ServerCom conn, GeneralRepInterface generalRep) {
        super ("GeneralRep_" + GeneralRepClientProxy.getProxyId ());
        this.conn = conn;
        this.generalRep = generalRep;
    }

    /**
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run() {

            Message msg = (Message) conn.readObject();
            if (msg == null) return;
            Message send = generalRep.handleRequest(msg);
            conn.writeObject(send);
            conn.close();

    }

    /**
     *  Generation of the instantiation identifier.
     *
     *     @return instantiation identifier
     */

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
