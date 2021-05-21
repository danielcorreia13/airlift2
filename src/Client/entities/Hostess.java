package Client.entities;

import Client.stubs.DepartureAirportStub;
import Client.stubs.PlaneStub;
import Common.RunParameters;


/**
 *   Passenger thread.
 *
 *   It simulates the hostess life cycle.
 *
 */
public class Hostess extends Thread
{

    /**
     *  Reference to Departure Airport
     */
    private final DepartureAirportStub depAirStub;

    /**
     *  Reference to Plane
     */
    private final PlaneStub planeStub;


    /**
     *  Hostess state
     */
    private int hState;

    /**
     * Hostess constructor
     * Creates a Hostess thread and initializes it's parameters
     * @param name name of the thread
     * @param depAir Reference to Departure Airport
     * @param plane Reference to Plane
     */
    public Hostess(String name, DepartureAirportStub depAir, PlaneStub plane)
    {
        super(name);
        this.depAirStub = depAir;
        this.planeStub = plane;
        this.hState = Hostess.States.WAIT_FOR_NEXT_FLIGHT;
    }

    /**
     * Get Hostess state
     * @return hostess current state
     */
    public int gethState() {
        return hState;
    }

    /**
     * Ste Hostess state
     * @param hState new state
     */
    public void sethState(int hState)
    {
        this.hState = hState;

    }

    /**
     * Main loop of the hostess that runs it's life cycle
     */
    @Override
    public void run()
    {
        int count = 0;
        do {
            depAirStub.waitForNextFlight();

            prepareForPassBoarding();
            int max = RunParameters.maxPassengers;
            int min = RunParameters.minPassengers;
            while (true) {
                if(depAirStub.getnPassengers() == max) {
//                    System.out.println("HOSTESS: Plane full, informing pilot");
                    break;
                }
                if(depAirStub.empty() && depAirStub.getnPassengers() > min){
//                    System.out.println("HOSTESS: No passengers waiting, informing pilot");
                    break;
                }
                if(count == RunParameters.nPassengers){
//                    System.out.println("HOSTESS: Last passenger boarding, informing pilot");
                    break;
                }
                count++;
                depAirStub.waitForNextPassenger();
                depAirStub.checkDocuments();
            }
            planeStub.informPlaneIsReadyToTakeOff(depAirStub.getnPassengers());
        }while (count < RunParameters.nPassengers);
    }

    /**
     * Hostess prepares for pass boarding
     *
     * The thread is put to sleep for a random amount of time
     */
    public void prepareForPassBoarding()
    {
//    	System.out.println("HOSTESS: Preparing for pass board");
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException ignored) {}


    }

    /**
     *    Definition of the internal states of the hostess during his life cycle.
     */
    public static final class States
    {

        /**
         *   The hostess waits for the next flight.
         */
        public static final int WAIT_FOR_NEXT_FLIGHT = 0;

        /**
         *   The hostess waits for a passenger to arrive.
         */
        public static final int WAIT_FOR_PASSENGER = 1;

        /**
         *   The hostess checks the passenger's documents.
         */
        public static final int CHECK_PASSENGER = 2;

        /**
         *   The hostess tells the pilot that all the passengers have boarded the plane.
         */
        public static final int READY_TO_FLY = 3;

    }
}

