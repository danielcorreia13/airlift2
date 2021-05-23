package Client;

import Client.entities.Passenger;
import Client.stubs.DepartureAirportStub;
import Client.stubs.DestinationAirportStub;
import Client.stubs.PlaneStub;
import Common.RunParameters;

/**
 * Passenger Main
 */
public class PassengerMain
{
    /**
     *  Reference to Departure Airport
     */
    private static DepartureAirportStub departureAirportStub;

    /**
     *  Reference to Destination Airport
     */
    private static DestinationAirportStub destinationAirportStub;

    /**
     *  Reference to Plane
     */
    private static PlaneStub planeStub;

    /**
     *  Reference to the general repository of information
     */
    //private final GeneralRep generalRep;


    /**
     *  References to Passengers
     */
    private static Passenger[] passenger;

    public static void main(String[] args)
    {
        departureAirportStub = new DepartureAirportStub();
        destinationAirportStub = new DestinationAirportStub();
        planeStub = new PlaneStub();

        passenger = new Passenger[Common.RunParameters.nPassengers];

        for (int i = 0; i < RunParameters.nPassengers; i++)
        {
            passenger[i] = new Passenger( "Passenger" + i, i, departureAirportStub, destinationAirportStub, planeStub);
        }

        for (Passenger p : passenger){

            p.start();
        }

        try {
            for (Passenger p : passenger)
            {
                p.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
        }
    }
}