package com.bignerdranch.android.criminalintent;

import java.util.UUID;

public class Crime {

	private UUID mId;
	private String mTitle;
	
	public Crime() {
		this(generateRandomUuid(), "default title");
	}
	
	public Crime(UUID id, String title) {
		this.mId = id;
		this.mTitle = title;
	}
	
	private static UUID generateRandomUuid() {
		return UUID.randomUUID();
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getmId() {
		return mId;
	}
	
	
}
