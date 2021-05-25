package Servers.DestinationAirport;

import Client.stubs.GeneralRepStub;
import Servers.Common.*;
import static Common.States.Passenger.*;
import static Common.States.Pilot.*;
import static Common.States.Hostess.*;


/**
 * Server side, Shared region : Destination Airport
 */
public class DestinationAirport
{
    /**
     * Reference to general repository
     */
    private final GeneralRepStub generalRep;

    /**
     * Number of passengers
     */
    private int nPassengers;

    /**
     * Number of total passengers
     */
    private int totalPassengers;

    /**
     * Number of passengers in last plane
     */
    private int expectedPassengers;



    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Destination Airport instantiation.
     *
     *    @param repos reference to the general repository
     */
    public DestinationAirport(GeneralRepStub repos)
    {
        this.generalRep = repos;
        this.nPassengers = 0;
        totalPassengers = 0;
        expectedPassengers = -1;
    }

    /**
     * Get number of total transported passengers
     *
     * @return number of total transported passengers
     */
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
     *
     */
    public synchronized void leaveThePlane() {
        int passId = ((DestinationAirportClientProxy) Thread.currentThread()).getPassId();

        while ( expectedPassengers == -1 )
        {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        ((DestinationAirportClientProxy) Thread.currentThread()).setEntityState(AT_DESTINATION);
        generalRep.setPassengerState(passId, AT_DESTINATION);
        this.nPassengers++;
        this.totalPassengers++;
        //System.out.println(nPassengers);
        if (nPassengers == expectedPassengers)
        {
            notifyAll();
        }

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
    public synchronized void announceArrival(int nPass) {

        expectedPassengers = nPass;
        notifyAll();


        ((DestinationAirportClientProxy) Thread.currentThread()).setEntityState(DEBOARDING);
        generalRep.writeLog("Arrived");
        generalRep.setPilotState(DEBOARDING);

        //System.out.println("    [!] Set destinanion flag at TRUE");

        while ( nPassengers != expectedPassengers)
        {
            try{
                wait();
            }catch (InterruptedException ignored){}
        }
        ((DestinationAirportClientProxy) Thread.currentThread()).setEntityState(FLYING_BACK);
        generalRep.writeLog("Returning");
        generalRep.setPilotState(FLYING_BACK);
        nPassengers = 0;
        expectedPassengers = -1;
        //System.out.println("\n\n    [!] Set destinanion flag at FALSE");

        notifyAll();
    }
}
