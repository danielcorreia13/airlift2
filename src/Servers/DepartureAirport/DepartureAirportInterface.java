package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.*;


public class DepartureAirportInterface{

    private final DepartureAirport departureAirport;

    public DepartureAirportInterface(DepartureAirport departureAirport){

        this.departureAirport = departureAirport;
    }

    public Message handleRequest(Message request) {
        System.out.print("Received request: " + request.getType());

        Message reply = new Message();
        DepartureAirportClientProxy proxy = (DepartureAirportClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());
        switch (request.getType()){
            case WAIT_IN_QUEUE:
                proxy.setPassId(request.getId());
                departureAirport.waitInQueue();
                break;
            case SHOW_DOCUMENTS:
                proxy.setPassId(request.getId());
                departureAirport.showDocuments();
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
        }
        reply.setState(proxy.getEntityState());

        return reply;

    }
}
