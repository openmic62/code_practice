package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = "CrimeListFragment";

	private CrimeLab mCrimeLab;
	private ArrayList<Crime> mCrimes;
	private boolean mSubtitleVisible;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		mSubtitleVisible = false;

		getActivity().setTitle(R.string.crimes_title);

		mCrimeLab = CrimeLab.getCrimeLab(getActivity());
		mCrimes = mCrimeLab.getCrimes();

		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle != null) {
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime c = new Crime();
			mCrimeLab.addCrime(c);

			// Start CrimePagerActivity
			startCrimePagerActivity(c);
			return true;
		case R.id.menu_item_show_subtitle:
			ActionBar actionBar = getActivity().getActionBar();
			boolean subTitleIsShowing = actionBar.getSubtitle() != null;
			if (subTitleIsShowing) {
				actionBar.setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			} else {
				actionBar.setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);

		startCrimePagerActivity(c);
	}

	private void startCrimePagerActivity(Crime c) {
		// Start CrimePagerActivity
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	}

	private class CrimeAdapter extends ArrayAdapter<Crime> {

		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);
			}

			// Configure the view for this crime
			Crime c = getItem(position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_dateTextView);
			Date crimeDate = c.getDate();
			String crimeDateFormatted = (String) DateFormat.format(
					"MMM dd, yyyy", crimeDate);
			dateTextView.setText(crimeDateFormatted);
			CheckBox solvedCheckBox = (CheckBox) convertView
					.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());

			return convertView;

		}
	}

}
