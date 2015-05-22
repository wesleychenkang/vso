package com.vsoyou.sdk.vscenter.view.person;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.entiy.Quetion;
import com.vsoyou.sdk.vscenter.entiy.QuetionsResult;
import com.vsoyou.sdk.vscenter.entiy.parser.QuetionListParse;
import com.vsoyou.sdk.vscenter.entiy.requestparam.QuetionListRequestParam;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost.KeyILayoutHost;
import com.vsoyou.sdk.vscenter.view.widget.ExpandPullListView;
import com.vsoyou.sdk.vscenter.view.widget.ExpandPullListView.IXListViewListener;

public class QuetionLayoutView extends BaseLayout implements IXListViewListener {
	private TextView txt_all;
	private TextView txt_solved;
	private TextView txt_unsolved;
	private TextView txt_ask;
	private View all_line;
	private View unsolved_line;
	private View solved_line;
	private Context context;
	private ArrayList<Quetion> list = new ArrayList<Quetion>();
	private MyExpandAdapter dapter;
	private ExpandPullListView listView;
	private QuetionsResult result;
	private int nowState; // 当前需要刷新的数据类型
	private static final int ALL = 3;
	private final static int SOLVED = 1;
	private final static int UNSOLVED = 0;

	public QuetionLayoutView(Context context, ParamChain env) {
		super(context, env);
		super.initUI(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onInitUI(Context context) {
		requestQuetionsList(context, 3, 0);
		setTitleText("我的提问");
		FrameLayout sub = (FrameLayout) getView_subject(context);
		LinearLayout all = new LinearLayout(context);
		all.setOrientation(VERTICAL);
		// all.setPadding(MetricUtil.getDip(context, 5),
		// MetricUtil.getDip(context, 0),
		// MetricUtil.getDip(context, 5), 0);
		sub.addView(all);

		LinearLayout top = new LinearLayout(context);
		top.setBackgroundColor(Color.parseColor("#fcfcfc"));
		all.addView(top);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp.weight = 0.3f;
		LayoutParams lp_line = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_line.height = MetricUtil.getDip(context, 3);

		{
			LinearLayout l_all = new LinearLayout(context);
			l_all.setOrientation(VERTICAL);
			top.addView(l_all, lp);

			txt_all = new TextView(context);
			txt_all.setOnClickListener(this);
			txt_all.setGravity(Gravity.CENTER);
			int count = 0;
			;
			String str = "全部( <font color='red'>" + count + "</font> )";
			txt_all.setText(Html.fromHtml(str));
			txt_all.setTextColor(Color.parseColor("#016ADE"));
			txt_all.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			txt_all.setPadding(MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10),
					MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10));
			l_all.addView(txt_all, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			all_line = new View(context);
			all_line.setBackgroundColor(Color.rgb(36, 112, 209));
			all_line.setVisibility(VISIBLE);
			l_all.addView(all_line, lp_line);
		}

		{

			LinearLayout l_unsolved = new LinearLayout(context);
			l_unsolved.setOrientation(VERTICAL);
			top.addView(l_unsolved, lp);
			txt_unsolved = new TextView(context);
			txt_unsolved.setOnClickListener(this);
			txt_unsolved.setText("待解决(" + 0 + ")");
			txt_unsolved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			txt_unsolved.setTextColor(Color.parseColor("#4c4c4c"));
			txt_unsolved.setGravity(Gravity.CENTER);
			txt_unsolved.setPadding(MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10),
					MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10));
			l_unsolved.addView(txt_unsolved, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			unsolved_line = new View(context);
			unsolved_line.setVisibility(GONE);
			unsolved_line.setBackgroundColor(Color.rgb(36, 112, 209));

			l_unsolved.addView(unsolved_line, lp_line);
		}

		{
			LinearLayout l_solved = new LinearLayout(context);
			l_solved.setOrientation(VERTICAL);
			top.addView(l_solved, lp);

			txt_solved = new TextView(context);
			txt_solved.setText("已解决(" + 0 + ")");
			txt_solved.setTextColor(Color.parseColor("#4c4c4c"));
			txt_solved.setGravity(Gravity.CENTER);
			txt_solved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			txt_solved.setPadding(MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10),
					MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10));
			txt_solved.setOnClickListener(this);

