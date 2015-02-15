/* 
 * Values of a good isolation frameworkd - recursive fakes       
 * <u>The Art of Unit Testing</u>                             
 * Section 6.2 Values of good Isolation frameworks
 * The book lists the following values      
 * pdf-p. 130b-131a                                           
   1. Future proofing
   2. Usability
   3. Recursive fakes             
   4. Ignored arguments by default
   5. Wide faking                 
   6. Nonstrict behavior fakes    
   7. Nonstrict mocks                   

	 For the month of September 2013, I put great effort into learning JMockit,
	 a non-constrained Java faking framework. I read their extensive tutorial,
	 studied the Javadocs for the 3 major sub-APIs, and did examples. Through that
	 work, I satisfactorily answered to myself that "yes, JMockit posseses these
	 values.
	 
	 However, I felt a bit unsure about recursive fakes. So I investigated it further.
	 My investigation is why this copy of the actual JMockit tests resides here. This version of
	 ReentrantMockTest.java comes from ...
	 http://jmockit.googlecode.com/svn-history/r2276/trunk/main/test/mockit/ReentrantMockTest.java
	 ... which came out Jan 21, 2013 to resolve issue 270. Issue 270 log message states
	 
	     Changed the (unspecified in API docs) behavior of @Mock methods in the case   
	     where the mocked method is recursive, so that a recursive invocation also goes
	     through the mock method (fixes  issue 270 ).                                  
	 
	 I believe this provides a definitive answer of yes to my question of whether
	 JMockit provides recursive fakes.
	 
	 As an additional bonus, the test contained herein give me EXCELLENT examples
	 of how to write unit tests. Specifically, these test show exactly how to do a partial
	 mock of a real class. Partial mocking was something I tried to master as a part of
	 learning Event mocking back in ch. 5. Please see my extensive notes at the beginning
	 of source file EventRelatedJMTests.java.	 
	 
   6.3.1 Recursive fakes
   Recursive Fakes are a special behavior of fake objects in case of functions that return other
   objects. Those objects will always be fake , automatically. And any objects that functions in
   those automatically faked objects return, will be fake as well, recursively.
   pdf-p. 131b
   
   Anyway, I accomplished the goal to answer my question about recursive fakes AND
   I got some bonus benefits. Pretty cool ... and thank you qigong.
 */
   
