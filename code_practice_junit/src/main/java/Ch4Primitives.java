/**
* 1Z0-803 OCA Java SE 7 Study Guide
* Ch. 4, p. 145b
* Section 2, Item 1
* Declare and initialize variables
* Section 2, Item 2
* Differentiate between object reference variables and primitive variables
* Illustrates: Java's 8 primitive types, primitive variables, wrapper classes
* reflection, generics
*/

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.text.NumberFormat;

public class Ch4Primitives {

	// <mlr 130312 - begin: from http://stackoverflow.com/questions/15351963/specify-a-java-class-literal-programmatically-without-hard-coding-it-reflecti>
	public static Constructor<?> getPrimitiveSingleArgConstructor(Class<?> myC) {
		Constructor<?> lConstructor = null;
		for( Constructor<?> constructor : myC.getConstructors() ) {
			//System.out.println("    constructor-->" + constructor + "<--");
			if( constructor.getParameterTypes().length == 1 ) {
				Class<?> paramType = constructor.getParameterTypes()[0];
				if (paramType.isPrimitive()) { // <mlr 130318: gotta figure out how to chose the correct one for Double>
					lConstructor = constructor;
				}
			}
		}
		return lConstructor;
	}
	// <mlr 130312 - end: from http://stackoverflow.com/questions/15351963/specify-a-java-class-literal-programmatically-without-hard-coding-it-reflecti>

	// I attempted to learn Generics. Tried here, but failed miserably on my first try
	///public static <T> String primitiveToBinaryString(T pPrim) {
	public static <T extends Number> String primitiveToBinaryString(T pPrim) {
		boolean debug = false;  // use this flag to control debugging console output
		int primLength = 16;    // set a default value for the primitive length
		int count = 0;          // create a counter for looping through each bit of the prim
		long lv = 0L;           // a local variable we use in the loop (gets the value passed in via pPrim)
		/// String className = pPrim.getClass().getName(); // <mlr 130312: changed per stackoverflow recommendation>
		if (debug) System.out.println("pPrim: " + pPrim);
		try {
			//Class<?> myC = Class.forName(className); // <mlr 130312: changed per stackoverflow recommendation>
			Class<?> myC = pPrim.getClass(); // <mlr 130312: changed per stackoverflow recommendation>
			Constructor<?> myConstructorQ = getPrimitiveSingleArgConstructor(myC);
			Constructor<T> myConstructorT = (Constructor<T>) myConstructorQ;
			//Object o = myC.getConstructor(Double.TYPE).newInstance(myC.cast(pPrim)); // <mlr 130312: changed per stackoverflow recommendation>
			//Object o = myConstructorQ.newInstance(myC.cast(pPrim)); // <mlr 130312: changed per stackoverflow recommendation>
			//if ( true ) System.out.println("o-->" + o.getClass() + "<--"); // <mlr 130312: changed per stackoverflow recommendation>
			T t = myConstructorT.newInstance(myC.cast(pPrim));
			if ( debug ) System.out.println("t-->" + t + "<--");

			primLength = t.getClass().getField("SIZE").getInt(t);                // reflectively get the size of the prim wrapper
			if (debug) System.out.println("primLength-->" + primLength + "<--");
			/* <mlr 130318: begin - in seat 2J, AA flight 135 to Tokyo Haneda>
			* After all this, I tried lv = pPrim.longValue(); and it worked!
			* Looks like I didn't need all this reflection stuff. But that's cool.
			* Now I know how to do it!! :-)
			* <mlr 130925: begin found this article that as some good reflection info>
			  "Testing a private method through Reflection of JMockit"                                   
			  http://robertmarkbramprogrammer.blogspot.com.au/2013/03/testing-private-method-through.html
			  Note: I copied the good stuff in that article to OneNote->UnitTesting in case
			        the article goes offline.
			* <mlr 130925: end>
			*/
			lv = t.longValue();                                                  // every prim wrapper has longValue()
		} catch ( Exception e ) {
			System.out.println("Caught exception: " + e);
		}
		String s = "";
		while ( count++ < primLength ) {
			long sm = lv & 0x0000000000000001;
			lv >>= 1;
			s = sm + s;
			if ( count % 4 == 0 && count != primLength ) {
				s = " " + s;
			}
		}
		if (debug) System.out.println(s);
		return s;
	}

