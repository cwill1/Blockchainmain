
import javax.xml.bind.JAXBException;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import java.io.StringWriter;
import java.io.StringReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;
import java.util.StringTokenizer;

/* CDE Some other uitilities: */

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.text.*;


public class BlockInputE extends Thread {

    private static String FILENAME;

    /* Token indexes for input: */
    private static final int iFNAME = 0;
    private static final int iLNAME = 1;
    private static final int iDOB = 2;
    private static final int iSSNUM = 3;
    private static final int iDIAG = 4;
    private static final int iTREAT = 5;
    private static final int iRX = 6;
    private static Integer pnum = 0;
    private static ServerSingleton mySingleton = ServerSingleton.getInstance();

    public BlockInputE(Integer processNumber){
        this.pnum = processNumber;
    }

    public void run(){

        /* CDE: Process numbers and port numbers to be used: */
        int UnverifiedBlockPort;
        int BlockChainPort;
        pnum = 0;
        /*
        /* CDE If you want to trigger bragging rights functionality... */
        /*
        if (args.length > 1) System.out.println("Special functionality is present \n");

        if (args.length < 1) pnum = 0;
        else if (args[0].equals("0")) pnum = 0;
        else if (args[0].equals("1")) pnum = 1;
        else if (args[0].equals("2")) pnum = 2;
        else pnum = 0;
        */
        UnverifiedBlockPort = 4710 + pnum;
        BlockChainPort = 4820 + pnum;

        System.out.println("Process number: " + pnum + " Ports: " + UnverifiedBlockPort + " " +
                BlockChainPort + "\n");

        switch(pnum){
            case 1: FILENAME = "BlockInput1.txt"; break;
            case 2: FILENAME = "BlockInput2.txt"; break;
            default: FILENAME= "BlockInput0.txt"; break;
        }
        System.out.println("Using input file: " + FILENAME);

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
                String[] tokens = new String[10];
                String stringXML;
                String InputLineStr;
                String suuid;
                UUID idA;

                BlockRecord[] blockArray = new BlockRecord[20];

                JAXBContext jaxbContext = JAXBContext.newInstance(BlockRecord.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter sw = new StringWriter();

                // CDE Make the output pretty printed:
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                int n = 0;

                while ((InputLineStr = br.readLine()) != null) {
                    blockArray[n] = new BlockRecord();

                    blockArray[n].setASHA256String("SHA string goes here...");
                    blockArray[n].setASignedSHA256("Signed SHA string goes here...");

                    /* CDE: Generate a unique blockID. This would also be signed by creating process: */
                    idA = UUID.randomUUID();
                    suuid = new String(UUID.randomUUID().toString());
                    blockArray[n].setABlockID(suuid);
                    blockArray[n].setACreatingProcess("Process" + Integer.toString(pnum));
                    blockArray[n].setAVerificationProcessID("To be set later...");
                    /* CDE put the file data into the block record: */
                    tokens = InputLineStr.split(" +"); // Tokenize the input
                    blockArray[n].setFSSNum(tokens[iSSNUM]);
                    blockArray[n].setFFname(tokens[iFNAME]);
                    blockArray[n].setFLname(tokens[iLNAME]);
                    blockArray[n].setFDOB(tokens[iDOB]);
                    blockArray[n].setGDiag(tokens[iDIAG]);
                    blockArray[n].setGTreat(tokens[iTREAT]);
                    blockArray[n].setGRx(tokens[iRX]);
                    //Add blocks to BlockChain
                    //mySingleton.block
                    n++;
                }
                System.out.println(n + "records read.");
                System.out.println("Names from input:");
                for(int i=0; i < n; i++){
                    System.out.println("  " + blockArray[i].getFFname() + " " +
                            blockArray[i].getFLname());
                }
                System.out.println("\n");

                stringXML = sw.toString();
                for(int i=0; i < n; i++){
                    jaxbMarshaller.marshal(blockArray[i], sw);
                }
                String fullBlock = sw.toString();
                String XMLHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
                String cleanBlock = fullBlock.replace(XMLHeader, "");
                // Show the string of concatenated, individual XML blocks:
                String XMLBlock = XMLHeader + "\n<BlockLedger>" + cleanBlock + "</BlockLedger>";
                System.out.println(XMLBlock);
            } catch (IOException e) {e.printStackTrace();}
        } catch (Exception e) {e.printStackTrace();}
    }
}

/* Could use string tools to modify XML as well:

   stringXML = sw.toString();
   fullBlock = stringXML.substring(0,stringXML.indexOf("<blockID>")) +
   "<SignedSHA256>" + SignedSHA256 + "</SignedSHA256>\n" +
   "    <SHA256String>" + SHA256String + "</SHA256String>\n    " +
   stringXML.substring(stringXML.indexOf("<blockID>"));
   System.out.println(stringXML); // A single record...

   ...You'll need something like this to insert the seed before hashing.
*/