import java.net.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.net.DatagramSocket;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

// Network code modified from https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
public class Sender {

    private final String filename = "serialized_data.xml";

    public void sendFile(String hostname, int port) throws IOException {
        Socket socket = null;

        socket = new Socket(hostname, port);

        File file = new File(filename);
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
    }


    public void SerializeObj(Object obj) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();


        Document document = documentBuilder.newDocument();
        Element root = document.createElement("serialized");
        document.appendChild(root);

        if (obj instanceof SimpleObject) {
            root.appendChild(((SimpleObject) obj).toElements(document));
        }
        if (obj instanceof ArrayOfPrimsObject) {
            root.appendChild(((ArrayOfPrimsObject) obj).toElements(document));
        }



        // Builds file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filename));
        transformer.transform(domSource, streamResult);
    }




    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        ObjectCreator creator = new ObjectCreator();
        Object sendObj = creator.objectCreate();
        sender.SerializeObj(sendObj);
        sender.sendFile("localhost", 9876);
    }
}
