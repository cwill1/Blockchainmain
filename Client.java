import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//test for 6th commit
public class Client {
    //socket setup
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;


    //ports
    public static Integer port1;
    public static Integer port2;
    public static Integer port3;

	public Client(){}

    public Client(Integer port){
	    this.port1 = port;
    }
    public Client(Integer port1, Integer port2, Integer port3){
	    this.port1 = port1;
	    this.port2 = port2;
	    this.port3 = port3;

    }

    public void test6(){}
    public void setup() throws UnknownHostException, IOException, ClassNotFoundException {
        socket = new Socket("localhost", 2652);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    /*
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
			Integer firstNumber = 10;
			Integer secondNumber = 15;
			Socket socket = new Socket("localhost", 2652);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Message message = new Message(firstNumber,secondNumber);
			objectOutputStream.writeObject(message);
			Message returnMessage = (Message)objectInputStream.readObject();
			System.out.println(returnMessage.getResult());
			socket.close();
	}
	*/

    public void connectAndSendMessage(String _message, Integer _port) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", _port);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = new Message(_message);
        objectOutputStream.writeObject(message);
        Message returnMessage = (Message)objectInputStream.readObject();
        System.out.println(returnMessage.getResult());
        socket.close();
    }

    public void connectAndSendMessage(ArrayList<Integer> _message , Integer _port) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", _port);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = new Message(_message);
        objectOutputStream.writeObject(message);
        Message returnMessage = (Message)objectInputStream.readObject();
        System.out.println(returnMessage.getResult());
        socket.close();
    }

    public void sendMessage(String _message) throws UnknownHostException, IOException, ClassNotFoundException {

        Message message = new Message(_message);
        objectOutputStream.writeObject(message);
        Message returnMessage = (Message)objectInputStream.readObject();
        System.out.println(returnMessage.message);
        socket.close();
    }

	public static boolean isInteger (String value)  {
		boolean returnValue = true;
		try { Integer.parseInt(value); } 
		catch( Exception e) { returnValue = false; } 
		return returnValue;
	}
}
