package Servers.GenrealRep;

import Client.entities.Passenger;

import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

public class GeneralRepMain {

    public static void main(String[] args)
    {
        GeneralRep generalRep = new GeneralRep("LOG_FILE.txt");

        GeneralRepInterface generalRepInterface = new GeneralRepInterface(generalRep);

        ServerCom scon, sconi;

        scon =  new ServerCom(RunParameters.RepositoryPort);
        scon.start();
        GeneralRepClientProxy proxy;
        while(true){
            try{
                sconi = scon.accept ();
                proxy = new GeneralRepClientProxy(sconi,generalRepInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
    }

}