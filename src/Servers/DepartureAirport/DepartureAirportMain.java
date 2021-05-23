package Servers.DepartureAirport;

import Client.entities.Passenger;

import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

public class DepartureAirportMain {

    public static void main(String[] args)
    {
        GeneralRepStub generalRepStub = new GeneralRepStub();

        DepartureAirport departureAirport = new DepartureAirport(generalRepStub);

        DepartureAirportInterface departureAirportInterface = new DepartureAirportInterface(departureAirport);

        ServerCom scon, sconi;

        scon =  new ServerCom();

        while(true){



        }
    }

}
