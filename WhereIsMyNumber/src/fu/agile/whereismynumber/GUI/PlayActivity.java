package fu.agile.whereismynumber.GUI;

import java.util.ArrayList;
import java.util.Collections;

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
import android.widget.Toast;
import fu.agile.whereismynumber.R;

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
			numbers_adapter = new NumberAdapter(getActivity(),
					listNumberDisplay, numberOfColumns);
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
						try {
							printNextTargetNumber();
						} catch (IndexOutOfBoundsException e) {
							gameStop();
						}
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
		}

		private void initiateGamePlay() {
			getGameSetting();
			getMatrixSize();
			createGridNumberList();
			createTargetNumberList();
			loadAnimation();
		}

		private void loadAnimation() {
			slide_left_out = AnimationUtils.loadAnimation(getActivity(),
					R.anim.slide_out_left);
			slide_right_in = AnimationUtils.loadAnimation(getActivity(),
					R.anim.slide_in_right);
		}

		private void getGameSetting() {
			// Lay du dieu ve chieu dai va rong cua ma tran
			game_setting = getActivity().getIntent().getBundleExtra(
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
				for (int i = amountOfNumbers - 1; i > 0; i--) {
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

			Toast.makeText(getActivity(),
					"Completed:" + minutes + ":" + seconds, Toast.LENGTH_SHORT)
					.show();
			score = seconds + minutes * 60;
			store = new StoreData(getActivity());
			store.setHighscore(score);
			highscore = store.getHighscore();
			Toast.makeText(getActivity(), "Highscore:" + highscore,
					Toast.LENGTH_SHORT).show();
		}

		private void printNextTargetNumber() {
			nextTargetNumber();
			targetNumberTextView.startAnimation(slide_left_out);
			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());
			targetNumberTextView.startAnimation(slide_right_in);
		}

		private void nextTargetNumber() {
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
