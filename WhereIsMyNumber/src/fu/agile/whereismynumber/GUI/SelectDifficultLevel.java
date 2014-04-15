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
import fu.agile.whereismynumber.R;

public class SelectDifficultLevel extends ActionBarActivity {

	private static int play_type = 0;
	private static int difficult_level = 0;

	private static final int EASY_LEVEL = 1;
	private static final int MEDIUM_LEVEL = 2;
	private static final int HARD_LEVEL = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_difficult_level);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		play_type = getIntent().getIntExtra("PLAY_TYPE", 1);

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private Button easyLevelButton, mediumLevelButton, hardLevelButton;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_select_difficult_level, container, false);

			// Get resources
			easyLevelButton = (Button) rootView
					.findViewById(R.id.easyLevelButton);
			mediumLevelButton = (Button) rootView
					.findViewById(R.id.mediumLevelButton);
			hardLevelButton = (Button) rootView
					.findViewById(R.id.hardLevelButton);

			// Set onClickListener
			easyLevelButton.setOnClickListener(this);
			mediumLevelButton.setOnClickListener(this);
			hardLevelButton.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.easyLevelButton:
				difficult_level = EASY_LEVEL;
				break;
			case R.id.mediumLevelButton:
				difficult_level = MEDIUM_LEVEL;
				break;
			case R.id.hardLevelButton:
				difficult_level = HARD_LEVEL;
				break;
			default:
				break;
			}
			
			Intent goToPlayIntent = new Intent(getActivity(), PlayActivity.class);
			Bundle game_setting = new Bundle();
			game_setting.putInt("PLAY_TYPE", play_type);
			game_setting.putInt("DIFFICULT_LEVEL", difficult_level);
			goToPlayIntent.putExtra("GAME_SETTING", game_setting);
			startActivity(goToPlayIntent);
		}
	}

}
