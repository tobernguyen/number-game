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
import android.widget.Toast;
import fu.agile.whereismynumber.R;

public class MainScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnClickListener {

    	private Button playButton, highScoreButton;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            
            //Get resoures
            playButton = (Button) rootView.findViewById(R.id.playButton);
            highScoreButton = (Button) rootView.findViewById(R.id.highScoreButton);
            
            
            
            //OnClickListener for each button
            playButton.setOnClickListener(this);
            highScoreButton.setOnClickListener(this);
            
            return rootView;
        }


		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.playButton:
				Intent goToSelectPlayType = new Intent(getActivity(), SelectPlayType.class);
	            startActivity(goToSelectPlayType);
				break;
			case R.id.highScoreButton:
				//TODO: Intent for High Score Button
				Toast.makeText(getActivity(), "High Score Button Clicked", Toast.LENGTH_SHORT).show();
				
			default:
				break;
			}
			
		}
    }

}
