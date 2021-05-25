package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.*;

import static Common.MessageType.OK;

/**
 *  Interface to the Departure Airport.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Departure Airport and generate the outgoing message.
 */

public class DepartureAirportInterface{

    /**
     *  Reference to the Departure Airport.
     */

    private final DepartureAirport departureAirport;

    /**
     *  Instantiation of an interface to the Departure Airport.
     *
     *    @param departureAirport reference to the barber shop
     */

    public DepartureAirportInterface(DepartureAirport departureAirport){

        this.departureAirport = departureAirport;
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

        DepartureAirportClientProxy proxy = (DepartureAirportClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());

        switch (request.getType()){
            case WAIT_IN_QUEUE:
                proxy.setPassId(request.getId());
                departureAirport.waitInQueue();
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                departureAirport.waitForNextPassenger();
                break;
            case CHECK_DOCUMENTS:
                departureAirport.checkDocuments();
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                departureAirport.waitForNextFlight();
                break;
            case INFORM_PLANE_READY_BOARDING:
                departureAirport.informPlaneReadyForBoarding();
                break;
            case PARK_AT_TRANSFER_GATE:
                departureAirport.parkAtTransferGate();
                break;
            case GET_N_PASSENGERS:
                reply.setInt1(departureAirport.getnPassengers());
                break;
            case SHUT:
                departureAirport.shutdown();
                break;
        }

        reply.setState(proxy.getEntityState());

        return reply;
    }
}
