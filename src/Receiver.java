import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.*;
import java.net.DatagramSocket;

// Network code modified from https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
public class Receiver extends Thread {

    public void receiveFile(int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Can't accept client connection. ");
        }

        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            System.out.println("Can't get socket input stream. ");
        }

        try {
            out = new FileOutputStream("received_serialized_file.xml");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. ");
        }

        byte[] bytes = new byte[16*1024];

        int count;

        try {
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            socket.close();
            serverSocket.close();
        }
        catch (IOException e) {
            System.out.println("Exception caught while receiving file: ");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Receiver receiver = new Receiver();
        receiver.receiveFile(9876);
    }

}
