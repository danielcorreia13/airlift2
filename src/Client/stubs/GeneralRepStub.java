package Client.stubs;

import Client.communications.ClientCom;
import Common.RunParameters;

import Common.Message;
import Common.MessageType;
import Client.communications.ClientCom;
import Common.RunParameters;

public class GeneralRepStub {

    public GeneralRepStub () { }

    private static ClientCom clientCom;

    /**
     *  Operation to performe a client communication with server
     *
     *  It is called by each method of this class
     *
     * @return ClientCom object
     */
    public ClientCom Communication()
    {
        clientCom = new ClientCom(RunParameters.PlaneHostName, RunParameters.PlanePort);

        while( !clientCom.open() )
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return clientCom;
    }


    /**
     *   Set Passenger state.
     *
     *     @param id passenger id
     *     @param state passenger state
     */
    public void setPassengerState (int id, int state) {
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.PASSENGER_STATE);
        pkt.setState(state);
        pkt.setId(id);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }

    /**
     *   Set pilot state.
     *
     *     @param state pilot state
     */
    public void setPilotState (int state) {
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.PILOT_STATE);
        pkt.setState(state);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }

    /**
     *  Set hosstess state
     *
     *	@param state hostess state
     */
    public void setHostess (int state) {
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.HOSTESS_STATE);
        pkt.setState(state);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }

    /**
     *  Increment next id flight
     */
    public void nextFlight(){
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.NEXT_FLIGHT);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }

    /**
     *  Write to the logging file, called by another instance.
     *
     *	@param msg message to write in log
     */
    public void writeLog(String msg) {
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.WRITE_LOG);
        pkt.setStr(msg);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }

    /*                                     STATUS                                    */
    /*-------------------------------------------------------------------------------*/


    /**
     *  Close the logging file
     */
    public void endReport() {
        clientCom = Communication();
        Message pkt = new Message();

        /* Send Message */
        pkt.setType(MessageType.END_REPORT);
        clientCom.writeObject(pkt);

        /* Receive Message */
        pkt = (Message) clientCom.readObject();

        clientCom.close();
    }
    
}