package com.bignerdranch.android.photogallery;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PhotoGalleryFragment extends Fragment {
	private static final String TAG = "PhotoGalleryFragment";

	private GridView mGridView;
	private ArrayList<GalleryItem> mItems;
	private ThumbnailDownloader<ImageView> mThumbnailThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		new FetchItemsTask().execute();

		// mThumbnailThread = new ThumbnailDownloader<ImageView>();
		mThumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
		mThumbnailThread
				.setListener(new ThumbnailDownloader.Listener<ImageView>() {
					@Override
					public void onThumbnailDownloaded(ImageView imageView,
							Bitmap thumbnail) {
						if (isVisible()) {
							imageView.setImageBitmap(thumbnail);
						}
					}
				});
		mThumbnailThread.start();
		mThumbnailThread.getLooper();
		Log.i(TAG, "Background thread started.");
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mThumbnailThread.clearQueue();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_photo_gallery, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_search:
			getActivity().onSearchRequested();
			return true;
		case R.id.menu_item_clear:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mThumbnailThread.quit();
		Log.i(TAG, "Background thread destroyed.");
	}

	private void setUpAdapter() {
		if (getActivity() == null || mGridView == null) {
			return;
		}

		if (mItems != null) {
			// mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
			// android.R.layout.simple_gallery_item, mItems));
			mGridView.setAdapter(new GalleryItemAdapter(mItems));
		} else {
			mGridView.setAdapter(null);
		}
	}

	private class FetchItemsTask extends
			AsyncTask<Void, Void, ArrayList<GalleryItem>> {
		@Override
		protected ArrayList<GalleryItem> doInBackground(Void... params) {
			String query = "android"; // Just for testing
			if (query != null) {
				return new FlickrFetchr().search(query);
			} else {
				return new FlickrFetchr().fecthItems();
			}
		}

		@Override
		protected void onPostExecute(ArrayList<GalleryItem> result) {
			super.onPostExecute(result);
			mItems = result;
			setUpAdapter();
		}
	}

	private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {

		// Eclipse auto-generated constructor
		public GalleryItemAdapter(Context context, int resource) {
			super(context, resource);
		}

		// Constructor given by NAP
		public GalleryItemAdapter(ArrayList<GalleryItem> items) {
			super(getActivity(), 0, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// return super.getView(position, convertView, parent);
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.gallery_item, parent, false);
			}

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.gallery_item_imageView);
			imageView.setImageResource(R.drawable.mike_up_close);
			GalleryItem item = getItem(position);
			mThumbnailThread.queueThumbnail(imageView, item.getUrl());

			return convertView;
		}
	}
}
