package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;
	
	private CrimeLab(Context appContext) {
		this.mAppContext = appContext;
		this.mCrimes = new ArrayList<Crime>();
		populateArrayListWithBoringCrimes();
	}
	
	private void populateArrayListWithBoringCrimes() {
		for (int i = 0; i < 100; i++) {
			Crime c = new Crime();
			c.setTitle("Crime #" + i);
			c.setSolved(i % 2 == 0);
			mCrimes.add(c);
		}
	}
	
	public static CrimeLab getCrimeLab(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getCrimes() {return mCrimes;}
	
	public Crime getCrime(UUID uuid) {
		Crime crimeReturned = null;
		for (Crime crime : mCrimes) {
			if (crime.getId().equals(uuid)) {
				crimeReturned = crime;
			}
		}
		return crimeReturned;
	}
}
