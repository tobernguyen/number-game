package fu.agile.whereismynumber.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.Config;
import fu.agile.whereismynumber.Enquity.MyData;
import fu.agile.whereismynumber.GUI.MainScreen;
import fu.agile.whereismynumber.GUI.PlayActivity;

public class MyDialog extends Dialog {

	private Context mContext;

	public MyDialog(Context context) {
		super(context);
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/*
	 * Dialog for end game
	 */
	public void showDialogEndGame(Bundle gameSetting, long score,
			boolean isNewBest) {
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.gameover);

		ImageView playBtn = (ImageView) findViewById(R.id.play_btn);
		ImageView menuBtn = (ImageView) findViewById(R.id.menu_btn);
		TextView mscore = (TextView) findViewById(R.id.curent_score);
		TextView mhighscore = (TextView) findViewById(R.id.best_score);
		TextView gameover_txt = (TextView) findViewById(R.id.gameover_txt);
		TextView you_txt = (TextView) findViewById(R.id.you_txt);
		TextView best_txt = (TextView) findViewById(R.id.best_txt);

		MyData scoreData = new MyData((Activity) mContext);

		Typeface customfont;
		customfont = Typeface.createFromAsset(mContext.getAssets(),
				Config.Font.MAIN_SCREEN_FONT);
		mscore.setTypeface(customfont);
		mhighscore.setTypeface(customfont);
		gameover_txt.setTypeface(customfont);
		you_txt.setTypeface(customfont);
		best_txt.setTypeface(customfont);

		mscore.setText(scoreData.milisecondsToString(score));
		mhighscore.setText(scoreData.getBestScoreString(gameSetting));

		if (isNewBest) {
			// TODO : implement notify that user make new best
		}

		setCancelable(false);
		setCanceledOnTouchOutside(false);
		menuBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				((Activity) mContext).finish();
				Intent goToMenu = new Intent(mContext, MainScreen.class);
				mContext.startActivity(goToMenu);

			}
		});
		playBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				((Activity) mContext).finish();
				mContext.startActivity(((Activity) mContext).getIntent());

			}

		});
		show();

	}

	public void showDialogPauseGame(Bundle gameSetting) {
		setContentView(R.layout.pause_dialog);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ImageView playBtn = (ImageView) findViewById(R.id.play_btn);
		ImageView menuBtn = (ImageView) findViewById(R.id.menu_btn);

		TextView pause_txt = (TextView) findViewById(R.id.pause_txt);

		Typeface customfont;
		customfont = Typeface.createFromAsset(mContext.getAssets(),
				Config.Font.MAIN_SCREEN_FONT);
		pause_txt.setTypeface(customfont);

		setCancelable(false);
		setCanceledOnTouchOutside(false);
		menuBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				((Activity) mContext).finish();

			}
		});
		playBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				PlayActivity.resumeGame();
			}

		});
		show();
	}
}
