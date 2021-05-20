package Client.stubs;

import Client.common.Message;
import Client.common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Hostess;
import Client.entities.Passenger;
import Client.entities.Pilot;
import Client.Settings;
//import SharedRegions.GeneralRep;
import Client.myLib.*;
import java.util.Arrays;

/**
 * Shared region : Departure Airport
 */
public class DepartureAirportStub
{
    /**
     * Departure Passenger Queue
     */
    private MemFIFO<Integer> passengerQueue;  // passengers waiting to check documents


    /**
     * References tio the passengers
     */

    private final Passenger[] passengers;  // passenger objects

    /**
     * Ready for boarding flag
     */
    private boolean readyForBoardig;


    /**
     * Keeps track of how many passengers are entering the plane
     */
    private int nPassengers;

    /**
     * Reference to General Repository
     */
    //private final GeneralRep generalRep;

    /**
     * Show documents flags
     */
    private final boolean[] showDocuments;

    /**
     * Allowed to board flags
     */
    private final boolean[] canBoard;


    /*                                  CONSTRUCTOR                                    */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Departure Airport instantiation.
     *
     *    //@param repos reference to the general repository
     */
    /* TODO: Verificar melhor o construtor */
    public DepartureAirportStub( /*GeneralRep repos */) {
        //generalRep = repos;

        try {
            passengerQueue = new MemFIFO<>(new Integer [Settings.nPassengers]);
        } catch (MemException e) {
            System.err.println("Instantiation of waiting FIFO failed: " + e.getMessage ());
            passengerQueue = null;
            System.exit (1);
        }

        passengers = new Passenger[Settings.nPassengers];
        showDocuments = new boolean[Settings.nPassengers];
        canBoard = new boolean[Settings.nPassengers];
        Arrays.fill(canBoard, false);
        Arrays.fill(showDocuments, false);
        Arrays.fill(passengers, null);

        readyForBoardig = false;
        nPassengers = 0;
    }

    /**
     *
     * @return true if the passenger queue is empty
     */
    /* TODO : Modificar para comunicar com o servidor */
    public boolean empty()
    {
        return passengerQueue.empty();
    }

    /**
     *
     * @return the number of passengers that have been checked
     */
    /* TODO : Modificar para comunicar com o servidor */
    public int getnPassengers()
    {
        return nPassengers;
    }

