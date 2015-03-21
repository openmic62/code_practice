package com.canon.vi.newsarticlereader.test;

//import org.junit.Test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
//import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.canon.vi.newsarticlereader.MainActivity;

//public class MainActivityTest extends BaseActivityUnitTestCase<MainActivity> {
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	private static final String TAG = "MainActivityTest";

	private MainActivity mActivity;

	public MainActivityTest() {
		///super(null);
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				MainActivity.class);
		startActivity(intent, null, null);
		//mActivity = getActivity();
		mActivity = (MainActivity) getActivity();
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// @Test
	// @UiThreadTest
	// @Ignore
	@SmallTest
	public void testNothing() {
		assertEquals(true, true);
	}

/**
 * This is what I had, but it didn't work 150319. Then I Google'd
 * "activityunittestcase example" and found
 * Android Testing Example – Helloworld with ActivityUnitTestCase
 * http://wptrafficanalyzer.in/blog/android-testing-example-helloworld-with-activityunittestcase/
 * From that web page I got the ActivityUnitTestCaseApp and pActivityUnitTestCaseTests
 * projects that succeeded running as "Android JUnit Test". So, I'm reverse engineering.
 * 	@Test
	// /@UiThreadTest
	// @Ignore
	public void testNothing() {
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				MainActivity.class);
		//startActivity(intent, null, null);
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				mActivity = startActivity(new Intent(Intent.ACTION_MAIN), null,
						null);
			}
		});

		mActivity = getActivity();
		// assertEquals(true, true);
	}
*/
}
