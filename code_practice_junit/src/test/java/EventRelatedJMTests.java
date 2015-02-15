/**

 * Using dynamic mock to test an event listener - Example
 * <u>The Art of Unit Testing</u>
 * Section 5.4.1 Testing an event listener
 * This file contrives an interface that will enable a Java version of
 * the .NET stuff illustrating dynamically created fake objects
 * who test an object's ability to listen for events.
 * pdf-p. 117a

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
   
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar

 ***** build the Mr. Happy mood events example files
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>set MOOD_FILES=ADayInTheLife.java FlockOfBirds.java Mood.java MoodEvent.java MoodListener.java MrHappyObject.java Sky.java
 J:\src\main\java>echo %MOOD_FILES%
 J:\src\main\java>javac -Xlint:unchecked -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes %MOOD_FILES%

 ***** Run the Mr. Happy mood events example
 J:\src\main\java>java -cp ..\..\..\target\classes ADayInTheLife

 ***** build the Tests EventRelatedJMTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes EventRelatedJMTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore EventRelatedJMTests
 */ 
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import mockit.Mocked;
import mockit.NonStrict;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
///@RunWith(JUnit4.class)
@RunWith(JMockit.class)
public class EventRelatedJMTests {

	/* Two issues arose while figuring out how to mock the triggering of an event.
	   I did this figuring out in mrHappy_whenPinched_firesMoodEventEvenWithNoListenersRegistered.
	   Issues: 1) how to mock just one method (_fireMoodEvent) in Mr. Happy
	           2) because _fireMoodEvent is private, how to get the mock to call it
	   
	   Background: I was learning from the Art of Unit Testing book,in section
	   5.4.2 Testing whether an event was triggered, pdf-p. 119a. The book gave
	   "Listing 5.19 Using an anonymous delegate to register to an event". I needed
	   a Java way to do the same event simulation the author did in .NET. So, through Googled I found
	   the Mr. Happy event exmaple. That example was very good and it helped my through
	   the Java event model learning curve, even though .NET uses delegate and Java does not.

	   "Mr. Happy Object teaches custom events"
	   http://www.javaworld.com/javaworld/javaqa/2002-03/01-qa-0315-happyevent.html?
	   In this example the event sequencing occurs as follows:
	   1) The app "ADayInTheLife" calls "pinch" or "hug" on the Mr. Happy object (mhObj)
	   2) The mhObj called methods (receivePinch() or receiveHug()) in turn call the
	      mhObj private method _fireMoodEvent().
	   3) If "Sky" or "FlockOfBirds" listeners are registerd, then the _fireMoodEvent()
	      calls the MoodListener interface method moodReceived() implemented by each listener.
	   
	   The test objective is to "prove that someone is Triggering an event". After studying
	   the Mr. Happy example, I decided that mhObj triggers the MoodEvent when he executes
	   a call to either of his public methods receivePinch() or receiveHug(). Specifically, in
	   each of those methods, mhObj "Triggers an event" by calling his private method 
	   _fireMoodEvent().
	   
	   So, my test reasoning became, "I want to execute REAL Mr. Happy production code in my test
	   EXCEPT for any calls to his private method _fireMoodEvent(). In other words, I needed to 
	   find a way to partially mock ONLY calls to Mr. Happy's private method _fireMoodEvent(). If
	   I could show that REAL Mr. Happy code made a call to a STUBBED _fireMoodEvent(), then that
	   would "prove that someone is Triggering an event".
	   
	   Going back to the 2 issues, Googling provided some help figuring out soltuions:
	   
	   Issue 1) how to mock just one method (_fireMoodEvent) in Mr. Happy
	   Newbie Question 1: Partial Mocks missing Coverage, hanging in Eclipse
	   https://groups.google.com/forum/#!searchin/jmockit-users/an$20instance$20real$20method$20with$20an$20static$20mock/jmockit-users/YLow5724oqk/XPC9fUOiFT8J
	   In this google group Q&A, Rogerio's answer gave the partial mock solution
	   
	   	   > The best way I have found to test it so far is to partially mock the
	   	   > SUT and create then instance it.  It looks something like this
	   	   >
	   	   > @Mocked({fieldToMock, methodToMock})
	   	   > MyModule mockModule;
          
	   	   You can only mock methods and constructors, not fields; the
	   	   "fieldToMock" entry above will simply be ignored.
	   	   
	   Based on this info, I added a "similarly mocked field" to my Expections annoymous class.
	   But, the limited scope therein prohibited access by the replay portion of the test. 
	   So, I had to declare the "similarly mocked field" as a test method parameter. Specifically,
	       @Mocked(methods="_fireMoodEvent") final MrHappyObject mockMrHappy,
	   
	   Declared as a test method parameter, "mockMrHappy" now became accessible to everthing in 
	   the test method.
	   
	   Next, I encounterd the 2nd issue that manifest as a compiler error
	       ->error: _fireMoodEvent() has private access in MrHappyObject<-

	   Issue 2) because _fireMoodEvent is private, how to get the mock to call it
	   Mock a private method of the class under test in JMockit
	   http://stackoverflow.com/questions/17218726/mock-a-private-method-of-the-class-under-test-in-jmockit
	   Here is the good stuff from that stackoverflow article
         new NonStrictExpectations(archivingBean) {{ // <== note the argument here
            invoke(archivingBean, "getConnection"); result = connection;
         }};
     I followed this template in my code below and the test started working. At that point, I had
     the mechanics of the test working.
     
     The JMockit tutorial talks about this approach at ...
	   http://jmockit.googlecode.com/svn/trunk/www/tutorial/BehaviorBasedTesting.html#deencapsulation
	   
	   ONE QUESTION: How can I convince myself (prove) that the REAL Mr. Happy code gets executed
	                 except for the one method that I do want to stub (_fireMoodEvent)?
	                 
	   Googled: "jmockit prove partial mocking" found ...
	   Debug Partial Mock in JMockit
	   http://stackoverflow.com/questions/4819257/debug-partial-mock-in-jmockit
	   which leads to ...
	   https://groups.google.com/forum/#!msg/jmockit-users/m1fN6lp8icI/8Y8xrbVME08J
	   
	   Google also found ...
	   Mocking part of class under test
	   https://groups.google.com/forum/#!topic/jmockit-users/MDc0Jd5ZkpE
	   
	   Googled: "jmockit how do I confirm a partial mock" found ...
	   "Hottest 'jmockit' Answers - Stack Overflow"  ... which included (first presented) ...
	   "Invoking a private method via JMockit to test result" ... at URL ...
	   http://stackoverflow.com/questions/15595765/invoking-a-private-method-via-jmockit-to-test-result/15608182#15608182

	   However, I think this is OPPOSITE of what I want to do because Robert Mark Bram states, 
    	   "... I don't think I want to mock the class.. 
    	    just test the result of calling the private method."
     Further reading in the SO article show RMB figured it out. He wrote up a very nice
     summary of what he learned here ...
     "Testing a private method through Reflection of JMockit"
     http://robertmarkbramprogrammer.blogspot.com.au/2013/03/testing-private-method-through.html
     RBM wrote, 
         "JMockit's de-encapsulation is intended to allow mocking of private methods,
         but it can be used to invoke them directly as well.
            
   */
	 
