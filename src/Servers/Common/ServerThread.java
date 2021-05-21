package Servers.Common;

import Common.Message;
import Servers.Common.ServerCom;
import Servers.Common.ServerProxy;

public class ServerThread extends Thread{

    private final ServerCom conn;

    private final ServerProxy proxy;


    public ServerThread(String name, ServerCom conn, ServerProxy proxy) {
        super(name);
        this.conn = conn;
        this.proxy = proxy;
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
