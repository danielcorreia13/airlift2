package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.ServerThread;
import Servers.Common.ServerProxy;

public class DepartureAirportProxy implements ServerProxy {

    public DepartureAirportProxy(){

    }


    @Override
    public Message handleRequest(Message request) {
        ServerThread thread = (ServerThread) Thread.currentThread();

        System.out.print("Received request: " + request.getType());

        Message reply = new Message();

        switch (request.getType()){
            case WAIT_IN_QUEUE:

                break;
            case SHOW_DOCUMENTS:

                break;
            case WAIT_FOR_NEXT_PASSENGER:

                break;
            case CHECK_DOCUMENTS:

                break;
            case WAIT_FOR_NEXT_FLIGHT:

                break;
            case INFORM_PLANE_READY_BOARDING:

                break;
            case PARK_AT_TRANSFER_GATE:

                break;
        }

        return reply;

    }
}