	@Test
	public void mrHappy_whenPinched_firesMoodEventEvenWithNoListenersRegistered
		/// Use the next line for simple test case
		///() {
		/// Use the next line for static partial mocking declared as test method paramter
		(@Mocked(methods="_fireMoodEvent") final MrHappyObject mockMrHappy,
		 @Mocked                           final Sky           stubSky) {
	 	 	
		/*
		// set up the partial mock expectations for the dependency on the event listener
		new Expectations() {
			/// Use the next line for static partial mocking declared as Expectations field.
			/// Chosing this, however, makes mockMrHappy unavailable outside the Expectations
			///@Mocked(methods="_fireMoodEvent") final MrHappyObject mockMrHappy;
			{
				/// <mlr 130924: this yields a compiler error ->error: _fireMoodEvent() has private access in MrHappyObject<-
				mockMrHappy._fireMoodEvent();
			}
		};
		*/
		
		///new NonStrictExpectations(mockMrHappy) {{ // <== note the argument here
		new Expectations(mockMrHappy) {{ // <== note the argument here
			///mockMrHappy.addMoodListener(stubSky); times=1;
    	invoke(mockMrHappy, "_fireMoodEvent");
		}};
		
		// instantiate the CUT
		///MrHappyObject mrHappy = new MrHappyObject();

		// exercise the CUT's ability to fire a MoodEvent
		///mockMrHappy.addMoodListener(stubSky);
		mockMrHappy.receivePinch();
		
		new Verifications() {{
			//invoke(mockMrHappy, "_fireMoodEvent");times=1;
		}};
	}

	@Test
	public void mrHappy_whenHuggedFiresMoodEvent_registeredListenerReceivesNotification
		(@Mocked    final MoodListener     mockMoodListener) {
	 	 	
		// set up the mock expectations for the dependency on the event listener
		new Expectations() {
			MoodEvent stubMoodEvent; // need a stub argument for the call to moodReceived()
			{
				mockMoodListener.moodReceived(withAny(stubMoodEvent));
			}
		};
		
		// instantiate the CUT
		MrHappyObject mrHappy = new MrHappyObject();

		// exercise the CUT's ability to handle filenames that are too short
		mrHappy.addMoodListener(mockMoodListener);
		mrHappy.receiveHug();
	}

	@Test
	public void mrHappy_whenPinchedFiresMoodEvent_registeredListenerReceivesNotification
		(@Mocked    final MoodListener     mockMoodListener) {
	 	 	
		// set up the mock expectations for the dependency on the event listener
		new Expectations() {
			MoodEvent stubMoodEvent; // need a stub argument for the call to moodReceived()
			{
				mockMoodListener.moodReceived(withAny(stubMoodEvent));
			}
		};
		
		// instantiate the CUT
		MrHappyObject mrHappy = new MrHappyObject();

		// exercise the CUT's ability to handle filenames that are too short
		mrHappy.addMoodListener(mockMoodListener);
		mrHappy.receivePinch();
	}
}