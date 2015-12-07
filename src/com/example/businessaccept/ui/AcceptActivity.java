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
import com.example.businessaccept.ui.vo.WorkFlowVo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 我来受理
 * 
 * @author liqi phone 18771970972 qq 197399622
 */
public class AcceptActivity extends Activity
{
	private WorkFlowVo workFlowVo;
	private WorkFlow newWorkFlow;
	private TextView bcontentTextView;
	private EditText bcontentEditText;
	private Button workflowButton;
	private Spinner departmentSpinner;
	private Spinner adminSpinner;
	private Button confirmButton;
	private Button indexButton;
	private Button overButton;
	private Button blistButton;

	private List<Department> depts = new ArrayList<Department>();

	private List<Admin> admins = new ArrayList<Admin>();

	private IDepartmentService departmentService = new DepartmentServiceImpl();

	private ArrayAdapter<Department> departmentAdapter;

	private int currentDeptId = -1;

	private int currentAdminId = -1;

	private IUserService userServiceImpl = new UserServiceImpl();

	private ArrayAdapter<Admin> adminAdapter;
	
	private IWorkFlowService workFlowService = new WorkFlowServiceImpl();

	private static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		workFlowVo = (WorkFlowVo) getIntent().getExtras()
				.getSerializable("workflow");
		setContentView(R.layout.layout_accept);
		findView();
		setListener();
	}

	public void findView()
	{
		bcontentTextView = (TextView) findViewById(
				R.id.textview_accept_bcontent);
		bcontentEditText = (EditText) findViewById(
				R.id.edittext_accept_businessContent);
		workflowButton = (Button) findViewById(R.id.button_accept_workflow);
		departmentSpinner = (Spinner) findViewById(R.id.spinner_accept_dept);
		adminSpinner = (Spinner) findViewById(R.id.spinner_accept_user);
		confirmButton = (Button) findViewById(R.id.button_accept_confirm);
		indexButton = (Button) findViewById(R.id.button_accept_index);
		overButton = (Button) findViewById(R.id.button_accept_over);
		blistButton = (Button) findViewById(R.id.button_accept_blist);
	}

	public void setData()
	{
		bcontentTextView.setText(workFlowVo.getDealContent());
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
	}

	private void setListener()
	{
		bcontentTextView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (dialog == null)
				{
					dialog = new ProgressDialog(AcceptActivity.this);
					dialog.setTitle("处理描述");
					dialog.setMessage(workFlowVo.getDealContent());
					dialog.setCancelable(false);
					dialog.show();
				}
				else
				{
					dialog.show();
				}

			}
		});
		indexButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(AcceptActivity.this, IndexActivity.class);
				startActivity(_Intent);
			}
		});
		workflowButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				newWorkFlow = new WorkFlow();
				newWorkFlow.setBusinessId(workFlowVo.getBusinessId());
				newWorkFlow.setpWorkFlowId(workFlowVo.getWorkFlowId());
				newWorkFlow.setCreateTime(new Date());
				newWorkFlow.setStatus(0);
				try
				{
					newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		confirmButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (null != newWorkFlow){
					newWorkFlow.setDepartId(currentDeptId);
					newWorkFlow.setOperatorId(currentAdminId);
					newWorkFlow.setStatus(2);
					try
					{
						newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		overButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if (null != newWorkFlow){
					newWorkFlow.setStatus(2);
					try
					{
						newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		blistButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(AcceptActivity.this, BusinessListActivity.class);
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
			adminAdapter = new ArrayAdapter<Admin>(AcceptActivity.this,
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
