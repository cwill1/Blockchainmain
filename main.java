import java.io.IOException;
//test for 6th commit
public class main {
    //test for 4th commit
    public static ProcessBuilder builder;
    public static Blockchain pro;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// create 3 processes
        //builder.directory(new File("/Users/cwill/IdeaProjects/BlockChain/src/"));
        //builder = new ProcessBuilder(new String[] {"java", "Blockchain"});
        //builder.redirectError();
        //builder.directory(new File("/Users/cwill/IdeaProjects/BlockChain/src/"));
       Blockchain tester = new Blockchain(2650);
     System.out.println(tester.PORT);





        Client test = new Client();

        //test.connectAndSendMessage("You did it again man");
       // test.sendMessage("You got it again Chris");
        //System.out.println("test is good Chris");
    }
    public void test6(){}

}
