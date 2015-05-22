package com.vsoyou.sdk.vscenter.view.person;
import java.lang.reflect.Constructor;
import android.content.Context;

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.activity.LayoutType;


public class LayoutFactory { 
	
public static final int PERSON = 0;
	
	public static final int FORUM = 1;
	
	public static final int PHONE = 2;
	
	public static final int EMAIL = 3;
	
	public static final int PASSWORD = 4;
	
	public static final int QUESTION = 5;
	
	public static final int CUSTOMER = 6;
	
	public static final int RECODER = 7;
	/**
	 * 产生指定的Layout
	 * @param context
	 * @return
	 */
	public static ILayoutView createLayoutView(Context context,LayoutType type,ParamChain env){
		ILayoutView view = null;
		if(type.equals(LayoutType.PERSON_CENTER)){
			view = new PersonLayoutView(context, env);
		}else if(type.equals(LayoutType.FORUM_CENTER)){
			view = new ForumLayout(context, env);
		}else if(type.equals(LayoutType.QUETION_CENTER)){
			view = new QuetionLayoutView(context, env);
		}
		return view;
	}
	
	/**
	 * 构造指定类名
	 * 
	 * @param ctx
	 * @param className
	 *            类名
	 * @param classLoader
	 *            加载器，若为null表示使用虚拟机的默认类加载器
	 * @param rootEnv
	 * @return
	 */
	public  static ILayoutView createLayout(Context ctx, String className,
			ClassLoader classLoader, ParamChain rootEnv) {
		try {
			Class<?> lFactoryClass = Class
					.forName(className, true, classLoader);
			if (ILayoutView.class.isAssignableFrom(lFactoryClass)) {
				Constructor<?> c = lFactoryClass.getConstructor(Context.class,
						ParamChain.class);
				return (ILayoutView) c.newInstance(ctx, rootEnv);
				// return (ILayoutView) lFactoryClass.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
  
}
