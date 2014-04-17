package fu.agile.whereismynumber.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.StoreData;

public class MainScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			OnClickListener, android.widget.RadioGroup.OnCheckedChangeListener {

		private Button playButton, highScoreButton;
		private RadioGroup matrixSizeRadio, playTypeRadio;
		Bundle game_setting;
		private TextView displayScore;
		private StoreData store;
		private int highscore;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_screen,
					container, false);

			// Get resources
			playButton = (Button) rootView.findViewById(R.id.playButton);
			highScoreButton = (Button) rootView
					.findViewById(R.id.highScoreButton);
			game_setting = new Bundle();
			displayScore = (TextView) rootView
					.findViewById(R.id.displayHighscore);
			store = new StoreData(getActivity());

			// OnClickListener for each button
			playButton.setOnClickListener(this);
			highScoreButton.setOnClickListener(this);

			// Get references to playSize_radioGroup
			matrixSizeRadio = (RadioGroup) rootView
					.findViewById(R.id.playSize_radioGroup);

			// Get references to playSize_radioGroup
			playTypeRadio = (RadioGroup) rootView
					.findViewById(R.id.playType_radioGroup);

			matrixSizeRadio.setOnCheckedChangeListener(this);
			playTypeRadio.setOnCheckedChangeListener(this);

			updateBestScoreTextView();
			return rootView;
		}

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.playButton:

				// Get value of checkedRadioButton
				int checkedRadioButton = matrixSizeRadio
						.getCheckedRadioButtonId();

				switch (checkedRadioButton) {
				case R.id.play_6x6_rbtn:
					game_setting.putInt("A", 6);
					game_setting.putInt("B", 6);
					break;

				case R.id.play_6x8_rbtn:
					game_setting.putInt("A", 6);
					game_setting.putInt("B", 8);
					break;
				default:
					game_setting.putInt("A", 6);
					game_setting.putInt("B", 8);
					break;
				}

				// Get value of checkedRadioButton
				checkedRadioButton = playTypeRadio.getCheckedRadioButtonId();

				switch (checkedRadioButton) {
				case R.id.playType_increase:
					game_setting.putInt("GAME_TYPE", 1);
					break;
				case R.id.playType_decrease:
					game_setting.putInt("GAME_TYPE", 2);
					break;
				case R.id.playType_random:
					game_setting.putInt("GAME_TYPE", 3);
					break;
				default:
					break;
				}

				Intent goToPlayActivity = new Intent(getActivity(),
						PlayActivity.class);
				goToPlayActivity.putExtra("GAME_SETTING", game_setting);
				startActivity(goToPlayActivity);
				break;
			case R.id.highScoreButton:
				// Intent for High Score Button
				Intent goToHighScoreActivity = new Intent(getActivity(),
						HighScoreActivity.class);
				startActivity(goToHighScoreActivity);

			}

		}

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			updateBestScoreTextView();

		}

		private void updateBestScoreTextView() {
			int size = matrixSizeRadio.getCheckedRadioButtonId();
			int mode = playTypeRadio.getCheckedRadioButtonId();
			highscore = store.getHighscore(mode, size);
			displayScore.setText("Best score: " + highscore + " s");
		}

	}

}
