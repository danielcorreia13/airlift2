package Servers.Plane;

import Client.entities.Passenger;

import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

public class PlaneMain {

    public static void main(String[] args)
    {
        GeneralRepStub generalRepStub = new GeneralRepStub();

        Plane plane = new Plane(generalRepStub);

        PlaneInterface planeInterface = new PlaneInterface(plane);

        ServerCom scon, sconi;

        scon =  new ServerCom(RunParameters.PlanePort);
        scon.start();
        PlaneClientProxy proxy;
        while(true){
            try {
                sconi = scon.accept();
                proxy = new PlaneClientProxy(sconi, planeInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
    }

}