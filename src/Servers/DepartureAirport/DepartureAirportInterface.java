package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.*;

import static Common.MessageType.OK;


public class DepartureAirportInterface{

    private final DepartureAirport departureAirport;

    public DepartureAirportInterface(DepartureAirport departureAirport){

        this.departureAirport = departureAirport;
    }

    public Message handleRequest(Message request) {
        //System.out.println("Received request: " + request.getType());
        //System.out.println("Received Client State: " + request.getState() );

        Message reply = new Message();
        reply.setType(OK);

        DepartureAirportClientProxy proxy = (DepartureAirportClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());

        switch (request.getType()){
            case WAIT_IN_QUEUE:
                proxy.setPassId(request.getId());
                departureAirport.waitInQueue();
                break;
            /*case SHOW_DOCUMENTS:
                proxy.setPassId(request.getId());
                departureAirport.showDocuments();
                break;*/
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
