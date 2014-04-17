package fu.agile.whereismynumber.GUI;

import android.R.bool;
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
	public StoreData(Context context) {
		highscoreStore = context.getSharedPreferences("highscore",
				context.MODE_PRIVATE);
		soundStore = context
				.getSharedPreferences("sound", context.MODE_PRIVATE);
		modeStore = context.getSharedPreferences("mode", context.MODE_PRIVATE);
		editScore = highscoreStore.edit();
		editSound = soundStore.edit();
		editMode = modeStore.edit();
	}

	// Dat highscore luu vao trong data
	public void setHighscore(int score) {
		highscore = highscoreStore.getInt("highscore", 0);
		if (score > highscore) {
			highscore = score;
			editScore.putInt("highscore", score);
			editScore.commit();
		} else {
			editScore.putInt("highscore", highscore);
			editScore.commit();
		}
	}

	// Lay highscore tu data
	public int getHighscore() {
		highscore = highscoreStore.getInt("highscore", 0);
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
