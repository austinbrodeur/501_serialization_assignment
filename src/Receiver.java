import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.*;
import java.util.*;


// Network code modified from https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
public class Receiver extends Thread {

    private final String filename = "received_serialized_file.xml";


    public String getFilename() {
        return filename;
    }


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
            out = new FileOutputStream(filename);
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


    public Object deSerialize(String filename) throws IOException, ParserConfigurationException, SAXException {
        Object outObj = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filename);

        document.getDocumentElement().normalize();

        System.out.println("Root element: " + document.getDocumentElement().getNodeName());
        NodeList nList = document.getElementsByTagName("object");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            String name = nNode.getAttributes().getNamedItem("class").getNodeValue();
        }

        return outObj;
    }



    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Receiver receiver = new Receiver();
        Inspector inspector = new Inspector();
        Object obj;
        receiver.receiveFile(9876);
        inspector.inspect(receiver.deSerialize(receiver.getFilename()), false);
    }
}
