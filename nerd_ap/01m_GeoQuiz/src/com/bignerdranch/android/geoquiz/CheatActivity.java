package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	private boolean mAnswerIsTrue;
	private boolean mAnswerWasShown = false;

	private Button mShowAnswerButton;
	private TextView mAnswerTextView;

	private static final String KEY_ANSWER_IS_TRUE = "answerIsTrue";
	private static final String KEY_ANSWER_WAS_SHOWN = "answerWasShown";

	public static final String EXTRA_IN_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_OUT_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswerIsTrue = getIntent()
				.getBooleanExtra(EXTRA_IN_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		if (savedInstanceState != null) {
			mAnswerWasShown = savedInstanceState.getBoolean(KEY_ANSWER_WAS_SHOWN);
			mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE);
			if (mAnswerWasShown)
				setAnswerTextView(mAnswerIsTrue);
		}
		setAnswerShownResult(mAnswerWasShown);

		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setAnswerTextView(mAnswerIsTrue);
				setAnswerShownResult(true);
			}
		});
	}

	private void setAnswerTextView(boolean answerIsTrue) {
		if (answerIsTrue) {
			mAnswerTextView.setText(R.string.true_button);
		} else {
			mAnswerTextView.setText(R.string.false_button);
		}
	}

	private void setAnswerShownResult(boolean isAnswerShown) {
		mAnswerWasShown = isAnswerShown;
		Intent data = new Intent();
		data.putExtra(EXTRA_OUT_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_ANSWER_IS_TRUE, mAnswerIsTrue);
		outState.putBoolean(KEY_ANSWER_WAS_SHOWN, mAnswerWasShown);
	}
}
