package com.bignerdranch.android.runtracker;

import android.content.Context;
import android.location.Location;

public class LastLocationLoader extends DataLoader<Location> {
	private static final String TAG = "LastLocationLoader";
	
	private long mRunId;
	
	public LastLocationLoader(Context context, long runId) {
		super(context);
		mRunId = runId;
	}
	
	@Override
	public Location loadInBackground() {
		return RunManager.get(getContext()).getLastLocation(mRunId);
	}
}
