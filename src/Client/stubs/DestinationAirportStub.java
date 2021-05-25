package Client.stubs;

import Common.Message;
import Common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Passenger;
import Client.entities.Pilot;
import Common.RunParameters;


/**
 * Stub: Destination Airport
 */
public class DestinationAirportStub
{

    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Destination Airport Stub instantiation.
     */
    public DestinationAirportStub () { }

    /**
     *  Operation to performe a client communication with server
     *
     *  It is called by each method of this class
     *
     * @return ClientCom object
     */
    public ClientCom Communication()
    {
        ClientCom clientCom = new ClientCom(RunParameters.DestinationAirportHostName, RunParameters.DestinationAirportPort);

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


    /*                                 PASSENGER                                     */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Operation of passenger leaving the plane
     *
     *  It is called by the PASSENGER when he leaves the plane
     *
     */
    public void leaveThePlane() {
        ClientCom clientCom = Communication();

        Passenger passenger = (Passenger) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.LEAVE_PLANE);
        pkt.setId( passenger.getpId() );
        pkt.setState( passenger.getpState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        passenger.setpState( pkt.getState() );

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
        ClientCom clientCom = Communication();

        Pilot pilot = ((Pilot) Thread.currentThread());

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.ANNOUNCE_ARRIVAL);
        pkt.setState( pilot.getPilotState() );
        pkt.setInt1(nPass);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();
    }

    /**
     * Get number of total transported passengers
     *
     * @return number of total transported passengers
     */
    public int getTotalPassengers() {
        ClientCom clientCom = Communication();

        Pilot pilot = (Pilot) Thread.currentThread();

        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.GET_TOTAL_PASSENGERS);
        pkt.setState( pilot.getPilotState() );
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();
        pilot.setPilotState( pkt.getState() );

        clientCom.close();

        return pkt.getInt1(); /* Return Total of Passengers received by server */
    }
}
