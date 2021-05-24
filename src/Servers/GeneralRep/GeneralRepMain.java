package Servers.GeneralRep;


import Common.RunParameters;
import Servers.Common.ServerCom;

import java.net.SocketTimeoutException;

public class GeneralRepMain {

    /**
     * Flag to signal server shutdown
     */
    public static boolean shutdown;

    public static void main(String[] args)
    {
        GeneralRep generalRep = new GeneralRep(RunParameters.logFile);

        GeneralRepInterface generalRepInterface = new GeneralRepInterface(generalRep);

        ServerCom scon, sconi;

        scon =  new ServerCom(RunParameters.RepositoryPort);
        scon.start();
        GeneralRepClientProxy proxy;
        shutdown = false;
        while(!shutdown){
            try{
                sconi = scon.accept ();
                proxy = new GeneralRepClientProxy(sconi,generalRepInterface);
                proxy.start();
            }catch (SocketTimeoutException e) {}
        }
    }

}