package com.vsoyou.sdk.compents;

import com.vsoyou.sdk.util.LogUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

public abstract class BasicWindowView extends BasicView {

    public BasicWindowView(Context context) {
        super(context);
    }

    public BasicWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicWindowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void show() {

        if (!this.show) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.CENTER;
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.addView(this, params);
            this.setBackgroundColor(Color.parseColor("#b0000000"));
            onShow();
            setVisibility(VISIBLE);

            this.show = true;

        }

    }

    public void close() {

        if (this.show) {
            onClose();
            setVisibility(GONE);

            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.removeView(this);

            this.show = false;
        }

    }
    

}
