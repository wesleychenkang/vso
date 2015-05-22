package com.vsoyou.sdk.vscenter.service;

import android.content.Context;
import android.content.Intent;

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.ParamChain.KeyGlobal;
import com.vsoyou.sdk.vscenter.activity.LayoutType;
import com.vsoyou.sdk.vscenter.activity.PersonCenterActivity;

public class FloatCenterService {
	 private  ParamChain ev;
	 private  static FloatCenterService personManager;
	 public static FloatCenterService getInstance(){
		 if(personManager==null){
			 personManager = new FloatCenterService();
		 }
		 return personManager;
		 
	 }
	 private  FloatCenterService(){
		 ev = PersonCenterActivity.GET_GLOBAL_PARAM_CHAIN();
	 }
	
    private  boolean  startPersonCenter(Context ctx,ParamChain chain,LayoutType type){
    	Intent intent = new Intent("com.vsoyou.sdk.activity.personcenter");
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.putExtra(KeyGlobal.KEY_UINAME, type.key());
    	chain.add(KeyGlobal.LAYOUT_TYPE, type);
    	ctx.getApplicationContext().startActivity(intent);
    	FloatWindowService.hideFloatView(ctx);
    	return true;
    }
    /**
	 * 开启个人中心
	 * @param ctx
	 * @param chain
	 * @return
	 */
    public  void startPersonCenter(Context ctx){
    	ParamChain env_person =ev.grow(KeyGlobal.class.getName());
    	ev.add(LayoutType.PERSON_CENTER.key(), env_person);
        startPersonCenter(ctx,ev,LayoutType.PERSON_CENTER);
    }
    /**
	 * 开启论坛中心
	 * @param ctx
	 * @param chain
	 * @return
	 */
    public void startForumCenter(Context ctx){
    	ParamChain env_forum =ev.grow(KeyGlobal.class.getName());
    	ev.add(LayoutType.FORUM_CENTER.key(), env_forum);
    	startPersonCenter(ctx,ev,LayoutType.FORUM_CENTER);
    }
    
    public void startQuetionCenter(Context ctx){
    	ParamChain env_forum =ev.grow(KeyGlobal.class.getName());
    	ev.add(LayoutType.QUETION_CENTER.key(), env_forum);
    	startPersonCenter(ctx,ev,LayoutType.QUETION_CENTER);
    	
    }
}
