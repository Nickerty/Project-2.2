import java.io.*;
import javax.xml.parsers.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.xml.sax.*;

public class SocketForServer {
    public static void main(String args[]){
        int port = 7789;                //Port for connection (server-client)
        ServerSocket server;
        InputStream input;
        try {
            server = new ServerSocket(port);    //Make new serverSocket
        } catch (Exception e) {
            //System.out.println(e);
            server = null;
        }
        int i = 0;
        MergeData mergeData = new MergeData();      //Make new instance of MergeData
        Thread merger = new Thread(mergeData);      //Make new Thread which will run the mergeData instance
        merger.start();                             //Start the Thread
        while (true) {
            try {
                System.out.println("Waiting...");
                Socket client = server.accept();    //Server waiting for an incoming connection
                System.out.println("Accepted");
                i++;                                //Keep track of how many connections have been made (TEST PURPOSES)

                input = client.getInputStream();    //Get the input received at the socket
                XMLReader xmlReader = new XMLReader(merger, mergeData); //Read XML file using an instance of XMLReader
                xmlReader.addData(input);                               //Add the received data to the instance of XMLReader
                Thread worker = new Thread(xmlReader);                  //New thread for the already existing instance of XMLReader
                worker.start();                                         //Start the Thread
                System.out.println("Connection established");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
