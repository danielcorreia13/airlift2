package Client.stubs;

import Common.Message;
import Common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Passenger;
import Client.entities.Pilot;


/**
 * Stub: Destination Airport
 */
public class DestinationAirportStub
{
    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Destination Airport Stub instantiation.
     *
     *    //@param repos reference to the general repository
     */
    public DestinationAirportStub ( /* GeneralRep repos */ ) { }


    /*                                 PASSENGER                                     */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Operation of passenger leaving the plane
     *
     *  It is called by the PASSENGER when he leaves the plane
     *
     */
    public void leaveThePlane() {
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

        Passenger passenger = (Passenger) Thread.currentThread();
        System.out.println("PASSENGER " + passenger.getpId() + ": Left the plane");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.LEAVE_PLANE);
        pkt.setId( passenger.getId() );
        pkt.setPassengerState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState( pkt.getPassengerState() );

        clientCom.close();
    }


    /*                                    PILOT                                      */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Operation inform that plane reached the destionation
     *
     *  It is called by the pilot when plane was landed
     *
     *  @param nPass number of passengers in the plane
     */
    public void announceArrival(int nPass) {
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

        Pilot pilot = ((Pilot) Thread.currentThread());
        System.out.println("PILOT: Plane arrived at destination");

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.ANNOUNCE_ARRIVAL);
        pkt.setId( pilot.getId());
        pkt.setPilotState( pilot.getPilotState() );
        pkt.setInt1(nPass);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getPilotState() );

        clientCom.close();
    }

    /**
     * Get number of total transported passengers
     *
     * @return number of total transported passengers
     */
    public int getTotalPassengers() {
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

        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.GET_TOTAL_PASSENGERS);
        pkt.setId( pilot.getId() );
        pkt.setPilotState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getPilotState() );

        clientCom.close();

        /* TODO: Retornar o numero de passageiros */
        return 0;
    }
}
