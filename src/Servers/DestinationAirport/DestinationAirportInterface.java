package Servers.DestinationAirport;

import Common.*;
import Servers.Common.*;
import Servers.DepartureAirport.DepartureAirportClientProxy;

import static Common.MessageType.OK;


public class DestinationAirportInterface{

    private final DestinationAirport destinationAirport;

    public DestinationAirportInterface(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Message handleRequest(Message request) {


        System.out.println("Received request: " + request.getType());

        Message reply = new Message();
        reply.setType(OK);

        DestinationAirportClientProxy proxy = (DestinationAirportClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());


        switch (request.getType()){
            case LEAVE_PLANE:
                proxy.setPassId(request.getId());
                destinationAirport.leaveThePlane();
                break;
            case ANNOUNCE_ARRIVAL:
                destinationAirport.announceArrival(request.getInt1());
                break;
        }

        reply.setState(proxy.getEntityState());

        return reply;

    }

}
