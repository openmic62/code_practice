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

// <mlr 130922: this is very Enumeration-ish>
public class Mood {

    public static final Mood HAPPY   = new Mood( "happy" );
    public static final Mood ANNOYED = new Mood( "annoyed" );
    public static final Mood ANGRY   = new Mood( "angry" );
    
    private String _mood;
    
    public String toString() {
        return _mood;
    }
    
    private Mood( String mood ) {
        _mood = mood;
    }
    
}
