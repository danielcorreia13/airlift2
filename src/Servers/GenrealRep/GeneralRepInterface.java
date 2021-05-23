package Servers.GenrealRep;

import Common.*;
import Servers.Common.*;

import static Common.MessageType.OK;


public class GeneralRepInterface{

    private final GeneralRep generalRep;

    public GeneralRepInterface(GeneralRep generalRep) {
        this.generalRep = generalRep;
    }


    public Message handleRequest(Message request) {

        //System.out.println("Received request: " + request.getType());
        //System.out.println("ID:" + request.getId());

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
                System.out.println("GETSTR: " + request.getStr());
                generalRep.writeLog(request.getStr());
                break;
            case END_REPORT:
                generalRep.endReport();
                break;

        }

        return reply;

    }

}
