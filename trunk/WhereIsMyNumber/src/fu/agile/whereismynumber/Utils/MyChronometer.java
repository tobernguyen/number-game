package fu.agile.whereismynumber.Utils;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class MyChronometer extends Chronometer {

	boolean isPaused = false;

	public MyChronometer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyChronometer(Context context) {
		super(context);
	}

	public MyChronometer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private long timeWhenStopped;

	public long stopAndGetTimeInSeconds() {
		stop();
		long time = SystemClock.elapsedRealtime() - getBase();
		long hours = (int) (time / 3600000);
		long minutes = (int) (time - hours * 3600000) / 60000;
		long seconds = (int) (time - hours * 3600000 - minutes * 60000) / 1000;
		return seconds + minutes * 60;
	}

	public void pause() {
		if (!isPaused) {
			isPaused = true;
			timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
			stop();
		}
	}

	public void resume() {
		if (isPaused) {
			setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
			start();
			isPaused = false;
		}
	}
}
