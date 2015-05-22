package com.vsoyou.sdk.vscenter.view.widget;

import java.util.ArrayList;

import com.vsoyou.sdk.vscenter.entiy.Quetion;
import com.vsoyou.sdk.vscenter.entiy.QuetionsResult;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ExpandAdapter extends BaseExpandableListAdapter {
	private ArrayList<Quetion> list;
	private Context ctx;
	public ExpandAdapter(Context ctx, ArrayList<Quetion> list) {
		this.ctx = ctx;
		this.list = list;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		FrameLayout frame = new FrameLayout(ctx);
		frame.setBackgroundColor(Color.WHITE);
		LinearLayout ly = new LinearLayout(ctx);
		ly.setOrientation(LinearLayout.VERTICAL);
		frame.addView(ly, new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		TextView txt = new TextView(ctx);
		txt.setText(list.get(groupPosition).questionContent);
		txt.setPadding(MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 5));
		txt.setTextColor(Color.parseColor("#545556"));
		txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		ly.addView(txt);

		TextView txt_time = new TextView(ctx);
		txt_time.setText("提问时间" + list.get(groupPosition).addTime);
		txt_time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
		txt_time.setTextColor(Color.parseColor("#545556"));
		txt_time.setPadding(MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 2), MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 2));
		ly.addView(txt_time);

		ImageView image = new ImageView(ctx);
		FrameLayout.LayoutParams lp_image = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_image.gravity = Gravity.RIGHT | Gravity.CENTER;
		lp_image.rightMargin = MetricUtil.getDip(ctx, 10);
		if (isExpanded) {
			image.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
					"top_icon.png"));

		} else {

			image.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
					"bottom_icon.png"));
		}
		frame.addView(image, lp_image);

		return frame;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		FrameLayout frame = new FrameLayout(ctx);
		frame.setBackgroundDrawable(BitmapCache.getDrawable(ctx, "reple.png"));
		LinearLayout ly = new LinearLayout(ctx);
		ly.setOrientation(LinearLayout.VERTICAL);
		ly.setPadding(MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 10));
		frame.addView(ly, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		TextView txt = new TextView(ctx);
		txt.setText("回复详情:");
		txt.setPadding(0, MetricUtil.getDip(ctx, 10), 0, 0);
		txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		txt.setTextColor(Color.parseColor("#545556"));
		ly.addView(txt);

		TextView txt_content = new TextView(ctx);
		txt_content.setText(list.get(groupPosition).reply);
		txt_content.setPadding(0, MetricUtil.getDip(ctx, 10), 0, 0);
		txt_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		txt_content.setTextColor(Color.parseColor("#545556"));
		ly.addView(txt_content);

		TextView status = new TextView(ctx);
		int statu = list.get(groupPosition).status;
		String str;
		if (statu == 0) {
			str = "回复状况: <font color='red'>未回复</font>";
		} else {
			str = "回复状况: <font color='green'>已回复</font>";

		}
		status.setText(Html.fromHtml(str), TextView.BufferType.SPANNABLE);
		status.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		status.setTextColor(Color.parseColor("#545556"));
		status.setPadding(0, MetricUtil.getDip(ctx, 15),
				MetricUtil.getDip(ctx, 5), 0);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.RIGHT | Gravity.TOP;
		frame.addView(status, lp);
		return frame;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
