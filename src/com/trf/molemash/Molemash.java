package com.trf.molemash;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class Molemash extends Activity{
	
	private MolemashView mMolemashView;
	private TextView mScore;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.molemash_layout);
		mMolemashView = (MolemashView)findViewById(R.id.molemash);
		mMolemashView.setDependentView((TextView)findViewById(R.id.text), (BackgroundView)findViewById(R.id.background));
		mScore = (TextView)findViewById(R.id.score);
		
		if(savedInstanceState == null)
			mMolemashView.setMode(MolemashView.READY);
		else
			mMolemashView.setMode(MolemashView.PAUSE);
		mMolemashView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (mMolemashView.getGameState() == MolemashView.RUNNING) {
					//如果 event.x在 mMoleLeft与mMoleLeft+mMoleWidth之间
					//	  event.y在mMoleTop与mMoleTop+mMoleHeight之间,则打中mole并加分
					if (mMolemashView.mMoleLeft <= event.getX() && event.getX() <= (mMolemashView.mMoleLeft+mMolemashView.mMole.getWidth())){
						if(mMolemashView.mMoleTop <= event.getY() && event.getY() <= (mMolemashView.mMoleTop+mMolemashView.mMole.getHeight())){
							mMolemashView.mScore += 10;
							mScore.setText(mMolemashView.mScore + "");
							mMolemashView.update();
							mMolemashView.invalidate();
						}
					}
				}else {
					mMolemashView.moveMole();
				}
				return false;
			}
		});
		}
}