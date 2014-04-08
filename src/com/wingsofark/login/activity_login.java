package com.wingsofark.login;

import com.wingsofark.main.MainActivity;
import com.wingsofark.main.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class activity_login extends Activity{
	
	private Button login;
	private Button login_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_login);
		
		login = (Button)findViewById(R.id.login);
		login_register = (Button)findViewById(R.id.login_Register);
		
		
		login.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login.setClickable(false);
				Intent intent = new Intent(activity_login.this, MainActivity.class);
				startActivity(intent);
				login.setClickable(true);
			}
		});
		
		login_register.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				login_register.setClickable(false);
				Intent intent = new Intent(activity_login.this, activity_register.class);
				startActivity(intent);
				login_register.setClickable(true);
			}
		});
	}
	/***********menu 功能键的点击事件**************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "退出");
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		//菜单项1被选择
		case 0 :
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		}
		return true;
	}
	/***********************press KEY_BACK twice to exit*******************/
    private long exitTime = 0;
    @SuppressLint("ShowToast")
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime) > 2000){  
                Toast.makeText(getApplicationContext(), "再按一次退出程序", 2000).show();                         
                exitTime = System.currentTimeMillis();
            } else {
//            	GlobalID globalID = ((GlobalID)getApplication());
//            	if(!et_login_ID.getText().toString().equals(""))globalID.setID(et_login_ID.getText().toString());
////            	globalID.setId_check(cb_login_ID.isChecked());
////            	globalID.clear();
                finish();
        		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /***********************press KEY_BACK twice to exit*******************/
    

}
