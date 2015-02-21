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
	
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}
}
