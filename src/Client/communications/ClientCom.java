package Client.communications;

import java.io.*;
import java.net.*;

public class ClientCom
{
    /**
     * Communication socket
     */
    private Socket commSocket = null;

    /**
     * Name of the computation system where the server is located
     */
    private String serverHostName;

    /**
     * Number of the listening port at the computation system where the server is located
     */
    private int serverPortNumb;

    /**
     *  Input stream of the communication channel
     */
    private ObjectInputStream in = null;

    /**
     * Output stream of the communication channel
     */
    private ObjectOutputStream out = null;

    /**
     *
     * Instantiation of a communication channel
     *
     * @param hostName name of the computation system where the server is located
     * @param portNumb number of the listening port at the computation system where the server is located
     */
    public ClientCom (String hostName, int portNumb)
    {
        serverHostName = hostName;
        serverPortNumb = portNumb;
    }

    /**
     * Open the communication channel
     *
     * Instantiation of the communication socket and its binding the the server address
     * The socket input and output streams are opened
     *
     * @return true, if the communication channel is opened - false, otherwise
     */
    public boolean open ()
    {
        boolean success = true;                                                                      // flag signaling
        // success on opening the communication channel
        SocketAddress serverAddress = new InetSocketAddress (serverHostName, serverPortNumb);        // inet address

        try
        {
            commSocket = new Socket();
            commSocket.connect (serverAddress);
        }
        catch (UnknownHostException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - the name of the computational system where the server is located, is unknown: " +
                serverHostName + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NoRouteToHostException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - the name of the computational system where the server is located, is unreachable: " +
                serverHostName + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (ConnectException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - the server does not respond at: " + serverHostName + "." + serverPortNumb + "!");
            if (e.getMessage ().equals ("Connection refused"))
                success = false;
            else
            {
                System.out.println (e.getMessage () + "!");
                e.printStackTrace ();
                System.exit (1);
            }
        }
        catch (SocketTimeoutException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - time out has occurred in establishing the connection at: " +
                serverHostName + "." + serverPortNumb + "!");
            success = false;
        }
        catch (IOException e) // fatal error --- other reasons
        {
            System.out.println (Thread.currentThread ().getName () +
                " - an indeterminate error has occurred in establishing the connection at: " +
                serverHostName + "." + serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        }

        if (!success)
            return (success);

        try
        {
            out = new ObjectOutputStream (commSocket.getOutputStream ());
        }
        catch (IOException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - it was not possible to open the output stream!");
            e.printStackTrace ();
            System.exit (1);
        }

        try
        {
            in = new ObjectInputStream (commSocket.getInputStream ());
        }
        catch (IOException e)
        {
            System.out.println (Thread.currentThread ().getName () +
                " - it was not possible to open the input stream!");
            e.printStackTrace ();
            System.exit (1);
        }

        return (success);
    }
}
