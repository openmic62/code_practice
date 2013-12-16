/*
<mlr 131214: begin - GOOS, p. 141a; they use an inner enum. So I Googled a little to beef up my skills>
http://stackoverflow.com/questions/7296785/using-nested-enum-types-in-java
<mlr 131214: begin - GOOS, p. 141a; they use an inner enum. So I Googled a little to beef up my skills>

 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-beta9.jar;lib\log4j-core-2.0-beta9.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\DrinkEnumExample.java
 java  -cp %CLASSPATH%;%SC%;%TC% DrinkEnumExample

*/

public final class DrinkEnumExample {

	public interface DrinkTypeInterface {

		String getDisplayableType();
	}

	public static enum DrinkType implements DrinkTypeInterface {

		COFFEE("Coffee"), TEA("Tea");
		private final String type;

		private DrinkType(final String type) {
			this.type = type;
		}

		public String getDisplayableType() {
			return type;
		}
	}

	public static enum Drink implements DrinkTypeInterface {

		COLUMBIAN("Columbian Blend", DrinkType.COFFEE),
		ETHIOPIAN("Ethiopian Blend", DrinkType.COFFEE),
		MINT_TEA("Mint", DrinkType.TEA),
		HERBAL_TEA("Herbal", DrinkType.TEA),
		EARL_GREY("Earl Grey", DrinkType.TEA);
		private final String label;
		private final DrinkType type;

		private Drink(String label, DrinkType type) {
			this.label = label;
			this.type = type;
		}

		public String getDisplayableType() {
			return type.getDisplayableType();
		}

		public String getLabel() {
			return label;
		}
	}

	public DrinkEnumExample() {
		super();
	}

	public static void main(String[] args) {
		System.out.println("All drink types");
		for (DrinkType type : DrinkType.values()) {
			displayType(type);
			System.out.println();
		}
		System.out.println("All drinks");
		for (Drink drink : Drink.values()) {
			displayDrink(drink);
			System.out.println();
		}
	}

	private static void displayDrink(Drink drink) {
		displayType(drink);
		System.out.print(" - ");
		System.out.print(drink.getLabel());
	}

	private static void displayType(DrinkTypeInterface displayable) {
		System.out.print(displayable.getDisplayableType());
	}
}