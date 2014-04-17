package fu.agile.whereismynumber.Adapter;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.Number;

public class NumberAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Number> numbers;
	private int screen_width, cellSize;

	public NumberAdapter(Context context, ArrayList<Number> numbers,
			int numberOfColumns) {
		super();
		this.context = context;

		// Initiate default number
		this.numbers = numbers;

		// Shuffle number list
		Collections.shuffle(numbers);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);

		screen_width = displaymetrics.widthPixels;

		// Tuy chinh cellSize dua theo kich thuoc man hinh va so cot
		cellSize = screen_width / numberOfColumns;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return numbers.size();
	}

	@Override
	public Object getItem(int position) {
		return numbers.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolderItem viewHolder;

		if (convertView == null) {

			// inflate the layout
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grid_number, null);

			// set upthe ViewHolder
			viewHolder = new ViewHolderItem();
			viewHolder.textViewItem = (TextView) convertView
					.findViewById(R.id.gridValue);

			// store the holder with the view
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();
		}

		// Tuy chinh kich thuoc cua cell theo cellSize (theo kich thuoc man
		// hinh)
		TextView mTextView = viewHolder.textViewItem;
		mTextView.setWidth(cellSize);
		mTextView.setHeight(cellSize);
		mTextView.setText("" + numbers.get(position));

		return convertView;
	}

	static class ViewHolderItem {
		TextView textViewItem;
	}
}
