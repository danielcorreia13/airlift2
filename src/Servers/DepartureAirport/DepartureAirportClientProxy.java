package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.ServerCom;

import java.io.EOFException;
import java.io.IOException;

public class DepartureAirportClientProxy extends Thread{

    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    private final ServerCom conn;

    private final DepartureAirportInterface departureAirport;

    /**
     *  Id do passageiro
     */

    private int passId;

    /**
     *  Estado da entidade que este proxy representa
     */

    private int entityState;

    /**
     *  @return Id do passageiro
     */

    public int getPassId() {
        return passId;
    }

    /**
     *  Atribuir Id do passageiro
     */

    public void setPassId(int passId) {
        this.passId = passId;
    }

    /**
     *  @return Estado da entidade
     */

    public int getEntityState() {
        return entityState;
    }

    /**
     *  Atribuir Estado da entidade
     */

    public void setEntityState(int entityState) {
        this.entityState = entityState;
    }


    public DepartureAirportClientProxy(ServerCom conn, DepartureAirportInterface departureAirport) {
        super ("DepartureAirport_" + DepartureAirportClientProxy.getProxyId ());
        this.conn = conn;
        this.departureAirport= departureAirport;
    }

    @Override
    public void run() {

            Message msg = (Message) conn.readObject();
            if (msg == null) return;
            Message send = departureAirport.handleRequest(msg);
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
        { cl = Class.forName ("Servers.DepartureAirport.DepartureAirportClientProxy");
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
