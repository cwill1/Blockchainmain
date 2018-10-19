import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

//test for 6th commit
public class Client {
    //socket setup
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;
    public static ArrayList<Integer> publicKeys = new ArrayList<Integer>();


    //ports
    public static String port1;
    public static String port2;
    public static String port3;

	public Client(){}

    public Client(String port){
	    this.port1 = port;
    }

    public Client(String port1, String port2, String port3){
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
    public ArrayList<Integer> getPublicKeys(){
        return publicKeys;
    }

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

    public void connectAndSendMessage(ArrayList<String> publicKeys, Integer _port) throws UnknownHostException, IOException,
            ClassNotFoundException, InterruptedException,ClassCastException {
        Thread.sleep(1000);

        Socket socket = new Socket("localhost", _port);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> keys = publicKeys;
        objectOutputStream.writeObject(keys);
        Object returnMessage = (Object) objectInputStream.readObject();
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
