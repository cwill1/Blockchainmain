import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
//test for 4th commit
//starts a new server process at a new port
public class Blockchain {

    //keep information that has to be kept the same

	public static int PORT = 2650;
	public static ServerSocket serverSocket;
	public static Socket socket;
    public static BufferedReader input;
	public static Block head;
	public static Client messageSender;

    public Blockchain(Integer _port){
        this.PORT = _port;

//
    }

    public void test(){}
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
        //System.out.println("Blockchain up & ready for connections...");

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

        //populate Blockchain xml with header
        if(PORT == 4930) {
            populateBlockChainLedger("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }

        //print out what port they connected at
        System.out.println("We sucessfully started a process");



        System.out.println("Blockchain up & ready for connections...");
	    head = new Block("firstblock");

        serverSocket = new ServerSocket(PORT);

        while(true) {
             System.out.println("Blockchain up & ready for connections...");
             socket = serverSocket.accept();
             new Assistant(socket).start();
        }

	}


}


class Assistant extends Thread {
    //create a socket to open a communication stream between Client and Blockchain
    Socket connectionSocket;
    //create a Assistant socket apart from the main thread to delegate work to be done
    //use a constructor and assign a socket to
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    Assistant (Socket s) {connectionSocket = s;}

    public void run(){


        try{
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            Message message = (Message)objectInputStream.readObject();

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
