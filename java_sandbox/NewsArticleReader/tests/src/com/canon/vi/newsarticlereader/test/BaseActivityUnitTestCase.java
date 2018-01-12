package com.canon.vi.newsarticlereader.test;

import java.util.concurrent.atomic.AtomicReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.test.ActivityUnitTestCase;

public class BaseActivityUnitTestCase<T extends Activity> extends
		ActivityUnitTestCase<T> {

	public BaseActivityUnitTestCase(Class<T> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected T startActivity(final Intent intent,
			final Bundle savedInstanceState,
			final Object lastNonConfigurationInstance) {
		return startActivityOnMainThread(intent, savedInstanceState,
				lastNonConfigurationInstance);
	}

	private T startActivityOnMainThread(final Intent intent,
			final Bundle savedInstanceState,
			final Object lastNonConfigurationInstance) {
		final AtomicReference<T> activityRef = new AtomicReference<>();
		final Runnable activityRunnable = new Runnable() {
			@Override
			public void run() {
				activityRef.set(BaseActivityUnitTestCase.super.startActivity(
						intent, savedInstanceState,
						lastNonConfigurationInstance));
			}
		};

		if (Looper.myLooper() != Looper.getMainLooper()) {
			getInstrumentation().runOnMainSync(activityRunnable);
		} else {
			activityRunnable.run();
		}

		return activityRef.get();
	}
}
