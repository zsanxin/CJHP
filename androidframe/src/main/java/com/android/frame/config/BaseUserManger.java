package com.android.frame.config;


import com.android.frame.util.SPUtils;

public abstract class BaseUserManger {

	/**
	 * 获得登陆状态
	 */
	public static boolean isLogin() {
		SPUtils spUtils = new SPUtils("userConfig");
		return (Boolean) spUtils.get("isLogin", false);
	}

	/**
	 * 设置登陆状态
	 */
	public static void setIsLogin( boolean b) {
		SPUtils spUtils = new SPUtils("userConfig");
		spUtils.put("isLogin", b);
	}



	public static void setIsFirstOpen(boolean isFirst) {
		SPUtils spUtils = new SPUtils("userConfig");
		spUtils.put("isFirst", isFirst);
	}


	public static boolean isFirstOpen() {
		SPUtils spUtils = new SPUtils("userConfig");
		return (boolean) spUtils.get("isFirst", true);
	}


	/**
	 * 获取app设置的token
	 *
	 * @param token
	 */
	public static void setToken(String token) {
		SPUtils spUtils = new SPUtils("userConfig");
		spUtils.put("token", token);
	}


	/**
	 * 获取app设置的token
	 *
	 * @return
	 */
	public static String getToken(){
		SPUtils spUtils = new SPUtils("userConfig");
		String token = (String) spUtils.get("token", "");
		return token;
	}


}