    /*                                   HOSTESS                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the hostess needs do check the passenger documents
     *
     *  It is called by the HOSTESS when she is requesting a passenger documents
     *
     *
     */
    /* TODO : Analisar melhor como se faz nestes casos com dois whiles */
    public void checkDocuments() {
        int passId = -1;

        try {
            passId = passengerQueue.read();
        } catch (MemException e) {
            System.err.println("Retrieval of passenger from waiting queue failed: " + e.getMessage());
            System.exit(1);
        }

//        System.out.println("HOSTESS: Passenger "+ passId +" is next on queue");

        showDocuments[passId] = true;

        //generalRep.writeLog("Passenger " + passId + " checked");
        ((Hostess) Thread.currentThread()).sethState(Hostess.States.CHECK_PASSENGER);
        //generalRep.setHostess(Hostess.States.CHECK_PASSENGER);

        notifyAll();

        while (showDocuments[passId])
        {
//        	System.out.println("HOSTESS: Checking passenger "+ passId +" documents");

            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

//        System.out.println("	HOSTESS: Passenger "+ passId +" documents checked!");
//        System.out.println("		HOSTESS: Passenger "+ passId +" allowed to board");

        canBoard[passId] = true;

        nPassengers++;
        notifyAll();
    }

    /**
     *  Operation inform that the hostess is waiting for a passenger on the departure queue
     *
     *  It is called by the HOSTESS when she is waiting for a passenger on the queue
     *
     *
     */
    public void waitForNextPassenger() {
        System.out.println("HOSTESS: Waiting for passenger on Queue");

        ClientCom clientCom = new ClientCom("localhost", 4001);

        /* while (passengerQueue.empty()) */
        while( !clientCom.open() )
        {
            try
            {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Hostess hostess = (Hostess) Thread.currentThread();
            Message pkt = new Message();

            pkt.setType(MessageType.WAIT_FOR_NEXT_PASSENGER);
            pkt.setId( hostess.getId() );
            pkt.setHostState( hostess.gethState() );

            clientCom.writeObject(pkt);

            pkt = (Message) clientCom.readObject();
            hostess.sethState( pkt.getHostState() );
            clientCom.close();

        }
    }

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on flight waiting to reach the destination
     *
     */
    public void waitForNextFlight() {
        System.out.println("HOSTESS: Waiting for next flight");

        ClientCom clientCom = new ClientCom("localhost", 4001);

        /* while(!readyForBoarding) */
        while( !clientCom.open() )                            /* Enquanto a comunicação não estiver estabelecida, aguarda */
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Message pkt = new Message();
        Hostess hostess = (Hostess) Thread.currentThread();   /* Caso comunicação estabelecida, busca o thread atual */

        pkt.setType(MessageType.WAIT_FOR_NEXT_FLIGHT);        /* Tipo de mensagem */
        pkt.setId( hostess.getId() );                          /* Id da thread */
        pkt.setHostState(hostess.gethState());                /* Estado atual da thread */

        clientCom.writeObject(pkt);                           /* Escreve o objeto na mensagem */

        pkt = (Message) clientCom.readObject();               /* Recebe uma mensagem do servidor */
        hostess.sethState( pkt.getHostState() );              /* Atualiza o estado caso tenha mudado */
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
    /* TODO : Analisar melhor como se faz nestes casos com dois whiles */
    public void waitInQueue() {
        int passId = ((Passenger) Thread.currentThread()).getpId();
        passengers[passId] = (Passenger) Thread.currentThread();
        passengers[passId].setpState(Passenger.States.IN_QUEUE);
        //generalRep.setPassengerState(passId,Passenger.States.IN_QUEUE);
//        System.out.println("[!] PASSENGER " + passId + ": Arrived at departure airport");


        try {
            passengerQueue.write(passId);
        } catch (MemException e){
            System.err.println("Insertion of passenger in waiting queue failed: " + e.getMessage());
            System.exit(1);
        }
        notifyAll();

        while (!showDocuments[passId]){
            try{
                wait();
            }catch (InterruptedException ignored){

            }
        }

        showDocuments();

        while (!canBoard[passId]){
            try{
                wait();
            }catch (InterruptedException ignored){

            }
        }
    }

    /**
     *  Operation inform that the passenger is showing his documents
     *
     *  It is called by the PASSENGER when he need to show his documents to the hostess
     *
     *
     */
    public void showDocuments() {
        ClientCom clientCom = new ClientCom("localhost", 4001);

        while( !clientCom.open() )                            /* Enquanto a comunicação não estiver estabelecida, aguarda */
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Passenger passenger = (Passenger) Thread.currentThread();
        System.out.println("PASSENGER "+ passenger.getpId() +": Shows documents");

        /* showDocuments[passId] = false; */

        Message pkt = new Message();
        pkt.setType(MessageType.SHOW_DOCUMENTS);
        pkt.setId( passenger.getpId() );                               /* Envia como parametro o ID da Thread Passenger */
        pkt.setPassengerState( passenger.getpState() );                /* Estado atual da thread */

        clientCom.writeObject(pkt);                                    /* Escreve o objeto na mensagem */

        pkt = (Message) clientCom.readObject();                        /* Recebe uma mensagem do servidor */
        passenger.setpState( pkt.getPassengerState() );                /* Atualiza o estado caso tenha mudado */

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
        ClientCom clientCom = new ClientCom("localhost", 4001);

        /* readyForBoardig = true; */

        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("PILOT: Plane is ready for boarding");
        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.INFORM_PLANE_READY_BOARDING);
        pkt.setId( pilot.getId() );
        pkt.setPilotState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getPilotState() );

        clientCom.close();

    }

    /**
     * It's called by the pilot to park the plane at the transfer gate
     */
    public void parkAtTransferGate() {
        ClientCom clientCom = new ClientCom("localhost", 4001);

        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("PILOT: Plane is ready for boarding");
        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.PARK_AT_TRANSFER_GATE);
        pkt.setId( pilot.getId() );
        pkt.setPilotState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getPilotState() );

        clientCom.close();
    }
}
