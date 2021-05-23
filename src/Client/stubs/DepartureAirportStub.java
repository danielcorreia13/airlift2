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

    //private static ClientCom clientCom;


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
     *
     * @return true if the passenger queue is empty
     */
    public boolean empty() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_QUEUE_EMPTY);
//        pkt.setId( hostess.getId() );
        pkt.setState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getState() );

        clientCom.close();

        return pkt.getBool1(); /* Return to client boolean value received from server */
    }

    /**
     *
     * @return the number of passengers that have been checked
     */
    public int getnPassengers() {
        ClientCom clientCom = Communication();

        Hostess hostess = (Hostess) Thread.currentThread();
        System.out.println("Sending: HOSTESS: Get n Passengers");
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.GET_N_PASSENGERS);
//        pkt.setId( hostess.getId() );
        pkt.setState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getState() );
        System.out.println("Recieved: HOSTESS: Get n Passengers");
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

        System.out.println("Sending: Hostess: Check documents");
        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_DOCUMENTS);
//        pkt.setId( hostess.getId() );
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

        System.out.println("Sending: HOSTESS: Waiting for passenger on Queue");
        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        pkt.setType(MessageType.WAIT_FOR_NEXT_PASSENGER);
//        pkt.setId( hostess.getId() );
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

        System.out.println("Sending: HOSTESS: Waiting for next flight");
        Hostess hostess = (Hostess) Thread.currentThread();   /* Caso comunicação estabelecida, busca o thread atual */

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_FOR_NEXT_FLIGHT);        /* Tipo de mensagem */
//        pkt.setId( hostess.getId() );                       /* Id da thread */
        pkt.setState(hostess.gethState());                /* Estado atual da thread */

        clientCom.writeObject(pkt);                           /* Escreve o objeto na mensagem */

        /* Receive Message */
        pkt = (Message) clientCom.readObject();               /* Recebe uma mensagem do servidor */
        hostess.sethState( pkt.getState() );                  /* Atualiza o estado caso tenha mudado */

        clientCom.close();                                    /* Encerra a comunicação */
    }


    /*                                 PASSENGER                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform the hostess that the passenger is waiting on departure airport queue
     *
     *  It is called by the PASSENGER when he is on the queue
     *
     */
    public void waitInQueue() {
        ClientCom clientCom = Communication();

        System.out.println("Sending: Passenger " + ((Passenger)Thread.currentThread()).getpId() + " in queue");
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

    /* TODO : Possivelmente nao sera necessario este metodo aqui */
    /**
     *  Operation inform that the passenger is showing his documents
     *
     *  It is called by the PASSENGER when he need to show his documents to the hostess
     *
     */
     /*public void showDocuments() {
         ClientCom clientCom = Communication();

        Passenger passenger = (Passenger) Thread.currentThread();
        System.out.println("Sending: PASSENGER "+ passenger.getpId() +": Show documents");

        Message pkt = new Message();

        pkt.setType(MessageType.SHOW_DOCUMENTS);
        pkt.setId( passenger.getpId() );
        pkt.setState( passenger.getpState() );

        clientCom.writeObject(pkt);

        pkt = (Message) clientCom.readObject();
        passenger.setpState( pkt.getState() );

        clientCom.close();
    }*/


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

        System.out.println("Sending: PILOT: Plane is ready for boarding");
        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.INFORM_PLANE_READY_BOARDING);
//        pkt.setId( pilot.getId() );
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        System.out.println("recieved: PILOT: Plane is ready for boarding");
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }

    /**
     * It's called by the pilot to park the plane at the transfer gate
     */
    public void parkAtTransferGate() {
        ClientCom clientCom = Communication();

        System.out.println("Sending: PILOT: Park at transfer gate");
        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.PARK_AT_TRANSFER_GATE);
//        pkt.setId( pilot.getId() );
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);


        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }
}
