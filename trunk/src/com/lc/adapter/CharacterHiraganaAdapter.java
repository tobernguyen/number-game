package com.lc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.japaneseapp.R;

public class CharacterHiraganaAdapter extends BaseAdapter {

	private Context mContext;

	public CharacterHiraganaAdapter(Context c) {
		// TODO Auto-generated constructor stub
		mContext = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hiraganaAdapter.length;
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
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			grid = inflater.inflate(R.layout.gird_character, parent, false);
			
		} else {
			grid = convertView;
		}
		TextView textHiragana = (TextView) grid
				.findViewById(R.id.characterHiragana);
		TextView textRomaji = (TextView) grid
				.findViewById(R.id.characterRomaji);
		textHiragana.setText(hiraganaAdapter[position]);
		textRomaji.setText(romajiAdapter[position]);
		return grid;
	}

	public String[] romajiAdapter = { "a", "i", "u", "e", "o", "ka", "ki",
			"ku", "ke", "ko", "sa", "shi", "su", "se", "so", "ta", "chi",
			"tsu", "te", "to", "na", "ni", "nu", "ne", "no", "ha", "hi", "fu",
			"he", "ho", "ma", "mi", "mu", "me", "mo", "ya"," ", "yu"," ", "yo", "ra",
			"ri", "ru", "re", "ro", "wa", "wo", "n" };

	public String[] hiraganaAdapter = { "あ", "い", "う", "え", "お", "か", "き", "く",
			"け", "こ", "さ", "し", "す", "せ", "そ", "た", "ち", "つ", "て", "と", "な",
			"に", "ぬ", "ね", "の", "は", "ひ", "ふ", "へ", "ほ", "ま", "み", "む", "め",
			"も", "や", " ","ゆ"," ", "よ", "ら", "り", "る", "れ", "ろ", "わ", "を", "ん" };

	// public String[] katakanaAdapter = { "ア", "イ", "ウ", "エ", "オ", "カ", "キ",
	// "ク",
	// "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ", "ト", "ナ",
	// "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ",
	// "モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン" };

}
