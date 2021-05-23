package Client.entities;

import Client.stubs.DepartureAirportStub;
import Client.stubs.DestinationAirportStub;
import Client.stubs.PlaneStub;

/**
 *   Passenger thread.
 *
 *   It simulates the passenger life cycle.
 *
 */
public class Passenger extends Thread {
    /**
     * Reference to Departure Airport
     */

    private final DepartureAirportStub depAirStub;

    /**
     * Reference to Destination Airport
     */
    private final DestinationAirportStub destAirStub;

    /**
     * Reference to Plane
     */
    private final PlaneStub planeStub;


    /**
     * Passenger identification
     */
    private final int pId;


    /**
     * Passenger state
     */
    private int pState;


    /**
     * Instantiation of a Passenger thread.
     *
     * @param name    thread name
     * @param id      passenger id
     * @param depAirStub  reference to the Departure Airport
     * @param destAirStub reference to the Destination Airport
     * @param planeStub   reference to the Plane
     */
    public Passenger(String name, int id, DepartureAirportStub depAirStub, DestinationAirportStub destAirStub, PlaneStub planeStub)
    {
        super(name);
        this.depAirStub = depAirStub;
        this.destAirStub = destAirStub;
        this.planeStub = planeStub;
        this.pState = Common.States.Passenger.GOING_TO_AIRPORT;
        this.pId = id;
    }

    /**
     * Operation to get the passenger state
     *
     * @return int
     */
    public int getpState() {
        return pState;
    }

    /**
     * Operation to set the passenger state
     *
     * @param state new state
     */
    public void setpState(int state)
    {
        pState = state;
    }

    /**
     * Operation to get the passenger identification
     *
     * @return int
     */
    public int getpId() {
        return pId;
    }
    /**
     * Life cycle of the passenger.
     */

    @Override
    public void run() {
        try {
            travelToAirport();
            sleep(300);
            depAirStub.waitInQueue();
            depAirStub.showDocuments();
            planeStub.boardThePlane();
            sleep(450);
            planeStub.waitForEndOfFlight();

            destAirStub.leaveThePlane();
        } catch (InterruptedException e) {
            System.out.print("Something went wrong");
        }
    }

    /**
     * Going to airport.
     * <p>
     * Puts the thread a sleep for a random amount of time
     */
    public void travelToAirport()
    {
        try {
            sleep((long) (1 + 2500 * Math.random()));
        } catch (InterruptedException ignored) {
        }
    }


    /**
     * Definition of the internal states of the passenger during his life cycle.
     */
    public static final class States {

        /**
         * The customer takes the bus to go to the departure airport.
         */
//        public static final int GOING_TO_AIRPORT = 0;

        /**
         * The customer queue at the boarding gate waiting for the flight to be announced.
         */
//        public static final int IN_QUEUE = 1;

        /**
         * The customer flies to the destination airport.
         */
//        public static final int IN_FLIGHT = 2;

        /**
         * The customer arrives at the destination airport, disembarks and leaves the airport.
         */
//        public static final int AT_DESTINATION = 3;

    }
}