/*
 * Copyright (c) 2006-2013 Rogério Liesenfeld
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit;

import java.io.*;

import static mockit.Mockit.*;

import org.junit.*;
import static org.junit.Assert.*;

public final class ReentrantMockTest
{
   public static class RealClass
   {
      String foo() { return "real value"; }
      static int staticRecursiveMethod(int i) { return i <= 0 ? 0 : 2 + staticRecursiveMethod(i - 1); }
      int recursiveMethod(int i) { return i <= 0 ? 0 : 2 + recursiveMethod(i - 1); }
   }

   @MockClass(realClass = RealClass.class)
   public static class AnnotatedMockClass
   {
      private static Boolean fakeIt;
      public RealClass it;

      @Mock(reentrant = true)
      public String foo()
      {
         if (fakeIt == null) {
            throw new IllegalStateException("null fakeIt");
         }
         else if (fakeIt) {
            return "fake value";
         }
         else {
            return it.foo();
         }
      }
   }

   @Test
   public void callMockMethod()
   {
      setUpMocks(AnnotatedMockClass.class);
      AnnotatedMockClass.fakeIt = true;

      String foo = new RealClass().foo();

      assertEquals("fake value", foo);
   }

   @Test
   public void callOriginalMethod()
   {
      setUpMocks(AnnotatedMockClass.class);
      AnnotatedMockClass.fakeIt = false;

      String foo = new RealClass().foo();

      assertEquals("real value", foo);
   }

   @Test(expected = IllegalStateException.class)
   public void calledMockThrowsException()
   {
      setUpMocks(AnnotatedMockClass.class);
      AnnotatedMockClass.fakeIt = null;

      new RealClass().foo();
   }

   @MockClass(realClass = Runtime.class)
   public static class MockRuntime
   {
      public Runtime it;
      private int runFinalizationCount;

      @Mock(reentrant = true, minInvocations = 3)
      public void runFinalization()
      {
         if (runFinalizationCount < 2) {
            it.runFinalization();
         }

         runFinalizationCount++;
      }

      @Mock(reentrant = true)
      public boolean removeShutdownHook(Thread hook)
      {
         if (hook == null) {
            //noinspection AssignmentToMethodParameter
            hook = Thread.currentThread();
         }

         return it.removeShutdownHook(hook);
      }

      @Mock(invocations = 1)
      public void runFinalizersOnExit(boolean value)
      {
         assertTrue(value);
      }
   }

   @Test
   public void callMockMethodForJREClass()
   {
      Runtime runtime = Runtime.getRuntime();
      setUpMocks(MockRuntime.class);

      runtime.runFinalization();
      runtime.runFinalization();
      runtime.runFinalization();

      assertFalse(runtime.removeShutdownHook(null));

      //noinspection deprecation
      Runtime.runFinalizersOnExit(true);
   }

   @MockClass(realClass = Runtime.class)
   public static class ReentrantMockForNativeMethod
   {
      @Mock(reentrant = true)
      public int availableProcessors() { return 5; }
   }

   @Test(expected = IllegalArgumentException.class)
   public void attemptToSetUpReentrantMockForNativeMethod()
   {
      setUpMocks(ReentrantMockForNativeMethod.class);
   }

   @MockClass(realClass = RealClass.class)
   static class MultiThreadedMock
   {
      public RealClass it;
      private static boolean nobodyEntered = true;

      @Mock(reentrant = true)
      public String foo() throws InterruptedException
      {
         String value = it.foo();

         synchronized (MultiThreadedMock.class) {
            if (nobodyEntered) {
               nobodyEntered = false;
               //noinspection WaitNotInLoop
               MultiThreadedMock.class.wait(5000);
            }
            else {
               MultiThreadedMock.class.notifyAll();
            }
         }

         return value.replace("real", "fake");
      }
   }

   @Test(timeout = 1000)
   public void twoConcurrentThreadsCallingTheSameReentrantMock() throws Exception
   {
      setUpMocks(MultiThreadedMock.class);

      final StringBuilder first = new StringBuilder();
      final StringBuilder second = new StringBuilder();

      Thread thread1 = new Thread(new Runnable()
      {
         public void run() { first.append(new RealClass().foo()); }
      });
      thread1.start();

      Thread thread2 = new Thread(new Runnable()
      {
         public void run() { second.append(new RealClass().foo()); }
      });
      thread2.start();

      thread1.join();
      thread2.join();

      assertEquals("fake value", first.toString());
      assertEquals("fake value", second.toString());
   }

   static final class RealClass2
   {
      int firstMethod() { return 1; }
      int secondMethod() { return 2; }
   }

   @Test
   public void reentrantMockForNonJREClassWhichCallsAnotherFromADifferentThread()
   {
      new MockUp<RealClass2>() {
         RealClass2 it;
         int value;

         @Mock(reentrant = true)
         int firstMethod() { return it.firstMethod(); }

         @Mock(reentrant = true)
         int secondMethod() throws InterruptedException
         {
            Thread t = new Thread() {
               @Override
               public void run() { value = it.firstMethod(); }
            };
            t.start();
            t.join();
            return value;
         }
      };

      RealClass2 r = new RealClass2();
      assertEquals(1, r.firstMethod());
      assertEquals(1, r.secondMethod());
   }

   @Test
   public void reentrantMockForJREClassWhichCallsAnotherFromADifferentThread()
   {
      System.setProperty("a", "1");
      System.setProperty("b", "2");

      new MockUp<System>() {
         String property;

         @Mock(reentrant = true)
         String getProperty(String key) { return System.getProperty(key); }

         @Mock(reentrant = true)
         String clearProperty(final String key) throws InterruptedException
         {
            Thread t = new Thread() {
               @Override
               public void run() { property = System.getProperty(key); }
            };
            t.start();
            t.join();
            return property;
         }
      };

      assertEquals("1", System.getProperty("a"));
      assertEquals("2", System.clearProperty("b"));
   }

   @Test
   public void mockFileAndForceJREToCallReentrantMockedMethod()
   {
      new MockUp<File>() {
         File it;

         @Mock(reentrant = true)
         boolean exists() { return !it.exists(); }
      };

      // Cause the JVM/JRE to load a new class, calling the mocked File#exists() method in the process:
      new Runnable() { public void run() {} };

      assertTrue(new File("noFile").exists());
   }

   static final class RealClass3
   {
      RealClass3 newInstance() { return new RealClass3(); }
   }

   @Test
   public void reentrantMockForMethodWhichInstantiatesAndReturnsNewInstanceOfTheMockedClass()
   {
      new MockUp<RealClass3>() {
         @Mock(reentrant = true)
         RealClass3 newInstance() { return null; }
      };

      assertNull(new RealClass3().newInstance());
   }

   @MockClass(realClass = RealClass.class)
   public static final class MockClassWithReentrantMockForRecursiveMethod
   {
      RealClass it;

      @Mock(reentrant = true)
      int recursiveMethod(int i) { return 1 + it.recursiveMethod(i); }

      @Mock(reentrant = true)
      static int staticRecursiveMethod(int i) { return 1 + RealClass.staticRecursiveMethod(i); }
   }

   @Test
   public void reentrantMockMethodForRecursiveMethods()
   {
      assertEquals(0, RealClass.staticRecursiveMethod(0));
      assertEquals(2, RealClass.staticRecursiveMethod(1));

      RealClass r = new RealClass();
      assertEquals(0, r.recursiveMethod(0));
      assertEquals(2, r.recursiveMethod(1));

      setUpMocks(MockClassWithReentrantMockForRecursiveMethod.class);

      assertEquals(1, RealClass.staticRecursiveMethod(0));
      assertEquals(1 + 2 + 1, RealClass.staticRecursiveMethod(1));
      assertEquals(1, r.recursiveMethod(0));
      assertEquals(4, r.recursiveMethod(1));
   }

   @Test
   public void mockUpThatProceedsIntoRecursiveMethod()
   {
      RealClass r = new RealClass();
      assertEquals(0, r.recursiveMethod(0));
      assertEquals(2, r.recursiveMethod(1));

      new MockUp<RealClass>() {
         @Mock
         int recursiveMethod(Invocation inv, int i)
         {
            int ret = inv.proceed();
            return 1 + ret;
         }
      };

      assertEquals(1, r.recursiveMethod(0));
      assertEquals(4, r.recursiveMethod(1));
   }
}