package Servers.Plane;

import Common.Message;
import Servers.DepartureAirport.DepartureAirportClientProxy;

public class PlaneInterface{

    private final Plane plane;

    public PlaneInterface(Plane plane) {
        this.plane = plane;
    }

    public Message handleRequest(Message request) {
        System.out.print("Received request: " + request.getType());

        Message reply = new Message();

        PlaneClientProxy proxy = (PlaneClientProxy) Thread.currentThread();
        proxy.setEntityState(request.getState());

        switch (request.getType()){
            case INFORM_PLANE_READY_TAKEOFF:
                plane.informPlaneIsReadyToTakeOff(request.getInt1());
                break;
            case SET_AT_DESTINATION:
                plane.setAtDestination(request.getBool1());
                break;
            case WAIT_END_FLIGHT:
                plane.waitForEndOfFlight();
                break;
            case BOARD_THE_PLANE:
                plane.boardThePlane();
                break;
            case WAIT_FOR_ALL_IN_BOARD:
                int ret = plane.waitForAllInBoard();
                reply.setInt1(ret);
                break;
        }

        reply.setState(proxy.getEntityState());

        return reply;

    }

}
