package fu.agile.whereismynumber.GUI;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Adapter.NumberAdapter;
import fu.agile.whereismynumber.Adapter.ViewHolderItem;
import fu.agile.whereismynumber.Enquity.Number;
import fu.agile.whereismynumber.Enquity.StoreData;
import fu.agile.whereismynumber.Utils.MySoundManager;

public class PlayActivity extends ActionBarActivity {
	private static Typeface customfont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play);
		customfont = Typeface.createFromAsset(getAssets(), "fonts/Karate.ttf");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	protected void reload() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
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

		// The method of android to display stop watch
		private Chronometer mChronometer;

		// Number of column to divide
		int numberOfColumns;

		// Variable of score time
		private long time;
		// variable to display highscore
		private int highscore, score;
		// Tao bien de store data
		private StoreData store;

		// Animation holder
		private Animation slide_left_out;
		private Animation slide_right_in;

		// Bien hien thi diem high score
		private TextView displayScore;

		// Variable for sound
		MySoundManager soundManager;

		// Context
		Context mContext;
		private Dialog custom;
		private Button menubtn, replaybtn;
		private PlayActivity instance;
		private TextView mscore;
		private TextView mhighscore;

		private void showDialog(Context context) {
			custom = new Dialog(context);
			custom.requestWindowFeature(Window.FEATURE_NO_TITLE);
			custom.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.DKGRAY));
			custom.setContentView(R.layout.dialog);
			menubtn = (Button) custom.findViewById(R.id.menubtn);
			replaybtn = (Button) custom.findViewById(R.id.replaybtn);
			mscore = (TextView) custom.findViewById(R.id.score);
			mhighscore = (TextView) custom.findViewById(R.id.highscore);
			// custom.setTitle("Complete");
			mscore.setTypeface(customfont);
			mhighscore.setTypeface(customfont);
			mscore.setText("Score : " + score);
			mhighscore.setText("Best : " + highscore);
			custom.setCancelable(false);
			custom.setCanceledOnTouchOutside(false);
			menubtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent goToMenu = new Intent(getActivity(),
							MainScreen.class);
					getActivity().finish();
					startActivity(goToMenu);

				}
			});
			replaybtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					instance = (PlayActivity) getActivity();
					instance.reload();

				}

			});
			custom.show();

		}

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
			displayScore = (TextView) rootView
					.findViewById(R.id.displayHighscore);
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
			mChronometer.setTypeface(customfont);
			targetNumberTextView.setTypeface(customfont);
			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());
		}

		private void initiateGamePlay() {
			iniResources();
			getGameSetting();
			getMatrixSize();
			createGridNumberList();
			createTargetNumberList();
			loadAnimation();
		}

		private void iniResources() {
			mContext = getActivity();
			soundManager = new MySoundManager(mContext);
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

			Toast.makeText(mContext, "Completed:" + minutes + ":" + seconds,
					Toast.LENGTH_SHORT).show();
			score = seconds + minutes * 60;
			store = new StoreData(mContext, GAME_TYPE, amountOfNumbers);
			store.setHighscore(score, GAME_TYPE, amountOfNumbers);
			highscore = store.getHighscore(GAME_TYPE, amountOfNumbers);

			Toast.makeText(mContext, "Highscore:" + highscore,
					Toast.LENGTH_SHORT).show();
			showDialog(getActivity());
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

		private void getReferenceToView(View currentView) {
			mChronometer = (Chronometer) currentView
					.findViewById(R.id.chronometer);
			gridNumber = (GridView) currentView.findViewById(R.id.gridView);
			targetNumberTextView = (TextView) currentView
					.findViewById(R.id.targetNumberTextView);
		}
	}

}
