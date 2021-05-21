package Common;

import java.util.Date;

public class RunParameters
{
    /**
     *  Number of passengers to be transported
     */
    public static final int nPassengers = 21;

    /**
     *  Minimum number of passengers to be transported in the plane
     */
    public static final int minPassengers = 5;

    /**
     *  Maximum number of passengers to be transported in the plane
     */
    public static final int maxPassengers = 10;

    /**
     * Log filename
     */
    public static final String logFile = "log_" + new Date().toString().replace(' ', '_') + ".txt";

    /**
     * Departure Airport Port
     */
    public static final int DepartureAirportPort = 22400;

    /**
     * Departure Airport Hostname
     */
    public static final String DepartureAirportHostName = "localhost";

    /**
     * Destination Airport Port
     */
    public static final int DestinationAirportPort = 22401;

    /**
     * Destination Airport Hostname
     */
    public static final String DestinationAirportHostName = "localhost";

    /**
     * Plane Port
     */
    public static final int PlanePort = 22402;

    /**
     * Plane Hostname
     */
    public static final String PlaneHostName = "localhost";

    /**
     * Repository Port
     */
    public static final int RepositoryPort = 22403;

    /**
     * Repository Hostname
     */
    public static final String RepositoryHostName = "localhost";
}
