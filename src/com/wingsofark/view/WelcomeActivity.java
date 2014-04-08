package com.wingsofark.view;

import com.wingsofark.login.activity_login;
import com.wingsofark.main.MainActivity;
import com.wingsofark.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WelcomeActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//no title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		
		Handler handler = new Handler();
		handler.postDelayed(new Loading(), 2000);
//		Thread thread = new Thread() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void run() {
//				try{
//					sleep(1000);  //这里进行操作，设置显示
//				}
//				catch(InterruptedException e){
//					//do nothing
//				}
//				finally{
//					finish(); // 调用这个finish方法后， 在主界面按手机上的back键就会直接退出程序
//					// 启动主程序，进入主界面
//					
//					Intent intent = new Intent();
//					intent.setClass(WelcomeActivity.this, MainActivity.class);
//					startActivity(intent);
//					
//					stop();
//				}
//			}
//		};
//		
//		thread.start();
		
	}
	class Loading implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(WelcomeActivity.this, activity_login.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
			
		}
		
	}

}
