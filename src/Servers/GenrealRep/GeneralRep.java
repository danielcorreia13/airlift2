package Servers.GenrealRep;




import Common.RunParameters;
import static Common.States.Passenger.*;
import static Common.States.Pilot.*;
import static Common.States.Hostess.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

/**
 * Shared region : General Repository of Information
 */
public class GeneralRep implements Servers.Common.GeneralRep
{
	/**
	*  Log file handler.
	*/
	private PrintWriter log;       

	/**
	*  State of the passengers.
	*/
	private final int [] passengerState;

	/**
	*  State of the hostess.
	*/
	private int hostessState;
	   
	   
	/**
	*  State of the hostess.
	*/
	private int pilotState;

	/**
	*  Current flight number
	*/
	private int flightId;
	
	int nPassenger;

	/**
	* Number of passengers per flight
	*/
	private final int[] nPassFlight;

	
	
	/*                                    CONSTRUCTOR                                */
	/*-------------------------------------------------------------------------------*/
	/**
	*   Instantiation of a general repository object.
	*
	*     @param logFileName name of the logging file
	*
	*/
	public GeneralRep (String logFileName)
	{
	   try {
	       if ((logFileName == null) || Objects.equals(logFileName, ""))
	    	   log = new PrintWriter("logger");
	       else
	           log = new PrintWriter(logFileName);
	   }catch (IOException e){
	       System.out.println ("The operation of creating the file " + logFileName + " failed!");
	       System.exit (1);
	   }

	  passengerState = new int [RunParameters.nPassengers];
	  for (int i = 0; i < RunParameters.nPassengers; i++)
	    passengerState[i] = GOING_TO_AIRPORT;

	  hostessState = WAIT_FOR_NEXT_FLIGHT;
	  pilotState = AT_TRANSFER_GATE;
	  flightId = 1;
	  nPassenger = 0;
	  nPassFlight = new int[10];
	  Arrays.fill(nPassFlight, 0);
	
	  reportInitialStatus ();
   }
	   
	   
    /*                                     SETTING STATES                            */
    /*-------------------------------------------------------------------------------*/	   

   /**
    *   Set Passenger state.
    *
    *     @param id passenger id
    *     @param state passenger state
    */
    public synchronized void setPassengerState (int id, int state)
    {
    	if (passengerState[id] != state)
    	{
	    	passengerState[id] = state;
	    	reportStatus ();
    	}
    }

   /**
    *   Set pilot state.
    *
    *     @param state pilot state
    */
    public synchronized void setPilotState (int state)
    {
    	if (pilotState != state)
    	{
    		pilotState = state;
    		reportStatus ();
    	}
    }
    
    /**
    *  Set hosstess state
    *  
    *	@param state hostess state
    */	    
    public synchronized void setHostess (int state)
    {
    	if (hostessState != state)
    	{
    		hostessState = state;
    		reportStatus ();
    	}
    }
    
    /**
    *  Increment next id flight
    */
    public synchronized void nextFlight(){
    		flightId++;
	}
    
    /**
    *  Write to the logging file, called by another instance.
    *  
    *	@param msg message to write in log
    */
	public synchronized void writeLog(String msg){
		System.out.println("FINAL:" + msg);
    	log.println("\nFlight " + flightId + ": " + msg);
	}
	    
    /*                                     STATUS                                    */
    /*-------------------------------------------------------------------------------*/

    /**
    *  Write the header to the logging file.
    *
    *  The hostess is waiting for passenger and pilot is sleeping waiting for passengers on board
    *  Passengers are going to airport
    *  Internal operation.
    */   
   private synchronized void reportInitialStatus ()
   {

      log.println ("\n\tAirlift - Description of the internal state:\n\n");
      log.println ("  PT    HT   P00   P01   P02   P03   P04   P05   P06   P07   P08   P09   P10   P11   P12   P13   P14   P15   P16   P17   P18   P19   P20   InQ  InF  PTAL");
      log.flush();
      reportStatus ();
   }
	   
   /**
    *  Write a state line at the end of the logging file.
    *
    *  The current state of the passengers, pilot and hostess is organized in a line to be printed.
    *  Internal operation.
    */	   
	private synchronized void reportStatus ()
	{
		int nPassQueue = 0;
		int nPassPlane = 0;
		int nPassArrived = 0;
		
		StringBuilder lineStatus = new StringBuilder();                              // state line to be printed

		switch (pilotState) {
			case AT_TRANSFER_GATE -> lineStatus.append(" ATRG ");
			case DEBOARDING -> lineStatus.append(" DRPP ");
			case FLYING_BACK -> lineStatus.append(" FLBK ");
			case FLYING_FORWARD -> lineStatus.append(" FLFW ");
			case READY_FOR_BOARDING -> lineStatus.append(" RDFB ");
			case WAIT_FOR_BOARDING -> lineStatus.append(" WTFB ");
		}

		switch (hostessState) {
			case CHECK_PASSENGER -> lineStatus.append(" CKPS ");
			case READY_TO_FLY -> lineStatus.append(" RDTF ");
			case WAIT_FOR_NEXT_FLIGHT -> lineStatus.append(" WTFL ");
			case WAIT_FOR_PASSENGER -> lineStatus.append(" WTFP ");
		}

		for (int i = 0; i < RunParameters.nPassengers; i++)
			switch (passengerState[i]) {
				case GOING_TO_AIRPORT -> lineStatus.append(" GTAP ");
				case AT_DESTINATION -> {
					lineStatus.append(" ATDS ");
					nPassArrived++;
				}
				case IN_FLIGHT -> {
					lineStatus.append(" INFL ");
					nPassPlane++;
				}
				case IN_QUEUE -> {
					lineStatus.append(" INQE ");
					nPassQueue++;
				}
			}

		lineStatus.append("  ").append(nPassQueue).append("    ").append(nPassPlane).append("    ").append(nPassArrived);
		if (hostessState == READY_TO_FLY){
			if (nPassPlane > nPassFlight[flightId-1])
				nPassFlight[flightId-1] = nPassPlane;
		}
		log.println (lineStatus);
		log.flush();
	}

    /**
     *  Close the logging file
     */
    public synchronized void endReport(){
    	log.println("\nAirlift sum up:");
		for (int i = 0; i < nPassFlight.length; i++) {
			if (nPassFlight[i] == 0) break;
			log.println("Flight " + (i+1) + " transported " + nPassFlight[i] + " passengers");
		}
        log.close();
		GeneralRepMain.shutdown = true;
		notifyAll();
    }
}
