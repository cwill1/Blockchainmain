import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class testClient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 4930);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String message = "I think you got it this time Chris";
        objectOutputStream.writeObject(message);
        //Message returnMessage = (Message)objectInputStream.readObject();
        //System.out.println(returnMessage.getResult());
        socket.close();
    }


}
