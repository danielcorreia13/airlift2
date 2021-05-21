package Servers.DestinationAirport;

import Common.*;
import Servers.Common.*;


public class DestinationAirportProxy implements ServerProxy {

    private final DestinationAirport destinationAirport;

    public DestinationAirportProxy(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    @Override
    public Message handleRequest(Message request) {


        System.out.print("Received request: " + request.getType());

        Message reply = new Message();
        int state = -1;
        switch (request.getType()){
            case LEAVE_PLANE:
                state = destinationAirport.leaveThePlane(request.getId());
                break;
            case ANNOUNCE_ARRIVAL:
                state = destinationAirport.announceArrival(request.getId());
                break;
        }
        if (state != -1){
            reply.setState(state);
        }

        return reply;

    }

}
