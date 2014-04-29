package fu.agile.whereismynumber.GUI;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Adapter.NumberAdapter;
import fu.agile.whereismynumber.Adapter.ViewHolderItem;
import fu.agile.whereismynumber.Enquity.Config;
import fu.agile.whereismynumber.Enquity.MyData;
import fu.agile.whereismynumber.Enquity.Number;
import fu.agile.whereismynumber.Utils.MyChronometer;
import fu.agile.whereismynumber.Utils.MyDialog;
import fu.agile.whereismynumber.Utils.MySoundManager;

@SuppressLint("NewApi")
public class PlayActivity extends ActionBarActivity {
	// Variables to detect Pause
	boolean isFromPause = false;

	// Variables for MyDialog
	public static MyDialog dialogManager;

	// Bundle for game setting, get from MainScreen
	private static Bundle gameSetting;
	private static int GAME_TYPE;

	// The method of android to display stop watch
	private static MyChronometer mChronometer;

	// Context
	private static Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/*
	 * Hide Status Bar and Action Bar
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (Build.VERSION.SDK_INT < 16) {
			// Hide the status bar
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			// Hide the action bar
			getSupportActionBar().hide();
		} else {
			// Hide the status bar
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_FULLSCREEN);
			// Hide the action bar
			getActionBar().hide();
		}

		if (isFromPause)
			dialogManager.showDialogPauseGame(gameSetting);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		// Custom font for X character
		// private Typeface X_font;
		private Typeface fontForText;

		// Amount of numbers
		private int amountOfNumbers;

		// Index for next target number
		private int index = 0;

		// Initiate Array of Number to play
		private ArrayList<Number> listNumberDisplay = new ArrayList<Number>();
		private ArrayList<Number> listNumberTarget = new ArrayList<Number>();

		// Adapter for GridNumber
		private NumberAdapter numbers_adapter;

		// Variable to handle Views
		private GridView gridNumber;
		private ImageView pauseImageView;
		private TextView targetNumberTextView;
		private TextView bestScoreTextView;

		// Number of column to divide
		private int numberOfColumns;

		// variable to display highscore
		private long score;

		// Variable for score
		MyData scoreData;

		// Animation holder
		private Animation slide_left_out;
		private Animation slide_right_in;

		// Variable for sound
		private MySoundManager soundManager;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_play, container,
					false);
			/*
			 * Initiate Game Play
			 */
			initiateGamePlay();
			getReferenceToView(rootView);
			/*
			 * Xu ly gridNumber
			 */
			// Tao ra numberAdapter de ti nua su dung cho gridNumber
			numbers_adapter = new NumberAdapter(mContext, listNumberDisplay,
					numberOfColumns);
			gridNumber.setAdapter(numbers_adapter);
			gridNumber.setSoundEffectsEnabled(false);
			// OnClick for gridNumber
			gridNumber.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					// Kiem tra xem so nguoi dung click vao co dung khong
					TextView mTextView = ((ViewHolderItem) view.getTag()).textViewItem;

