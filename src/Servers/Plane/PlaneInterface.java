package Servers.Plane;

import Common.Message;
import static Common.MessageType.*;

public class PlaneInterface{

    private final Plane plane;

    public PlaneInterface(Plane plane) {
        this.plane = plane;
    }

    public Message handleRequest(Message request) {
        //System.out.println("Received request: " + request.getType());

        Message reply = new Message();
        reply.setType(OK);
        PlaneClientProxy proxy = (PlaneClientProxy) Thread.currentThread();
        proxy.setPassId(request.getId());
        proxy.setEntityState(request.getState());

        switch (request.getType()){
            case INFORM_PLANE_READY_TAKEOFF:
                plane.informPlaneIsReadyToTakeOff(request.getInt1());
                break;
            case SET_AT_DESTINATION:
                System.out.println("DESTINATION_FLAG:" + request.getBool1() );
                plane.setAtDestination(request.getBool1());
                break;
            case WAIT_END_FLIGHT:
                plane.waitForEndOfFlight();
                break;
            case BOARD_THE_PLANE:
                //System.out.println("Calling board the plane");
                plane.boardThePlane();
                //System.out.println("board the plane returned");
                break;
            case WAIT_FOR_ALL_IN_BOARD:
                int ret = plane.waitForAllInBoard();
                reply.setInt1(ret);
                break;
            case SHUT:
                plane.shutdown();
            break;
        }

        reply.setState(proxy.getEntityState());
        //System.out.println("Replayed to request: " + request.getType());
        return reply;

    }

}
