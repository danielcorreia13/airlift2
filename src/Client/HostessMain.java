package Client;

import Client.entities.Hostess;
import Client.stubs.DepartureAirportStub;
import Client.stubs.PlaneStub;
//import SharedRegions.GeneralRep;

/**
 * Hostess Main
 */
public class HostessMain
{
    /**
     *  Reference to Departure Airport Stub
     */
    private static DepartureAirportStub sharedDepartureAirportStub;

    /**
     *  Reference to Plane Stub
     */
    private static PlaneStub sharedPlaneStub;


    /**
     *  Reference to Hostess
     */
    private static Hostess hostess;

    /**
     * Hostess Main function
     *
     * @param args
     */
    public static void main(String[] args)
    {
        sharedDepartureAirportStub = new DepartureAirportStub();
        sharedPlaneStub = new PlaneStub();
        hostess = new Hostess("hostess1", sharedDepartureAirportStub, sharedPlaneStub);

        hostess.start();
        try
        {
            hostess.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Something went wrong");
        }
    }
}
