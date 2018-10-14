import java.io.Serializable;
//test for 6th commit
public class Message implements Serializable {
	private static final long serialVersionUID = -2723363051271966964L;
	Integer firstNumber = null;
	Integer secondNumber = null;
	Integer result = null;
	String message = null;
    public void test6(){}
	public Message(Integer firstNumber, Integer secondNumber){
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}
	public Message(String _message){
		this.message = _message;
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
