package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.BusinessType;
import com.example.businessaccept.service.IBusinessTypeService;
import com.example.businessaccept.service.impl.BusinessTypeServiceImpl;
import com.example.businessaccept.ui.vo.BusinessCondition;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class QueryBusinessActivity extends Activity
{
	private Button indexButton;
	private Button resetButton;
	private Button queryButton;
	private Spinner businessTypeSpinner;
	List<BusinessType> businessTypes = new ArrayList<BusinessType>();
	private ArrayAdapter<BusinessType> adapter;
	IBusinessTypeService businessTypeService = new BusinessTypeServiceImpl();
	private int businessTypeID = -1;

	private Spinner myBusinessSpinner;

	private Spinner statusSpinner;

	private EditText businessNoEditText;

	private EditText businessContentEditText;

	private EditText startTimeEditText;

	private EditText endTimeEditText;
	
	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_query_business);

		indexButton = (Button)findViewById(R.id.button_query_business_index);
		resetButton = (Button)findViewById(R.id.button_query_business_reset);
		queryButton = (Button) findViewById(R.id.button_query_business_query);
		businessTypeSpinner = (Spinner) findViewById(
				R.id.spinner_query_business_type);
		myBusinessSpinner = (Spinner)findViewById(R.id.spinner_query_business_my);
		statusSpinner = (Spinner)findViewById(R.id.spinner_query_business_status);
		businessNoEditText = (EditText)findViewById(R.id.edittext_query_business_businessNo);
		businessContentEditText=(EditText)findViewById(R.id.edittext_query_business_business);
		startTimeEditText = (EditText)findViewById(R.id.edittext_query_business_startTime);
		endTimeEditText=(EditText)findViewById(R.id.edittext_query_business_endTime);
		
		indexButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(QueryBusinessActivity.this, IndexActivity.class);
				startActivity(_Intent);
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				businessTypeSpinner.setSelection(0);
				myBusinessSpinner.setSelection(0);
				statusSpinner.setSelection(0);
				businessContentEditText.setText("");
				businessNoEditText.setText("");
				startTimeEditText.setText("");
				endTimeEditText.setText("");
			}
		});
		queryButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				BusinessCondition businessCondition = new BusinessCondition();
				businessCondition.setBusinessType(businessTypeID);
				//businessCondition.setBusinessId(businessId);
				String bc = businessContentEditText.getText() == null ? null : businessContentEditText.getText().toString();
				businessCondition.setBusinessContent(bc);
				String bId = businessNoEditText.getText()==null ? null : businessNoEditText.getText().toString();
				businessCondition.setBusinessId((bId==null || "".equals(bId)) ? 0 : Integer.valueOf(bId));
				String st = startTimeEditText.getText() == null ? null : startTimeEditText.getText().toString();
				businessCondition.setStartTime(st);
				String et = endTimeEditText.getText() == null ? null : endTimeEditText.getText().toString();
				businessCondition.setEndTime(et);
				
				Intent _Intent = new Intent(QueryBusinessActivity.this,
						BusinessListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("businessCondition", businessCondition);
				_Intent.putExtras(bundle);
				//startActivity(_Intent);
				startActivityForResult(_Intent, 0);
			}
		});
		
		startTimeEditText.setOnClickListener(new OnClickListener()
		{
			Calendar calendar = Calendar.getInstance();
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				DatePickerDialog dlg = new DatePickerDialog(QueryBusinessActivity.this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO Auto-generated method stub
						//更新EditText控件日期 小于10加0
						startTimeEditText.setText(new StringBuilder().append(year).append('-')
						    .append((month + 1) < 10 ? 0 + (month + 1) : (month + 1))
		                    .append('-')
						    .append((day < 10) ? 0 + day : day) ); 
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH) );
				dlg.show();
			}
		});
		endTimeEditText.setOnClickListener(new OnClickListener()
		{
			Calendar calendar = Calendar.getInstance();
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				DatePickerDialog dlg = new DatePickerDialog(QueryBusinessActivity.this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO Auto-generated method stub
						//更新EditText控件日期 小于10加0
						endTimeEditText.setText(new StringBuilder().append(year).append('-')
						    .append((month + 1) < 10 ? 0 + (month + 1) : (month + 1))
		                    .append('-')
						    .append((day < 10) ? 0 + day : day) ); 
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH) );
				dlg.show();
			}
		});
		setData();
	}
	private IHandler iHandler = new IHandler(this);
	public void setbt(List<BusinessType> bts){
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<BusinessType>(
				QueryBusinessActivity.this,
				android.R.layout.simple_spinner_item, bts);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		businessTypeSpinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		businessTypeSpinner.setOnItemSelectedListener(
				new SpinnerSelectedListener());

		// 设置默认值
		businessTypeSpinner.setVisibility(View.VISIBLE);
	}
	
	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(QueryBusinessActivity queryBusinessActivit)
		{
			mActivity = new WeakReference<Activity>(queryBusinessActivit);
		}

		@Override
		public void handleMessage(Message msg)
		{
			if (dialog != null)
			{
				dialog.dismiss();
			}
			ArrayList list = msg.getData().getParcelableArrayList("list");
			List<BusinessType> bts=(List<BusinessType>)list.get(0);
			QueryBusinessActivity queryBusinessActivit =((QueryBusinessActivity) mActivity.get());
			queryBusinessActivit.setbt(bts);
			
		}

	}
	private void setData()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					businessTypes = businessTypeService.list();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(QueryBusinessActivity.this, "获取数据失败，请稍后再试",
							Toast.LENGTH_SHORT).show();
				}
				Message message = new Message();
				Bundle data = new Bundle();
				ArrayList list = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
				list.add(businessTypes);
				data.putParcelableArrayList("list", list);
				message.setData(data);
				iHandler.sendMessage(message);
			}
		}).start();

	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener
	{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			businessTypeID = businessTypes.get(arg2).getBusinessTypeID();
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
		}
	}
}
