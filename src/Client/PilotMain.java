package Client;

import Client.entities.Pilot;
import Client.stubs.DepartureAirportStub;
import Client.stubs.DestinationAirportStub;
import Client.stubs.PlaneStub;

/**
 * Pilot Main
 */
public class PilotMain
{
    /**
     *  Reference to Departure Airport Stub
     */
    private static DepartureAirportStub departureAirportStub;

    /**
     *  Reference to Destination Airport Stub
     */
    private static DestinationAirportStub destinationAirportStub;

    /**
     *  Reference to Plane Stub
     */
    private static PlaneStub planeStub;

    /**
     *  Reference to Pilot
     */
    private static Pilot pilot;

    /**
     * Pilot Main function
     *
     * @param args
     */
    public static void main(String[] args)
    {
        departureAirportStub = new DepartureAirportStub();
        destinationAirportStub = new DestinationAirportStub();
        planeStub = new PlaneStub();
        pilot = new Pilot("pilot1", departureAirportStub, destinationAirportStub, planeStub);

        pilot.start();

        try
        {
            pilot.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Something went wrong");
        }
    }
}