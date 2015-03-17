package com.canon.vi.newsarticlereader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements
		HeadlinesFragment.OnHeadlineClickedListener,
		ArticleFragment.OnArticleActionListener {

	private FragmentTransaction mTx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (BuildConfig.DEBUG) Log.d("MainActivity", "onCreate - method called.");
		setContentView(R.layout.activity_main);

		FrameLayout singlePaneContainer = (FrameLayout) findViewById(R.id.single_pane_container);
		if (singlePaneContainer != null) {
			if (BuildConfig.DEBUG) Log.d("MainActivity", "onCreate (single-pane)");
			HeadlinesFragment headlineFragment = HeadlinesFragment.newInstance(-1);
			if (savedInstanceState == null) {
				doFragmentTransaction(headlineFragment, "headlineFragment");
			}
		} else {
			if (BuildConfig.DEBUG) Log.d("MainActivity", "onCreate (dual-pane)");
		}
	}

	private void doFragmentTransaction(Fragment fragment, String fragmentTag) {
		mTx = getFragmentManager().beginTransaction();
		mTx.replace(R.id.single_pane_container, fragment, fragmentTag);
		mTx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		mTx.addToBackStack(null);
		mTx.commit();
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
		if (BuildConfig.DEBUG) Log.d("MainActivity", "onHeadlineClicked - method called with: -->" + position + "<--");

		if (findViewById(R.id.single_pane_container) != null) {
			if (BuildConfig.DEBUG) Log.d("MainActivity", "onHeadlineClicked: single-pane");
			ArticleFragment af = ArticleFragment.newInstance(position);
			doFragmentTransaction(af, "articleFragment");
		} else {
			if (BuildConfig.DEBUG) Log.d("MainActivity", "onHeadlineClicked: dual-pane");
			ArticleFragment af = (ArticleFragment) getFragmentManager()
				.findFragmentById(R.layout.fragment_article);
			af.updateViewWithArticleBody(position);
		}
	}

	@Override
	public void onArticleAction(Uri uri) {
		// TODO Auto-generated method stub
		if (BuildConfig.DEBUG) Log.d("MainActivity", "onArticleAction - method called with: -->"
				+ uri + "<--");
	}
}

