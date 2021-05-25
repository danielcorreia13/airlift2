package Servers.GeneralRep;

import Common.*;


import static Common.MessageType.OK;

/**
 *  Interface to the General Repository.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 */

public class GeneralRepInterface{

    /**
     *  Reference to the General Repository.
     */

    private final GeneralRep generalRep;

    /**
     *  Instantiation of an interface to the General Repository.
     *
     *    @param generalRep reference to the barber shop
     */

    public GeneralRepInterface(GeneralRep generalRep) {
        this.generalRep = generalRep;
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

        switch (request.getType()){
            case PASSENGER_STATE:
                generalRep.setPassengerState(request.getId(), request.getState());
                break;
            case PILOT_STATE:
                generalRep.setPilotState(request.getState());
                break;
            case HOSTESS_STATE:
                generalRep.setHostess(request.getState());
                break;
            case NEXT_FLIGHT:
                generalRep.nextFlight();
                break;
            case WRITE_LOG:
                generalRep.writeLog(request.getStr());
                break;
            case END_REPORT:
                generalRep.endReport();
                break;
        }

        return reply;
    }
}
