/*
<mlr 131214: begin - GOOS, p. 141a; they use an inner enum. So I Googled a little to beef up my skills>
http://www.avajava.com/tutorials/lessons/how-do-i-use-the-enum-type-with-a-constructor.html
<mlr 131214: begin - GOOS, p. 141a; they use an inner enum. So I Googled a little to beef up my skills>

 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-beta9.jar;lib\log4j-core-2.0-beta9.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set ACL3=lib\commons-lang3-3.1.jar
 set CLASSPATH=%ACL3%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\EnumDemo.java
 java  -cp %CLASSPATH%;%SC%;%TC% EnumDemo

*/

public class EnumDemo {

	public enum Food {
		HAMBURGER(7), FRIES(2), HOTDOG(3), ARTICHOKE(4);

		Food(int price) {
			this.price = price;
		}

		private final int price;

		public int getPrice() {
			return price;
		}
	}

	public static void main(String[] args) {
		for (Food f : Food.values()) {
			System.out.print("Food: " + f + ", ");

			if (f.getPrice() >= 4) {
				System.out.print("Expensive, ");
			} else {
				System.out.print("Affordable, ");
			}

			switch (f) {
			case HAMBURGER:
				System.out.println("Tasty");
				continue;
			case ARTICHOKE:
				System.out.println("Delicious");
				continue;
			default:
				System.out.println("OK");
			}
		}

	}

}