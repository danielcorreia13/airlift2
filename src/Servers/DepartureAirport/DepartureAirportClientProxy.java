package Servers.DepartureAirport;

import Common.Message;
import Servers.Common.ServerCom;

public class DepartureAirportClientProxy extends Thread{

    private final ServerCom conn;

    private final DepartureAirportInterface departureAirport;

    /**
     *  Id do passageiro
     */

    private int passId;

    /**
     *  Estado da entidade que este proxy representa
     */

    private int entityState;

    /**
     *  @return Id do passageiro
     */

    public int getPassId() {
        return passId;
    }

    /**
     *  Atribuir Id do passageiro
     */

    public void setPassId(int passId) {
        this.passId = passId;
    }

    /**
     *  @return Estado da entidade
     */

    public int getEntityState() {
        return entityState;
    }

    /**
     *  Atribuir Estado da entidade
     */

    public void setEntityState(int entityState) {
        this.entityState = entityState;
    }


    public DepartureAirportClientProxy(String name, ServerCom conn, DepartureAirportInterface departureAirport) {
        super(name);
        this.conn = conn;
        this.departureAirport= departureAirport;
    }

    @Override
    public void run() {
        Message msg = conn.readMessage();
        if (msg == null) return;
        Message send = departureAirport.handleRequest(msg);
        conn.writeMessage(send);
        conn.close();
    }
}
