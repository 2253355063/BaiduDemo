package com.yunkakeji.baidudemo.net.basic;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络判断
 *
 * Author : liuYang
 * Date : 13:46 2018/5/11
 */

public class NetCheck {

	/**
    * 判断是否有可用的网络
    * @param activity
    * @return
    */
	public static boolean isNetworkAvailable(Activity activity) {
		if (null == activity) {
			return false;
		}
		Context context = activity.getApplicationContext();
		return isNetworkAvailable(context);
	}

	/**
    * 判断是否有可用的网络
    * @param context
    * @return
    */
	public static boolean isNetworkAvailable(Context context) {
		if(null == context) {
			return false;
		}
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(wifi.isConnected() || mobile.isConnected()) {
			return true;
		}
		else {
			return false;
		}
	}

}
