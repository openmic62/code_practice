/*
H:\student\code_practice_junit\src\test\java>javac -cp ..\..\..\lib\junit-4.11.jar -d ..\..\..\target\classes MoneyTests.java
H:\student\code_practice_junit\src\test\java>java -cp ..\..\..\lib\junit-4.11.jar;..\..\..\lib\hamcrest-core-1.3.jar;..\..\..\target\classes org.junit.runner.JUnitCore MoneyTests
*/

public class Franc {
	private int amount;
	Franc(int amount){
		this.amount = amount;
	}
	Franc times(int multiplier){
		return new Franc(amount * multiplier);
	}
	public boolean equals(Object object) {
		Franc franc = (Franc) object;
		return this.amount == franc.amount;
	}
}