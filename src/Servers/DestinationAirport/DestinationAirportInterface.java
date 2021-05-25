package Servers.DestinationAirport;

import Common.*;

import static Common.MessageType.OK;

/**
 *  Interface to the Destination Airport.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Destination Airport and generate the outgoing message.
 */

public class DestinationAirportInterface{

    /**
     *  Reference to the Destination Airport.
     */

    private final DestinationAirport destinationAirport;

    /**
     *  Instantiation of an interface to the Destination Airport.
     *
     *    @param destinationAirport reference to the barber shop
     */

    public DestinationAirportInterface(DestinationAirport destinationAirport)
    {
        this.destinationAirport = destinationAirport;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param request service request
     *    @return service reply
     */

    public Message handleRequest(Message request)
    {
        Message reply = new Message();
        reply.setType(OK);

        DestinationAirportClientProxy proxy = (DestinationAirportClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());

        switch (request.getType())
        {
            case LEAVE_PLANE:
                proxy.setPassId(request.getId());
                destinationAirport.leaveThePlane();
                break;
            case ANNOUNCE_ARRIVAL:
                destinationAirport.announceArrival(request.getInt1());
                break;
            case GET_TOTAL_PASSENGERS:
                reply.setInt1(destinationAirport.getTotalPassengers());
                break;
        }

        reply.setState(proxy.getEntityState());
        return reply;
    }
}
