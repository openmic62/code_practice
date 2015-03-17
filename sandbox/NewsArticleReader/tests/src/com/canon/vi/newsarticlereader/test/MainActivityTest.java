package com.canon.vi.newsarticlereader.test;

import org.junit.Test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;

import com.canon.vi.newsarticlereader.MainActivity;

//public class MainActivityTest extends BaseActivityUnitTestCase<MainActivity> {
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	private static final String TAG = "MainActivityTest";

	private MainActivity mActivity;

	public MainActivityTest() {
		super(null);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
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

}
