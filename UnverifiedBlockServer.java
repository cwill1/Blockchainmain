import java.util.concurrent.BlockingQueue;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.io.IOException;

public class UnverifiedBlockServer implements Runnable {
    static BlockingQueue<String> queue;
    UnverifiedBlockServer(BlockingQueue<String> queue){
        this.queue = queue; // Constructor binds our priority queue to the local variable.
    }

  /* Inner class to share priority queue. We are going to place the unverified blocks into this queue in the order we get
     them, but they will be retrieved by a consumer process sorted by blockID. */

    static class UnverifiedBlockWorker extends Thread { // Class definition
        Socket sock; // Class member, socket, local to Worker.
        UnverifiedBlockWorker (Socket s) {sock = s;} // Constructor, assign arg s to local sock
        public void run(){
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String data = in.readLine ();
                System.out.println("Put in priority queue: " + data + "\n");
                queue.put(data);
                sock.close();
            } catch (Exception x){x.printStackTrace();}
        }
    }

    public static void main(String[] args){
        int q_len = 6; /* Number of requests for OpSys to queue */
        Socket sock;
        System.out.println("Starting the Unverified Block Server input thread using " +
                Integer.toString(Ports.UnverifiedBlockServerPort));
        try{
            ServerSocket servsock = new ServerSocket(Ports.UnverifiedBlockServerPort, q_len);
            while (true) {
                sock = servsock.accept(); // Got a new unverified block
                System.out.println("incoming request ... ");
                new UnverifiedBlockWorker(sock).start(); // So start a thread to process it.

            }
        }catch (IOException ioe) {System.out.println(ioe);}
    }


    public void run(){
        int q_len = 6; /* Number of requests for OpSys to queue */
        Socket sock;
        System.out.println("Starting the Unverified Block Server input thread using " +
                Integer.toString(Ports.UnverifiedBlockServerPort));
        try{
            ServerSocket servsock = new ServerSocket(Ports.UnverifiedBlockServerPort, q_len);
            while (true) {
                sock = servsock.accept(); // Got a new unverified block
                new UnverifiedBlockWorker(sock).start(); // So start a thread to process it.
            }
        }catch (IOException ioe) {System.out.println(ioe);}
    }
}


class Ports{
    public static int KeyServerPortBase = 6050;
    public static int UnverifiedBlockServerPortBase = 6051;
    public static int BlockchainServerPortBase = 6052;

    public static int KeyServerPort;
    public static int UnverifiedBlockServerPort = 4670;
    public static int BlockchainServerPort;
/*
    public void setPorts(){
        KeyServerPort = KeyServerPortBase + (bc.PID * 1000);
        UnverifiedBlockServerPort = UnverifiedBlockServerPortBase + (bc.PID * 1000);
        BlockchainServerPort = BlockchainServerPortBase + (bc.PID * 1000);
    }
    */
}
