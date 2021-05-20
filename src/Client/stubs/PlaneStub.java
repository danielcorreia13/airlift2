package Client.stubs;

import Client.common.Message;
import Client.common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Hostess;
import Client.entities.Passenger;
import Client.entities.Pilot;
//import SharedRegions.GeneralRep;

/**
 * Shared region : Plane
 */
public class PlaneStub
{
    /**
     * Reference to general repository
     */
    //private final GeneralRep generalRep;

    /**
     * All passengers on board flag
     */
    private boolean allInBoard;

    /**
     * Number of passengers flag
     */
    private int nPassengers;

    /**
     * Plane at destination flag
     */
    private boolean atDestination;

    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/

    /**
     *  Plane instantiation.
     *
     *    //@param repos reference to the general repository
     */
    /* TODO : Analisar o construtor */
    public PlaneStub ( /* GeneralRep repos */ )
    {
        //generalRep = repos;
        this.allInBoard = false;
        setAtDestination(false);
        this.nPassengers = 0;
    }

    /*                                  HOSTESS                                      */
    /*-------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the plane is ready for take off
     *
     *  It is called by the HOSTESS when all passengers are on board,
     *	Requiring minimum passengers on board and departure queue is empty
     *  Or if maximum passengers on board was reached
     *  Or if number of passengers on board is between min and max, and departure queue is empty
     * @param nPass number of passengers that boarded the plane
     */
    public void informPlaneIsReadyToTakeOff(int nPass)
    {
        /* TODO : Verificar o hostName e portNumb */
        ClientCom clientCom = new ClientCom("localhost", 4001);

        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Hostess hostess = (Hostess) Thread.currentThread();
        System.out.println("HOSTESS: Inform that plane is ready to takeoff");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.INFORM_PLANE_READY_TAKEOFF);
        pkt.setId( hostess.getId() );
        pkt.setHostState( hostess.gethState() );
        pkt.setInt1(nPass);                                /* Enviar o parametro recebido na funcao */
        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        hostess.sethState( pkt.getHostState() );

        /*TODO : Atualizar no hostess o valor recebido do servidor*/
        //hostess.setNPass( pkt.getInt1() );

        clientCom.close();
    }




    /**
     *  Operation inform that plane is at destination
     *
     *  It is called by the pilot to notify passengers that the plane was landed
     *
     * @return true if at destination
     */
    /* TODO : Modificar para comunicar com o servidor */
    public synchronized boolean isAtDestination()
    {
        return this.atDestination;
    }

    //                                  PASSENGER                                    //
    //---------------------------------------------------------------------------------

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on the plane
     *
     *
     */
    /* TODO : Modificar para comunicar com o servidor */
    public synchronized void waitForEndOfFlight()
    {

//    	int passId = ((Passenger) Thread.currentThread()).getpId();
        //System.out.println("[!] PASSENGER "+ passId +": Waiting for the end of the flight");

        while ( !isAtDestination() )
        {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        notifyAll();

    }

    /**
     *  Operation inform that the passenger as entered the plane
     *
     *  It is called by the PASSENGER when enters the plane
     *
     *
     */
    /* TODO : Modificar para comunicar com o servidor */
    public synchronized void boardThePlane()
    {

        int passId = ((Passenger) Thread.currentThread()).getpId();
        ((Passenger) Thread.currentThread()).setpState(Passenger.States.IN_FLIGHT);
        //generalRep.setPassengerState(passId, Passenger.States.IN_FLIGHT);
        //System.out.println("PASSENGER "+passId+ ": Seated on plane");
        nPassengers++;
        notifyAll();
    }


    //                                   PILOT                                      //
    //--------------------------------------------------------------------------------

    /**
     *  Operation to set plane at destination
     *
     * It is called by pilot on destination point
     *
     * @param atDestination new value
     */
    public void setAtDestination(boolean atDestination)
    {
        /* TODO : hostName e portNumb estao incorretos */
        ClientCom clientCom = new ClientCom("localhost", 4001);

        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Pilot pilot = (Pilot) Thread.currentThread();
        System.out.println("PILOT: Inform that plane is at destination");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.ANNOUNCE_ARRIVAL);
        pkt.setId( pilot.getId() );
        pkt.setPilotState( pilot.getPilotState() );
        pkt.setBool1(atDestination);                              /* Enviar o parametro recebido na funcao */
        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getPilotState() );

        /*TODO : Atualizar o valor recebido do servidor */
        //pilot.setAtDestination( pkt.getBool1() );

        clientCom.close();
    }
    /**
     *  Operation inform that PILOT is waiting for the hostess signal, indicating that all passengers are on board.
     *
     *  It is called by the PILOT while waiting for all passengers on board
     *
     *    @return void
     */
    /* TODO : Modificar para comunicar com o servidor */
    public synchronized int waitForAllInBoard()
    {
        //System.out.println("PILOT: Waiting for all passengers on board");
        nPassengers = 0;

        ((Pilot) Thread.currentThread()).setPilotState(Pilot.States.WAIT_FOR_BOARDING);
        //generalRep.setPilotState(Pilot.States.WAIT_FOR_BOARDING);
        try
        {
            while( !allInBoard)
                wait();
        }
        catch (InterruptedException ignored) {}
        allInBoard = false;
        ((Pilot) Thread.currentThread()).setPilotState(Pilot.States.FLYING_FORWARD);

        //generalRep.setPilotState(Pilot.States.FLYING_FORWARD);
        return nPassengers;
    }
}
