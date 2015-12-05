package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import com.example.businessaccept.R;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.ui.vo.BusinessCondition;
import com.example.businessaccept.ui.vo.BusinessInfoVo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BusinessListActivity extends Activity
{
	IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();
	private Button loadmore;
	private BusinessListAdapter adapter;

	private ListView buesinessListView;

	private static ProgressDialog dialog = null;
	private static int pageNo = 0;// 设置pageNo的初始化值为0，即默认获取的是第一页的数据
	private BusinessCondition businessCondition;
	List<BusinessInfoVo> allList = new ArrayList<BusinessInfoVo>();
	List<BusinessInfoVo> list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		businessCondition = (BusinessCondition) getIntent()
				.getSerializableExtra("businessCondition");
		setContentView(R.layout.layout_business_list);
		if (businessCondition == null)
		{
			Toast.makeText(BusinessListActivity.this, "获取条件失败，请稍后再试！", 3000)
					.show();
			return;
		}
		loadmore = (Button) findViewById(R.id.button_business_list_loadmore);
		buesinessListView = (ListView) findViewById(
				R.id.listview_business_list);
		if (dialog == null)
		{
			dialog = new ProgressDialog(BusinessListActivity.this);
			dialog.setTitle("请等待");
			dialog.setMessage("正在加载信息...");
			dialog.show();
		}
		loadData();

		loadmore.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				pageNo++;
				loadData();
			}
		});

	}

	public void loadData()
	{
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
					list = businessInfoService.query(businessCondition, pageNo);
					iHandler.sendEmptyMessage(1);
				}
				catch (ConnectTimeoutException e)
				{
					e.printStackTrace();
					Message message = new Message();
					Bundle data = new Bundle();
					data.putString("errorMsg", "连接超时，请稍后再试");
					message.setData(data);
					iHandler.sendMessage(message);
				}
				catch (SocketTimeoutException e)
				{
					e.printStackTrace();
					Message message = new Message();
					Bundle data = new Bundle();
					data.putString("errorMsg", "连接超时，请稍后再试");
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
					data.putString("errorMsg", "查询错误");
					message.setData(data);
					iHandler.sendMessage(message);
				}
			}
		});
		thread.start();
	}

	public void showData()
	{
		allList.addAll(list);
		if (pageNo <= 1)
		{
			adapter = new BusinessListAdapter(this, allList);
			buesinessListView.setAdapter(adapter);
			buesinessListView.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					Intent _Intent = new Intent(BusinessListActivity.this, BusinessMatterActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("from", 1);
					bundle.putInt("businessInfoId", allList.get(position).getBusinessID());
					_Intent.putExtras(bundle);
					startActivityForResult(_Intent, 0);
				}
			});
		}
		else
		{
			adapter.notifyDataSetInvalidated();
		}

	}

	private void showTip(String msg)
	{
		Toast.makeText(BusinessListActivity.this, msg, Toast.LENGTH_SHORT)
				.show();
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(BusinessListActivity businessListActivity)
		{
			mActivity = new WeakReference<Activity>(businessListActivity);
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
				((BusinessListActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				BusinessListActivity _MBusinessListActivity = (BusinessListActivity) mActivity
						.get();

				_MBusinessListActivity.showData();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);

}
