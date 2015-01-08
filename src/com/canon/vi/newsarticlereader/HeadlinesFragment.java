package com.canon.vi.newsarticlereader;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.canon.vi.newsarticlereader.content.ArticlesContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 * 
 * @param <T>
 */
public class HeadlinesFragment extends ListFragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String CURRENT_HEADLINE_SELECTION = "current.headline.selection";

	// TODO: Rename and change types of parameters
	private int mItemCurrentlySelected = -1;

	private OnHeadlineClickedListener mListener;
	private HighlightedItemArrayAdapter<ArticlesContent.Article> highlightedItemArrayAdapter;

	// TODO: Rename and change types of parameters
	public static HeadlinesFragment newInstance(int currentSelection) {
		HeadlinesFragment fragment = new HeadlinesFragment();
		Bundle args = new Bundle();
		args.putInt(CURRENT_HEADLINE_SELECTION, currentSelection);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public HeadlinesFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnHeadlineClickedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineClickedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mItemCurrentlySelected = getArguments().getInt(CURRENT_HEADLINE_SELECTION);
		}
		if (savedInstanceState != null) {
			mItemCurrentlySelected = savedInstanceState
					.getInt(CURRENT_HEADLINE_SELECTION);
		}

		highlightedItemArrayAdapter = new HighlightedItemArrayAdapter<ArticlesContent.Article>(
				getActivity(), R.layout.fragment_headlines, ArticlesContent.ARTICLES);
		highlightedItemArrayAdapter.setSelectedIndex(mItemCurrentlySelected);
		setListAdapter(highlightedItemArrayAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		highlightedItemArrayAdapter.setSelectedIndex(mItemCurrentlySelected);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CURRENT_HEADLINE_SELECTION, mItemCurrentlySelected);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		mItemCurrentlySelected = position;
		highlightedItemArrayAdapter.setSelectedIndex(mItemCurrentlySelected);

		if (null != mListener) {
			Log.d("HeadlineFragment", "onListItemClick - method called");
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mListener.onHeadlineClicked(position);
		}
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnHeadlineClickedListener {
		// TODO: Update argument type and name
		public void onHeadlineClicked(int position);
	}

}
