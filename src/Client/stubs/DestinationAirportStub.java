package Client.stubs;

import Client.common.Message;
import Client.common.MessageType;
import Client.communications.ClientCom;
import Client.entities.Passenger;
import Client.entities.Pilot;


/**
 * Shared region : Destination Airport
 */
public class DestinationAirportStub
{
    /**
     * Reference to general repository
     */
    //private final GeneralRep generalRep;

    /**
     * Number of passengers
     */
    private int nPassengers;

    /**
     * Number of total passengers
     */
    private int totalPassengers;



    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Destination Airport instantiation.
     *
     *    //@param repos reference to the general repository
     */
    /* TODO : Verificar melhor o construtor */
    public DestinationAirportStub ( /* GeneralRep repos */)
    {
        //this.generalRep = repos;
        this.nPassengers = 0;
        totalPassengers = 0;
    }

    /**
     * Get number of total transported passengers
     *
     * @return number of total transported passengers
     */
    /* TODO : Modificar para comunicar com o servidor */
    public int getTotalPassengers()
    {
        return totalPassengers;
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
        pkt.setPilotState( passenger.getpState() );
        clientCom.writeObject(pkt);

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

    //* TODO : Modificar para comunicar com o servidor */
    public void announceArrival(int nPass) {

        nPassengers = nPass;

        //System.out.println("PILOT: Plane arrived at destination");
        ((Pilot) Thread.currentThread()).setPilotState(Pilot.States.DEBOARDING);
        //generalRep.writeLog("Arrived");
        //generalRep.setPilotState(Pilot.States.DEBOARDING);

        //System.out.println("    [!] Set destinanion flag at TRUE");

        notifyAll();

        //System.out.println("PILOT: Waiting for all passengers to leave the plane");
        while ( nPassengers != 0)
        {
            try{
                wait();
            }catch (InterruptedException ignored){}
        }

        ((Pilot) Thread.currentThread()).setPilotState(Pilot.States.FLYING_BACK);
        //generalRep.writeLog("Returning");
        //generalRep.setPilotState(Pilot.States.FLYING_BACK);

        //System.out.println("\n\n    [!] Set destinanion flag at FALSE");
    }
}
