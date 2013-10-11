/*
H:\student\code_practice_junit\src\test\java>javac -cp ..\..\..\lib\junit-4.11.jar -d ..\..\..\target\classes MoneyTests.java
H:\student\code_practice_junit\src\test\java>java -cp ..\..\..\lib\junit-4.11.jar;..\..\..\lib\hamcrest-core-1.3.jar;..\..\..\target\classes org.junit.runner.JUnitCore MoneyTests
*/

public class Franc extends Money{
	//private String currency;
	
	Franc(int amount, String currency){
		super(amount, currency);
	}
	Money times(int multiplier){
		return new Money(amount * multiplier, currency);
		///return new Franc(amount * multiplier, "CHF");
		////return Money.franc(amount * multiplier);
	}
}