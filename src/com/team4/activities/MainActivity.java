package com.team4.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.team4.lawyertools.R;
import com.team4.utils.util.FuncUtil;
import com.team4.views.CompaniesView;

public class MainActivity extends Activity implements OnClickListener {

	private static final int FOCUS_LINE_WIDTH_DP = 60; // 单位为dp
	private static final int textViewIndex[] = { R.id.text1, R.id.text2,
			R.id.text3, R.id.text4 };
	private int mCurrentPos = 0;
	private ViewPager mViewPager;
	private View mFocusLine;
	private int mTvWidth;
	private int mOffset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTextView();
		initViewPager();
	}

	@Override
	protected void onStart() {
		super.onStart();
		initFocusLine();
		setFocusTab(mCurrentPos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initTextView() {
		for (int i = 0; i < textViewIndex.length; i++) {
			TextView tv = (TextView) findViewById(textViewIndex[i]);
			tv.setTag(i);
			tv.setOnClickListener(this);
		}
	}

	private void initFocusLine() {
		mFocusLine = findViewById(R.id.v_focus_line);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mTvWidth = dm.widthPixels / textViewIndex.length;
		mOffset = (mTvWidth - FuncUtil.dp2px(this, FOCUS_LINE_WIDTH_DP)) / 2;
	}

	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.vPager);
		final List<View> listViews = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		listViews.add(new CompaniesView(this));
		listViews.add(inflater.inflate(R.layout.activity_page_2, null));
		listViews.add(inflater.inflate(R.layout.activity_page_3, null));
		listViews.add(inflater.inflate(R.layout.activity_page_4, null));
		mViewPager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return (arg0 == arg1);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listViews.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
			}

			public Object instantiateItem(ViewGroup container, int position) {
				View view = listViews.get(position);
				container.addView(view, 0);
				return view;
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				setFocusTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		mViewPager.setCurrentItem(mCurrentPos);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem((Integer) view.getTag(), true);
	}

	private void setFocusTab(int position) {
		int lastX = mTvWidth * mCurrentPos + mOffset;

		TextView tv = (TextView) findViewById(textViewIndex[mCurrentPos]);
		tv.setTextColor(getResources().getColor(R.color.color_zhonghui));
		mCurrentPos = position;
		tv = (TextView) findViewById(textViewIndex[mCurrentPos]);
		tv.setTextColor(getResources().getColor(R.color.white));

		TranslateAnimation animation = new TranslateAnimation(lastX, mTvWidth
				* mCurrentPos + mOffset, 0, 0);
		animation.setFillAfter(true);
		mFocusLine.setAnimation(animation);
	}
}
