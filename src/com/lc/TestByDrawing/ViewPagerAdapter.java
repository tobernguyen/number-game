package com.lc.TestByDrawing;



import com.example.japaneseapp.R;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
	Context mContext;
	public ViewPagerAdapter(Context context ) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return romajiAdapter.length;
	}

	
	@Override
	public Object instantiateItem(View container, int position) {
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // set view to display in page
		View view = inflater.inflate(R.layout.layout_test, null);
        TextView tvTest = (TextView) view.findViewById(R.id.tvTest);
        // set text to display
		tvTest.setText(romajiAdapter[position]);
		((ViewPager) container).addView(view, 0);
        return view;
	}
	

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
 
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
 
    @Override
    public Parcelable saveState() {
        return null;
    }
    
    /* khi a tao bo test thi a truyen vao 1 mot array duoc tao ra thay array nay thi se test ok 
     * khong thi sau do em viet 1 ham nhan vao 1 array xong thay array nay la xong 
     */
	public String[] romajiAdapter = { "a", "i", "u", "e", "o", "ka", "ki",
			"ku", "ke", "ko", "sa", "shi", "su", "se", "so", "ta", "chi",
			"tsu", "te", "to", "na", "ni", "nu", "ne", "no", "ha", "hi", "fu",
			"he", "ho", "ma", "mi", "mu", "me", "mo", "ya", "yu","yo", "ra",
			"ri", "ru", "re", "ro", "wa", "wo", "n" };
	
}
