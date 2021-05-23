package Servers.GenrealRep;

import Common.Message;
import Servers.Common.ServerCom;

public class GeneralRepClientProxy extends Thread{

    private final ServerCom conn;

    private final GeneralRepInterface generalRep;


    public GeneralRepClientProxy(String name, ServerCom conn, GeneralRepInterface generalRep) {
        super(name);
        this.conn = conn;
        this.generalRep = generalRep;
    }

    @Override
    public void run() {
        Message msg = conn.readMessage();
        if (msg == null) return;
        Message send = generalRep.handleRequest(msg);
        conn.writeMessage(send);
        conn.close();
    }
}
