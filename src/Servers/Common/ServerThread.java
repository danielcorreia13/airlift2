package Servers.Common;

import Common.Message;
import Servers.Common.ServerCom;
import Servers.Common.ServerProxy;

public class ServerThread extends Thread{

    private final ServerCom conn;

    private final ServerProxy proxy;

    private int Passid;

    private int nPassengers;

    public ServerThread(String name, ServerCom conn, ServerProxy proxy) {
        super(name);
        this.conn = conn;
        this.proxy = proxy;
    }

    private int Entitystate;


    public int getnPassengers() {
        return nPassengers;
    }

    public int getEntitystate() {
        return Entitystate;
    }

    public void setEntitystate(int entitystate) {
        Entitystate = entitystate;
    }

    public int getPassid() {
        return Passid;
    }

    public void setPassid(int passid) {
        Passid = passid;
    }

    public void setnPassengers(int nPassengers) {
        this.nPassengers = nPassengers;
    }

    @Override
    public void run() {
        Message msg = conn.readMessage();
        if (msg == null) return;
        Message send = proxy.handleRequest(msg);
        conn.writeMessage(send);
        conn.close();
    }
}
