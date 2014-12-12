package com.canon.vi.newsarticlereader;

import com.canon.vi.newsarticlereader.content.ArticlesContent;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.provider.MediaStore.Audio.ArtistColumns;

public class MainActivity extends Activity implements
		HeadlinesFragment.OnHeadlineClickedListener,
		ArticleFragment.OnArticleActionListener {

	private static final int TRANS_ACTION_ADD = 1;
	private static final int TRANS_ACTION_REPLACE = 2;
	
	private FragmentTransaction mTx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("NAR", "MainActivity.onCreate - method called.");
		setContentView(R.layout.activity_main);
		HeadlinesFragment headlineFragment = HeadlinesFragment.newInstance(-1);
		if (savedInstanceState == null) {
			doFragmentTransaction(headlineFragment, "headlineFragment", TRANS_ACTION_ADD);
		}
	}

	private void doFragmentTransaction(Fragment fragment, String fragmentTag, int actionType) {
		mTx = getFragmentManager().beginTransaction();
		doActionOnFragment(fragment, fragmentTag, actionType);
		mTx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		mTx.addToBackStack(null);
		mTx.commit();
	}

	private void doActionOnFragment(Fragment fragment, String fragmentTag, int actionType) {
		if (actionType == TRANS_ACTION_ADD) {
			mTx.add(R.id.container, fragment, fragmentTag);
		}
		else if (actionType == TRANS_ACTION_REPLACE) {
			mTx.replace(R.id.container, fragment, fragmentTag);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.article_body) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onHeadlineClicked(int position) {
		Log.d("NAR", "MainActivity.onHeadlineClicked - method called with: -->"
				+ position + "<--");

		ArticleFragment articleFragment = ArticleFragment.newInstance(position);
		doFragmentTransaction(articleFragment, "articleFragment", TRANS_ACTION_REPLACE);
	}

	@Override
	public void onArticleAction(Uri uri) {
		// TODO Auto-generated method stub
		Log.d("NAR", "MainActivity.onArticleAction - method called with: -->"
				+ uri + "<--");
	}
}
