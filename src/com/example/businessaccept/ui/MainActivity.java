package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.Admin;
import com.example.businessaccept.service.IUserService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.service.impl.UserServiceImpl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
{


	private static final int FLAG_LOGIN_SUCCESS = 1;
	private static final String MSG_LOGIN_SUCCESS = "登陆成功";
	private static final String MSG_LOGIN_ERROR = "登陆错误";
	public static final String MSG_LOGIN_FAIL = "登陆名或密码错误";
	public static final String MSG__LOGIN_RESPONSE_ERROR = "登陆响应错误";
	private static final String MSG_REQUEST_ERROR = "服务器请求超时";
	private static final String MSG_RESPONSE_ERROR = "服务器响应超时";
	private EditText loginNameEditText;
	private EditText loginPasswordEditText;
	private Button loginButton;
	private Button resetButton;
	private static ProgressDialog dialog;
	
	private IUserService userService = new UserServiceImpl();
	private Admin admin = null;

	/**
	 * 初始化控件
	 */
	private void init()
	{
		this.loginNameEditText = (EditText) findViewById(
				R.id.edittext_login_name);
		this.loginPasswordEditText = (EditText) findViewById(
				R.id.edittext_login_password);
		this.loginButton = (Button) findViewById(R.id.button_login);
		this.resetButton = (Button) findViewById(R.id.button_reset);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始
		this.init();
		// 点击登陆
		this.loginButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.i("douzi", "点击登陆按钮");
				final String loginName = loginNameEditText.getText().toString();
				final String loginPassword = loginPasswordEditText.getText()
						.toString();
				Toast.makeText(MainActivity.this, "登陆名： " + loginName,
						Toast.LENGTH_SHORT).show();
				Toast.makeText(MainActivity.this, "密码： " + loginPassword,
						Toast.LENGTH_SHORT).show();
						/**
						 * 检验
						 */

				/**
				 * login...
				 */

				if (dialog == null)
				{
					dialog = new ProgressDialog(MainActivity.this);
					dialog.setTitle("请等待");
					dialog.setMessage("登陆中...");
					dialog.setCancelable(false);
					dialog.show();
				}
				/**
				 * 副线程
				 */
				Thread thread = new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						try
						{
							admin = userService.userLogin(loginName, loginPassword);
							
							iHandler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
						}
						catch(ConnectTimeoutException e){
							e.printStackTrace();
							Message message = new Message();
							Bundle data = new Bundle();
							data.putString("errorMsg", MSG_REQUEST_ERROR);
							message.setData(data);
							iHandler.sendMessage(message);
						}
						catch (SocketTimeoutException e) {
							e.printStackTrace();
							Message message = new Message();
							Bundle data = new Bundle();
							data.putString("errorMsg", MSG_RESPONSE_ERROR);
							message.setData(data);
							iHandler.sendMessage(message);
						}
						catch (ServiceRulesException e)
						{
							e.printStackTrace();
							Message message = new Message();
							Bundle data = new Bundle();
							data.putString("errorMsg", e.getMessage());
							message.setData(data);
							iHandler.sendMessage(message);
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							Message message = new Message();
							Bundle data = new Bundle();
							data.putString("errorMsg", MSG_LOGIN_ERROR);
							message.setData(data);
							iHandler.sendMessage(message);
						}
					}
				});
				thread.start();
			}
		});
		// 重置
		this.resetButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				loginNameEditText.setText("");
				loginPasswordEditText.setText("");
			}
		});
	}

	private void showTip(String msg)
	{
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(MainActivity MainActivity)
		{
			mActivity = new WeakReference<Activity>(MainActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			if (dialog != null)
			{
				dialog.dismiss();
			}
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((MainActivity) mActivity.get()).showTip(msgStr);
				break;
			case FLAG_LOGIN_SUCCESS:
				MainActivity _MainActivity = (MainActivity) mActivity.get();
				//_MainActivity.showTip(MSG_LOGIN_SUCCESS);
				//SharedPreferences 保存数据的实现代码
				SharedPreferences sharedPreferences =
				_MainActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				//如果不能找到Editor接口。尝试使用 SharedPreferences.Editor
				editor.putInt("id", _MainActivity.admin.getId());
				editor.putString("name", _MainActivity.admin.getName());
				editor.putInt("deptId", _MainActivity.admin.getDeptId());
				//我将用户信息保存到其中，你也可以保存登录状态
				editor.commit();
				Intent _Intent = new Intent(_MainActivity, IndexActivity.class);
				//Intent _Intent = new Intent(_MainActivity, TestActivity.class);
				
				_MainActivity.startActivity(_Intent);
				_MainActivity.finish();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
	
	public static void main(String[] args)
	{
		
	}
}
