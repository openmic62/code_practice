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

public class Sky implements MoodListener {

    public void moodReceived(MoodEvent event) {
        if( event.mood() == Mood.HAPPY )
        {
            System.out.println( "Sun is shining!" );
        }
        else if( event.mood() == Mood.ANNOYED )
        {
            System.out.println( "Cloudy Skies!" );
        }
        else
        {
            System.out.println( "Lightning rains from the heavens!" );
        }
    }
    
}
