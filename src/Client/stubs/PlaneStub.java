package Client.stubs;

import Common.Message;
import Common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Hostess;
import Client.entities.Passenger;
import Client.entities.Pilot;
import Common.RunParameters;

/**
 * Stub : Plane
 */
public class PlaneStub
{
    /**
     *  Reference to ClientCom
     */
//    private static ClientCom clientCom;

    
    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/

    /**
     *  Plane Stub instantiation.
     */
    public PlaneStub () { }

    /**
     *  Operation to perform a client communication with server
     *
     *  It is called by each method of this class
     *
     * @return ClientCom object
     */
    public ClientCom Communication()
    {
        ClientCom clientCom = new ClientCom(RunParameters.PlaneHostName, RunParameters.PlanePort);
        
        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return clientCom;
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
     *
     */
    public void informPlaneIsReadyToTakeOff(int nPass) {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();
        System.out.println("HOSTESS: Inform that plane is ready to takeoff");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.INFORM_PLANE_READY_TAKEOFF);
//        pkt.setId( hostess.getId() );
        pkt.setState( hostess.gethState() );
        pkt.setInt1(nPass);                                /* Enviar o parametro recebido na funcao */
        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        hostess.sethState( pkt.getState() );

        clientCom.close();
    }


    /**
     *  Operation inform that plane is at destination
     *
     *  It is called by the pilot to notify passengers that the plane was landed
     *
     * @return true if at destination
     */
    /* TODO : Verificar se e necessario */
    public boolean isAtDestination() {
        //return this.atDestination;
        return false;
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
    public void waitForEndOfFlight() {
        ClientCom clientCom = Communication();

        Passenger passenger = (Passenger) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_END_FLIGHT);
        pkt.setId( passenger.getpId() );
        pkt.setState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState( pkt.getState() );

        System.out.println("PASSENGER " + passenger.getpId() + ": Left the plane");
        clientCom.close();
    }

    /**
     *  Operation inform that the passenger as entered the plane
     *
     *  It is called by the PASSENGER when enters the plane
     *
     */
    public void boardThePlane() {
        ClientCom clientCom = Communication();

        Passenger passenger = (Passenger) Thread.currentThread();
        System.out.println("Sending: PASSENGER "+ passenger.getpId() +": Board the plane");
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.BOARD_THE_PLANE);
        pkt.setId( passenger.getpId() );
        pkt.setState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState( pkt.getState() );

        System.out.println("Recieved: PASSENGER " + passenger.getpId() + ": Board the plane");
        clientCom.close();
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
    public void setAtDestination(boolean atDestination) {
        ClientCom clientCom = Communication();

        Pilot pilot = (Pilot) Thread.currentThread();
        System.out.println("PILOT: Inform that plane is at destination");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.SET_AT_DESTINATION);
//        pkt.setId( pilot.getId() );
        pkt.setState( pilot.getPilotState() );
        pkt.setBool1( atDestination );                              /* Enviar o parametro recebido na funcao */
        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }
    /**
     *  Operation inform that PILOT is waiting for the hostess signal, indicating that all passengers are on board.
     *
     *  It is called by the PILOT while waiting for all passengers on board
     *
     *    @return nPassengers
     */
    public int waitForAllInBoard() {
        ClientCom clientCom = Communication();

        Pilot pilot = (Pilot) Thread.currentThread();
        System.out.println("Sending: PILOT: Wait for all in board");
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_FOR_ALL_IN_BOARD);
//        pkt.setId( pilot.getId() );
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );
        System.out.println("recieved: PILOT: All in board");
        clientCom.close();

        return pkt.getInt1(); /* Return nPassengers */
    }

    /**
     * Shutdown the server
     */
    public void shutdown() {
        ClientCom clientCom = Communication();

        System.out.println("Sending to plane: Shutdown");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.SHUT);

        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }
}
