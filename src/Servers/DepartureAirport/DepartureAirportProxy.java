package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.*;


public class DepartureAirportProxy implements ServerProxy {

    private final DepartureAirport departureAirport;

    public DepartureAirportProxy(DepartureAirport departureAirport){

        this.departureAirport = departureAirport;
    }


    @Override
    public Message handleRequest(Message request) {
        System.out.print("Received request: " + request.getType());

        Message reply = new Message();
        int state = -1;
        switch (request.getType()){
            case WAIT_IN_QUEUE:
                state = departureAirport.waitInQueue(request.getId());
                break;
            case SHOW_DOCUMENTS:
                departureAirport.showDocuments(request.getId());
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                state = departureAirport.waitForNextPassenger();
                break;
            case CHECK_DOCUMENTS:
                state = departureAirport.checkDocuments();
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                state = departureAirport.waitForNextFlight();
                break;
            case INFORM_PLANE_READY_BOARDING:
                state = departureAirport.informPlaneReadyForBoarding();
                break;
            case PARK_AT_TRANSFER_GATE:
                departureAirport.parkAtTransferGate();
                break;
        }
        if (state != -1){
            reply.setState(state);
        }

        return reply;

    }
}
