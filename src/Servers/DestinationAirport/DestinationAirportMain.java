package Servers.DestinationAirport;

import Client.entities.Passenger;

import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

public class DestinationAirportMain {

    public static void main(String[] args)
    {
        GeneralRepStub generalRepStub = new GeneralRepStub();

        DestinationAirport destinationAirport = new DestinationAirport(generalRepStub);

        DestinationAirportInterface destinationAirportInterface = new DestinationAirportInterface(destinationAirport);

        ServerCom scon, sconi;

        scon =  new ServerCom(RunParameters.DestinationAirportPort);
        scon.start();
        DestinationAirportClientProxy proxy;
        while(true){
            try{
                sconi = scon.accept ();
                proxy = new DestinationAirportClientProxy(sconi,destinationAirportInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
    }

}
