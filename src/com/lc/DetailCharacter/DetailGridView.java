package com.lc.DetailCharacter;

import com.example.japaneseapp.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class DetailGridView  extends Dialog{

	private Context mContext;
	
	public DetailGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public void showDetail(){
		setContentView(R.layout.show_detail_gridview);
		TextView textHiraga = (TextView) findViewById(R.id.detailHira);
		TextView textRomaji = (TextView) findViewById(R.id.detailRomaji);
		textHiraga.setText("あ");
		textRomaji.setText("a");
		show();
	}

}
