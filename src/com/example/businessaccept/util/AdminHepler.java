package com.example.businessaccept.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 账号
 * @author liqi
 * phone 18771970972
 * qq 197399622
 */
public abstract class AdminHepler
{
	/**
	 * 获取用户ID
	 * @param context
	 * @return
	 */
	public static int getAdminId(Context context){
		int user_id;
		SharedPreferences sharedPreferences =
		context.getSharedPreferences("user", Context.MODE_PRIVATE);
		user_id = sharedPreferences.getInt("id", -1);
		return user_id;
	}
	
	/**
	 * 用户名称
	 * @param context
	 * @return
	 */
	public static String getAdminName(Context context){
		String user_name= null ;
		SharedPreferences sharedPreferences =
		context.getSharedPreferences("user", Context.MODE_PRIVATE);
		user_name = sharedPreferences.getString("name", "");
		return user_name;
	}
	/**
	 * 用户部门
	 * @param context
	 * @return
	 */
	public static int getAdminDeptId(Context context){
		int user_deptId;
		SharedPreferences sharedPreferences =
		context.getSharedPreferences("user", Context.MODE_PRIVATE);
		user_deptId = sharedPreferences.getInt("deptId", -1);
		return user_deptId;
	}
}