					if (mTextView.getText().toString()
							.equals(targetNumberTextView.getText().toString())) {
						// Play right_sound
						soundManager.play(MySoundManager.RIGHT_ANWSER_SOUND);

						// Chuyen thanh dau X
						// mTextView.setTypeface(X_font);
						mTextView.setTextColor(Color.RED);
						// mTextView.setTextSize(70);
						mTextView.setText("X");

						try {
							printNextTargetNumber();
						} catch (IndexOutOfBoundsException e) {
							gameStop();
						}
					} else {
						soundManager.play(MySoundManager.WRONG_ANSWER_SONUD);
					}
				}
			});

			/*
			 * Start Chronometer and print target number
			 */
			gameStart();

			return rootView;
		}

		/*
		 * Below is support functions which use in onCreateView
		 */

		private void gameStart() {
			mChronometer.start();

			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());

			// Hien thi thoi gian tot nhat
			bestScoreTextView
					.setText(scoreData.getBestScoreString(gameSetting));
		}

		private void initiateGamePlay() {
			iniResources();
			getGameSetting();
			getMatrixSize();
			createGridNumberList();
			createTargetNumberList();
			loadAnimation();
			loadFont();
		}

		private void loadFont() {
			// X_font = Typeface.createFromAsset(mContext.getAssets(),
			// Config.Font.RIGHT_ANSWER_TEXT_FONT);
			fontForText = Typeface.createFromAsset(mContext.getAssets(),
					Config.Font.PLAY_ACTIVITY_TEXTVIEW_FONT);
		}

		private void setFont(View currentView) {
			mChronometer.setTypeface(fontForText);
			targetNumberTextView.setTypeface(fontForText);
			bestScoreTextView.setTypeface(fontForText);

			TextView bestTimeTitleTv = (TextView) currentView
					.findViewById(R.id.bestTimeTitleTv);
			bestTimeTitleTv.setTypeface(fontForText);

			TextView currentTimeTitleTv = (TextView) currentView
					.findViewById(R.id.currentTimeTitleTv);
			currentTimeTitleTv.setTypeface(fontForText);
		}

		private void iniResources() {
			mContext = getActivity();
			soundManager = new MySoundManager(mContext);
			dialogManager = new MyDialog(mContext);
			scoreData = new MyData(mContext);
		}

		private void getReferenceToView(View currentView) {
			mChronometer = (MyChronometer) currentView
					.findViewById(R.id.chronometer);
			gridNumber = (GridView) currentView.findViewById(R.id.gridView);
			targetNumberTextView = (TextView) currentView
					.findViewById(R.id.targetNumberTextView);
			bestScoreTextView = (TextView) currentView
					.findViewById(R.id.bestScoreTextView);
			pauseImageView = (ImageView) currentView
					.findViewById(R.id.pauseImageView);

			pauseImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					gamePause();
				}

			});

			// Set custom font for system static TextView
			setFont(currentView);
		}

		private void loadAnimation() {
			slide_left_out = AnimationUtils.loadAnimation(mContext,
					R.anim.slide_out_left);
			slide_right_in = AnimationUtils.loadAnimation(mContext,
					R.anim.slide_in_right);
		}

		private void getGameSetting() {
			// Lay du dieu ve chieu dai va rong cua ma tran
			gameSetting = ((Activity) mContext).getIntent().getBundleExtra(
					"GAME_SETTING");
			GAME_TYPE = gameSetting.getInt("GAME_TYPE", 3);
		}

		private void getMatrixSize() {
			// Tinh toan tong so chu so
			amountOfNumbers = gameSetting.getInt("X", 6)
					* gameSetting.getInt("Y", 8);

			numberOfColumns = gameSetting.getInt("X", 6);
		}

		private void createGridNumberList() {
			// Tao ra mot day so moi va shuffle no de ti nua dua vao GridView
			for (int i = 1; i <= amountOfNumbers; i++) {
				listNumberDisplay.add(new Number(i));
			}
			Collections.shuffle(listNumberDisplay);
		}

		private void createTargetNumberList() {
			// Tao ra mot day so target tuy thuoc vao kieu choi nguoi dung da
			// chon
			switch (GAME_TYPE) {
			case 1:
				for (int i = 1; i <= amountOfNumbers; i++) {
					listNumberTarget.add(new Number(i));
				}
				break;
			case 2:
				for (int i = amountOfNumbers; i > 0; i--) {
					listNumberTarget.add(new Number(i));
				}
				break;
			case 3:
				for (int i = amountOfNumbers; i > 0; i--) {
					listNumberTarget.add(new Number(i));
				}
				Collections.shuffle(listNumberTarget);
			default:
				break;
			}
		}

		private void gameStop() {
			// // Dung do ho dem gio
			// mChronometer.stop();
			//
			// // Lay thoi gian cua lan choi do
			// time = SystemClock.elapsedRealtime() - mChronometer.getBase();
			// int hours = (int) (time / 3600000);
			// int minutes = (int) (time - hours * 3600000) / 60000;
			// int seconds = (int) (time - hours * 3600000 - minutes * 60000) /
			// 1000;
			//
			// score = seconds + minutes * 60;

			score = mChronometer.stopAndGetTimeInSeconds();

			// Check whether user make new best time
			boolean isNewBest = scoreData.isNewBestScore(gameSetting, score);

			if (isNewBest) {
				scoreData.setBestScore(gameSetting, score);
			}

			// Show dialog when end game
			dialogManager.showDialogEndGame(gameSetting, score, isNewBest);
		}

		private void printNextTargetNumber() {
			increaseIndex();
			targetNumberTextView.startAnimation(slide_left_out);
			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());
			targetNumberTextView.startAnimation(slide_right_in);

		}

		private void increaseIndex() {
			index++;
		}

	}

	@Override
	protected void onPause() {
		isFromPause = true;
		mChronometer.pause();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		gamePause();
	}

	private static void gamePause() {
		mChronometer.pause();
		dialogManager.showDialogPauseGame(gameSetting);
	}

	public static void resumeGame() {
		if (Build.VERSION.SDK_INT < 16) {
			// Hide the status bar
			((Activity) mContext).getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			// Hide the status bar
			((Activity) mContext).getWindow().getDecorView()
					.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
		mChronometer.resume();

	}

}
