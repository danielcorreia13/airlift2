package Servers.Plane;

import Common.Message;
import static Common.MessageType.*;

/**
 *  Interface to the Plane.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Destination Airport and generate the outgoing message.
 */

public class PlaneInterface{

    /**
     *  Reference to the Plane.
     */

    private final Plane plane;

    /**
     *  Instantiation of an interface to the Plane.
     *
     *    @param plane reference to the barber shop
     */

    public PlaneInterface(Plane plane)
    {
        this.plane = plane;
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
        PlaneClientProxy proxy = (PlaneClientProxy) Thread.currentThread();
        proxy.setPassId(request.getId());
        proxy.setEntityState(request.getState());

        switch (request.getType()){
            case INFORM_PLANE_READY_TAKEOFF:
                plane.informPlaneIsReadyToTakeOff(request.getInt1());
                break;
            case SET_AT_DESTINATION:
//                System.out.println("DESTINATION_FLAG:" + request.getBool1() );
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
            case SHUT:
                plane.shutdown();
            break;
        }

        reply.setState(proxy.getEntityState());
        return reply;
    }
}
