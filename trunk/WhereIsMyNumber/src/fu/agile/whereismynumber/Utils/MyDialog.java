package fu.agile.whereismynumber.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.MyData;
import fu.agile.whereismynumber.GUI.MainScreen;

public class MyDialog extends Dialog {
	private TextView mscore;
	private TextView mhighscore;
	private Context mContext;

	public MyDialog(Context context) {
		super(context);
		mContext = context;
	}

	/*
	 * Dialog for end game
	 */
	public void showDialogEndGame(Bundle gameSetting, long score,
			boolean isNewBest) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.gameover);

		ImageView playBtn = (ImageView) findViewById(R.id.play_btn);
		ImageView menuBtn = (ImageView) findViewById(R.id.menu_btn);
		mscore = (TextView) findViewById(R.id.curent_score);
		mhighscore = (TextView) findViewById(R.id.best_score);

		MyData scoreData = new MyData((Activity) mContext);

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pause_dialog);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ImageView playBtn = (ImageView) findViewById(R.id.play_btn);
		ImageView menuBtn = (ImageView) findViewById(R.id.menu_btn);

		setCancelable(true);
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
			}

		});
		show();
	}
}
