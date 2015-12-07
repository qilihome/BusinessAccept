package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.Customer;
import com.example.businessaccept.service.ICustomerService;
import com.example.businessaccept.service.impl.CustomerServiceImpl;
import com.example.businessaccept.ui.AutoLoadListener.AutoLoadCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class QueryCustomerActivity extends Activity implements OnScrollListener
{
	private Button queryButton;
	private Button callbackButton;
	private Spinner fieldSpinner;
	private int fieldPosition = 0;
	private EditText valueEditText;

	private ICustomerService customerService = new CustomerServiceImpl();
	private List<Customer> list = null;
	private ArrayAdapter<Customer> adapter;

	private ListView customerListView;

	private Intent _Intent;
	private int pageNo = 0;// 设置pageNo的初始化值为0，即默认获取的是第一页的数据
	private int visibleLastIndex = 0; // 最后的可视项索引

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_query_customer);
		_Intent = this.getIntent();
		queryButton = (Button) findViewById(R.id.button_query_customer_query);
		callbackButton = (Button) findViewById(
				R.id.button_query_customer_callback);
		fieldSpinner = (Spinner) findViewById(
				R.id.spinner_query_customer_field);
		valueEditText = (EditText) findViewById(
				R.id.edittext_query_customer_value);
		customerListView = (ListView) findViewById(
				R.id.listview_query_customer_customer);
		queryButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (null != list){
					list.clear();
				}
				getData(0);

			}
		});
		callbackButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// Intent _Intent = new Intent(QueryCustomerActivity.this,
				// NewBusinessActivity.class);
				// startActivity(_Intent);
				setResult(0, _Intent);
				// 调用这个当你的活动,应该关闭。ActivityResult传回给谁了你通过onActivityResult()。
				finish();
			}
		});
		fieldSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				fieldPosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private void getData(final int pageNo){
		final Customer customer = new Customer();
		String vStr = valueEditText.getText().toString();
		if (null != vStr || !"".equals(vStr))
			if (1 == fieldPosition)
			{
				customer.setCustomerNo(vStr);
			}
			else if (2 == fieldPosition)
			{
				customer.setCustomerName(vStr);
			}
			else if (3 == fieldPosition)
			{
				customer.setAddress(vStr);
			}
			else if (2 == fieldPosition)
			{
				customer.setPhone(vStr);
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
					list = customerService.query(customer, pageNo);
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
		Toast.makeText(QueryCustomerActivity.this, msg, Toast.LENGTH_SHORT)
				.show();
	}

	private void showDialog()
	{
		if (list.isEmpty())
		{
			showTip("无任何数据!");
			return;
		}

		adapter = new ArrayAdapter<Customer>(this,
				android.R.layout.simple_list_item_single_choice, list);
		// AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
		// customerListView.setOnScrollListener(autoLoadListener);

		customerListView.setAdapter(adapter);
		customerListView.setOnScrollListener(this);
		customerListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				String customerNo = list.get(position).getCustomerNo();
				_Intent.putExtra("customerNo", customerNo);
				setResult(0, _Intent);
				// 调用这个当你的活动,应该关闭。ActivityResult传回给谁了你通过onActivityResult()。
				finish();
			}
		});
	}

	AutoLoadCallBack callBack = new AutoLoadCallBack()
	{

		public void execute(String url)
		{
			Toast.makeText(QueryCustomerActivity.this, url, 500).show();
		}

	};

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> arg0, // The AdapterView where
														// the
														// click happened
				View arg1, // The view within the AdapterView that was clicked
				int arg2, // The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		)
		{
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			setTitle((String) item.get("ItemText"));
		}

	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(QueryCustomerActivity queryCustomerActivity)
		{
			mActivity = new WeakReference<Activity>(queryCustomerActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((QueryCustomerActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				QueryCustomerActivity _QueryCustomerActivity = (QueryCustomerActivity) mActivity
						.get();
				_QueryCustomerActivity.showDialog();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// TODO Auto-generated method stub
		int itemsLastIndex = adapter.getCount(); // 数据集最后一项的索引
		int lastIndex = itemsLastIndex; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex)
		{
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			getData(pageNo+1);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{

		this.visibleLastIndex = totalItemCount;
	}
}
