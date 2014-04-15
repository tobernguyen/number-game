package fu.agile.whereismynumber.GUI;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
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
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import fu.agile.whereismynumber.R;

public class PlayActivity extends ActionBarActivity {

	private static int play_type = 0;
	private static int difficult_level = 0;

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

		Bundle game_setting = getIntent().getBundleExtra("GAME_SETTING");
		play_type = game_setting.getInt("PLAY_TYPE");
		difficult_level = game_setting.getInt("DIFFICULT_LEVEL");

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		int index = 0;
		ArrayList<Number> listNumberDisplay = new ArrayList<Number>();
		ArrayList<Number> listNumberTarget = new ArrayList<Number>();

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_play, container,
					false);

			// TextView testTextView = (TextView)
			// rootView.findViewById(R.id.testTextView);
			// testTextView.setText("Play type: " + play_type +
			// " Difficult level: " + difficult_level);

			// Tao ra mot day so random de bat nguoi dung chon
			for (int i = 1; i <= 48; i++) {
				listNumberDisplay.add(new Number(i));
				listNumberTarget.add(new Number(i));
			}

			// Dao day so
			Collections.shuffle(listNumberDisplay);
			Collections.shuffle(listNumberTarget);

			// Xu ly GridNumber
			final NumberAdapter numbers_adapter = new NumberAdapter(
					getActivity(), listNumberDisplay);
			GridView gridNumber = (GridView) rootView
					.findViewById(R.id.gridView);
			gridNumber.setAdapter(numbers_adapter);

			// Xu ly target number
			final TextView targetNumberTextView = (TextView) rootView
					.findViewById(R.id.targetNumberTextView);
			targetNumberTextView
					.setText(listNumberTarget.get(index).toString());

			// Xu ly onLick GridView
			gridNumber.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					
					//Lay phan tu nguoi dung click vao
					Number clickedNumber = (Number) numbers_adapter
							.getItem(position);
					if (clickedNumber.equals(listNumberTarget.get(index))) {
						//Tang index
						nextTargetNumber();
						try {
							//Hieu ung cho target number
							Animation slide_left_out = AnimationUtils
									.loadAnimation(getActivity(),
											R.anim.slide_out_left);
							Animation slide_right_in = AnimationUtils
									.loadAnimation(getActivity(),
											R.anim.slide_in_right);
							targetNumberTextView.startAnimation(slide_left_out);
							targetNumberTextView.setText(listNumberTarget.get(
									index).toString());
							targetNumberTextView.startAnimation(slide_right_in);

						} catch (IndexOutOfBoundsException e) {
							//Hoan thanh phan choi
							Toast.makeText(getActivity(), "Completed",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			});

			return rootView;
		}

		public void nextTargetNumber() {
			index++;
		}

	}

}
