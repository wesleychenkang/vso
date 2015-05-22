package com.vsoyou.sdk.vscenter.view.person;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.entiy.Record;
import com.vsoyou.sdk.vscenter.entiy.RecordListResult;
import com.vsoyou.sdk.vscenter.entiy.parser.RecordListParse;
import com.vsoyou.sdk.vscenter.entiy.requestparam.RecordRequestParam;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.widget.ExpandPullListView;
import com.vsoyou.sdk.vscenter.view.widget.ExpandPullListView.IXListViewListener;

public class RecordLayoutView extends BaseLayout implements IXListViewListener{
	private Context context;
	private ArrayList<Record>  list_record = new ArrayList<Record>();
	private ExpandPullListView list_view;
	private MyExpandAdapter adapter;
	public RecordLayoutView(Context context, ParamChain env) {
		super(context, env);
		super.initUI(context);
		this.context = context;
	}

	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onInitUI(Context ctx) {
		setTitleText("充值记录");
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
	    list_view = new ExpandPullListView(ctx);
	    list_view.setXListViewListener(this);
		sub.addView(list_view);
		list_view.setChildIndicator(null);
		list_view.setGroupIndicator(null);
		list_view.setPullLoadEnable(true);
		Drawable b = BitmapCache.getDrawable(ctx, "line.png");
		list_view.setDivider(b);
		list_view.setDividerHeight(MetricUtil.getDip(getContext(), 1));
		adapter = new MyExpandAdapter();
		list_view.setAdapter(adapter);
		requestQuetionsList(ctx, 0);
	}

	
	class MyExpandAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			FrameLayout frame = new FrameLayout(getContext());
			frame.setBackgroundDrawable(BitmapCache.getDrawable(getContext(), "reple.png"));
			LinearLayout ly = new LinearLayout(getContext());
			ly.setOrientation(VERTICAL);
			ly.setPadding(MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 10));
			frame.addView(ly, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			TextView txt = new TextView(getContext());
			
			txt.setText("商品名称:"+list_record.get(groupPosition).productName.split("\\.")[1]);
			txt.setPadding(0, MetricUtil.getDip(getContext(), 10), 0, 0);
			txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			txt.setTextColor(Color.parseColor("#545556"));
			ly.addView(txt);
			
			TextView txt_content = new TextView(getContext());
			txt_content.setText("支付方式 : "+list_record.get(groupPosition).theName);
			txt_content.setPadding(0, MetricUtil.getDip(getContext(), 10), 0, 0);
			txt_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			txt_content.setTextColor(Color.parseColor("#545556"));
			ly.addView(txt_content);
			
			TextView txt_orde = new TextView(getContext());
			txt_orde.setText("订单号:"+list_record.get(groupPosition).orderNo);
			txt_orde.setPadding(0, MetricUtil.getDip(getContext(), 10), 0, 0);
			txt_orde.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			txt_orde.setTextColor(Color.parseColor("#545556"));
			ly.addView(txt_orde);

			return frame;
			
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return list_record.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			 FrameLayout frame = new FrameLayout(getContext());
				frame.setBackgroundColor(Color.WHITE);
				LinearLayout ly = new LinearLayout(getContext());
				ly.setOrientation(VERTICAL);
				frame.addView(ly, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				TextView txt = new TextView(getContext());
				txt.setText(list_record.get(groupPosition).appName);
				txt.setPadding(MetricUtil.getDip(getContext(), 5), MetricUtil.getDip(getContext(), 5), MetricUtil.getDip(getContext(), 5), MetricUtil.getDip(getContext(), 5));
				txt.setTextColor(Color.parseColor("#545556"));
				txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
				ly.addView(txt);

				TextView txt_time = new TextView(getContext());
				txt_time.setText(list_record.get(groupPosition).addTime);
				txt_time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
				txt_time.setTextColor(Color.parseColor("#545556"));
				txt_time.setPadding(MetricUtil.getDip(getContext(), 5), MetricUtil.getDip(getContext(), 2), MetricUtil.getDip(getContext(), 5), MetricUtil.getDip(getContext(), 2));
				ly.addView(txt_time);

				TextView txt_price = new TextView(getContext());
				int price = list_record.get(groupPosition).price;
				txt_price.setText(""+price/100+"元");
				txt_price.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
				txt_price.setTextColor(Color.parseColor("#545556"));
				FrameLayout.LayoutParams lp_image = new FrameLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				lp_image.gravity = Gravity.TOP|Gravity.RIGHT|Gravity.CENTER;
				lp_image.rightMargin = MetricUtil.getDip(getContext(), 10);
				frame.addView(txt_price, lp_image);

				return frame;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

	}
	/**
	 * 
	 * @param ctx
	 * @param status
	 *            当查询全部时，请传入数字“3”，待解决为0，已解决为1
	 * @param page
	 *            页码
	 */
	private void requestQuetionsList(Context ctx, int page) {
		showDialog();
		HttpRequest<RecordListResult> request = new HttpRequest<RecordListResult>(
				ctx, null, new RecordListParse(), new RequestCodeHttpCallback());
		RecordRequestParam param = new RecordRequestParam(ctx, page);
		LocalStorage storage = LocalStorage.getInstance(ctx);
		String url = DESCoder.decryptoPriAndPub(ctx,
				storage.getString(Constants.USERRECHARGELIST_URL, ""));
		request.execute(url, param.toJson());
	}

	class RequestCodeHttpCallback implements HttpCallback<RecordListResult> {

		@Override
		public void onSuccess(RecordListResult object) {
			if (object.success) {
				closeDialog();
				list_record =object.lists;
				adapter.notifyDataSetChanged();
				onLoad();
				Toast.makeText(context, "获取充值列表成功", Toast.LENGTH_SHORT).show();
			} else {
				closeDialog();
				Toast.makeText(context, "获取充值列表失败", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub
			closeDialog();
			Toast.makeText(context, "获取充值列表失败", Toast.LENGTH_SHORT).show();
		}

	}
	@Override
	public void onRefresh() {
     //下拉刷新的工作
		requestQuetionsList(context, 0);
	}

	@Override
	public void onLoadMore() {
		//上拉加载更多
		// TODO Auto-generated method stub
		requestQuetionsList(context, 0);
	}
	
	private void onLoad(){
		
		list_view.stopRefresh();
		list_view.stopLoadMore();
		list_view.setRefreshTime("刚刚");
	}
	
	
}
