/*
 * http://www.javacodegeeks.com/2010/09/xmpp-im-with-smack-for-java.html
 *
 * How to build a test API for my application - Example
 * <u>Growing Object-Oriented Software, Guided by Tests<br>
 *    Part III: A Worked Example<br></u>
 * 		Ch. 11 Passing the First Test<br>
 *    The Fake Auction<br>
 *    p. 93b - 94a
 *
 * The book starts by creating a walking skeleton. They caution several times
 * that this usually takes a long time ... and they are right. 
 *
 * I take the approach to learning of studying their explanation and requirements first,
 * then writing my own code, then going back and comparing to their code in the book.
 * I've been pleasantly surprised how close my code comes to theirs. I usually see the
 * necessary parts, but implement them differently.
 * 
 * The task at hand requires me to get running the walking skeleton. Part of the
 * skeleton contains a FakeAuctionServer that stubs out the "Southabee's" auction
 * server. The scheme uses an XMPP server, "Openfire", and the API "Smack" with its
 * library jars.
 *
 * I studied the Smack API docs and have a pretty good idea of how use it. But,
 * as always, there's a shit load of details to take care of before I get this
 * part of FakeAuctionServer running. So, I found this Chat example online by
 * Googling "smack 2-way conversation example". I'll get this working outside
 * the FakeAuctionServer, then bring it in to get it running.

 -------------------------------------------------------------------------------

 --------------- which java ------------------
 for %i in (java.exe) do @echo.   %~$PATH:i
 --------------- which java ------------------
 
 For JMockit java.exe MUST be of JDK (with attach.dll) like
   C:\Program Files\Java\jdk1.7.0_02\bin\java.exe
 NOT the JRE one (missing attach.dll) Java automatically installs at
   C:\Windows\system32\java.exe
   
 Work the System PATH variable to ensure correct java.exe gets used.
 PATH = (system) PATH + (user) PATH - in that specific order
 
 -------------------------------------------------------------------------------
   
 C:\Users\michaelr>
 C:\Users\michaelr>H:
 H:\>set CLASSPATH

 ***** build the template example production source files
 H:\>cd student\code_practice_junit\src\main\java
 H:\student\code_practice_junit\src\main\java>l
 H:\student\code_practice_junit\src\main\java>set CLASSPATH=..\..\..\lib\smack.jar;..\..\..\lib\smackx-debug.jar;..\..\..\lib\smackx.jar;..\..\..\target\classes
 H:\student\code_practice_junit\src\main\java>echo %CLASSPATH%
 H:\student\code_practice_junit\src\main\java>set XMPP_FILES=XmppManager.java XmppTest.java
 H:\student\code_practice_junit\src\main\java>echo %XMPP_FILES%
 H:\student\code_practice_junit\src\main\java>javac -cp %CLASSPATH% -d ..\..\..\target\classes %XMPP_FILES%

 ***** run the XmppTest application
 WITHOUT DEBUG
 H:\student\code_practice_junit\src\test\java>java -cp %CLASSPATH% XmppTest auction
 
 WITH DEBUG
 H:\student\code_practice_junit\src\test\java>java -cp %CLASSPATH% -Dsmack.debugEnabled=true XmppTest auction
 */ 

public class XmppTest {

	public static void main(String[] args) throws Exception {

		boolean isDefaultChat = true;
		if (args.length == 1) {
		 	isDefaultChat = false;
		}
		
		String buddyJID;
		String buddyName;
		String message;
		String username;
		String password;
		
		if (isDefaultChat) {
			username  = "testuser1";
			password  = "testuser1pass";	
			buddyJID  = "testuser2@roco-3";
			buddyName = "testuser2";
			message   = "Hello, mate.";
		} else {
			username  = "auction-item-54321";
			password  = "auction";
			buddyJID  = "sniper@roco-3";
			buddyName = "Sniper Bitch";
			message   = "Please give me an auction command...";
		}

		XmppManager xmppManager = new XmppManager("roco-3", 5222);

		xmppManager.init();
		xmppManager.performLogin(username, password);
		xmppManager.setStatus(true, "Hello everyone");

		xmppManager.createEntry(buddyJID, buddyName);

		xmppManager.sendMessage(message, buddyJID);

		boolean isRunning = true;

		while (isRunning) {
			Thread.sleep(50);
		}

		xmppManager.destroy();

	}

}