package fu.agile.whereismynumber.GUI;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Adapter.NumberAdapter;
import fu.agile.whereismynumber.Adapter.ViewHolderItem;
import fu.agile.whereismynumber.Enquity.Config;
import fu.agile.whereismynumber.Enquity.Number;
import fu.agile.whereismynumber.Enquity.StoreData;
import fu.agile.whereismynumber.Utils.MyDialog;
import fu.agile.whereismynumber.Utils.MySoundManager;

public class PlayActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		// Variables for MyDialog
		private MyDialog dialogManager;

		// Custom font for X character
		private Typeface X_font;
		private Typeface fontForText;

		// Bundle for game setting, get from MainScreen
		private Bundle game_setting;
		private int GAME_TYPE;

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
		private TextView targetNumberTextView;
		private TextView bestScoreTextView;

		// The method of android to display stop watch
		private Chronometer mChronometer;

		// Number of column to divide
		private int numberOfColumns;

		// Variable of score time
		private long time;
		// variable to display highscore
		private int highscore, score;
		// Tao bien de store data
		private StoreData store;

		// Animation holder
		private Animation slide_left_out;
		private Animation slide_right_in;

		// Variable for sound
		private MySoundManager soundManager;

		// Context
		private Context mContext;

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

			// OnClick for gridNumber
			gridNumber.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					// Kiem tra xem so nguoi dung click vao co dung khong
					Number clickedNumber = (Number) numbers_adapter
							.getItem(position);
					if (clickedNumber.equals(listNumberTarget.get(index))) {
						// Play right_sound
						soundManager.play(MySoundManager.RIGHT_ANWSER_SOUND);

						// Chuyen thanh dau X
						TextView mTextView = ((ViewHolderItem) view.getTag()).textViewItem;
						mTextView.setTypeface(X_font);
						mTextView.setTextColor(Color.RED);
						mTextView.setTextSize(80);
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
			// mChronometer.setTypeface(fontForText);
			targetNumberTextView.setTypeface(fontForText);
			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());

			// Hien thi thoi gian tot nhat
			highscore = store.getHighscore(GAME_TYPE, amountOfNumbers);
			bestScoreTextView.setText(store.getBestTime(highscore));
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
			X_font = Typeface.createFromAsset(mContext.getAssets(),
					Config.Font.RIGHT_ANSWER_TEXT_FONT);
			fontForText = Typeface.createFromAsset(mContext.getAssets(),
					Config.Font.PLAY_ACTIVITY_TEXTVIEW_FONT);
		}

		private void iniResources() {
			mContext = getActivity();
			soundManager = new MySoundManager(mContext);
			dialogManager = new MyDialog(mContext);
			store = new StoreData(mContext, GAME_TYPE, amountOfNumbers);
		}

		private void getReferenceToView(View currentView) {
			mChronometer = (Chronometer) currentView
					.findViewById(R.id.chronometer);
			gridNumber = (GridView) currentView.findViewById(R.id.gridView);
			targetNumberTextView = (TextView) currentView
					.findViewById(R.id.targetNumberTextView);
			bestScoreTextView = (TextView) currentView
					.findViewById(R.id.bestScoreTextView);
		}

		private void loadAnimation() {
			slide_left_out = AnimationUtils.loadAnimation(mContext,
					R.anim.slide_out_left);
			slide_right_in = AnimationUtils.loadAnimation(mContext,
					R.anim.slide_in_right);
		}

		private void getGameSetting() {
			// Lay du dieu ve chieu dai va rong cua ma tran
			game_setting = ((Activity) mContext).getIntent().getBundleExtra(
					"GAME_SETTING");
			GAME_TYPE = game_setting.getInt("GAME_TYPE", 3);
		}

		private void getMatrixSize() {
			// Tinh toan tong so chu so
			amountOfNumbers = game_setting.getInt("A", 6)
					* game_setting.getInt("B", 8);

			numberOfColumns = game_setting.getInt("A", 6);
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
			// Dung do ho dem gio
			mChronometer.stop();

			// Lay thoi gian cua lan choi do
			time = SystemClock.elapsedRealtime() - mChronometer.getBase();
			int hours = (int) (time / 3600000);
			int minutes = (int) (time - hours * 3600000) / 60000;
			int seconds = (int) (time - hours * 3600000 - minutes * 60000) / 1000;

			score = seconds + minutes * 60;

			store.setHighscore(score, GAME_TYPE, amountOfNumbers);

			// Show dialog when end game
			dialogManager.showDialogEndGame(score, highscore);
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

}
