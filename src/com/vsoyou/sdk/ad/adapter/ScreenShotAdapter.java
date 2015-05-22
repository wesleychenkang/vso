package com.vsoyou.sdk.ad.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.ad.cache.ImageLoader;
import com.vsoyou.sdk.ad.entity.UrlImage;
import com.vsoyou.sdk.compents.GalleryImageItemView;
import com.vsoyou.sdk.resources.ResourceLoader;

/**
 * 游戏屏幕截图adapter
 * @author lmy
 * 2013-6-28
 */
public class ScreenShotAdapter extends BaseAdapter {
	
	public static final String TAG = "GameScreenShotAdapter";
	
	private Context context;
	private ArrayList<UrlImage> imageList;
	private int SCREEN_W;
	private ImageLoader imageLoader;
	
	public ScreenShotAdapter(Context context,ArrayList<UrlImage> imageList,int screenW,ImageLoader imageLoader) {
		this.context = context;
		this.imageList = imageList;
		this.SCREEN_W = screenW;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return  Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = new GalleryImageItemView(context);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = ((GalleryImageItemView)convertView).imageView;
			int imgW = SCREEN_W * 3 / 5;
			viewHolder.mImageView.setLayoutParams(new LinearLayout.LayoutParams(imgW,
					imgW * 3 / 2 ));
			viewHolder.mImageView.setScaleType(ScaleType.FIT_CENTER);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImageView.setImageDrawable(ResourceLoader.getBitmapDrawable("data_7.pvd"));
		imageLoader.DisplayImage(viewHolder.mImageView, false, imageList.get(position%imageList.size()).url);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView mImageView;
	}

}
