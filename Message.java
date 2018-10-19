import java.io.Serializable;
import java.util.ArrayList;

//test for 6th commit
public class Message implements Serializable {
	private static final long serialVersionUID = -2723363051271966964L;
	Integer firstNumber;
	Integer secondNumber;
	Integer result;
	String message;
    public static ArrayList<Integer> publicKeys;
    public static Block blockChain;



    public static void setPublicKeys(ArrayList<Integer> _publicKeys) {
        publicKeys = _publicKeys;
    }


    public void test6(){}
	public Message(Integer firstNumber, Integer secondNumber){
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}
	public Message(String _message){
		this.message = _message;
	}

	public Message(ArrayList<Integer>  _publicKeys){

    	this.publicKeys = _publicKeys;
    }

	public ArrayList<Integer> getPublicKeys (){
        return publicKeys;
	}

	public Integer getFirstNumber() {
		return firstNumber;
	}
	public Integer getSecondNumber() {
		return secondNumber;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
}
