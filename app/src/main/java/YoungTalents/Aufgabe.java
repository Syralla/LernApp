package youngtalents;

public class Aufgabe {

	private int number1;
	private int number2;
	private Operations op;

	public Aufgabe(int number1, int number2, Operations op) {
		this.number1 = number1;
		this.number2 = number2;
		this.op = op;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public Operations getOp() {
		return op;
	}

	public void setOp(Operations op) {
		this.op = op;
	}

	
	
}
