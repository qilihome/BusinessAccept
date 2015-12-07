package com.example.businessaccept.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.BusinessInfo;
import com.example.businessaccept.entity.BusinessType;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.IBusinessTypeService;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.service.impl.BusinessTypeServiceImpl;
import com.example.businessaccept.util.AdminHepler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewBusinessActivity extends Activity
{
	private Button businessIndexButton;

	private Button submitButton;

	private Button queryBusinessButton;
	
	private Button queryCustomerButton;

	private Spinner businessTypeSpinner;

	private EditText meterCode;

	private EditText customerName;
	private EditText address;
	private EditText telephone;
	private EditText businessContent;

	private ArrayAdapter<BusinessType> adapter;
	
	List<BusinessType> businessTypes = new ArrayList<BusinessType>();
	

	IBusinessTypeService businessTypeService = new BusinessTypeServiceImpl();
	
	IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();
	
	private int businessTypeID = -1;
	
	private BusinessInfo businessInfo = new BusinessInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		///在Android2.2以后必须添加以下代码  
        //本应用采用的Android4.0  
        //设置线程的策略  
         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()     
         .detectDiskReads()     
         .detectDiskWrites()     
         .detectNetwork()   // or .detectAll() for all detectable problems     
         .penaltyLog()     
         .build());     
        //设置虚拟机的策略  
          StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()     
                 .detectLeakedSqlLiteObjects()     
                 //.detectLeakedClosableObjects()     
                 .penaltyLog()     
                 .penaltyDeath()     
                 .build());  
          
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		int businessId = getIntent().getIntExtra("businessId", 0);
		
		setContentView(R.layout.layout_business);
		if (businessId >0){
			//TODO
			//赋值
		}
		findView();
		setListener();
		setData();

	}

	private void findView()
	{
		businessIndexButton = (Button) findViewById(R.id.button_business_index);
		submitButton = (Button) findViewById(R.id.button_business_submit);
		queryCustomerButton = (Button)findViewById(R.id.button_business_queryCustomer);
		queryBusinessButton = (Button) findViewById(
				R.id.button_business_query_business);
		
		businessTypeSpinner = (Spinner) findViewById(
				R.id.spinner_business_type);
		meterCode = (EditText)findViewById(R.id.edittext_business_meterCode);
		customerName = (EditText)findViewById(R.id.edittext_business_customerName);
		address = (EditText)findViewById(R.id.edittext_business_address);
		telephone = (EditText)findViewById(R.id.edittext_business_telephone);
		businessContent = (EditText)findViewById(R.id.edittext_business_businessContent);
	}

	private void setListener()
	{
		businessIndexButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(NewBusinessActivity.this,
						IndexActivity.class);
				startActivity(_Intent);
			}
		});
		submitButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (businessTypeID < 0){
					Toast.makeText(NewBusinessActivity.this, "请选择业务类别", Toast.LENGTH_SHORT).show();
					return;
				}
				businessInfo.setBusinessTypeID(businessTypeID);
				
				
				String meterCodeValue = meterCode.getText().toString();
				if (null == meterCodeValue || "".equals(meterCodeValue)){
					Toast.makeText(NewBusinessActivity.this, "请输入或选择立户编号", Toast.LENGTH_SHORT).show();
					return;
				}
				businessInfo.setMeterCode(meterCodeValue);
				
				String customerNameValue = customerName.getText().toString();
				if (null == customerNameValue || "".equals(meterCodeValue)){
					Toast.makeText(NewBusinessActivity.this, "请输入户主名", Toast.LENGTH_SHORT).show();
					return;
				}
				businessInfo.setCustomerName(customerNameValue);
				
				String addressValue = address.getText().toString();
				businessInfo.setAddress(addressValue);
				
				String telephoneValue = telephone.getText().toString();
				businessInfo.setTelephone(telephoneValue);
				
				String businessContentValue = businessContent.getText().toString();
				if (null == businessContentValue || "".equals(meterCodeValue)){
					Toast.makeText(NewBusinessActivity.this, "请输入描述事项", Toast.LENGTH_SHORT).show();
					return;
				}
				businessInfo.setBusinessContent(businessContentValue);
				
				businessInfo.setOperatorID(AdminHepler.getAdminId(NewBusinessActivity.this));
				
				try
				{
					if (businessInfo.getBusinessID() < 1){
						
						businessInfo = businessInfoService.save(businessInfo);
					}else{
						businessInfo = businessInfoService.update(businessInfo);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Toast.makeText(NewBusinessActivity.this, "保存业务失败，请稍后再试", Toast.LENGTH_SHORT).show();
					return;
				}
				
				int bId = businessInfo.getBusinessID();
				if (bId > 0){
					Intent _Intent = new Intent(NewBusinessActivity.this,
							MatterActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("businessInfoId", bId);
					_Intent.putExtras(bundle);
					startActivityForResult(_Intent, 4);
				}else{
					Toast.makeText(NewBusinessActivity.this, "保存业务失败，请稍后再试", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		queryCustomerButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(NewBusinessActivity.this,
						QueryCustomerActivity.class);
				startActivityForResult(_Intent,0);
			}
		});
		queryBusinessButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(NewBusinessActivity.this,
						QueryBusinessActivity.class);
				startActivity(_Intent);
			}
		});
	}

	private void setData()
	{
		
		try
		{
			businessTypes = businessTypeService.list();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(NewBusinessActivity.this, "获取数据失败，请稍后再试",
					Toast.LENGTH_SHORT).show();
		}
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<BusinessType>(this,
				android.R.layout.simple_spinner_item, businessTypes);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		businessTypeSpinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		businessTypeSpinner
				.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		businessTypeSpinner.setVisibility(View.VISIBLE);
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
	
	// 回调方法，从第二个页面回来的时候会执行这个方法  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        String customerNo = data.getStringExtra("customerNo");  
        // 根据上面发送过去的请求吗来区别  
        switch (resultCode) {  
        case 0:  
        	meterCode.setText(customerNo);  
            break;  
        default:  
            break;  
        }  
        super.onActivityResult(requestCode, resultCode, data);
    }  
}
