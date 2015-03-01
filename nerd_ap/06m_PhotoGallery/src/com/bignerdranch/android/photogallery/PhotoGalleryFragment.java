package com.bignerdranch.android.photogallery;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class PhotoGalleryFragment extends Fragment {
	private static final String TAG = "PhotoGalleryFragment";

	private GridView mGridView;
	private ArrayList<GalleryItem> mItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		new FetchItemsTask().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_photo_gallery, container,
				false);

		mGridView = (GridView) v.findViewById(R.id.gridView);

		setUpAdapter();

		return v;
	}

	private void setUpAdapter() {
		if (getActivity() == null || mGridView == null) {
			return;
		}

		if (mItems != null) {
			mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
					android.R.layout.simple_gallery_item, mItems));
		} else {
			mGridView.setAdapter(null);
		}
	}

//	private class FetchItemsTask extends AsyncTask<Void, Void, Void> {
	private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {
		@Override
//		protected Void doInBackground(Void... params) {
		protected ArrayList<GalleryItem> doInBackground(Void... params) {
			// try {
			// String result = new
			// FlickrFetchr().getUrl("http://www.google.com");
			// Log.i(TAG, "Fetched contents of URL: " + result);
			// } catch (IOException ioe) {
			// Log.e(TAG, "Failed to fetch URL:" + ioe);
			// }
//			new FlickrFetchr().fecthItems();
//			return null;
			return new FlickrFetchr().fecthItems();
		}
		
		@Override
		protected void onPostExecute(ArrayList<GalleryItem> result) {
			super.onPostExecute(result);
			mItems = result;
			setUpAdapter();
		}
	}
}
