package com.wingsofark.main;

import java.util.ArrayList;
import java.util.List;

import com.wingsofark.util.LockableHorizontalScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ViewPager mPager;   //页面内容
	private List<View> listviews;  // tab页面列表
	private ImageView cursor;   // 动画图片
	private TextView New, Message, Contact;   //页面头标
	private int tabwidth;   //每个tab头的宽度
	private int offset = 0;   //动画图片偏移量
	private int currIndex = 0;   //当前页面编号
	private int bmpw;   //动画图片宽度
	
	private LockableHorizontalScrollView scrollView;
	private Button slide;
	private LinearLayout listViews,cateMenu,closeMenuLinear;
	private View closeMenu;
	
	private int screenWidth;
	private boolean isOpen = false;//右侧menu是否打开的标识
	private final Handler mHandler = new Handler();
	Handler handler = new Handler();

	private boolean isexit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_view);
		
		scrollView = (LockableHorizontalScrollView) findViewById(R.id.ScrollView);
		slide = (Button) findViewById(R.id.slide);
		listViews = (LinearLayout) findViewById(R.id.list_views);
		cateMenu = (LinearLayout) findViewById(R.id.cate_menu);
		closeMenuLinear = (LinearLayout) findViewById(R.id.close_menu_linear);
		closeMenu = findViewById(R.id.close_menu);
		
		DisplayMetrics metric = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metric);
	    //窗口的宽度   
	    screenWidth = metric.widthPixels;

 		LayoutParams menuparams = (LayoutParams) cateMenu.getLayoutParams();
 		menuparams.width = screenWidth*5/6;
 		cateMenu.setLayoutParams(menuparams);
	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listViews.getLayoutParams();
  		params.width = screenWidth;
  		listViews.setLayoutParams(params);
	  //创建一个ScrollView对象
 		LayoutParams scroll_params = (LayoutParams) scrollView.getLayoutParams();
 		scroll_params.width = screenWidth;
 		scrollView.setLayoutParams(scroll_params);
 		LayoutParams closeparams = (LayoutParams) closeMenu.getLayoutParams();
 		closeparams.width = screenWidth/6;
 		closeMenu.setLayoutParams(closeparams);
 		
 		slide.setOnClickListener(mClickListener);// 添加点击事件监听
 		setListener();
 		Log.e("setlistener", "success");
 		
		InitTextView();
		Log.e("textview", "success");
		InitViewPager();
		Log.e("viewpager", "success");
		InitImageView();
		Log.e("imageview", "success");
		
		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "侧滑点击事件生效", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void setListener() {
		// TODO Auto-generated method stub
closeMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mHandler.post(mScrollToButton);//传递一个消息进行滚动
			}
		});
	}
	// Button事件监听
  	private Button.OnClickListener mClickListener = new Button.OnClickListener() {
  		@Override
  		public void onClick(View v) {
  			mHandler.post(mScrollToButton);//传递一个消息进行滚动
  		}
  	};
    private Runnable mScrollToButton = new Runnable() {
 		@Override
 		public void run() {
 			if (!isOpen) {
 				scrollView.smoothScrollTo( screenWidth*5/6,0);// 改变滚动条的位置
 				isOpen = true;
	 			closeMenuLinear.setVisibility(View.VISIBLE);
 			}else{
 				scrollView.smoothScrollTo( 0,0);
 				isOpen = false;
 				closeMenuLinear.setVisibility(View.GONE);
 			}
 		}
 	};
 	/**
	 * 捕捉返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	if(isOpen){
        		mHandler.post(mScrollToButton);//传递一个消息进行滚动
        		return true;
			}
        	if(!isexit) {  
                isexit = true;    
                Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",  
                        Toast.LENGTH_SHORT).show();  
                // 利用handler延迟发送更改状态信息  
                myhandler.sendEmptyMessageDelayed(0, 2000);   
            }  
        	else{
        		 Log.e("退出", "exit application");  
                 
                 this.finish();
                 System.exit(0);
        	}
          return true;  	
        }
        return super.onKeyDown(keyCode, event);
    }
	
	Handler myhandler = new Handler() {  
		  
        @Override  
        public void handleMessage(android.os.Message msg) {  
            // TODO Auto-generated method stub  
            super.handleMessage(msg);  
            isexit = false;  
        }  
  
    };  
	
	private void InitViewPager() {
		// TODO Auto-generated method stub
		mPager = (ViewPager) findViewById(R.id.vpager);
		listviews = new ArrayList<View>();
		LayoutInflater minflater = getLayoutInflater();
		listviews.add(minflater.inflate(R.layout.activity_login, null));
		listviews.add(minflater.inflate(R.layout.activity_register, null));
		listviews.add(minflater.inflate(R.layout.activity_main, null));
		
		mPager.setAdapter(new MyPagerAdapter(listviews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}

	private void InitTextView() {
		// TODO Auto-generated method stub
		New = (TextView) findViewById(R.id.New); 
		Message = (TextView) findViewById(R.id.Message);
		Contact = (TextView) findViewById(R.id.Contact);
		
		New.setOnClickListener(new MyOnClickListener(0));
		Message.setOnClickListener(new MyOnClickListener(1));
		Contact.setOnClickListener(new MyOnClickListener(2));
	}
	
	/****
	 * ****初始化动画
	 * ****/

	private void InitImageView() {
		// TODO Auto-generated method stub
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.cursor)
				.getWidth();  //获取图片宽度
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);   // 获取分辨率宽度
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenW = dm.widthPixels;
		Log.e("screenW", "screenW value = " + String.valueOf(screenW));
