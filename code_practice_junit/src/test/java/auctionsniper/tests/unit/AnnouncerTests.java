package auctionsniper.tests.unit;
//package org.jmock.example.announcer;

import auctionsniper.Announcer;

import java.util.EventListener;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AnnouncerTests {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	public static class CheckedException extends Exception {}
	
	public interface Listener extends EventListener {
		public void eventA();
		public void eventB();
		public void eventWithArguments(int a, int b);
		public void badEvent() throws CheckedException;
	}
	
	Announcer<Listener> announcer = Announcer.to(Listener.class);
	
	Listener listener1 = context.mock(Listener.class, "listener1");
	Listener listener2 = context.mock(Listener.class, "listener2");
	
	@Before
    public void setUp() {
		announcer.addListener(listener1);
		announcer.addListener(listener2);
	}
	
	@Test
	public void testAnnouncesToRegisteredListenersInOrderOfAddition() {
		final Sequence eventOrder = context.sequence("eventOrder");
		
		context.checking(new Expectations() {{
			oneOf (listener1).eventA(); inSequence(eventOrder);
			oneOf (listener2).eventA(); inSequence(eventOrder);
			oneOf (listener1).eventB(); inSequence(eventOrder);
			oneOf (listener2).eventB(); inSequence(eventOrder);
		}});
		
		announcer.announce().eventA();
		announcer.announce().eventB();
	}
	
	@Test
	public void testPassesEventArgumentsToListeners() {
		context.checking(new Expectations() {{
			oneOf (listener1).eventWithArguments(1, 2);
			oneOf (listener2).eventWithArguments(1, 2);
			oneOf (listener1).eventWithArguments(3, 4);
			oneOf (listener2).eventWithArguments(3, 4);
		}});
		
		announcer.announce().eventWithArguments(1, 2);
		announcer.announce().eventWithArguments(3, 4);
	}
	
	@Test
	public void testCanRemoveListeners() {
		announcer.removeListener(listener1);
		
		context.checking(new Expectations() {{
			oneOf (listener2).eventA();
		}});
		
		announcer.announce().eventA();
	}
	
	@Test
	public void testDoesNotAllowListenersToThrowCheckedExceptions() throws Exception {
		context.checking(new Expectations() {{
			allowing (listener1).badEvent(); will(throwException(new CheckedException()));
		}});
		
		try {
			announcer.announce().badEvent();
			Assert.fail("should have thrown UnsupportedOperationException");
		}
		catch (UnsupportedOperationException expected) {}
	}
}
