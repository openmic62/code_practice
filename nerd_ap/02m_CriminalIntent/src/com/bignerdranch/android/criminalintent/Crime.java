package com.bignerdranch.android.criminalintent;

import java.util.UUID;

public class Crime {

	private UUID mId;
	private String mTitle;
	
	public Crime() {
		this.mId = UUID.randomUUID();
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getId() {
		return mId;
	}
	
}
