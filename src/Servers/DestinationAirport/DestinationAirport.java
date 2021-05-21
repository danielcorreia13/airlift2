package Servers.DestinationAirport;

import Servers.Common.*;
import static Common.States.Passenger.*;
import static Common.States.Pilot.*;
import static Common.States.Hostess.*;


/**
 * Shared region : Destination Airport
 */
public class DestinationAirport
{
    /**
     * Reference to general repository
     */
    private final GeneralRep generalRep;

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
     *    @param repos reference to the general repository
     */
    public DestinationAirport(GeneralRep repos)
    {
        this.generalRep = repos;
        this.nPassengers = 0;
        totalPassengers = 0;
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
	public synchronized int leaveThePlane(int passId) {

		//System.out.println("PASSENGER " + passId + ": Left the plane");
		int state = AT_DESTINATION;
		generalRep.setPassengerState(passId, AT_DESTINATION);
        nPassengers--;
        totalPassengers++;
        //System.out.println(nPassengers);
        if (nPassengers == 0) 
        {
            System.out.println("        PASSENGER : " + passId + " Was the last to left the plane, notify the pilot");
            notifyAll();
        }
        return state;
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
    public synchronized int announceArrival(int nPass) {

        nPassengers = nPass;
        
        //System.out.println("PILOT: Plane arrived at destination");
        int state = DEBOARDING;
        generalRep.writeLog("Arrived");
        generalRep.setPilotState(DEBOARDING);
        
        //System.out.println("    [!] Set destinanion flag at TRUE");
        
        notifyAll();
        
        //System.out.println("PILOT: Waiting for all passengers to leave the plane");
        while ( nPassengers != 0) 
        {
            try{          	
                wait();
            }catch (InterruptedException ignored){}
        }

        state = FLYING_BACK;
        generalRep.writeLog("Returning");
        generalRep.setPilotState(FLYING_BACK);

        //System.out.println("\n\n    [!] Set destinanion flag at FALSE");
        return state;
    }
}
