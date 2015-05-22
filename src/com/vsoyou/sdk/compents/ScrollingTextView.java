package com.vsoyou.sdk.compents;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯 效果的 textview
 * 
 * @author lmy 2013-6-17
 */
public class ScrollingTextView extends TextView {
	public boolean isScroll = true;

	public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrollingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollingTextView(Context context) {
		super(context);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean focused) {
		if (focused)
			super.onWindowFocusChanged(focused);
	}

	@Override
	public boolean isFocused() {
		return isScroll;
	}

}
