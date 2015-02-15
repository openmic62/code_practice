/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 19 Generating the Log Message<br>
 *    The end of the example<br>
 *    p. 223a
 *
 * LoggingXMPPFailureReporter holds the responsibility for reporting an XMPP
 * failure event by writing an entry to a log file.

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
 
 C:\Users\michaelr>h:
 ***** Abstract the ENV
 H:\> copy and paste the following lines straight onto the command line
      NOTE: 131117 - SIH is short for SYSINTERNALS_HOME 
                     (usually =C:\Users\Mike\Downloads\SysinternalsSuite in system ENV)
 
 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set JML=lib\jmock-legacy-2.6.0.jar;lib\cglib-nodep-2.2.3.jar;lib\objenesis-1.0.jar
 set CLASSPATH=%JML%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-rc1.jar;lib\log4j-core-2.0-rc1.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set ACL3=lib\commons-lang3-3.1.jar
 set CLASSPATH=%ACL3%;%CLASSPATH%
 set ACIO=lib\commons-io-2.4.jar
 set CLASSPATH=%ACIO%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\xmpp\LoggingXMPPFailureReporter.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\integration\LoggingXMPPFailureReporterTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.integration.LoggingXMPPFailureReporterTests
 ant runtest -DtestClass=LoggingXMPPFailureReporterTests
 
 ***** build the LoggingXMPPFailureReporter source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set LXFR_FILES=LoggingXMPPFailureReporter.java
 H:\student\code_practice_junit>echo %LXFR_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%LXFR_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\LoggingXMPPFailureReporter.java
                                
 ***** build the Tests LoggingXMPPFailureReporterTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\integration\LoggingXMPPFailureReporterTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.LoggingXMPPFailureReporterTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=LoggingXMPPFailureReporterTests

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=LoggingXMPPFailureReporterTests
 */ 
package auctionsniper.tests.integration;

import auctionsniper.xmpp.LoggingXMPPFailureReporter;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class LoggingXMPPFailureReporterTests {
	
	@Rule 
	public  final JUnitRuleMockery           context    = new JUnitRuleMockery()
		{{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};
	private final Logger                     logger     = context.mock(Logger.class);
	private final LoggingXMPPFailureReporter translator = new LoggingXMPPFailureReporter(logger);
	
	@Test
	//@Ignore
	public void 
	writesMessageTranslationFailureToLog() {
		context.checking(new Expectations() {{
			oneOf(logger).severe("<auction id> "
			                   + "Could not translate message \"bad message\" "
			                   + "because \"java.lang.Exception: bad\"");
	  }});
	  translator.cannotTranslateMessage("auction id", "bad message", new Exception("bad"));
	}
}