	// here's a non-reflective version that still uses generics
	public static <T extends Number> String primitiveToBinaryStringNR(T pPrim) {
		boolean debug = false;  // use this flag to control debugging console output
		int primLength = 16;    // set a default value for the primitive length
		int count = 0;          // create a counter for looping through each bit of the prim
		long lv = 0L;           // a local variable we use in the loop (gets the value passed in via pPrim)
		lv = pPrim.longValue();     // every prim wrapper has longValue()

		String s = "";
		while ( count++ < primLength ) {
			long sm = lv & 0x0000000000000001;
			lv >>= 1;
			s = sm + s;
			if ( count % 4 == 0 && count != primLength ) {
				s = " " + s;
			}
		}
		if (debug) System.out.println(s);
		return s;
	}

	public static String shortToBinaryString(short pShort) {
		int primLength = 16;
		int count = 0;
		String s = "";
		while ( count++ < primLength ) {
			short sm = (short) (pShort & 0x0001);
			pShort >>= 1;
			s = sm + s;
			if ( count % 4 == 0 && count != primLength ) {
				s = " " + s;
			}
		}
		return s;
	}

	public static String byteToBinaryString(byte pByte) {
		int primLength = 8;
		int count = 0;
		String s = "";
		while ( count++ < primLength ) {
			byte sm = (byte) (pByte & 0x01);
			pByte >>= 1;
			s = sm + s;
			if ( count % 4 == 0 && count != primLength ) {
				s = " " + s;
			}
		}
		return s;
	}

