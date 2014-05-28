package com.example.japaneseapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.lc.DetailCharacter.DetailGridView;
import com.lc.DetailCharacter.PronunciationCharacter;
import com.lc.TestByDrawing.ViewPagerAdapter;
import com.lc.adapter.CharacterHiraganaAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private CharacterHiraganaAdapter adapterCharacter;
		private GridView gridCharacter;
		private DetailGridView detailCharacter;
		private PronunciationCharacter pronunciationCharacter;
		private ViewPager mPager;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			// display gridView Hiragana or Katakana
			/* sau day minh o the tach ra 2 activity voi 2 function em de vao trong nay de anh test cho nhanh 
			 * function gird view kia em lam xong roi con phan audio thoi dang nghi cach cho no do thu cong
			 * kieu nhu an vao cai nao thi no tim den R.raw de tuong ung ay 
			 * thu cong nhat la so sanh string xong de chon audio tuong ung nhung ko hieu nang lam 
			 */
//			View rootView = inflater.inflate(R.layout.hiragana_gridview, container,
//					false);
//			super.onCreate(savedInstanceState);
//			adapterCharacter = new CharacterHiraganaAdapter(getActivity());
//			gridCharacter = (GridView) rootView.findViewById(R.id.characterHiraganaGrid);
//			gridCharacter.setAdapter(adapterCharacter);
//			gridCharacter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//				
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					
//					detailCharacter = new DetailGridView(getActivity());
//					detailCharacter.showDetail();
//					
//				}
//			});
//			return rootView;
			//-----------------------------------------------------------///
			View rootView = inflater.inflate(R.layout.main_test_slide, container,
					false);
			mPager = (ViewPager) rootView.findViewById(R.id.mytestslide);
			ViewPagerAdapter adapterTest = new ViewPagerAdapter(getActivity());
			mPager.setAdapter(adapterTest);
			return rootView;
			
		}
		
	}
}
	
	
