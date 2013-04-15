package com.trf.molemash;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class Molemash extends Activity{
	
	private MolemashView mMolemashView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.molemash_lauout);
		mMolemashView = (MolemashView)findViewById(R.id.molemash);
		mMolemashView.setDependentView((TextView)findViewById(R.id.text), (BackgroundView)findViewById(R.id.background));
		
		if(savedInstanceState == null)
			mMolemashView.setMode(MolemashView.READY);
		else
			mMolemashView.setMode(MolemashView.PAUSE);
		mMolemashView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mMolemashView.moveMole();
				return false;
			}
		});
		}
}
