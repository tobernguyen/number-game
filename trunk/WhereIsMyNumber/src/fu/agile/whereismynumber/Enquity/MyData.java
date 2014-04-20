package fu.agile.whereismynumber.Enquity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MyData {

	Context mContext;
	SharedPreferences prefs;

	private long bestScoreInMiliseconds;

	public MyData(Context context) {
		this.mContext = context;
		prefs = mContext.getSharedPreferences("SCORE_PREFS",
				Context.MODE_PRIVATE);
	}

	/*
	 * Check whether current score (in miliseconds) is lower than bestTime or
	 * not!
	 */
	public boolean isNewBestScore(Bundle gameSetting, long scoreInMiliseconds) {
		getGameBestScore(gameSetting);
		return scoreInMiliseconds < bestScoreInMiliseconds;
	}

	/*
	 * Get BestScore and assign into bestScoreInMiliseconds, if not exist assign
	 * 99999
	 */
	private void getGameBestScore(Bundle gameSetting) {
		int gameType = gameSetting.getInt("GAME_TYPE");
		int gameSize_x = gameSetting.getInt("X");
		int gameSize_y = gameSetting.getInt("Y");
		bestScoreInMiliseconds = prefs.getLong(gameType + "-" + gameSize_x
				+ "-" + gameSize_y, 99999);
	}

	/*
	 * Return best score in format mm:ss
	 */
	public String getBestScoreString(Bundle gameSetting) {
		getGameBestScore(gameSetting);
		return milisecondsToString(bestScoreInMiliseconds);
	}

	/*
	 * Support function for getBestScoreString
	 */
	public String milisecondsToString(long miliseconds) {
		if (miliseconds == 99999)
			return "Not yet :(";
		int minute = (int) miliseconds / 60;
		int second = (int) miliseconds - 60 * ((int) miliseconds / 60);
		String time = "";
		if (minute < 10 && second < 10) {
			time = "0" + minute + ":" + "0" + second;
		} else if (minute < 10 && second > 10) {
			time = "0" + minute + ":" + second;
		} else if (minute > 10 && second < 10) {
			time = minute + ":" + "0" + second;
		} else if (minute > 10 && second > 10) {
			time = minute + ":" + second;
		}
		return time;
	}

	/*
	 * Write score to specific game type
	 */
	public void setBestScore(Bundle gameSetting, long newScoreInMiliseconds) {
		int gameType = gameSetting.getInt("GAME_TYPE");
		int gameSize_x = gameSetting.getInt("X");
		int gameSize_y = gameSetting.getInt("Y");

		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(gameType + "-" + gameSize_x + "-" + gameSize_y,
				newScoreInMiliseconds);
		editor.commit();
	}
}
