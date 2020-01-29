import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * The SocketForServer class is a class which will make as many sockets as there are clients who are willing to connect.
 * Once a client makes a connection with this class (Server), this class will make a Thread for it and start the
 * data processing phase by letting the Thread handle a new instance of XMLReader.
 *
 * @author Matthijs van der Wal, Anne de Graaff, Nick Scholma
 * @version 1.0
 * @since 24-1-2020
 */
public class SocketForServer {

    /**
     * Method which will run as soon as the program starts. It will wait for the first client to arrive and than it
     * will take actions accordingly.
     */
    public static void main(String args[]){
        int port = 7789;                //Port for connection (server-client)
        ServerSocket server;
        InputStream input;
        int connection = 0;
        try {
            server = new ServerSocket(port, 800);    //Make new serverSocket
            server.setSoTimeout(1000);
            int i = 0;
            MergeData mergeData = new MergeData();      //Make new instance of MergeData
            Thread worker = new Thread(mergeData);
            worker.start();
            while (true) {
                System.out.println(connection);
                System.out.println("Waiting...");
                Socket client;
                try {
                    client = server.accept();    //Server waiting for an incoming connection
                    connection++;
                } catch (SocketTimeoutException e) {
                    client = null;
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (Exception es) {
//                        System.out.println("WTF");
//                    }
                    System.out.println("Socket timeout");
                } catch (Exception se) {
                    client = null;
                    System.out.println("Other exception");
                }

                if(client != null) {
                    System.out.println("Accepted");
                    try {
                        input = client.getInputStream();    //Get the input received at the socket
                    } catch (Exception is) {
                        System.out.println(is);
                        input = null;
                    }
                    if(input != null) {
                        XMLReader xmlReader = new XMLReader(mergeData);         //Read XML file using an instance of XMLReader
                        xmlReader.addData(input);                               //Add the received data to the instance of XMLReader
                        Thread xmlWorker = new Thread(xmlReader);                                       //Start the Thread
                        xmlWorker.start();
                        System.out.println("Connection established");
                    } else {
                        System.out.println("Input is null");
                    }
                }

            }
        } catch (Exception e) {
            //System.out.println(e);
            server = null;
        }


    }
}
