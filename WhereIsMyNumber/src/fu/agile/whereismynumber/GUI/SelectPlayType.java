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

public class SelectPlayType extends ActionBarActivity {

	private final static int INCREMENT_TYPE = 1;
	private final static int DECREMENT_TYPE = 2;
	private final static int RANDOM_TYPE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_play_type);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		private Button incrementTypeButton, decrementTypeButton, randomTypeButton;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_select_play_type, container, false);
			
			//Get resources
			incrementTypeButton = (Button) rootView.findViewById(R.id.incrementTypeButton);
			decrementTypeButton = (Button) rootView.findViewById(R.id.decrementTypeButton);
			randomTypeButton = (Button) rootView.findViewById(R.id.randomTypeButton);
			
			//Set onClickLisenter
			incrementTypeButton.setOnClickListener(this);
			decrementTypeButton.setOnClickListener(this);
			randomTypeButton.setOnClickListener(this);
			
			return rootView;
		}

		@Override
		public void onClick(View arg0) {
			int play_type = 0;
			
			//Create intent go to Select Difficult Level activity
			Intent goToDifficultLevel = new Intent(getActivity(), SelectDifficultLevel.class);
			
			switch (arg0.getId()) {
			case R.id.incrementTypeButton:
				play_type = INCREMENT_TYPE;
				break;
			case R.id.decrementTypeButton:
				play_type = DECREMENT_TYPE;
				break;
			case R.id.randomTypeButton:
				play_type = RANDOM_TYPE;
				break;
			default:
				break;
			}
			//Intent
			goToDifficultLevel.putExtra("PLAY_TYPE", play_type);
			startActivity(goToDifficultLevel);
		}
	}

}
