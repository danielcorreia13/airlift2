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
 * Stub : Departure Airport
 */
public class DepartureAirportStub
{

    /*                                  CONSTRUCTOR                                    */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Departure Airport instantiation.
     *
     *    //@param repos reference to the general repository
     */
    public DepartureAirportStub( /*GeneralRep repos */) { }



    /*                                   HOSTESS                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *
     * @return true if the passenger queue is empty
     */
    public boolean empty() {
        /* TODO : Modificar locahost e portNumb */
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

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_QUEUE_EMPTY);
        pkt.setId( hostess.getId() );
        pkt.setHostState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getHostState() );

        clientCom.close();

        /* TODO : Retornar o valor boolean*/
        return false;
    }

    /**
     *
     * @return the number of passengers that have been checked
     */
    public int getnPassengers() {
        /* TODO : Modificar locahost e portNumb */
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

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.GET_N_PASSENGERS);
        pkt.setId( hostess.getId() );
        pkt.setHostState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getHostState() );

        clientCom.close();

        /* TODO : Retornar o numero de passageitros*/
        return 0;
    }

    /**
     *  Operation inform that the hostess needs do check the passenger documents
     *
     *  It is called by the HOSTESS when she is requesting a passenger documents
     *
     */
    public void checkDocuments() {
        /* TODO : Modificar locahost e portNumb */
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
        Hostess hostess = (Hostess) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.CHECK_DOCUMENTS);
        pkt.setId( hostess.getId() );
        pkt.setHostState( hostess.gethState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        hostess.sethState ( pkt.getHostState() );

        clientCom.close();
    }

    /**
     *  Operation inform that the hostess is waiting for a passenger on the departure queue
     *
     *  It is called by the HOSTESS when she is waiting for a passenger on the queue
     *
     */
    public void waitForNextPassenger() {
        /* TODO : Modificar locahost e portNumb */
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

            System.out.println("HOSTESS: Waiting for passenger on Queue");
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
        /* TODO : Modificar locahost e portNumb */
        ClientCom clientCom = new ClientCom("localhost", 4001);

        while( !clientCom.open() )                            /* Enquanto a comunicação não estiver estabelecida, aguarda */
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("HOSTESS: Waiting for next flight");
        Hostess hostess = (Hostess) Thread.currentThread();   /* Caso comunicação estabelecida, busca o thread atual */

        Message pkt = new Message();
        /* Send Message */
        pkt.setType(MessageType.WAIT_FOR_NEXT_FLIGHT);        /* Tipo de mensagem */
        pkt.setId( hostess.getId() );                         /* Id da thread */
        pkt.setHostState(hostess.gethState());                /* Estado atual da thread */

        clientCom.writeObject(pkt);                           /* Escreve o objeto na mensagem */
        /* Receive Message */
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
    public void waitInQueue() {
        /* TODO : Modificar locahost e portNumb */
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
        Passenger passenger = (Passenger) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WAIT_IN_QUEUE);
        pkt.setId( passenger.getpId() );
        pkt.setPassengerState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState ( pkt.getPassengerState() );

        clientCom.close();
    }

    /**
     *  Operation inform that the passenger is showing his documents
     *
     *  It is called by the PASSENGER when he need to show his documents to the hostess
     *
     */
    /* TODO : Possivelmente nao sera necessario este metodo aqui*/
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
        /* TODO : Modificar locahost e portNumb */
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
        /* TODO : Modificar locahost e portNumb */
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
