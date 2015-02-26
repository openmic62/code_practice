package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBarActivity;

// http://stackoverflow.com/questions/18451575/action-bar-fragment-activity
public class CrimePagerActivity extends FragmentActivity implements CrimeFragment.Callbacks {
//public class CrimePagerActivity extends ActionBarActivity {

	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	public void onCrimeUpdate(Crime crime) {};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		mCrimes = CrimeLab.getCrimeLab(this).getCrimes();

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				return mCrimes.size();
			}

			@Override
			public Fragment getItem(int position) {
				Crime crime = mCrimes.get(position);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				setTitle(mCrimes.get(i).getTitle());
				break;
			}
		}
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				Crime crime = mCrimes.get(position);
				if (crime.getTitle() != null) {
					setTitle(crime.getTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) { }
			
			@Override
			public void onPageScrollStateChanged(int state) { }
		});
	}
}
