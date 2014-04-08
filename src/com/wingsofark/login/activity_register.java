package com.wingsofark.login;


import com.wingsofark.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class activity_register extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
	}

}
