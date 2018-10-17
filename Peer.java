import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

//test for 6th commit
//starts a new server process at a new port
public class Peer {

    //keep information that has to be kept the same

	public static int PORT = 4930;
	public static int publicKey;
	public static ServerSocket serverSocket;
	public static Socket socket;
    public static BufferedReader input;
	public static Block head;
	public static Client messageSender;
	public static ArrayList<Integer> publicKeys = new ArrayList<Integer>();

    public static void peerSetUp (Integer _port) throws IOException, ClassNotFoundException{
        PORT = _port;
        publicKey = PORT;
        messageSender = new Client(4930, 4931,4932);
        //if port is P2
        publicKeys.add(4930);
        publicKeys.add(4931);
        publicKeys.add(4932);

        if (PORT == 4930){
            //remove public key from arrayList
            publicKeys.remove(0);
            //add public keys to arrayList
         //multicast public keys
            for (int i = 0; i < publicKeys.size(); i ++) {
                messageSender.connectAndSendMessage(publicKeys, publicKeys.get(i));
            }
        }
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
        //System.out.println("Peer up & ready for connections...");
        //peerSetUp(4930);
        String enterkey = null;
        while((enterkey == null)) {
            if (args.length < 1) {
                //prompt for port
                input = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("start process 0, 1, or 2");
                enterkey = input.readLine();
            }
            if(args.length > 0){
               switch (enterkey){
                   case "start process 0": enterkey = "4930";
                   case "start process 1": enterkey = "4931";
                   case "start process 2": enterkey = "4932";
               }
            }
        }
        PORT = Integer.parseInt(enterkey);
        //add code to connect and send messages to the ports that are availible

        //populate Peer xml with header
        if(PORT == 4930) {
            //check if file exists
            File file = new File("src/Blockchainledger.xml");
            if(file.exists()){
                file.delete();
            }
            populateBlockChainLedger("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }

        //print out what port they connected at
        System.out.println("We sucessfully started a process");

        //instantiate a peer


        System.out.println("Peer up & ready for connections...");
	    head = new Block("firstblock");

        serverSocket = new ServerSocket(PORT);

        while(true) {
             System.out.println("Peer up & ready for connections...");
             socket = serverSocket.accept();
             new Assistant(socket).start();
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

    Assistant (Socket s) {connectionSocket = s;
    }

    public void run(){


        try{
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            Message message = (Message)objectInputStream.readObject();
            //store the updated blockchain in Blockchain
            this.Blockchain = message.blockChain;

            //close the communication between the server and the client
            objectOutputStream.writeObject(message);
            connectionSocket.close();

            //close the Input/Output operation if there is a failure


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
