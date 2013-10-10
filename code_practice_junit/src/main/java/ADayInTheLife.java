/*
 Mr. Happy Object teaches custom events
 http://www.javaworld.com/javaworld/javaqa/2002-03/01-qa-0315-happyevent.html?

 * Using dynamic mock to test an event listener - Example
 * <u>The Art of Unit Testing</u>
 * Section 5.4.1 Testing an event listener
 * This file contrives an interface that will enable a Java version of
 * the .NET stuff illustrating dynamically created fake objects
 * who test an object's ability to listen for events.
 * pdf-p. 117a
 *
 */

public class ADayInTheLife {

    public static void main( String [] args ) {
        MrHappyObject happy = new MrHappyObject();
        MoodListener  sky   = new Sky();
        MoodListener  birds = new FlockOfBirds();
        happy.addMoodListener( sky );
        happy.addMoodListener( birds );
        
        System.out.println( "Let's pinch MrHappyObject and find out what happens:" );
        happy.receivePinch();
        
        System.out.println("");
        System.out.println( "Let's hug MrHappyObject and find out what happens:" );
        happy.receiveHug();
        
        System.out.println("");
        System.out.println( "Now, let's make MrHappyObject angry and find out what happens:" );
        System.out.println("");
        System.out.println("one pinch:");
        happy.receivePinch();
        System.out.println("");
        System.out.println("second pinch, look out:");
        happy.receivePinch();
    }
    
}