			l_solved.addView(txt_solved, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			solved_line = new View(context);
			solved_line.setBackgroundColor(Color.rgb(36, 112, 209));
			solved_line.setVisibility(GONE);
			l_solved.addView(solved_line, lp_line);

		}
		txt_ask = new TextView(context);
		txt_ask.setGravity(Gravity.CENTER);
		txt_ask.setOnClickListener(this);
		txt_ask.setText("我要提问");
		txt_ask.setPadding(MetricUtil.getDip(context, 5),
				MetricUtil.getDip(context, 10), MetricUtil.getDip(context, 5),
				MetricUtil.getDip(context, 10));
		txt_ask.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		// txt_ask.setBackgroundDrawable(BitmapCache.getDrawable(context,
		// "get_token.png"));
		txt_ask.setTextColor(Color.parseColor("#4c4c4c"));

		// lp.leftMargin = MetricUtil.getDip(context, 5);
		LayoutParams lp_ask = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_ask.weight = 0.3f;
		// lp_ask.height = MetricUtil.getDip(context, 35);
		// lp_ask.leftMargin = MetricUtil.getDip(context, 5);
		// lp_ask.rightMargin = MetricUtil.getDip(context, 5);
		top.addView(txt_ask, lp_ask);

		View l = new View(context);
		l.setBackgroundDrawable(BitmapCache.getDrawable(context, "line.png"));
		LayoutParams lp_l = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_l.height = 1;
		lp_l.topMargin = MetricUtil.getDip(context, 5);
		all.addView(l, lp_l);

