import java.io.IOException;
import java.net.Socket;

public class NetworkUtils {

    public Socket openTcp(String serverName, int port) {
        Socket tcpConnection = null;
        try {
            tcpConnection = new Socket(serverName, port);
        } catch (IOException e) {
            System.out.println("Error opening TCP connection with receiver.");
            e.printStackTrace();
        }
        return tcpConnection;
    }
}
