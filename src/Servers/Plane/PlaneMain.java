package Servers.Plane;

import Client.stubs.GeneralRepStub;

import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

/**
 *    Server side of the Plane Main
 */

public class PlaneMain {

    /**
     * Flag to signal server shutdown
     */
    public static boolean shutdown;

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     */

    public static void main(String[] args)
    {
        GeneralRepStub generalRepStub = new GeneralRepStub();
        Plane plane = new Plane(generalRepStub);
        PlaneInterface planeInterface = new PlaneInterface(plane);
        ServerCom scon, sconi;
        scon =  new ServerCom(RunParameters.PlanePort);
        scon.start();
        PlaneClientProxy proxy;
        shutdown = false;

        while(!shutdown)
        {
            try {
                sconi = scon.accept();
                proxy = new PlaneClientProxy(sconi, planeInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
    }
}