		// ExpandableListView list = new ExpandableListView(context);
		listView = new ExpandPullListView(context);
		listView.setChildIndicator(null);
		listView.setGroupIndicator(null);
		listView.setPullLoadEnable(true);
		Drawable b = BitmapCache.getDrawable(context, "line.png");
		listView.setDivider(b);
		listView.setXListViewListener(this);
		listView.setDividerHeight(MetricUtil.getDip(getContext(), 1));
		LayoutParams lp_list = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_list.topMargin = MetricUtil.getDip(context, 2);
		all.addView(listView, lp_list);
		dapter = new MyExpandAdapter();
		listView.setAdapter(dapter);
	}

	@Override
	protected void initEnv(Context context, ParamChain env) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == txt_all) {
			undateTextViewColor(txt_all);
			nowState = ALL;
			requestQuetionsList(context, nowState, 0);
		} else if (v == txt_ask) {
			entryAsdkLayout();

		} else if (v == txt_solved) {
			nowState = SOLVED;
			requestQuetionsList(context, nowState, 0);
			undateTextViewColor(txt_solved);
		} else if (v == txt_unsolved) {
			nowState = UNSOLVED;
			requestQuetionsList(context, nowState, 0);
			undateTextViewColor(txt_unsolved);
		}
	}

	private void entryAsdkLayout() {
		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				AskLayoutView.class.getName());

	}

	private void undateTextViewColor(TextView view) {

		if (view == txt_all) {

			txt_all.setTextColor(Color.parseColor("#016ADE"));
			txt_solved.setTextColor(Color.parseColor("#4c4c4c"));
			txt_unsolved.setTextColor(Color.parseColor("#4c4c4c"));
			all_line.setVisibility(VISIBLE);
			unsolved_line.setVisibility(GONE);
			solved_line.setVisibility(GONE);

		} else if (view == txt_solved) {
			txt_all.setTextColor(Color.parseColor("#4c4c4c"));
			txt_solved.setTextColor(Color.parseColor("#016ADE"));
			txt_unsolved.setTextColor(Color.parseColor("#4c4c4c"));
			all_line.setVisibility(GONE);
			unsolved_line.setVisibility(GONE);
			solved_line.setVisibility(VISIBLE);
		} else if (view == txt_unsolved) {
			txt_all.setTextColor(Color.parseColor("#4c4c4c"));
			txt_solved.setTextColor(Color.parseColor("#4c4c4c"));
			txt_unsolved.setTextColor(Color.parseColor("#016ADE"));
			all_line.setVisibility(GONE);
			unsolved_line.setVisibility(VISIBLE);
			solved_line.setVisibility(GONE);
		}
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
			FrameLayout frame = new FrameLayout(context);
			frame.setBackgroundDrawable(BitmapCache.getDrawable(context,
					"reple.png"));
			LinearLayout ly = new LinearLayout(context);
			ly.setOrientation(LinearLayout.VERTICAL);
			ly.setPadding(MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 5),
					MetricUtil.getDip(context, 10));
			frame.addView(ly, new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			TextView txt = new TextView(context);
			txt.setText("回复详情:");
			txt.setPadding(0, MetricUtil.getDip(context, 10), 0, 0);
			txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			txt.setTextColor(Color.parseColor("#545556"));
			ly.addView(txt);

			TextView txt_content = new TextView(context);
			String content = list.get(groupPosition).reply;
			if (TextUtils.isEmpty(content)) {
				content = "";
			}
			txt_content.setText(content);
			txt_content.setPadding(0, MetricUtil.getDip(context, 10), 0, 0);
			txt_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			txt_content.setTextColor(Color.parseColor("#545556"));
			ly.addView(txt_content);

			TextView status = new TextView(context);
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
			status.setPadding(0, MetricUtil.getDip(context, 15),
					MetricUtil.getDip(context, 5), 0);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.RIGHT | Gravity.TOP;
			frame.addView(status, lp);
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
			return list.size();
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
			frame.addView(ly, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TextView txt = new TextView(getContext());
			txt.setText(list.get(groupPosition).questionContent);
			txt.setPadding(MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 5));
			txt.setTextColor(Color.parseColor("#545556"));
			txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			ly.addView(txt);

			TextView txt_time = new TextView(getContext());
			txt_time.setText("提问时间" + list.get(groupPosition).addTime);
			txt_time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
			txt_time.setTextColor(Color.parseColor("#545556"));
			txt_time.setPadding(MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 2),
					MetricUtil.getDip(getContext(), 5),
					MetricUtil.getDip(getContext(), 2));
			ly.addView(txt_time);

			ImageView image = new ImageView(getContext());
			FrameLayout.LayoutParams lp_image = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp_image.gravity = Gravity.RIGHT | Gravity.CENTER;
			lp_image.rightMargin = MetricUtil.getDip(getContext(), 10);
			if (isExpanded) {
				image.setBackgroundDrawable(BitmapCache.getDrawable(
						getContext(), "top_icon.png"));

			} else {

				image.setBackgroundDrawable(BitmapCache.getDrawable(
						getContext(), "bottom_icon.png"));
			}
			frame.addView(image, lp_image);

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
	 * @param context
	 * @param status
	 *            当查询全部时，请传入数字“3”，待解决为0，已解决为1
	 * @param page
	 *            页码
	 */
	private void requestQuetionsList(Context context, int status, int page) {
		showDialog();
		HttpRequest<QuetionsResult> request = new HttpRequest<QuetionsResult>(
				context, null, new QuetionListParse(),
				new RequestCodeHttpCallback());
		QuetionListRequestParam param = new QuetionListRequestParam(context,
				status, page);
		LocalStorage storage = LocalStorage.getInstance(context);
		String url = DESCoder.decryptoPriAndPub(context,
				storage.getString(Constants.USERQUESTIONLIST_URL, ""));
		request.execute(url, param.toJson());
	}

	class RequestCodeHttpCallback implements HttpCallback<QuetionsResult> {

		@Override
		public void onSuccess(QuetionsResult object) {
			if (object.success) {
				closeDialog();
				result = object;
				list = object.list;
				dapter.notifyDataSetChanged();
				onLoad();
				updateCount();
				Toast.makeText(context, "获取问题列表成功", Toast.LENGTH_SHORT).show();
			} else {
				closeDialog();
				Toast.makeText(context, "获取问题列表失败", Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub
			closeDialog();
			Toast.makeText(context, "访问出错", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onRefresh() {
		requestQuetionsList(context, nowState, 0);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		requestQuetionsList(context, nowState, 0);
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}

	private void updateCount() {
		String all = "全部( <font color='red'>" + result.totalAll + "</font> )";
		String wait = "待解决( <font color='red'>" + result.totalWai + "</font> )";
		String yes = "已解决( <font color='red'>" + result.totalYes + "</font> )";
		txt_all.setText(Html.fromHtml(all));
		txt_solved.setText(Html.fromHtml(yes));
		txt_unsolved.setText(Html.fromHtml(wait));

	}

}
