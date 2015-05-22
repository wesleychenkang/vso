package com.vsoyou.sdk.vscenter.view.person;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.entiy.PersonCetnerResult;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
@SuppressLint("NewApi")
public class CustomerLayout extends BaseLayout{
    private PersonCetnerResult result;
	public CustomerLayout(Context context, ParamChain env) {
		super(context, env);
		super.initUI(context);
	}
	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		// TODO Auto-generated method stub
		result = env.get(PersonCetnerResult.KEY, PersonCetnerResult.class);
		System.out.println("result.serviceInfo"+result.serviceInfo);
	}
	@Override
	protected void onInitUI(Context ctx) {
		setTitleText("客服中心");
      FrameLayout sub = (FrameLayout)getView_subject(ctx);
      sub.setPadding(MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 30), MetricUtil.getDip(ctx, 10), 0);
      LinearLayout all = new LinearLayout(ctx);
      all.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx, "person_black.9.png"));
      all.setOrientation(VERTICAL);
      LayoutParams lp_all = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
      lp_all.gravity = Gravity.TOP;
      sub.addView(all,lp_all);
      
      
      all.addView(createLayoutView("客服热线","0512-666457755","phone_customer.png",ctx));
      
      View line = new View(ctx);
      line.setBackgroundColor(Color.rgb(235, 235, 235));
      LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
      lp.height = 3;
      all.addView(line,lp);
      
      all.addView(createLayoutView("投诉热线","0512-666457755","qq_customer.png",ctx));
      
    		  
     // createView(String name,String path);
      
	}
    private LinearLayout createLayoutView(String name,String number,String path,Context ctx){
    	LinearLayout liner = new LinearLayout(ctx);
    	liner.setOrientation(HORIZONTAL);
    	liner.setPadding(0, MetricUtil.getDip(ctx, 15), 0, MetricUtil.getDip(ctx, 15));
    	
    	LinearLayout left = new LinearLayout(ctx);
    	left.setGravity(Gravity.CENTER|Gravity.LEFT);
    	LayoutParams lp_left = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    	lp_left.weight = 0.5f;
    	liner.addView(left,lp_left);
    	
    	
    	TextView txt_name = new TextView(ctx);
    	txt_name.setText(name);
    	txt_name.setPadding(MetricUtil.getDip(ctx, 10), 0, 0, 0);
    	left.addView(txt_name);
    	
    	
    	LinearLayout right = new LinearLayout(ctx);
    	right.setGravity(Gravity.RIGHT|Gravity.CENTER);
    	LayoutParams lp_right = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    	lp_right.weight = 0.5f;
    	liner.addView(right,lp_right);
    	
    	TextView txt_number = new TextView(ctx);
    	txt_number.setText(number);
    	//txt_number.setPadding(0, 0, MetricUtil.getDip(ctx, 10), 0);
    	right.addView(txt_number);
    	
    	ImageView image = new ImageView(ctx);
    	image.setBackgroundDrawable(BitmapCache.getDrawable(ctx, path));
    	LayoutParams lp_image = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	lp_image.rightMargin = MetricUtil.getDip(ctx, 10);
    	lp_image.leftMargin = MetricUtil.getDip(ctx, 10);
    	right.addView(image,lp_image);
    	
    	return liner;
    }
	

	
}
