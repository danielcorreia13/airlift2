package Servers.DestinationAirport;

import Common.Message;
import Servers.Common.ServerCom;


public class DestinationAirportClientProxy extends Thread{

    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private final ServerCom conn;

    /**
     *  Interface to the Destination Airport
     */

    private final DestinationAirportInterface destinationAirport;

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

    /**
     *  Instantiation of a Departure Airport Proxy
     *
     *     @param conn communication channel
     *     @param destinationAirport interface to Destination Airport
     */

    public DestinationAirportClientProxy(ServerCom conn, DestinationAirportInterface destinationAirport) {
        super ("DestinationAirport_" + DestinationAirportClientProxy.getProxyId ());
        this.conn = conn;
        this.destinationAirport = destinationAirport;
    }

    /**
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run()
    {
        Message msg = (Message) conn.readObject();
        if (msg == null) return;
        Message send = destinationAirport.handleRequest(msg);
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
        { cl = Class.forName ("Servers.DestinationAirport.DestinationAirportClientProxy");
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
