package com.lc.DetailCharacter;

import com.example.japaneseapp.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class PronunciationCharacter {
	private SoundPool mSoundPool;
	private AudioManager mAudioManager;
	private SparseIntArray mSoundPoolMap;
	private Context mContext;

	public PronunciationCharacter(Context context) {
		// set up our audio player
		mContext = context;

	}

	public void play(String idCharacter) {
		MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.a);
		mediaPlayer.setVolume(1.0f, 1.0f);
		mediaPlayer.start();
	}
}
