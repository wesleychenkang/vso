package com.vsoyou.sdk.compents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 自定义Gallery
 * @author lmy
 * 2013-6-26
 */
@SuppressWarnings("deprecation")
public class MyGallery extends Gallery{
	
	public MyGallery(Context context) {
		super(context);
	}
	
	public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }   
    
    public boolean onFling(MotionEvent e1,MotionEvent e2, float velocityX,
            float velocityY) {
        int keyCode;
        //这样能够实现每次滑动只滚动一张图片的效果
        if (isScrollingLeft(e1,e2)) {
            keyCode= KeyEvent.KEYCODE_DPAD_LEFT;
        }else{
            keyCode= KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode,null);
        return true;
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
            return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            return super.onTouchEvent(event);
    }

}
