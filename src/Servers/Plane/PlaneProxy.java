package Servers.Plane;

import Common.Message;
import Servers.Common.ServerProxy;
import Servers.DepartureAirport.DepartureAirport;

public class PlaneProxy implements ServerProxy {

    private final Plane plane;

    public PlaneProxy(Plane plane) {
        this.plane = plane;
    }

    @Override
    public Message handleRequest(Message request) {
        System.out.print("Received request: " + request.getType());

        Message reply = new Message();
        int state = -1;
        switch (request.getType()){
            case INFORM_PLANE_READY_TAKEOFF:
                state = plane.informPlaneIsReadyToTakeOff(request.getId());
                break;
            case SET_AT_DESTINATION:
                plane.setAtDestination(request.getBool1());
                break;
            case WAIT_END_FLIGHT:
                plane.waitForEndOfFlight();
                break;
            case BOARD_THE_PLANE:
                state = plane.boardThePlane(request.getId());
                break;
            case WAIT_FOR_ALL_IN_BOARD:
                int[] ret = plane.waitForAllInBoard();
                state = ret[1];
                reply.setInt1(ret[0]);
                break;
        }
        if (state != -1){
            reply.setState(state);
        }

        return reply;

    }

}
