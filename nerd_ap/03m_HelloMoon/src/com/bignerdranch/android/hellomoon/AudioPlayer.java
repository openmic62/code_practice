package com.bignerdranch.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	
	MediaPlayer mMediaPlayer;
	
	public void play(Context c) {
		stop();
		
		mMediaPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
			}
		});
		
		mMediaPlayer.start();
	}
	
	public void stop() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

}
