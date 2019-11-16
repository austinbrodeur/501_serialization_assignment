import java.net.*;
import java.io.*;
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

        printFile();
    }

    public void printFile() throws IOException {
        System.out.println("DOM file sent:\n");
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String line = in.readLine();
        while(line != null)
        {
            System.out.println(line);
            line = in.readLine();
        }
        in.close();
    }


    public void SerializeObj(Object obj) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();


        Document document = documentBuilder.newDocument();
        Element root = document.createElement("serialized");
        document.appendChild(root);

        if (obj instanceof SimpleObject) {
            root.appendChild(((SimpleObject) obj).toElements(document, 0));
        }
        else if (obj instanceof ArrayOfPrimsObject) {
            root.appendChild(((ArrayOfPrimsObject) obj).toElements(document, 0));
        }
        else if (obj instanceof ObjectReferenceObject) {
            root.appendChild(((ObjectReferenceObject) obj).toElements(document, 0));
        }

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
