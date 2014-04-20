package fu.agile.whereismynumber.GUI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.Config;
import fu.agile.whereismynumber.Enquity.MyData;

public class MainScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_screen);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener, OnCheckedChangeListener {

		private Button playButton;
		private RadioGroup matrixSizeRadio, playTypeRadio;
		Bundle gameSetting;
		private TextView displayScore;
		private MyData scoreData;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(
					R.layout.fragment_main_screen, container, false);

			// Init resource
			scoreData = new MyData(getActivity());

			// Khoi tao kieu choi mac dinh
			gameSetting = new Bundle();
			gameSetting.putInt("X", 6);
			gameSetting.putInt("Y", 6);
			gameSetting.putInt("GAME_TYPE", 1);

			// Get resources
			playButton = (Button) rootView.findViewById(R.id.playButton);
			displayScore = (TextView) rootView
					.findViewById(R.id.displayHighscore);
			matrixSizeRadio = (RadioGroup) rootView
					.findViewById(R.id.playSize_radioGroup);
			playTypeRadio = (RadioGroup) rootView
					.findViewById(R.id.playType_radioGroup);

			// Set font
			setFont();

			// OnClickListener for each button
			playButton.setOnClickListener(this);
			matrixSizeRadio.setOnCheckedChangeListener(this);
			playTypeRadio.setOnCheckedChangeListener(this);

			// Update and display Best Time
			updateBestScoreTextView();

			return rootView;
		}

		private void setFont() {
			Typeface customfont;
			customfont = Typeface.createFromAsset(getActivity().getAssets(),
					Config.Font.MAIN_SCREEN_FONT);
			displayScore.setTypeface(customfont);
		}

		@Override
		public void onClick(View arg0) {
			Intent goToPlayActivity = new Intent(getActivity(),
					PlayActivity.class);
			goToPlayActivity.putExtra("GAME_SETTING", gameSetting);
			startActivity(goToPlayActivity);

		}

		private void updateBestScoreTextView() {
			displayScore.setText("Best time: "
					+ scoreData.getBestScoreString(gameSetting));
			Log.e("BEST SCORE",
					"BEST: " + scoreData.getBestScoreString(gameSetting));
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// Get value of checkedRadioButton
			int checkedRadioButton = matrixSizeRadio.getCheckedRadioButtonId();

			switch (checkedRadioButton) {
			case R.id.play_6x6_rbtn:
				gameSetting.putInt("X", 6);
				gameSetting.putInt("Y", 6);
				break;

			case R.id.play_6x8_rbtn:
				gameSetting.putInt("X", 6);
				gameSetting.putInt("Y", 8);
				break;
			default:
				gameSetting.putInt("X", 6);
				gameSetting.putInt("Y", 6);
				break;
			}

			// Get value of checkedRadioButton
			checkedRadioButton = playTypeRadio.getCheckedRadioButtonId();

			switch (checkedRadioButton) {
			case R.id.playType_increase:
				gameSetting.putInt("GAME_TYPE", 1);
				break;
			case R.id.playType_decrease:
				gameSetting.putInt("GAME_TYPE", 2);
				break;
			case R.id.playType_random:
				gameSetting.putInt("GAME_TYPE", 3);
				break;
			default:
				break;
			}

			// Update Best Time on main screen when choose another game option
			updateBestScoreTextView();

		}

	}

}
