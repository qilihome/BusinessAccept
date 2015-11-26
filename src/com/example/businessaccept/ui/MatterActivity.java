package com.example.businessaccept.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.Admin;
import com.example.businessaccept.entity.Department;
import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.service.IDepartmentService;
import com.example.businessaccept.service.IUserService;
import com.example.businessaccept.service.IWorkFlowService;
import com.example.businessaccept.service.impl.DepartmentServiceImpl;
import com.example.businessaccept.service.impl.UserServiceImpl;
import com.example.businessaccept.service.impl.WorkFlowServiceImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MatterActivity extends Activity
{
	private Spinner departmentSpinner;

	private Spinner adminSpinner;

	private List<Department> depts = new ArrayList<Department>();

	private List<Admin> admins = new ArrayList<Admin>();

	private IDepartmentService departmentService = new DepartmentServiceImpl();

	private ArrayAdapter<Department> departmentAdapter;

	private int currentDeptId = -1;

	private int currentAdminId = -1;

	private IUserService userServiceImpl = new UserServiceImpl();

	private ArrayAdapter<Admin> adminAdapter;
	
	private Button saveButton;
	
	private int businessId;
	
	private IWorkFlowService workFlowService = new WorkFlowServiceImpl();
	
	private Button indexButton;
	private Button queryBusinessButton;
	private Button updateBusinessButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		businessId = getIntent().getIntExtra("businessId", 0);
		if (businessId < 1){
			Toast.makeText(MatterActivity.this, "获取业务ID错误，稍后再试！", 3000);
			return;
		}
		setContentView(R.layout.layout_matter);
		departmentSpinner = (Spinner) findViewById(R.id.spinner_matter_depart);
		adminSpinner = (Spinner) findViewById(R.id.spinner_matter_customer);
		saveButton = (Button)findViewById(R.id.button_matter_save);
		indexButton = (Button) findViewById(R.id.button_business_index);
		queryBusinessButton = (Button)findViewById(R.id.button_business_query_business);
		updateBusinessButton = (Button)findViewById(R.id.button_matter_business_update);
		try
		{
			depts = departmentService.list();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将可选内容与ArrayAdapter连接起来
		departmentAdapter = new ArrayAdapter<Department>(this,
				android.R.layout.simple_spinner_item, depts);

		// 设置下拉列表的风格
		departmentAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		departmentSpinner.setAdapter(departmentAdapter);

		// 添加事件Spinner事件监听
		departmentSpinner
				.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		departmentSpinner.setVisibility(View.VISIBLE);
		
		saveButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				WorkFlow workFlow = new WorkFlow();
				workFlow.setBusinessId(businessId);
				workFlow.setDepartId(currentDeptId);
				workFlow.setOperatorId(currentAdminId);
				workFlow.setCreateTime(new Date());
				workFlow.setStatus(0);
				try
				{
					workFlowService.saveOrUpdate(workFlow);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		indexButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(MatterActivity.this,
						IndexActivity.class);
				startActivity(_Intent);
			}
		});
		
		queryBusinessButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(MatterActivity.this,
						QueryBusinessActivity.class);
				startActivity(_Intent);
			}
		});
		
		updateBusinessButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(MatterActivity.this,
						QueryBusinessActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("businessId", businessId);
				_Intent.putExtras(bundle);
				startActivity(_Intent);
			}
		});
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener
	{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			currentDeptId = depts.get(arg2).getDepartID();

			try
			{
				admins = userServiceImpl.queryByDeptId(currentDeptId);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 将可选内容与ArrayAdapter连接起来
			adminAdapter = new ArrayAdapter<Admin>(MatterActivity.this,
					android.R.layout.simple_spinner_item, admins);

			// 设置下拉列表的风格
			adminAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);

			// 将adapter 添加到spinner中
			adminSpinner.setAdapter(adminAdapter);

			// 添加事件Spinner事件监听
			departmentSpinner
					.setOnItemSelectedListener(new SpinnerSelectedListener());

			// 设置默认值
			departmentSpinner.setVisibility(View.VISIBLE);
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
		}
	}

	// 使用数组形式操作
	class AdminSpinnerSelectedListener implements OnItemSelectedListener
	{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			currentAdminId = admins.get(arg2).getId();
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{

		}
	}
}
