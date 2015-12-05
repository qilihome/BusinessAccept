package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import com.example.businessaccept.R;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.ui.vo.BusinessInfoVo;
import com.example.businessaccept.util.DateHepler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BusinessMatterActivity extends Activity
{
	IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();
	private int businessInfoId;
	private int from;
	private BusinessInfoVo businessInfoVo ;
	
	private TextView businessNoTextView;
	
	private TextView businessTypeNameTextView;
	
	private TextView customerNoTextView;
	
	private TextView customerNameTextView;
	private TextView addressTextView;
	private TextView phoneTextView;
	private TextView operatorNameTextView;
	private TextView createTimeTextView;
	private TextView businessContentTextView;
	private Button indexButton;
	private Button callbackButton;
	private Button followRecordButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		businessInfoId = getIntent().getExtras().getInt("businessInfoId");
		from = getIntent().getExtras().getInt("from");
		setContentView(R.layout.layout_business_matter);
		
		businessNoTextView = (TextView)findViewById(R.id.textview_business_matter_businessNo);
		
		businessTypeNameTextView= (TextView)findViewById(R.id.textview_business_matter_businessTypeName);;
		
		customerNoTextView= (TextView)findViewById(R.id.textview_business_matter_customerNo);;
		
		customerNameTextView= (TextView)findViewById(R.id.textview_business_matter_customerName);;
		addressTextView= (TextView)findViewById(R.id.textview_business_matter_address);;
		phoneTextView= (TextView)findViewById(R.id.textview_business_matter_phone);;
		operatorNameTextView= (TextView)findViewById(R.id.textview_business_matter_operatorName);;
		createTimeTextView= (TextView)findViewById(R.id.textview_business_matter_createTime);;
		businessContentTextView= (TextView)findViewById(R.id.textview_business_matter_businessContent);;
		callbackButton = (Button)findViewById(R.id.button_business_matter_callback);
		indexButton = (Button)findViewById(R.id.button_business_matter_index);
		followRecordButton = (Button)findViewById(R.id.button_business_matter_follow_record);
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
					businessInfoVo= businessInfoService.queryById(businessInfoId);
					
					iHandler.sendEmptyMessage(1);
				}
				catch(ConnectTimeoutException e){
					e.printStackTrace();
					Message message = new Message();
					Bundle data = new Bundle();
					data.putString("errorMsg", "连接超时，请稍后再试！");
					message.setData(data);
					iHandler.sendMessage(message);
				}
				catch (SocketTimeoutException e) {
					e.printStackTrace();
					Message message = new Message();
					Bundle data = new Bundle();
					data.putString("errorMsg", "连接超时，请稍后再试！");
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
					data.putString("errorMsg", "访问错误，请稍后再试");
					message.setData(data);
					iHandler.sendMessage(message);
				}
			}
		});
		thread.start();
		
		indexButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(BusinessMatterActivity.this,
						IndexActivity.class);
				startActivity(_Intent);
			}
		});
		
		followRecordButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(BusinessMatterActivity.this,
						FollowRecordActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("businessInfoId", businessInfoId);
				startActivity(_Intent);
			}
		});
		
		callbackButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = null;
				if (from == 0){
					_Intent = new Intent(BusinessMatterActivity.this,
							IndexActivity.class);
				}else if(from == 1){
					_Intent = new Intent(BusinessMatterActivity.this,
							QueryBusinessActivity.class);
				}
				// TODO Auto-generated method stub
				setResult(from, _Intent);
				finish();
			}
		});
	}
	
	private void setData(){
		if (null != businessInfoVo){
			businessNoTextView.setText(businessInfoVo.getBusinessNo());
			
			businessTypeNameTextView.setText(businessInfoVo.getBusinessTypeName());
			
			//customerNoTextView.setText(businessInfoVo.get);
			
			customerNameTextView.setText(businessInfoVo.getCustomerName());
			addressTextView.setText(businessInfoVo.getAddress());
			phoneTextView.setText(businessInfoVo.getTelephone());
			operatorNameTextView.setText(businessInfoVo.getName());
			String createTimeStr = businessInfoVo.getCreateTime() == null ? "" : DateHepler.dateToStrLong(businessInfoVo.getCreateTime()) ;
			createTimeTextView.setText(createTimeStr);
			businessContentTextView.setText(businessInfoVo.getBusinessContent());
		}
	}
	
	private void showTip(String msg)
	{
		Toast.makeText(BusinessMatterActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(BusinessMatterActivity BusinessMatterActivity)
		{
			mActivity = new WeakReference<Activity>(BusinessMatterActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((BusinessMatterActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				BusinessMatterActivity _BusinessMatterActivity = (BusinessMatterActivity) mActivity.get();
				_BusinessMatterActivity.setData();
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
}
