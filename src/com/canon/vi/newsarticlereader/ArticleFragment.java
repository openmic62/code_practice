package com.canon.vi.newsarticlereader;

import com.canon.vi.newsarticlereader.content.ArticlesContent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ArticleFragment.OnArticleActionListener} interface
 * to handle interaction events. Use the {@link ArticleFragment#newInstance}
 * factory method to create an instance of this fragment.
 *
 */
public class ArticleFragment extends Fragment {

	private static final String ARG_POSITION = "position";

	// TODO: Rename and change types of parameters
	private int mPosition = -1;

	private OnArticleActionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param mPosition
	 *            Parameter 1.
	 *
	 * @return A new instance of fragment ArticleFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ArticleFragment newInstance(int position) {
		ArticleFragment fragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	public ArticleFragment() {
		// Required empty public constructor
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnArticleActionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleActionListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mPosition = getArguments().getInt(ARG_POSITION);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_article, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		updateViewWithArticleBody(mPosition);
	}

	protected void updateViewWithArticleBody(int position) {
		if (position == -1)
			return;
		TextView textView = (TextView) getActivity().findViewById(
				R.id.article_body);
		String articleBody = "" + (position + 1) + " "
				+ ArticlesContent.ARTICLES.get(position).getBody();
		textView.setText(articleBody);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onArticleAction(uri);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION, mPosition);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
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
	public interface OnArticleActionListener {
		// TODO: Update argument type and name
		public void onArticleAction(Uri uri);
	}

}
