package fu.agile.whereismynumber.Enquity;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreData {
	// bien de truyen vao luu highscore, nhac va che do choi
	SharedPreferences highscoreStore, soundStore, modeStore;
	// Bien dieu khien viec thay doi gia tri luu trong shareperferrence
	SharedPreferences.Editor editScore, editSound, editMode;
	// bien luu highscore
	private int highscore;
	// Bien luu che do bat hay tat nhac
	private boolean isSound;
	// Bien luu che do choi
	private int modePlay;

	// Tao constructor khoi tao gia tri ban dau cho cac setting va thiet lap de
	// sua vao shareperfference
	public StoreData(Context context, int mode, int size) {
		highscoreStore = context.getSharedPreferences(
				"highscore" + mode + size, context.MODE_PRIVATE);
		soundStore = context
				.getSharedPreferences("sound", context.MODE_PRIVATE);
		modeStore = context.getSharedPreferences("mode", context.MODE_PRIVATE);
		editScore = highscoreStore.edit();
		editSound = soundStore.edit();
		editMode = modeStore.edit();
		highscore = highscoreStore.getInt("highscore" + mode + size, 99999);
	}

	// Dat highscore luu vao trong data
	public void setHighscore(int score, int mode, int size) {
		if (score < highscore) {
			highscore = score;
			editScore.putInt("highscore" + mode + size, score);
			editScore.commit();
		}
	}

	// Lay highscore tu data
	public int getHighscore(int mode, int size) {
		highscore = highscoreStore.getInt("highscore" + mode + size, 99999);
		return highscore;
	}

	// Cai dat am thanh luu va trong data
	public void setSound(boolean settingSound) {
		editSound.putBoolean("sound", settingSound);
		editSound.commit();
		isSound = settingSound;
	}

	// Lay tuy chinh am thanh tu trong data
	public boolean getSound() {
		isSound = soundStore.getBoolean("sound", true);
		return isSound;
	}

	// Cai dat che do choi luu vao trong data
	public void setMode(int modePlay) {
		editMode.putInt("mode", modePlay);
		modePlay = modePlay;
		editMode.commit();
	}

	// Lay cai dat cho do choi tu data
	public int getMode() {
		modePlay = modeStore.getInt("mode", 36);
		return modePlay;
	}

}
