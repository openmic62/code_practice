/*
H:\student\code_practice_junit\src\test\java>javac -cp ..\..\..\lib\junit-4.11.jar -d ..\..\..\target\classes MoneyTests.java
H:\student\code_practice_junit\src\test\java>java -cp ..\..\..\lib\junit-4.11.jar;..\..\..\lib\hamcrest-core-1.3.jar;..\..\..\target\classes org.junit.runner.JUnitCore MoneyTests
*/

public class Dollar {
	private int amount;
	Dollar(int amount){
		this.amount = amount;
	}
	Dollar times(int multiplier){
		return new Dollar(amount * multiplier);
	}
	public boolean equals(Object object) {
		Dollar dollar = (Dollar) object;
		return this.amount == dollar.amount;
	}
}