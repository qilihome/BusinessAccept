package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.ui.vo.BusinessInfoVo;
import com.example.businessaccept.util.AdminHepler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class TestActivity extends Activity
{
	private ListView lv;
	private PendingListViewAdapter adapter = null;
	List<BusinessInfoVo> list = null;
	private IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_test);
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
					list = businessInfoService.queryToMyBusinessInfo(
							AdminHepler.getAdminId(TestActivity.this));
					iHandler.sendEmptyMessage(1);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	private void fill(){
		lv = (ListView)findViewById(R.id.list_index_businesses1);
		adapter = new PendingListViewAdapter(TestActivity.this, list);
		lv.setAdapter(adapter);
	}
	
	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(TestActivity TestActivity)
		{
			mActivity = new WeakReference<Activity>(TestActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				break;
			case 1:
				TestActivity _TestActivity = (TestActivity) mActivity.get();
				_TestActivity.fill();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
}
