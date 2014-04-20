package fu.agile.whereismynumber.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;
import fu.agile.whereismynumber.R;

public class MySoundManager {
	private SoundPool mSoundPool;
	private AudioManager mAudioManager;
	private SparseIntArray mSoundPoolMap;
	public final static int RIGHT_ANWSER_SOUND = 1;
	public final static int WRONG_ANSWER_SONUD = 2;

	public MySoundManager(Context context) {
		// set up our audio player
		mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		mSoundPoolMap = new SparseIntArray();

		// load fx
		mSoundPoolMap.put(RIGHT_ANWSER_SOUND,
				mSoundPool.load(context, R.raw.right_sound, 1));
		mSoundPoolMap.put(WRONG_ANSWER_SONUD,
				mSoundPool.load(context, R.raw.wrong_sound, 1));
	}

	public void play(int SOUND_ID) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundPoolMap.get(SOUND_ID), streamVolume,
				streamVolume, 1, 0, 1f);
	}
}
