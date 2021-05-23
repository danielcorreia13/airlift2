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
            System.out.println("----------------Travel to airport" + getpId());
            travelToAirport();
            sleep(30);
            System.out.println("-----------------Wait in Queue" + getpId());
            depAirStub.waitInQueue();
            //depAirStub.showDocuments();
            System.out.println("-----------------Board the plane" + getpId());
            planeStub.boardThePlane();
//            sleep(450);
            System.out.println("-----------------Waiting" + getpId());
            planeStub.waitForEndOfFlight();
            System.out.println("-----------------Leave  the plane" + getpId());
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
            sleep((long) (1 + 250 * Math.random()));
        } catch (InterruptedException ignored) {
        }
    }

}
