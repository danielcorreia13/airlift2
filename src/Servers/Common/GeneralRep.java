package Servers.Common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

public interface GeneralRep {

    /**
     *   Set Passenger state.
     *
     *     @param id passenger id
     *     @param state passenger state
     */
    public void setPassengerState (int id, int state) ;

    /**
     *   Set pilot state.
     *
     *     @param state pilot state
     */
    public void setPilotState (int state) ;

    /**
     *  Set hosstess state
     *
     *	@param state hostess state
     */
    public void setHostess (int state) ;

    /**
     *  Increment next id flight
     */
    public void nextFlight();

    /**
     *  Write to the logging file, called by another instance.
     *
     *	@param msg message to write in log
     */
    public void writeLog(String msg) ;

    /*                                     STATUS                                    */
    /*-------------------------------------------------------------------------------*/


    /**
     *  Close the logging file
     */
    public void endReport() ;
}
