package com.example.autoviewpagerdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener {
	
	private ViewPager viewPager;
	
	private ImageView[] tips;
	
	private ImageView[] mImageViews;
	
	private TextView mTitle;
	
	private int[] imgIdArray ;
	
	private String[] textArray;
	
	private boolean flag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		mTitle = (TextView) findViewById(R.id.titleText);
		imgIdArray = new int[]{R.drawable.item_01, R.drawable.item_02, R.drawable.item_03, R.drawable.item_04};
		
		textArray = new String[]{
				"QQ弹窗，腾讯帝国的万能毒药？",
				"母亲为让女儿认识自然包下一座山",
				"里约炸毁高速路为奥运开路",
				"奥巴马与日本机器人踢球"
		};
		
		tips = new ImageView[imgIdArray.length];
		for(int i=0; i<tips.length; i++){
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10,10);
			lp.setMargins(0, 0, 4, 0);
	    	imageView.setLayoutParams(lp);
	    	tips[i] = imageView;
	    	if(i == 0){
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
	    	}else{
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
	    	}
	    	
	    	 group.addView(imageView);
		}
		
		
		mImageViews = new ImageView[imgIdArray.length];
		for(int i=0; i<mImageViews.length; i++){
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		
		viewPager.setAdapter(new SlideAdapter());
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem((mImageViews.length) * 2);
		
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				System.err.println("start");
				viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			}
		};
		new Thread(){
			public void run() {
				while(flag){
					int delay = 2000;
					try {
						sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.sendEmptyMessage(0);
				}
			};
		}.start();

	}

	public class SlideAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
			
		}

		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}
		
		
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		System.out.println(arg0);
		setImageBackground(arg0 % mImageViews.length);
		setText(arg0 % mImageViews.length);
	}
	

	private void setImageBackground(int selectItems){
		for(int i=0; i<tips.length; i++){
			if(i == selectItems){
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}
	
	private void setText(int selectItems) {
		mTitle.setText(textArray[selectItems]);

	}
}
