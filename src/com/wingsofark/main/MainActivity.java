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
	
	private ViewPager mPager;   //ҳ������
	private List<View> listviews;  // tabҳ���б�
	private ImageView cursor;   // ����ͼƬ
	private TextView New, Message, Contact;   //ҳ��ͷ��
	private int tabwidth;   //ÿ��tabͷ�Ŀ��
	private int offset = 0;   //����ͼƬƫ����
	private int currIndex = 0;   //��ǰҳ����
	private int bmpw;   //����ͼƬ���
	
	private LockableHorizontalScrollView scrollView;
	private Button slide;
	private LinearLayout listViews,cateMenu,closeMenuLinear;
	private View closeMenu;
	
	private int screenWidth;
	private boolean isOpen = false;//�Ҳ�menu�Ƿ�򿪵ı�ʶ
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
	    //���ڵĿ��   
	    screenWidth = metric.widthPixels;

 		LayoutParams menuparams = (LayoutParams) cateMenu.getLayoutParams();
 		menuparams.width = screenWidth*5/6;
 		cateMenu.setLayoutParams(menuparams);
	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listViews.getLayoutParams();
  		params.width = screenWidth;
  		listViews.setLayoutParams(params);
	  //����һ��ScrollView����
 		LayoutParams scroll_params = (LayoutParams) scrollView.getLayoutParams();
 		scroll_params.width = screenWidth;
 		scrollView.setLayoutParams(scroll_params);
 		LayoutParams closeparams = (LayoutParams) closeMenu.getLayoutParams();
 		closeparams.width = screenWidth/6;
 		closeMenu.setLayoutParams(closeparams);
 		
 		slide.setOnClickListener(mClickListener);// ��ӵ���¼�����
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
				Toast.makeText(getBaseContext(), "�໬����¼���Ч", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void setListener() {
		// TODO Auto-generated method stub
closeMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mHandler.post(mScrollToButton);//����һ����Ϣ���й���
			}
		});
	}
	// Button�¼�����
  	private Button.OnClickListener mClickListener = new Button.OnClickListener() {
  		@Override
  		public void onClick(View v) {
  			mHandler.post(mScrollToButton);//����һ����Ϣ���й���
  		}
  	};
    private Runnable mScrollToButton = new Runnable() {
 		@Override
 		public void run() {
 			if (!isOpen) {
 				scrollView.smoothScrollTo( screenWidth*5/6,0);// �ı��������λ��
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
	 * ��׽���ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	if(isOpen){
        		mHandler.post(mScrollToButton);//����һ����Ϣ���й���
        		return true;
			}
        	if(!isexit) {  
                isexit = true;    
                Toast.makeText(getApplicationContext(), "�ٰ�һ�κ��˼��˳�����",  
                        Toast.LENGTH_SHORT).show();  
                // ����handler�ӳٷ��͸���״̬��Ϣ  
                myhandler.sendEmptyMessageDelayed(0, 2000);   
            }  
        	else{
        		 Log.e("�˳�", "exit application");  
                 
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
	 * ****��ʼ������
	 * ****/

	private void InitImageView() {
		// TODO Auto-generated method stub
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.cursor)
				.getWidth();  //��ȡͼƬ���
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);   // ��ȡ�ֱ��ʿ��
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenW = dm.widthPixels;
		Log.e("screenW", "screenW value = " + String.valueOf(screenW));
//		offset = (screenW / 3 - bmpw) / 2;
		tabwidth = screenW / listviews.size();     //����ƫ����
		Log.e("tabwidth", "tabwidth value = " + String.valueOf(tabwidth));
		
		if(bmpw > tabwidth) {
			cursor.getLayoutParams().width = tabwidth;
			bmpw = tabwidth;
		}
		offset = (tabwidth - bmpw) / 2;
		
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);    // ���ö�����ʼλ��
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	/**
	 * ͷ��������
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
	 * viewpager ������ʵ��
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
	 * ҳ��֮���л�����
	 * */
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		
		int one = offset * 2 + bmpw;   //ҳ��1 -> ҳ��2 ƫ����
		int two = one *2; //ҳ��2 -> ҳ��3 ƫ����

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
			animation.setFillAfter(true);   //true ͼƬͣ�ڶ�������λ��
			animation.setDuration(350);
			cursor.startAnimation(animation);
		}

	}
}
