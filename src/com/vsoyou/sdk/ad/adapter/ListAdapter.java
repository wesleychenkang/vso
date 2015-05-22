package com.vsoyou.sdk.ad.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.ad.cache.ImageLoader;
import com.vsoyou.sdk.ad.entity.AdCallbackRequestParam;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.ad.entity.GetMoreRequestParam;
import com.vsoyou.sdk.ad.entity.Topic;
import com.vsoyou.sdk.ad.entity.parser.TopicParser;
import com.vsoyou.sdk.compents.ListItemView;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.BrowseUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;

public class ListAdapter extends BaseAdapter implements HttpCallback<Topic> {

	public static final String TAG = "SubjectDetailListAdapter";

	private static final int MSG_NOFIFY_DATA_CHANGE = 1;

	private Context context;
	private ArrayList<AppEntity> appList;
	private int adId;
	private int currentPage = 1;
	private int totalPage;
	private ArrayList<AppEntity> tempList;

	private boolean isDownloading;
	private ImageLoader imageLoader;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_NOFIFY_DATA_CHANGE:
				LogUtil.i(TAG, "MSG_NOFIFY_DATA_CHANGE");
				appList.addAll(tempList);
				ListAdapter.this.notifyDataSetChanged();
				tempList.clear();
				tempList = null;
				isDownloading = false;
				break;

			default:
				break;
			}
		};
	};

	public ListAdapter(Context context, AdEntity adEntity) {
		this.context = context;
		imageLoader = AdManger.instance.imageLoader;
		this.appList = adEntity.carrierlist;
		this.adId = adEntity.id;
		if (appList.size() > 0) {
			totalPage = appList.get(0).pageNum;
		}
	}

	@Override
	public int getCount() {
		return appList.size();
	}

	@Override
	public Object getItem(int position) {
		return appList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = new ListItemView(context);
			viewHolder = new ViewHolder();
			viewHolder.icon = ((ListItemView) convertView).iconImageView;
			viewHolder.name = ((ListItemView) convertView).appNameTextView;
			viewHolder.size = ((ListItemView) convertView).appSizeTextView;
			viewHolder.progressBar = ((ListItemView) convertView).progressBar;
			viewHolder.version = ((ListItemView) convertView).versionTextView;
			viewHolder.download = ((ListItemView) convertView).downloadButton;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (currentPage <= totalPage) {
			if (position + 1 == appList.size() && !isDownloading) {
				LogUtil.i(TAG, "currentPage <= totalPage");
				LogUtil.i(TAG,
						"position + 1 == appList.size() && !isDownloading");
				// 自动加载下一页
				currentPage++;
				isDownloading = true;
				getMore();
			}
		}
		if (position + 1 == appList.size()) {
			viewHolder.progressBar.setVisibility(View.VISIBLE);
		} else {
			viewHolder.progressBar.setVisibility(View.GONE);
		}
		if (currentPage - 1 == totalPage) {
			viewHolder.progressBar.setVisibility(View.GONE);
		}
		final AppEntity nowAppEntity = appList.get(position);
		viewHolder.icon.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_24.pvd"));
		imageLoader.DisplayImage(viewHolder.icon, false, nowAppEntity.iconUrl);
		viewHolder.name.setText(nowAppEntity.titleName);
		viewHolder.size.setText(Formatter.formatFileSize(context,
				nowAppEntity.fileSize));
//		viewHolder.time.setText(nowAppEntity.addTime);
		viewHolder.version.setText(nowAppEntity.verName);
		viewHolder.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BrowseUtil.openBrowser(context, nowAppEntity.fileUrl);
				HttpRequest<AdCallbackRequestParam> request = new HttpRequest<AdCallbackRequestParam>(context, null, null, null);
				LocalStorage storage = LocalStorage.getInstance(context);
				request.execute(DESCoder.decryptoPriAndPub(context,
						storage.getString(Constants.ADCALLBACK_URL, "")),
						new AdCallbackRequestParam(context, "click", adId,
								nowAppEntity.carrierId).toJSON());
			}
		});
		return convertView;
	}

	private void getMore() {
		LogUtil.i(TAG, "getMore.currentPage-->" + currentPage);
		HttpRequest<Topic> request = new HttpRequest<Topic>(context, null,
				new TopicParser(), new GetMoreListener(this));
		LocalStorage storage = LocalStorage.getInstance(context);
		request.execute(DESCoder.decryptoPriAndPub(context,storage.getString(Constants.ADTOPIC_URL, "")),
				new GetMoreRequestParam(context, currentPage, adId).toJSON());
	}

	private class GetMoreListener implements HttpCallback<Topic> {

		private HttpCallback<Topic> callback;

		public GetMoreListener(HttpCallback<Topic> callback) {
			this.callback = callback;
		}

		@Override
		public void onSuccess(Topic object) {
			callback.onSuccess(object);
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub

		}

	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView size;
		TextView time;
		TextView version;
		ImageView star;
		Button download;
		ProgressBar progressBar;
		ImageView coin;
	}

	@Override
	public void onSuccess(Topic object) {
		tempList = object.appList;
		mHandler.sendEmptyMessage(MSG_NOFIFY_DATA_CHANGE);
	}

	@Override
	public void onFailure(int errorCode, String errorMessage) {
		// TODO Auto-generated method stub

	}

}
