package com.trf.molemash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BackgroundView extends View{

	private Bitmap mBackground;
	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setFocusable(true);
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mBackground, 0, 0, new Paint());
	}

}
