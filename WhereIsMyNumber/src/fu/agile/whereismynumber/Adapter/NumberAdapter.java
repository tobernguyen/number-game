package fu.agile.whereismynumber.Adapter;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fu.agile.whereismynumber.R;
import fu.agile.whereismynumber.Enquity.Config;
import fu.agile.whereismynumber.Enquity.Number;

public class NumberAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Number> numbers;
	/* private int screen_width, cellSize; */
	private Typeface font_for_number_on_grid;
	private int backGround_1;
	private int backGround_2;

	// private int cellSize;

	public NumberAdapter(Context context, ArrayList<Number> numbers,
			int numberOfColumns) {
		super();
		this.context = context;
		font_for_number_on_grid = Typeface.createFromAsset(context.getAssets(),
				Config.Font.NUMBER_ON_GRID_FONT);

		// Initiate default number
		this.numbers = numbers;

		// Shuffle number list
		Collections.shuffle(numbers);

		// DisplayMetrics displaymetrics = new DisplayMetrics();
		// ((Activity) context).getWindowManager().getDefaultDisplay()
		// .getMetrics(displaymetrics);
		//
		// int screen_width = displaymetrics.widthPixels;
		// int screen_heigh = displaymetrics.heightPixels;
		//
		// Tuy chinh cellSize dua theo kich thuoc man hinh va so cot cellSize
		// cellSize = screen_width / numberOfColumns;

		// Get drawable ID
		backGround_1 = R.drawable.stroke;
		backGround_2 = R.drawable.stroke_2;
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

			// set up the ViewHolder
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

		// Set color

		int backGroundResourceID = backGround_1;
		switch (position % 12) {
		case 0:
		case 2:
		case 4:
		case 7:
		case 9:
		case 11:
			// colorCode = "#17b287";
			backGroundResourceID = backGround_1;
			break;
		case 1:
		case 3:
		case 5:
		case 6:
		case 8:
		case 10:
			// colorCode = "#0776b8";
			backGroundResourceID = backGround_2;
			break;
		default:
			break;
		}

		mTextView.setBackgroundResource(backGroundResourceID);
		mTextView.setTextColor(Color.WHITE);
		// mTextView.setBackgroundColor(Color.parseColor(colorCode));

		mTextView.setTypeface(font_for_number_on_grid);
		// mTextView.setTextSize(20);
		// mTextView.setWidth(cellSize);
		// mTextView.setHeight(cellSize);
		mTextView.setText("" + numbers.get(position));

		return convertView;
	}

}
