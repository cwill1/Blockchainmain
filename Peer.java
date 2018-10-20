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
        ArrayList<String> publicKeys = new ArrayList<String>();
        messageSender = new Client("4930", "4931","4932");
        //if port is P2
        //save the public keys to this port

        publicKeys.add("4930");
        publicKeys.add("4931");
        publicKeys.add("4932");

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
        // 3 All processes start with the same initial one-block(dummy) entry form of the block chain
        mySingleton.initiateBlockChain();

        while(true) {
            //third port ..multicast once
            if(PORT == 4932){
                //2 trigger multicast of public keys
                p2TriggerMulticastOfPublicKeys();

                multiCastHappened = true;
            }
             System.out.println("incoming client request...");
             socket = serverSocket.accept();//stops after this
             new Assistant(socket, systemOut).start();

            //4 Read the data file for this process
            if(mySingleton.checkIfPublicKeyIsSet()){
                //spawn a new thread to read in the input from the files

            }

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
    public static ServerSingleton mySingleton = ServerSingleton.getInstance();

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
            mySingleton.setPublicKeys(returnMessage);
            systemOut.println("Test: Server printing message from client : " + mySingleton.getPublicKeys());
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
    //private static Object publicKeys = new ArrayList<Object>();
    private static Object publicKeys;
    private static Block blockChain;
    private static Boolean publicKeysSet = false;

    public static Object getPublicKeys() {
        return publicKeys;
    }

    public static void setPublicKeys(ArrayList<String> publicKeys) {
        ServerSingleton.publicKeys = publicKeys;
    }
    public static void setPublicKeys(Object _publicKeys) {
        ServerSingleton.publicKeys = _publicKeys;
        ArrayList<Object> keys;
        //code to parse the incoming public keys
        keys = new ArrayList<Object>();
        for (Object i: (ArrayList<Object>)_publicKeys){
           i = i.toString();
           keys.add(i);
        }
        ServerSingleton.publicKeys = keys;
        if(publicKeys != null) {
            publicKeysSet = true;
        }
    }
    public static void addRecord(){
        //blockChain.next = new Block();
    }
    public static Boolean checkIfPublicKeyIsSet(){
        return publicKeysSet;
    }

    public static ServerSingleton getInstance() {
        if (myInstance == null) {
            myInstance = new ServerSingleton();
        }
        return myInstance;
    }
    public static void initiateBlockChain(){
        blockChain = new Block("<BlockRecord>\n" +
                "  <SIGNED-SHA256> [B@5f150435 </SIGNED-SHA256> <!-- Verification procees SignedSHA-256-String  -->\n" +
                "  <SHA-256-String> 63b95d9c17799463acb7d37c85f255a511f23d7588d871375d0119ba4a96a </SHA-256-String>\n" +
                "  <!-- Start SHA-256 Data that was hashed -->\n" +
                "  <VerificationProcessID> 1 </VerificationProcessID> <!-- Process that is verifying this block, for credit-->\n" +
                "  <PreviousHash> From the previous block in the chain </PreviousHash>\n" +
                "  <Seed> Your random 256 bit string </Seed> <!-- guess the value to complete the work-->\n" +
                "  <BlockNum> 1 </BlockNum> <!-- increment with each block prepended -->\n" +
                "  <BlockID> UUID </BlockID> <!-- Unique identifier for this block -->\n" +
                "  <SignedBlockID> BlockID signed by creating process </SignedBlockID> <!-- Creating process signature -->\n" +
                "  <CreatingProcessID> 0 </CreatingProcessID> <!-- Process that made the ledger entry -->\n" +
                "  <TimeStamp> 2017-09-01.10:26:35 </TimeStamp>\n" +
                "  <DataHash> The creating process SHA-256 hash of the input data </DataHash> <!-- for auditing if Secret Key exposed -->\n" +
                "  <FName> Joseph </FName>\n" +
                "  <LName> Ng </LName>\n" +
                "  <DOB> 1995.06.22 </DOB> <!-- date of birth -->\n" +
                "  <SSNUM> 987-65-4321 </SSNUM>\n" +
                "  <Diagnosis> Measels </Diagnosis>\n" +
                "  <Treatment> Bedrest </Treatment>\n" +
                "  <Rx> aspirin </Rx>\n" +
                "  <Notes> Use for debugging and extension </Notes>\n" +
                "<!-- End SHA-256 Data that was hashed -->\n" +
                "</BlockRecord>");

    }

}



