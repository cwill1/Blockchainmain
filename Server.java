import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class Server {
    //keep information that has to be kept the same

    public static final int PORT = 2652;
    public static ServerSocket serverSocket;
    public static Socket socket;

    public static Block head;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Server up & ready for connections...");
        head = new Block("firstblock");

        serverSocket = new ServerSocket(PORT);
        while(true) {
            System.out.println("Server up & ready for connections...");
            socket = serverSocket.accept();
            new Assistant(socket).start();
        }

    }


}


