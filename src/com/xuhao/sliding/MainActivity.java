package com.xuhao.sliding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.xuhao.sliding.view.SildeMenu;
import comxuhao.sliding.R;

public class MainActivity extends Activity {
	private ImageView iv;
	private SildeMenu sildeMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		iv=(ImageView) findViewById(R.id.iv_menu);
		sildeMenu=(SildeMenu) findViewById(R.id.sildemenu);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sildeMenu.switchMenu();
			}
		});
	}

}
