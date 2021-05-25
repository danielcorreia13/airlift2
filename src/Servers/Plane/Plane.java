package Servers.Plane;

import Client.stubs.GeneralRepStub;

import javax.swing.plaf.synth.SynthTabbedPaneUI;

import static Common.States.Passenger.*;
import static Common.States.Pilot.*;
import static Common.States.Hostess.*;


/**
 * Server side, Shared region : Plane
 */
public class Plane
{
    /**
     * Reference to general repository
     */
    private final GeneralRepStub generalRep;

    /**
     * All passengers on board flag
     */
    private boolean allInBoard;

    /**
     * Number of passengers flag 
     */
    private int nPassengers;

    /**
     * Plane at destination flag
     */
    private boolean atDestination;

    /*                                 CONSTRUCTOR                                   */
    /*-------------------------------------------------------------------------------*/

    /**
     *  Plane instantiation.
     *
     *    @param repos reference to the general repository
     */
    public Plane (GeneralRepStub repos)
    {
        generalRep = repos;
        this.allInBoard = false;
        setAtDestination(false);
        this.nPassengers = 0;
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
     */
    public synchronized void informPlaneIsReadyToTakeOff(int nPass)
    {
        while (nPassengers != nPass){
            try{
                wait();
            }catch (InterruptedException ignored){}
        }

        allInBoard = true;

        ((PlaneClientProxy) Thread.currentThread()).setEntityState(READY_TO_FLY);
        generalRep.setHostess(READY_TO_FLY);
        generalRep.writeLog("Departed with " + nPass + " passengers");
//        System.out.println("Departed with " + nPass + " passengers");
//        System.out.println("HOSTESS->PILOT: Plane is ready for takeoff");

        notifyAll();

    }


    /**
     *  Operation to set plane at destination
     *
     * It is called by pilot on destination point
     *
     * @param atDestination new value
     */
    public synchronized void setAtDestination(boolean atDestination)
    {
        this.atDestination = atDestination;
        notifyAll();
    }

    /**
     *  Operation inform that plane is at destination
     *
     *  It is called by the pilot to notify passengers that the plane was landed
     *
     * @return true if at destination
     */
    public synchronized boolean isAtDestination()
    {
        return this.atDestination;
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
    public synchronized void waitForEndOfFlight()
    {
    	int passId = ((PlaneClientProxy) Thread.currentThread()).getPassId();

        while ( !isAtDestination() )
        {
            try {
//                System.out.println(" [!] PASSENGER "+ passId +": Waiting... ");
                wait();
            } catch (InterruptedException ignored) {}
        }

//        notifyAll();

    }

    /**
     *  Operation inform that the passenger as entered the plane
     *
     *  It is called by the PASSENGER when enters the plane
     *
     *
     */
    public synchronized void boardThePlane()
    {
//        System.out.println("BOARD THE PLANE");
        int passId = ((PlaneClientProxy) Thread.currentThread()).getPassId();
        ((PlaneClientProxy) Thread.currentThread()).setEntityState(IN_FLIGHT);
        generalRep.setPassengerState(passId, IN_FLIGHT);
//        System.out.println("PASSENGER "+passId+ ": Seated on plane");
        nPassengers++;

        notifyAll();
    }


    //                                   PILOT                                      //
    //--------------------------------------------------------------------------------

    /**
     *  Operation inform that PILOT is waiting for the hostess signal, indicating that all passengers are on board.
     *
     *  It is called by the PILOT while waiting for all passengers on board
     *
     *    @return void
     */
    public synchronized int waitForAllInBoard()
    {
        //System.out.println("PILOT: Waiting for all passengers on board");
        nPassengers = 0;

        ((PlaneClientProxy) Thread.currentThread()).setEntityState(WAIT_FOR_BOARDING);
        generalRep.setPilotState(WAIT_FOR_BOARDING);
        try
        {
            while( !allInBoard)
                wait();
        }
        catch (InterruptedException ignored) {}
        allInBoard = false;
        ((PlaneClientProxy) Thread.currentThread()).setEntityState(FLYING_FORWARD);

        generalRep.setPilotState(FLYING_FORWARD);

        notifyAll();
//        System.out.println("FLIGHT DEPARTURE");
        return nPassengers;
    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        PlaneMain.shutdown = true;
        notifyAll ();                                        // the barber may now terminate
    }
}
