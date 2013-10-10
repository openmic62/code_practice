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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MrHappyObject {

    private Mood _mood = Mood.HAPPY;
    private List<MoodListener> _listeners = new ArrayList<MoodListener>();
    
    public synchronized void receivePinch() {
        if( _mood == Mood.HAPPY ) {
            _mood = Mood.ANNOYED;
            _fireMoodEvent();
        } else {
            _mood = Mood.ANGRY;
            _fireMoodEvent();
        }
    }

    public synchronized void receiveHug() {
        if( _mood == Mood.ANGRY ) {
            _mood = Mood.ANNOYED;
            _fireMoodEvent();
        } else {
            _mood = Mood.HAPPY;
            _fireMoodEvent();
        }
    }

    public synchronized void addMoodListener( MoodListener l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeMoodListener( MoodListener l ) {
        _listeners.remove( l );
    }
     
    private synchronized void _fireMoodEvent() {
        MoodEvent mood = new MoodEvent( this, _mood );
        Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (MoodListener) listeners.next() ).moodReceived( mood );
        }
    }
}