	public static void main (String[] args) {
		// Print section header
		System.out.println("****************************************");
		System.out.println("*********** int primitives *************");
		System.out.println("****************************************");

		System.out.println();

		// illustrate primitive variable declaration
		int gallons;
		System.out.println("int gallons;                                                          // declare an int variable");

		// illustrate use without initialization compiler error
		//System.out.println("gallons-->" + gallons + "<--"); // commented this because it causes a compiler error
		System.out.println("System.out.println(\"gallons-->\" + gallons + \"<--\");                   // use without initialization; this causes a compilation error");
		System.out.println("   error: variable gallons might not have been initialized");
		// solve the compiler error by initializing THEN using
		gallons = 22;
		System.out.println("gallons = 22;                                                         // initialize");
		System.out.println("System.out.println(\"gallons-->\" + gallons + \"<--\");                   // use after initialization compiles and runs without error");
		System.out.println("gallons-->" + gallons + "<--");

		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("********* boolean primitives ***********");
		System.out.println("****************************************");

		System.out.println();

		// illustrate boolean variable declaration
		boolean hasTurboCharger = true;
		System.out.println("boolean hasTurboCharger = true;                                       // declare and initialize a boolean variable");
		System.out.println("System.out.println(\"hasTurboCharger-->\" + hasTurboCharger + \"<--\");   // show the initialized value");
		System.out.println("hasTurboCharger-->" + hasTurboCharger + "<--");

		System.out.println();

		hasTurboCharger = false;
		System.out.println("hasTurboCharger = false;                                              // assign a new value to the variable");
		System.out.println("System.out.println(\"hasTurboCharger-->\" + hasTurboCharger + \"<--\");   // show the new value");
		System.out.println("hasTurboCharger-->" + hasTurboCharger + "<--");

		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("********** char primitives *************");
		System.out.println("****************************************");

		System.out.println();

		// show some examples of declaring char variables and initializing
		char c1 = 'S';
		System.out.println("char c1 = 'S';                                                        // capital 'S' as a character");
		char c2 = '\u0068';
		System.out.println("char c2 = '\\u0068';                                                   // h in Unicode");
		char c3 = 0x0065;
		System.out.println("char c3 = 0x0065;                                                     // e in hexadecimal");
		char c4 = 0154;
		System.out.println("char c4 = 0154;                                                       // l in octal");
		char c5 = (char) 131170;
		System.out.println("char c5 = (char) 131170;                                              // b, casted (131170-131072=98, 2^17=131072)");
		char c6 = (char) 131193;
		System.out.println("char c6 = (char) 131193;                                              // y, casted (131193-131072=121)");
		char c7 = '\'';
		System.out.println("char c7 = '\\'';                                                       // ' apostrophe special character");
		char c8 = 's';
		System.out.println("char c8 = 's';                                                        // lowercase 's' as a character");

		System.out.println();

		char[] autoDesignerArray = {c1, c2, c3, c4, c5, c6, c7, c8};
		System.out.println("char[] autoDesignerArray = {c1, c2, c3, c4, c5, c6, c7, c8};          // make an array of the characters");
		System.out.println("System.out.println(new String(autoDesignerArray) + \" Mustang\");       // dump it, I had to add the constructor call to make it work");
		System.out.println(new String(autoDesignerArray) + " Mustang");
		System.out.println();

		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("********** byte primitives *************");
		System.out.println("****************************************");

		System.out.println();

		// show implicit and explicit case from int to byte
		byte passengers = 4;
		System.out.println("byte passengers = 4;                                                 // implicit cast from int to byte variable");
		System.out.println("System.out.println(\"passengers-->\" + passengers + \"<--\");            // show the initialized value");
		System.out.println("passengers-->" + passengers + "<--");

		System.out.println();

		// show implicit and explicit case from int to byte
		byte doors = (byte) 2;
		System.out.println("byte doors = (byte) 2;                                               // explicit cast from int to byte variable");
		System.out.println("System.out.println(\"doors-->\" + doors + \"<--\");                      // show the initialized value");
		System.out.println("doors-->" + doors + "<--");

		System.out.println();

		for ( int i = 0; i < 256; i++ ) {
			if ( i == 7 ) continue; // omit the beep ascii char
			char cByte = (char) i; // cast the decimal value in "i" to a char
			byte b1 = (byte) cByte;          // now, cast the char to a short (16 bits to 16 bits, each bit set exactly the same)
			///System.out.printf( "char(%c): charValue(%05d): bin(%s): dec(%+6d)\n", cByte, (int) cByte, byteToBinaryString(b1), b1 );
			System.out.printf( "char(%c): charValue(%05d): bin(%s): dec(%+6d)\n", cByte, (int) cByte, primitiveToBinaryString(b1), b1 ); // switch to my generic, reflective method
		}

		// Print section header
		System.out.println("****************************************");
		System.out.println("********** short primitives ************");
		System.out.println("****************************************");

		System.out.println();

		// show implicit and explicit case from int to short
		short unLadenWeightInPounds = 2350;
		System.out.println("short unLadenWeightInPounds = 2350;                                  // implicit cast from int to short variable");
		System.out.println("System.out.println(\"unLadenWeightInPounds-->\" + unLadenWeightInPounds + \"<--\"); // show the initialized value");
		System.out.println("unLadenWeightInPounds-->" + unLadenWeightInPounds + "<--");

		System.out.println();

		// show implicit and explicit case from int to short
		short capacityInCu = (short) 427;
		System.out.println("short capacityInCu = (short) 427;                                    // explicit cast from int to short variable");
		System.out.println("System.out.println(\"capacityInCu-->\" + capacityInCu + \"<--\");        // show the initialized value");
		System.out.println("capacityInCu-->" + capacityInCu + "<--");

		System.out.println();

		// make a TreeMap<int, String> that I will use to illustrate short s = (short) new Character(32769);
		// ended up not needing a TreeMap, but I learned how to use one in the process :-)
		TreeMap<Integer, String> charsTM = new TreeMap<Integer, String>();
		charsTM.put(00000, "00000");
		charsTM.put(00001, "00001");
		charsTM.put(00002, "00002");
		charsTM.put(00003, "00003");
		charsTM.put(32766, "32766");
		charsTM.put(32767, "32767"); // max value of a short primitive: (2^15 - 1), bit 16, the sign bit, is zero
		charsTM.put(32768, "32768"); // min value of a short primitive: -(2^16), bit 16, the sign bit, goes to one here
		charsTM.put(32769, "32769");
		charsTM.put(32770, "32770");
		charsTM.put(32771, "32771");
		charsTM.put(65533, "65533");
		charsTM.put(65534, "65534");
		charsTM.put(65535, "65535"); // largest negative value of a short primitive

		short s1  = 32767;
		char  ch1 = 32768;

		Set<Integer> charKeys = charsTM.keySet();
		// loop through the boundary values I selected to show what's going on in memory
		for ( Integer i : charKeys ) {
			ch1 = (char) i.intValue(); // cast the decimal value in "i" to a char
			s1 = (short) ch1;          // now, cast the char to a short (16 bits to 16 bits, each bit set exactly the same)
			///System.out.printf( "char(%c): charValue(%05d): bin(%s): dec(%+6d)\n", ch1, (int) ch1, shortToBinaryString(s1), s1 );
			System.out.printf( "char(%c): charValue(%05d): bin(%s): dec(%+6d)\n", ch1, (int) ch1, primitiveToBinaryStringNR(s1), s1 ); // switch to my generic, non-reflective method
		}

		System.out.println();

		// I used this when creating my generic, reflective method
		if (true) {
			primitiveToBinaryString(         2147483648D);
			primitiveToBinaryString(         2147483648F);
			primitiveToBinaryString( (long)  2147483648L);
			primitiveToBinaryString( (int)   2147483647);
			primitiveToBinaryString( (short) 32767 );
			primitiveToBinaryString( (byte)  127 );
		} else {
			//primitiveToBinaryString("This is a string.");
		}

		// Print section header
		System.out.println("****************************************");
		System.out.println("*********** int primitives *************");
		System.out.println("****************************************");

		System.out.println();

		// show implicit and explicit caset from int to short
		int auctionPrice = 7800000;  // straight assignment within the limits of int primitive values
		System.out.println("int auctionPrice = 7800000;                                           // straight assignment within the limits of int primitive values");
		System.out.println("System.out.println(\"auctionPrice-->\" + auctionPrice + \"<--\");         // show the initialized value");
		System.out.println("auctionPrice-->" + auctionPrice + "<--");

		System.out.println();

		// show implicit cast from char to int
		char cylinders = '\u0008';  // straight assignment
		int cyl = cylinders;  // implicit cast from char to int
		System.out.println("char cylinders = '\\u0008';                                            // initialize a char (backspace)");
		System.out.println("System.out.println(\"cylinders-->\" + cylinders + \"<--\");               // show the initialized value");
		System.out.println("cylinders-->" + cylinders + "<--");
		System.out.println("int cyl = cylinders;                                                  // implicit cast from char to int");
		System.out.println("System.out.println(\"cyl-->\" + cyl + \"<--\");                           // show the value");
		System.out.println("cyl-->" + cyl + "<--");

		System.out.println();

		// show implicit cast from byte to int
		byte wheelbase = 90;  // straight assignment within the limits of byte primitive values
		int wBase = wheelbase;  // implicit cast from byte to int
		System.out.println("byte wheelbase = 90;                                                  // initialize a byte");
		System.out.println("System.out.println(\"wheelbase-->\" + wheelbase + \"<--\");               // show the initialized value");
		System.out.println("wheelbase-->" + wheelbase + "<--");
		System.out.println("int wBase = wheelbase;                                                // implicit cast from byte to int");
		System.out.println("System.out.println(\"wBase-->\" + wBase + \"<--\");                       // show the value");
		System.out.println("wBase-->" + wBase + "<--");

		System.out.println();

		// show implicit cast from short to int
		short horsepower = 250;  // straight assignment within the limits of short primitive values
		int hPower = horsepower;  // implicit cast from short to int
		System.out.println("short horsepower = 250;                                               // initialize a short");
		System.out.println("System.out.println(\"horsepower-->\" + horsepower + \"<--\");             // show the initialized value");
		System.out.println("horsepower-->" + horsepower + "<--");
		System.out.println("int hPower = horsepower;                                              // implicit cast from short to int");
		System.out.println("System.out.println(\"hPower-->\" + hPower + \"<--\");                     // show the value");
		System.out.println("wBase-->" + hPower + "<--");

		System.out.println();

		// show explicit cast from float to int
		int length = (int) 151.5F;  // must cast floats explicitly
		int powerToWeight = (int) 405.1D;  // must cast doubles explicitly
		System.out.println("int length = (int) 151.5F;                                            // must cast floats explicitly");
		System.out.println("System.out.println(\"length-->\" + length + \"<--\");                     // show the cast float value");
		System.out.println("length-->" + length + "<--");
		System.out.println("System.out.println(\"151.5F-->\" + 151.5F + \"<--\");");
		System.out.println("151.5F-->" + 151.5F + "<--");
		System.out.println("int powerToWeight = (int) 405.1D;                                     // must cast doubles explicitly");
		System.out.println("System.out.println(\"powerToWeight-->\" + powerToWeight + \"<--\");       // show the cast double value");
		System.out.println("powerToWeight-->" + powerToWeight + "<--");
		System.out.println("System.out.println(\"405.1D-->\" + 405.1D + \"<--\");");
		System.out.println("405.1D-->" + 405.1D + "<--");

		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("*********** long primitives ************");
		System.out.println("****************************************");

		System.out.println();

		// do some long shit
		long mustangBingResults = 146000000L;  // straight assignment using "L"
		long mustangGoogleResults = 40500000;  // straight assignment using "l"
		long mustangAmazonBookResults = (long) 5774;  // explicit cast
		long mustangAmazonManualResults = 2380;  // implicit cast
		
		System.out.println("long mustangBingResults = 146000000L;                                                             // straight assignment using \"L\"");
		System.out.println("System.out.println(\"mustangBingResults-->\" + mustangBingResults + \"<--\");                         // show the initialized long value");
		System.out.println("mustangBingResults-->" + mustangBingResults + "<--");
		System.out.printf( "dec(%+6d): bin(%s)\n", mustangBingResults, primitiveToBinaryString(mustangBingResults) ); 
		
		System.out.println();
		
		System.out.println("long mustangAmazonBookResults = (long) 5774;                                                      // explicit cast");
		System.out.println("System.out.println(\"mustangAmazonBookResults-->\" + mustangAmazonBookResults + \"<--\");             // show the initialized long value");
		System.out.println("mustangAmazonBookResults-->" + mustangAmazonBookResults + "<--");
		System.out.printf( "dec(%+6d): bin(%s)\n", mustangAmazonBookResults, primitiveToBinaryString(mustangAmazonBookResults) );
		
		System.out.println();
		
		System.out.println("long mustangAmazonManualResults = 2380;                                                           // implicit cast");
		System.out.println("System.out.println(\"mustangAmazonManualResults-->\" + mustangAmazonManualResults + \"<--\");         // show the initialized long value");
		System.out.println("mustangAmazonManualResults-->" + mustangAmazonManualResults + "<--");
		System.out.printf( "dec(%+6d): bin(%s)\n", mustangAmazonManualResults, primitiveToBinaryString(mustangAmazonManualResults) );

		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("********** float primitives ************");
		System.out.println("****************************************");

		System.out.println();

		// do some float shit
		float currentBid = 80100.99F;  // straight assignment using "F"
		float openingBid1 = 20000.01f;  // straight assignment using "f"
		float openingBid = 20000.00f;  // straight assignment using "f"
		float reservePrice = (float) 92000;  // explicit cast
		float myBid = 36000;  // implicit cast
		
		System.out.println("float currentBid = 80100.99F;                                                     // straight assignment using \"F\"");
		System.out.println("System.out.println(\"currentBid-->\" + currentBid + \"<--\");                         // show the initialized long value");
		System.out.println("currentBid-->" + currentBid + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", currentBid, primitiveToBinaryString(currentBid) ); 
		
		System.out.println();
		
		System.out.println("float openingBid1 = 20000.01f;                                                    // straight assignment using \"f\"");
		System.out.println("System.out.println(\"openingBid1-->\" + openingBid1 + \"<--\");                       // show the initialized long value");
		System.out.println("openingBid1-->" + openingBid1 + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", openingBid1, primitiveToBinaryString(openingBid1) ); 
		
		System.out.println();
		
		System.out.println("float openingBid = 20000.00f;                                                     // straight assignment using \"f\"");
		System.out.println("System.out.println(\"openingBid-->\" + currentBid + \"<--\");                         // show the initialized long value");
		System.out.println("openingBid-->" + openingBid + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", openingBid, primitiveToBinaryString(openingBid) ); 
		
		System.out.println();
		
		System.out.println("float reservePrice = (float) 92000;                                               // explicit cast to float");
		System.out.println("System.out.println(\"reservePrice-->\" + reservePrice + \"<--\");                     // show the initialized long value");
		System.out.println("reservePrice-->" + reservePrice + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", reservePrice, primitiveToBinaryString(reservePrice) ); 
		
		System.out.println();
		
		System.out.println("float myBid = 36000;                                                              // implicit cast to float");
		System.out.println("System.out.println(\"myBid-->\" + myBid + \"<--\");                                   // show the initialized long value");
		System.out.println("myBid-->" + myBid + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", myBid, primitiveToBinaryString(myBid) ); 
		
		System.out.println();
		
		// Print section header
		System.out.println("****************************************");
		System.out.println("********** double primitives ***********");
		System.out.println("****************************************");

		System.out.println();

		// do some double shit
		double currentBidD = 80100.99F;  // straight assignment using "F"
		double openingBid1D = 20000.01f;  // straight assignment using "f"
		double openingBidD = 20000.00f;  // straight assignment using "f"
		double reservePriceD = (double) 92000;  // explicit cast
		double myBidD = 36000;  // implicit cast
		
		System.out.println("double currentBidD = 80100.99F;                                                     // straight assignment using \"F\"");
		System.out.println("System.out.println(\"currentBidD-->\" + currentBidD + \"<--\");                         // show the initialized long value");
		System.out.println("currentBidD-->" + currentBidD + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", currentBidD, primitiveToBinaryString(currentBidD) ); 
		
		System.out.println();
		
		System.out.println("double openingBid1D = 20000.01f;                                                    // straight assignment using \"f\"");
		System.out.println("System.out.println(\"openingBid1D-->\" + openingBid1D + \"<--\");                       // show the initialized long value");
		System.out.println("openingBid1D-->" + openingBid1D + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", openingBid1D, primitiveToBinaryString(openingBid1D) ); 
		
		System.out.println();
		
		System.out.println("double openingBidD = 20000.00f;                                                     // straight assignment using \"f\"");
		System.out.println("System.out.println(\"openingBidD-->\" + currentBid + \"<--\");                         // show the initialized long value");
		System.out.println("openingBidD-->" + openingBidD + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", openingBidD, primitiveToBinaryString(openingBidD) ); 
		
		System.out.println();
		
		System.out.println("double reservePriceD = (double) 92000;                                               // explicit cast to double");
		System.out.println("System.out.println(\"reservePriceD-->\" + reservePriceD + \"<--\");                     // show the initialized long value");
		System.out.println("reservePriceD-->" + reservePriceD + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", reservePriceD, primitiveToBinaryString(reservePriceD) ); 
		
		System.out.println();
		
		System.out.println("double myBidD = 36000;                                                              // implicit cast to double");
		System.out.println("System.out.println(\"myBidD-->\" + myBidD + \"<--\");                                   // show the initialized long value");
		System.out.println("myBidD-->" + myBidD + "<--");
		System.out.printf( "dec(%+8f): bin(%s)\n", myBidD, primitiveToBinaryString(myBidD) ); 
		System.out.println();

		// Print section header
		System.out.println("****************************************");
		System.out.println("******** floating-point math************");
		System.out.println("****************************************");

		System.out.println();

		// illustrate float precision concerns
		float a = 19801216.0F;   // a large float
		float b = 20120307.12F;  // a largeer float
		float c = a + b;         // add 'em up
		// format the float into US currency format
		String d = NumberFormat.getCurrencyInstance().format(c);
		///System.out.println(d);
		// print the number directly as it is stored
		///System.out.println(c);
		
		System.out.println("// illustrate float precision concerns");
		System.out.println("float a = 19801216.0F;   // a large float");
		System.out.println("float b = 20120307.12F;  // a larger float");
		System.out.println("float c = a + b;         // add 'em up");
		System.out.println("// format the float into US currency format");
		System.out.println("String d = NumberFormat.getCurrencyInstance().format(c);");
		System.out.println("System.out.println(\"d-->d<--\");");
		System.out.println("d-->" + d + "<--");
		System.out.println("// print the number directly as it is stored");
		System.out.println("System.out.println(\"c-->c<--\");");
		System.out.println("c-->" + c + "<--");
		
		System.out.printf( "dec(%+8f): bin(%s)\n", c, primitiveToBinaryString(c) ); 
		
		System.out.println();

		// illustrate double psuedo remedy
		double x = 19801216.0;   // a double
		double y = 20120307.12;  // another double
		double z = x + y;         // add 'em up
		// format the double into US currency format
		String s = NumberFormat.getCurrencyInstance().format(z);
		///System.out.println(s);
		// print the number directly as it is stored
		///System.out.println(z);
		
		System.out.println("// illustrate double psuedo remedy");
		System.out.println("double x = 19801216.0;   // a double - no \"f\" on the end");
		System.out.println("double y = 20120307.12;  // another double - no \"f\" on the end");
		System.out.println("double z = x + y;        // add 'em up");
		System.out.println("// format the double into US currency format");
		System.out.println("String s = NumberFormat.getCurrencyInstance().format(z);");
		System.out.println("System.out.println(\"s-->s<--\");");
		System.out.println("s-->" + s + "<--");
		System.out.println("// print the number directly as it is stored");
		System.out.println("System.out.println(\"z-->z<--\");");
		System.out.println("z-->" + z + "<--");
		
		System.out.printf( "dec(%+12.9f): bin(%s)\n", z, primitiveToBinaryString(z) ); 
		
		System.out.println();
		
		// Print section header
		System.out.println("****************************************");
		System.out.println("******** primitive wrappers ************");
		System.out.println("****************************************");

		System.out.println();
		
		// create an Integer and initialize it to 5
		Integer valueA = new Integer(5);
		// set a primitive with the value in the Integer wrapper
		int num = valueA.intValue();
		// Auto-boxing in action
		Integer valueB = num;
		
		System.out.println("// create an Integer and initialize it to 5");
		System.out.println("Integer valueA = new Integer(5);");
		System.out.println("System.out.println(\"valueA-->\" + valueA + \"<--\");");
		System.out.println("valueA-->" + valueA + "<--");

		System.out.println();
		
		System.out.println("// set a primitive with the value in the Integer wrapper");
		System.out.println("int num = valueA.intValue();");
		System.out.println("System.out.println(\"num-->\" + num + \"<--\");");
		System.out.println("num-->" + num + "<--");

		System.out.println();
		
		System.out.println("// Auto-boxing in action");
		System.out.println("Integer valueB = num;");
		System.out.println("System.out.println(\"valueB-->\" + valueB + \"<--\");");
		System.out.println("valueB-->" + valueB + "<--");

	}
}
