package fu.agile.whereismynumber.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
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
	public void showDialogEndGame(int score, int highscore) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.gameover);

		ImageView playBtn = (ImageView) findViewById(R.id.play_btn);
		ImageView menuBtn = (ImageView) findViewById(R.id.menu_btn);
		mscore = (TextView) findViewById(R.id.curent_score);
		mhighscore = (TextView) findViewById(R.id.best_score);
		if (score < highscore) {
			mscore.setText("" + score);
			mhighscore.setText("" + score);
		} else {
			mscore.setText("" + score);
			mhighscore.setText("" + highscore);
		}
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		menuBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent goToMenu = new Intent(mContext, MainScreen.class);
				((Activity) mContext).finish();
				mContext.startActivity(goToMenu);

			}
		});
		playBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				((Activity) mContext).finish();
				mContext.startActivity(((Activity) mContext).getIntent());

			}

		});
		show();

	}
}
