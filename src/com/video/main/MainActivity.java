package com.video.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.video.mplayer.R;
import com.video.view.PlayVideoView;


public class MainActivity extends Activity {

    public PlayVideoView mplay;
    public LinearLayout mline;
    public Button bt1,bt2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    initwidget();
		// new Download(mProgressbar);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onBackPressed();
	}
	private void initwidget() {
		// TODO Auto-generated method stub
		mplay=(PlayVideoView) findViewById(R.id.playView);
		mline=(LinearLayout) findViewById(R.id.test_line);
		bt1=(Button) findViewById(R.id.test_bt1);
		bt2=(Button) findViewById(R.id.test_bt2);
		bt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mplay.playVideo("forcetv://sportnewlive.itvpad.co:9926/5707713200057ecc00c1a07b757f2c48");
			}
			
		});
		bt2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mplay.playVideo("force://upload.btvgod.com:9906/57ad78be000cd4450012a0e5459b33a3");
			}
			
		});
		
		//mline.requestFocus();
	}


}
