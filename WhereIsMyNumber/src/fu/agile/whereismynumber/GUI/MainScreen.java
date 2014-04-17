package fu.agile.whereismynumber.GUI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.StoreData;

public class MainScreen extends ActionBarActivity {
	private static Typeface customfont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		customfont = Typeface.createFromAsset(getAssets(), "fonts/Karate.ttf");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private Button playButton;
		private RadioGroup matrixSizeRadio, playTypeRadio;
		Bundle game_setting;
		private TextView displayScore;
		private StoreData store;
		private int mode, size;
		private RadioButton radiobutton;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(
					R.layout.fragment_main_screen, container, false);
			mode = 3;
			size = 48;
			// Get resources
			playButton = (Button) rootView.findViewById(R.id.playButton);
			game_setting = new Bundle();
			displayScore = (TextView) rootView
					.findViewById(R.id.displayHighscore);

			// OnClickListener for each button
			playButton.setOnClickListener(this);

			// Get references to playSize_radioGroup
			matrixSizeRadio = (RadioGroup) rootView
					.findViewById(R.id.playSize_radioGroup);

			// Get references to playSize_radioGroup
			playTypeRadio = (RadioGroup) rootView
					.findViewById(R.id.playType_radioGroup);

			matrixSizeRadio
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {
							case R.id.play_6x6_rbtn:
								size = 36;
								break;
							case R.id.play_6x8_rbtn:
								size = 48;
								break;
							default:
								size = 36;
								break;
							}
							updateBestScoreTextView(mode, size);
						}
					});

			playTypeRadio
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {
							case R.id.playType_increase:
								mode = 1;
								break;
							case R.id.playType_decrease:
								mode = 2;
								break;
							case R.id.playType_random:
								mode = 3;
								break;
							}
							updateBestScoreTextView(mode, size);
						}
					});
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
					game_setting.putInt("B", 6);
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
			// case R.id.highScoreButton:
			// // Intent for High Score Button
			// Intent goToHighScoreActivity = new Intent(getActivity(),
			// HighScoreActivity.class);
			// startActivity(goToHighScoreActivity);

			}

		}

		private void updateBestScoreTextView(int mode, int size) {
			store = new StoreData(getActivity(), mode, size);
			int highscore = store.getHighscore(mode, size);
			displayScore.setTypeface(customfont);
			displayScore.setText("Best: " + highscore + " s");
		}

	}

}
