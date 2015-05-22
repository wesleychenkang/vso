package com.vsoyou.sdk.compents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class BasicView extends RelativeLayout {

    protected boolean show = false;

    public BasicView(Context context) {
        super(context);
        init(context);
    }

    public BasicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BasicView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public boolean isShow() {
        return show;
    }

    public void show() {

        if (!this.show) {
            onShow();
            setVisibility(VISIBLE);
            this.show = true;
        }

    }

    public void close() {

        if (this.show) {
            onClose();
            setVisibility(GONE);
            this.show = false;
        }

    }

    protected abstract void init(Context context);

    protected void onShow() {
    }

    protected void onClose() {
    }
    

}
