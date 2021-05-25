package Client.stubs;

import Common.Message;
import Common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Hostess;
import Client.entities.Passenger;
import Client.entities.Pilot;
import Common.RunParameters;

/**
 * Stub : Departure Airport
 */
public class DepartureAirportStub
{

    /*                                  CONSTRUCTOR                                    */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Departure Airport instantiation.
     */
    public DepartureAirportStub() { }


    /**
     *  Operation to performe a client communication with server
     *
     *  It is called by each method of this class
     *
     * @return ClientCom object
     */
    public ClientCom Communication()
    {
        ClientCom clientCom = new ClientCom(RunParameters.DepartureAirportHostName, RunParameters.DepartureAirportPort);

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


    /*                                   HOSTESS                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     * Operation to check if Passenger Queue is empty
     *
     * @return true if the passenger queue is empty
     */
    public boolean empty() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_QUEUE_EMPTY);
        pkt.setState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getState() );

        clientCom.close();

        return pkt.getBool1(); /* Return to client boolean value received from server */
    }

    /**
     * Operation called by hostess to get the number of passengers
     *
     * @return the number of passengers that have been checked
     */
    public int getnPassengers() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.GET_N_PASSENGERS);
        pkt.setState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getState() );
        clientCom.close();

        return pkt.getInt1(); /* Return to client int value received from server */
    }

    /**
     *  Operation inform that the hostess needs do check the passenger documents
     *
     *  It is called by the HOSTESS when she is requesting a passenger documents
     *
     */
    public void checkDocuments() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_DOCUMENTS);
        pkt.setState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getState() );

        clientCom.close();
    }

    /**
     *  Operation inform that the hostess is waiting for a passenger on the departure queue
     *
     *  It is called by the HOSTESS when she is waiting for a passenger on the queue
     *
     */
    public void waitForNextPassenger() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        pkt.setType(MessageType.WAIT_FOR_NEXT_PASSENGER);
        pkt.setState( hostess.gethState() );

        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        hostess.sethState( pkt.getState() );

        clientCom.close();
    }

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on flight waiting to reach the destination
     *
     */
    public void waitForNextFlight() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_FOR_NEXT_FLIGHT);
        pkt.setState(hostess.gethState());
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState( pkt.getState() );

        clientCom.close();
    }


    /*                                 PASSENGER                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform the hostess that a passenger is waiting on departure airport queue
     *
     *  It is called by the PASSENGER when is on the queue
     *
     */
    public void waitInQueue() {
        ClientCom clientCom = Communication();

        Passenger passenger = (Passenger) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_IN_QUEUE);
        pkt.setId( passenger.getpId() );
        pkt.setState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState ( pkt.getState() );

        clientCom.close();
    }


    /*                                     PILOT                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the plane is ready for boarding
     *
     *  It is called by the PILOT when plane is parked on departure and ready for boarding
     *
     */
    public void informPlaneReadyForBoarding() {
        ClientCom clientCom = Communication();

        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.INFORM_PLANE_READY_BOARDING);
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }

    /**
     * Operation performed by the pilot to park the plane at the transfer gate
     */
    public void parkAtTransferGate() {
        ClientCom clientCom = Communication();

//        System.out.println("Sending: PILOT: Park at transfer gate");
        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.PARK_AT_TRANSFER_GATE);
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }

    /**
     * Shutdown the server
     */
    public void shutdown() {
        ClientCom clientCom = Communication();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.SHUT);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }
}
