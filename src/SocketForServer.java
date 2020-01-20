import java.io.*;
import javax.xml.parsers.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.xml.sax.*;

public class SocketForServer {
    public static void main(String args[]){
        int port = 7789;
        ServerSocket server;
        InputStream input;
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            //System.out.println(e);
            server = null;
        }
        int i = 0;
        while (true) {
            try {
                System.out.println("Waiting...");
                Socket client = server.accept();
                System.out.println("Accepted");
                i++;


                input = client.getInputStream();
                XMLReader xmlReader = new XMLReader();
                xmlReader.addData(input);
                Thread worker = new Thread(xmlReader);
                worker.start();
                System.out.println("Connection established");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
