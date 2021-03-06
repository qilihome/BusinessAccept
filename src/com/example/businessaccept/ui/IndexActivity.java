package com.example.businessaccept.ui;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.ui.vo.BusinessInfoVo;
import com.example.businessaccept.util.AdminHepler;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity
{
	private TextView welcome;
	private TextView queryBusinessTextView;
	private Button newBusiness;
	private Button queryBusiness;
	private IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();
	private ListView lv ;
	private PendingListViewAdapter adapter = null;
	List<BusinessInfoVo> list = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
		welcome = (TextView) findViewById(R.id.textview_index_welcome);
		queryBusinessTextView = (TextView) findViewById(
				R.id.textview_index_bCount);
		lv=(ListView)findViewById(R.id.list_index_businesses);
		welcome.setText("欢迎您," + AdminHepler.getAdminName(IndexActivity.this));
		newBusiness = (Button) findViewById(R.id.button_index_new_business);
		queryBusiness = (Button)findViewById(R.id.button_index_query_business);
		newBusiness.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(IndexActivity.this,
						NewBusinessActivity.class);
				startActivity(_Intent);
			}
		});
		queryBusiness.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(IndexActivity.this,
						QueryBusinessActivity.class);
				startActivity(_Intent);
			}
		});

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
							AdminHepler.getAdminId(IndexActivity.this));
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

	private void showTip(String msg)
	{
		Toast.makeText(IndexActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	public void addWegit()
	{
		if (!list.isEmpty())
		{
			queryBusinessTextView.setText(
					"当前待处理业务" + list.size() + "条");
			adapter = new PendingListViewAdapter(IndexActivity.this, list);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					// TODO Auto-generated method stub
					Intent _Intent = new Intent(IndexActivity.this, BusinessMatterActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("from", 0);
					bundle.putInt("position", position);
					bundle.putInt("businessInfoId", list.get(position).getBusinessID());
					bundle.putSerializable("list", (ArrayList<BusinessInfoVo>)list);
					_Intent.putExtras(bundle);
					startActivityForResult(_Intent, 1);
				}
			});
		}
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(IndexActivity indexActivity)
		{
			mActivity = new WeakReference<Activity>(indexActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((IndexActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				IndexActivity _IndexActivity = (IndexActivity) mActivity.get();
				_IndexActivity.addWegit();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
}
