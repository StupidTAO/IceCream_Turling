package com.icecream;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
/**
 * @author manymore13
 */
@SuppressLint("HandlerLeak")
public class GuideActivity extends Activity {

	// 鍒拌揪鏈�悗涓�紶
	private static final int TO_THE_END = 0;   
	// 绂诲紑鏈�悗涓�紶
	private static final int LEAVE_FROM_END = 1; 

	// 鍙渶鍦ㄨ繖閲屾坊鍔犲垹闄ゅ浘鐗囧嵆鍙�	
	
	private int[] ids = { R.drawable.s1,
			R.drawable.s2, R.drawable.s3,
			 };
			
	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;         
	private ImageView start;          // 鐐瑰嚮浣撻獙
	private ImageView curDot;         //褰撳墠鍥剧墖
	private LinearLayout dotContain; // 瀛樺偍褰撳墠鐐圭殑瀹瑰櫒
	private int offset;              // 浣嶇Щ閲�	
	private int curPos = 0;          // 璁板綍褰撳墠鐨勪綅缃�	     

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //鍘婚櫎鏍囬
		setContentView(R.layout.main);
		init();         // 鍔熻兘浠嬬粛鐣岄潰鐨勫垵濮嬪寲
		
	}
	
	private ImageView buildImageView(int id)
	{
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		return iv;
	}
		
	// 鍔熻兘浠嬬粛鐣岄潰鐨勫垵濮嬪寲
	private void init()
	{
		this.getView();
		initDot();
		ImageView iv = null;
		guides.clear();
		for (int i = 0; i < ids.length; i++) {
			iv = buildImageView(ids[i]);
			guides.add(iv);  //娣诲姞鍒癓ist閲�		
		}
		
		System.out.println("guild_size="+guides.size());

		// 褰揷urDot鐨勬墍鍦ㄧ殑鏍戝舰灞傛灏嗚琚粯鍑烘椂姝ゆ柟娉曡璋冪敤
		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						// 鑾峰彇ImageView鐨勫搴︿篃灏辨槸褰撳墠鐐瑰浘鐗囩殑瀹藉害
						offset = curDot.getWidth();
						return true;
					}
				});

		final GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
		// ViewPager璁剧疆鏁版嵁閫傞厤鍣紝杩欎釜绫讳技浜庝娇鐢↙istView鏃剁敤鐨刟dapter
		pager.setAdapter(adapter);
		pager.clearAnimation();
		// 涓篤iewpager娣诲姞浜嬩欢鐩戝惉鍣�OnPageChangeListener
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position)
			{
		
				int pos = position % ids.length;
				
				moveCursorTo(pos);
				
				
				if (pos == ids.length-1) {// 鍒版渶鍚庝竴寮犱簡
					handler.sendEmptyMessageDelayed(TO_THE_END, 500);					
					
				} else if (curPos == ids.length - 1) {
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}
				curPos = pos;     //杩斿洖涓婁竴涓晫闈㈡椂浣撻獙鎸夐挳鍙互褰辫棌
				super.onPageSelected(position);
			}
		});
		
	}
	
	/**
	 *  鍦╨ayout涓疄渚嬪寲涓�簺View
	 */
	private void getView()
	{
		dotContain = (LinearLayout)this.findViewById(R.id.dot_contain);
		pager = (ViewPager) findViewById(R.id.contentPager);
		curDot = (ImageView) findViewById(R.id.cur_dot);
		start = (ImageView) findViewById(R.id.open);
		start.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();  //鍝嶅簲浣撻獙鎸夐挳
			}
		});
	}
	
	/**
	 * 鍒濆鍖栫偣 ImageVIew
	 * @return 杩斿洖true璇存槑鍒濆鍖栫偣鎴愬姛锛屽惁鍒欏疄渚嬪寲澶辫触
	 */
	private boolean initDot()
	{
		
		if(ids.length > 0){
			ImageView dotView ;
			for(int i=0; i<ids.length; i++)
			{
				dotView = new ImageView(this);
				dotView.setImageResource(R.drawable.dot1_w);
				dotView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
				
				dotContain.addView(dotView);
			}
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 绉诲姩鎸囬拡鍒扮浉閭荤殑浣嶇疆 鍔ㄧ敾
	 * @param position
	 * 鎸囬拡鐨勭储寮曞�
	 * */
	private void moveCursorTo(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation tAnim = 
				new TranslateAnimation(offset*curPos, offset*position, 0, 0);
		animationSet.addAnimation(tAnim);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		curDot.startAnimation(animationSet);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == TO_THE_END)
				start.setVisibility(View.VISIBLE);
			else if (msg.what == LEAVE_FROM_END)
				start.setVisibility(View.GONE);
		}
	};
	
	
	
	// ViewPager 閫傞厤鍣℅uidePagerAdapter锛岀户鎵縋agerAdapter绫�	
	class GuidePagerAdapter extends PagerAdapter{
		
		private List<View> views;
		
		public GuidePagerAdapter(List<View> views){
			this.views=views;
		}
		
		@Override
		public void destroyItem(View v, int position, Object arg2) {
			//浠嶸iewGroup涓Щ鍑哄綋鍓峍iew,鏂归潰涓嬩竴涓猇Iew鏄剧ず
			((ViewPager) v).removeView(views.get(position % views.size()));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			// 鑾峰彇褰撳墠绐椾綋鐣岄潰鏁�杩欓噷杩斿洖涓�釜绋嶅井澶х偣鍊�涓嶇劧婊戝埌椤跺氨婊戜笉鍔ㄤ簡
			return views.size();
		}

		@Override
		public Object instantiateItem(View v, int position) {
			//return涓�釜瀵硅薄锛岃繖涓璞¤〃鏄庝簡PagerAdapter閫傞厤鍣ㄩ�鎷╁摢涓璞℃斁鍦ㄥ綋鍓嶇殑ViewPager涓�			
			
			((ViewPager) v).addView(views.get(position % views.size()),0);
			return views.get(position % views.size());
		}

		@Override
		public boolean isViewFromObject(View v, Object object) {   //鐢ㄤ簬鍒ゆ柇鏄惁鐢卞璞＄敓鎴愮晫闈�			
			return v == (object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			
		}
		

	}
	
}