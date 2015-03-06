package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;

public class Box {
	private PointF mOrigin;
	private PointF mCurrent;
	
	public Box(PointF point){
		mOrigin = mCurrent = point;
	}

	public PointF getOrigin() {
		return mOrigin;
	}

	public void setOrigin(PointF origin) {
		mOrigin = origin;
	}

	public PointF getCurrent() {
		return mCurrent;
	}

	public void setCurrent(PointF current) {
		mCurrent = current;
	}
	
	
}