//		offset = (screenW / 3 - bmpw) / 2;
		tabwidth = screenW / listviews.size();     //计算偏移量
		Log.e("tabwidth", "tabwidth value = " + String.valueOf(tabwidth));
		
		if(bmpw > tabwidth) {
			cursor.getLayoutParams().width = tabwidth;
			bmpw = tabwidth;
		}
		offset = (tabwidth - bmpw) / 2;
		
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);    // 设置动画初始位置
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	/**
	 * 头标点击监听
	 * */
	public class MyOnClickListener implements View.OnClickListener {
		
		private int index = 0;
		
		public MyOnClickListener(int i ) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	}
	
	/**
	 * viewpager 适配器实现
	 * ***/
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;
		
		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			
			((ViewPager) container).removeView(mListViews.get(position));
		}
		@Override
		public void finishUpdate(View container) {
			// TODO Auto-generated method stub
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListViews.size();
		}
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mListViews.get(position), 0);
			return mListViews.get(position);
			
		}
		public boolean isViewFromObject(View container, Object arg) {
			return container == (arg);
		}
		
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// TODO Auto-generated method stub
			super.restoreState(state, loader);
		}
		public Parcelable saveState() {
			return null;
		}
		public void startUpate(View arg) {
			
		}
	}
	
	/*
	 * 页面之间切换监听
	 * */
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		
		int one = offset * 2 + bmpw;   //页面1 -> 页面2 偏移量
		int two = one *2; //页面2 -> 页面3 偏移量

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = new TranslateAnimation(tabwidth * currIndex
					+ offset, tabwidth * arg0 + offset, 0, 0);
//			Animation animation = null;
//			switch(arg0) {
//			case 0:
//				if (currIndex == 1) {
//					animation = new TranslateAnimation(one, 0, 0, 0);
//				} else if (currIndex == 2) {
//					animation = new TranslateAnimation(two, 0, 0, 0);
//				}
//				break;
//			case 1:
//				if (currIndex == 0) {
//					animation = new TranslateAnimation(offset, one, 0, 0);
//				} else if (currIndex == 2) {
//					animation = new TranslateAnimation(two, one, 0, 0);
//				}
//				break;
//			case 2:
//				if (currIndex == 0) {
//					animation = new TranslateAnimation(offset, two, 0, 0);
//				} else if (currIndex == 1) {
//					animation = new TranslateAnimation(one, two, 0, 0);
//				}
//				break;
//			}
			currIndex = arg0;
			animation.setFillAfter(true);   //true 图片停在动画结束位置
			animation.setDuration(350);
			cursor.startAnimation(animation);
		}

	}
}
