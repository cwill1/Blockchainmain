import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.io.Serializable;


//test for 6th commit
//starts a new server process at a new port
public class Peer {

    //keep information that has to be kept the same

	public static int PORT = 0;
	public static int publicKey;
	public static ServerSocket serverSocket;
	public static Socket socket;
    public static BufferedReader input;
	public static Block head;
	public static Client messageSender;
	public static boolean multiCastHappened = false;
	public static PrintStream systemOut = System.out;
	public static ServerSingleton mySingleton;

    public static void p2TriggerMulticastOfPublicKeys() throws IOException, ClassNotFoundException, InterruptedException{
        ArrayList<Integer> publicKeys = new ArrayList<Integer>();
        messageSender = new Client(4930, 4931,4932);
        //if port is P2
        //save the public keys to this port

        publicKeys.add(4930);
        publicKeys.add(4931);
        publicKeys.add(4932);
        mySingleton.setPublicKeys(publicKeys);
        //multicast public keys
        messageSender.connectAndSendMessage(publicKeys, 4930);
        messageSender.connectAndSendMessage(publicKeys, 4931);
        messageSender.connectAndSendMessage(publicKeys, 4932);



        //multicast the public keys if the PORT is 4932

    }

    public void test6(){}

    //create blockchain ledger
    public static void populateBlockChainLedger(String text){
        if(text != null) {
            try {
                PrintWriter outPutWrite = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter("Blockchainledger.xml", true)));
                outPutWrite.println(text);
                outPutWrite.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }


	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //System.out.println("Peer up & ready for connections...");
        //p2TriggerMulticastOfPublicKeys(4930);
        mySingleton = ServerSingleton.getInstance();
        String enterkey = null;
        //while((enterkey == null)) {
            if (args.length < 1) {
                //prompt for port
                input = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("start process 0, 1, or 2");
                enterkey = input.readLine();
            }
            if(args.length > 0){
                enterkey = args[2];
                switch (enterkey){
                   //1 start servers in the order p0, p1, p2
                    //this is all done in the bash script with the command line
                   case "0": enterkey = "4930"; PORT = 4930; System.out.println(enterkey); break;
                   case "1": enterkey = "4931"; PORT = 4931; System.out.println(enterkey); break;
                   case "2": enterkey = "4932"; PORT = 4932; System.out.println(enterkey); break;
               }
            }
        //}
        PORT = Integer.parseInt(enterkey);
        //add code to connect and send messages to the ports that are availible
        //populate Peer xml with header
        //1st port
        if(PORT == 4930) {
            //check if file exists
            File file = new File("src/Blockchainledger.xml");
            if(file.exists()){
                file.delete();
            }
            populateBlockChainLedger("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            //2 trigger multicast of public keys
        }
        //print out what port they connected at
        System.out.println("Peer up & ready for connections...");
        //instantiate a peer
	    head = new Block("firstblock");
        serverSocket = new ServerSocket(PORT);
        while(true) {
            //3rd port ..multicast once
            if(PORT == 4932){
                //2 trigger multicast of public keys
                p2TriggerMulticastOfPublicKeys();
                multiCastHappened = true;
            }
             System.out.println("incoming client request...");
             socket = serverSocket.accept();//stops after this
             new Assistant(socket, systemOut).start();

        }
	}




}


class Assistant extends Thread {
    //create a socket to open a communication stream between Client and Peer
    Socket connectionSocket;
    //create a Assistant socket apart from the main thread to delegate work to be done
    //use a constructor and assign a socket to
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    Block Blockchain;
    PrintStream systemOut;
    public static ArrayList<Integer> publicKeys = new ArrayList<Integer>();

    Assistant (Socket s) {connectionSocket = s;
    }

    Assistant (Socket s, PrintStream _systemOut) {connectionSocket = s;
        systemOut = _systemOut;
    }
    public ArrayList<Integer> printPublicKeys(Message message){
          return message.getPublicKeys();
    }
    public void run(){


        try{
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            //Message returnMessage = (Message)objectInputStream.readObject();
             Object returnMessage = (Object)objectInputStream.readObject();
            //store the updated blockchain in blockchain
            //this.Blockchain = returnMessage.blockChain;

            //close the communication between the server and the client
            objectOutputStream.writeObject(returnMessage);

            systemOut.println("Test: Server printing message from client : " + returnMessage);
            connectionSocket.close();

            //close the Input/Output operation if there is a failure

            //test code delete
            //printPublicKeys(message);



        }catch (IOException ioe){System.out.println(ioe);
        }
        catch (ClassNotFoundException e){

        }

        //catch and print the input/output exception if there is one
    }

    //accept a hostname or an IP address and a PrintStream as a parameter
    //print out the hostname or IPaddress once it recieves the communication from the client
    //if the results from the remote address is found.
    //if the name is not found throw an exception



    //read in an array of bytes. Iterate through the bytes using a 4 loop.
    //logical & the result to get the appropriate text we need.
    //convert the final result to string


}



class ServerSingleton implements Serializable {
    private static ServerSingleton myInstance = null;
    private static ArrayList<Integer> publicKeys = new ArrayList<Integer>();

    public static ArrayList<Integer> getPublicKeys() {
        return publicKeys;
    }

    public static void setPublicKeys(ArrayList<Integer> publicKeys) {
        ServerSingleton.publicKeys = publicKeys;
    }


    public static ServerSingleton getInstance() {
        if (myInstance == null) {
            myInstance = new ServerSingleton();
        }
        return myInstance;
    }

}