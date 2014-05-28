package com.lc.adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.japaneseapp.R;

public class CharacterKataganaAdapter extends BaseAdapter {

	private Context mContext;

	public CharacterKataganaAdapter(Context c) {
		// TODO Auto-generated constructor stub
		mContext = c;
	} 

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return katakanaAdapter.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return romajiAdapter[position];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.gird_character, null);
			  TextView textHiragana = (TextView) grid.findViewById(R.id.characterHiragana);
			  TextView textRomaji = (TextView) grid.findViewById(R.id.characterRomaji);
			  textHiragana.setText(katakanaAdapter[position]);
			  textHiragana.setTextColor(Color.BLACK);
			  textRomaji.setText(romajiAdapter[position]);
		}else {
            grid = convertView;
          }
      return grid;
	}
	public String[] romajiAdapter = {"a","i","u","e","o","ka","ki","ku","ke","ko",
			"sa","shi","su","se","so","ta","chi","tsu","te","to",
			"na","ni","nu","ne","no","ha","hi","fu","he","ho",
			"ma","mi","mu","me","mo","ya","yu","yo","ra","ri","ru","re","ro","wa","wo","n"};

	
	public String[] katakanaAdapter = { "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク",
			"ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ", "ト", "ナ",
			"ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ",
			"モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン" };

}
