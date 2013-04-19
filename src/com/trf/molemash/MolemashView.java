package com.trf.molemash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MolemashView extends View{
	
	private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;
	private static final String TAG = "OnDraw";
    
	private int times = 0;
	public int mScore = 0;
    public long mMoveDelay = 1000;
    private long mLastMove = 0;
    private TextView mStatusText;
    
    public float mMoleTop;
    public float mMoleLeft;
    
    private View mBackgroundView;
    
    Bitmap mMole = BitmapFactory.decodeResource(getResources(), R.drawable.mole);
    
	public MolemashView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initMolemashView();
	}
	
	private void initMolemashView() {
		// TODO Auto-generated method stub
		setFocusable(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mMole, mMoleLeft, mMoleTop, null);
	}
	
	public void setDependentView(TextView msgText, View backgroundView ){
		mStatusText = msgText;
		mBackgroundView = backgroundView;
	}
	
	public void moveMole(){
		if(mMode == READY | mMode == LOSE){
			initNewGame();
			setMode(RUNNING);
			update();
			return;
		}
		if(mMode == PAUSE){
			setMode(RUNNING);
			update();
			return;
		}
		return;
	}
	
	private void initNewGame(){
		mScore = 0;
		mMoveDelay = 1000;
		times = 60;
	}
	
	public void update(){
		if (mMode == RUNNING){
			long now = System.currentTimeMillis();
			Log.i(TAG, "times = " + times + "score=" + mScore);
			if (now - mLastMove > mMoveDelay){
				updateMole();
				mLastMove = now;
			}
			if (times >= 0){
				mRedrawHandler.sleep(mMoveDelay);
				times--;
			}else {
				setMode(LOSE);
			}
		}
	}
	
	private void updateMole(){
		mMoleTop = (float)Math.random() * (getBottom() - mMole.getHeight());
		mMoleLeft = (float)Math.random() * (getRight() - mMole.getWidth());
	}
	
	public RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        	MolemashView.this.update();
        	MolemashView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
	
	public void setMode(int newMode){
		int oldMode = mMode;
		mMode = newMode;
		
		if (newMode == RUNNING && oldMode != RUNNING){
			mStatusText.setVisibility(View.INVISIBLE);
			update();
			mBackgroundView.setVisibility(View.VISIBLE);
			return;
		}
		
		Resources res = getContext().getResources();
		CharSequence str = "";
		
		if (newMode == READY){
			mBackgroundView.setVisibility(View.GONE);
			str = res.getText(R.string.mode_ready);
			
		}
		if (newMode == PAUSE){
			str = res.getText(R.string.mode_pause);
		}
		if(newMode == LOSE){
            mBackgroundView.setVisibility(View.GONE);
			str = res.getString(R.string.mode_lose, mScore);
		}
		
		mStatusText.setText(str);
		mStatusText.setVisibility(View.VISIBLE);
	}
	public int getGameState(){
		return mMode;
	}
}
