package com.bignerdranch.android.geoquiz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {
	
	private int mCurrentIndex = 0;
	private boolean mIsCheater;
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true), };

	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getSupportActionBar();
			actionBar.setSubtitle("Bodies of Water");
		}
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}
		
		updateQuestion();

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});

		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0);
			}
		});
	}

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	private void checkAnswer(boolean userPressedTrue) {
		int messageId = 0;
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		if (mIsCheater) {
			messageId = R.string.judgment_toast;
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageId = R.string.correct_toast;
			} else {
				messageId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState(Bundle) called");
		outState.putInt(KEY_INDEX, mCurrentIndex);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onstart() called");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}
}
