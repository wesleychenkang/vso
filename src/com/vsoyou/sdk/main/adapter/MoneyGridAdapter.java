package com.vsoyou.sdk.main.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vsoyou.sdk.main.activity.view.MoneyListItemView;

public class MoneyGridAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<String> monyList;
	
	public MoneyGridAdapter(Context context, ArrayList<String> monyList) {
		this.context = context;
		this.monyList = monyList;
	}

	@Override
	public int getCount() {
		return monyList.size();
	}

	@Override
	public Object getItem(int position) {
		return monyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = new MoneyListItemView(context);
		}
		TextView textView = (TextView) convertView;
		textView.setText(monyList.get(position));
		return convertView;
	}

}
