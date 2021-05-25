package Servers.DepartureAirport;



import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

/**
 *    Server side of the Departure Airport Main
 */

public class DepartureAirportMain {

    /**
     * Flag to signal server shutdown
     */
    public static boolean shutdown;

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     */

    public static void main(String[] args)
    {
        GeneralRepStub generalRepStub = new GeneralRepStub();
        DepartureAirport departureAirport = new DepartureAirport(generalRepStub);
        DepartureAirportInterface departureAirportInterface = new DepartureAirportInterface(departureAirport);
        ServerCom scon, sconi;
        scon =  new ServerCom(RunParameters.DepartureAirportPort);
        scon.start();
        DepartureAirportClientProxy proxy;
        shutdown = false;

        while(!shutdown)
        {
            try{
                sconi = scon.accept ();
                proxy = new DepartureAirportClientProxy(sconi,departureAirportInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
        generalRepStub.endReport();
    }
}
