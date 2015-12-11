package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.Admin;
import com.example.businessaccept.entity.Department;
import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.service.IDepartmentService;
import com.example.businessaccept.service.IUserService;
import com.example.businessaccept.service.IWorkFlowService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.service.impl.DepartmentServiceImpl;
import com.example.businessaccept.service.impl.UserServiceImpl;
import com.example.businessaccept.service.impl.WorkFlowServiceImpl;
import com.example.businessaccept.ui.vo.WorkFlowVo;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

	private static AlertDialog.Builder dialog;

	private List<Department> depts = new ArrayList<Department>();

	private List<Admin> admins = new ArrayList<Admin>();

	private IDepartmentService departmentService = new DepartmentServiceImpl();

	private ArrayAdapter<Department> departmentAdapter;

	private int currentDeptId = -1;

	private int currentAdminId = -1;

	private IUserService userServiceImpl = new UserServiceImpl();

	private ArrayAdapter<Admin> adminAdapter;

	private IWorkFlowService workFlowService = new WorkFlowServiceImpl();

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
		setData();
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
					depts = departmentService.list();

					iHandler.sendEmptyMessage(1);
				}
				catch (ConnectTimeoutException e)
				{
					e.printStackTrace();
					Message message = new Message();
					Bundle data = new Bundle();
					data.putString("errorMsg", "连接超时，请稍后再试！");
					message.setData(data);
					iHandler.sendMessage(message);
				}
				catch (SocketTimeoutException e)
				{
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

	}

	private void setDept()
	{
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

	private void setUser()
	{
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

	private void setListener()
	{
		bcontentTextView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (dialog == null)
				{
					dialog = new AlertDialog.Builder(AcceptActivity.this);
					dialog.setTitle("处理描述");
					dialog.setMessage(workFlowVo.getDealContent());
					dialog.show();
				}
				else
				{
					dialog.setMessage(workFlowVo.getDealContent());
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
				Intent _Intent = new Intent(AcceptActivity.this,
						IndexActivity.class);
				startActivity(_Intent);
			}
		});
		workflowButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String bcontentStr = bcontentEditText.getText().toString();
				if (null == bcontentStr || "".equals(bcontentStr))
				{
					Toast.makeText(AcceptActivity.this, "处理意见不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				newWorkFlow = new WorkFlow();
				newWorkFlow.setBusinessId(workFlowVo.getBusinessId());
				newWorkFlow.setpWorkFlowId(workFlowVo.getWorkFlowId());
				newWorkFlow
						.setDealContent(bcontentEditText.getText().toString());
				newWorkFlow.setCreateTime(new Date());
				newWorkFlow.setStatus(0);
				
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
							newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
							iHandler.sendEmptyMessage(3);
						}
						catch (ConnectTimeoutException e)
						{
							e.printStackTrace();
							Message message = new Message();
							Bundle data = new Bundle();
							data.putString("errorMsg", "连接超时，请稍后再试！");
							message.setData(data);
							iHandler.sendMessage(message);
						}
						catch (SocketTimeoutException e)
						{
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
			}
		});
		confirmButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (null != newWorkFlow && newWorkFlow.getBusinessId() > 0)
				{
					newWorkFlow.setDepartId(currentDeptId);
					newWorkFlow.setOperatorId(currentAdminId);
					newWorkFlow.setStatus(2);
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
								newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
								iHandler.sendEmptyMessage(4);
							}
							catch (ConnectTimeoutException e)
							{
								e.printStackTrace();
								Message message = new Message();
								Bundle data = new Bundle();
								data.putString("errorMsg", "连接超时，请稍后再试！");
								message.setData(data);
								iHandler.sendMessage(message);
							}
							catch (SocketTimeoutException e)
							{
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
				}else{
					showTip("请先填写处理意见！");
				}
			}
		});
		overButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (null != newWorkFlow && newWorkFlow.getBusinessId() > 0)
				{
					newWorkFlow.setStatus(2);
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
								newWorkFlow = workFlowService.saveOrUpdate(newWorkFlow);
								iHandler.sendEmptyMessage(5);
							}
							catch (ConnectTimeoutException e)
							{
								e.printStackTrace();
								Message message = new Message();
								Bundle data = new Bundle();
								data.putString("errorMsg", "连接超时，请稍后再试！");
								message.setData(data);
								iHandler.sendMessage(message);
							}
							catch (SocketTimeoutException e)
							{
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
					
				}else{
					showTip("请先填写处理意见！");
				}
			}
		});
		blistButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(AcceptActivity.this,
						BusinessListActivity.class);
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
						admins = userServiceImpl.queryByDeptId(currentDeptId);
						iHandler.sendEmptyMessage(2);
					}
					catch (ConnectTimeoutException e)
					{
						e.printStackTrace();
						Message message = new Message();
						Bundle data = new Bundle();
						data.putString("errorMsg", "连接超时，请稍后再试！");
						message.setData(data);
						iHandler.sendMessage(message);
					}
					catch (SocketTimeoutException e)
					{
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

	private void showTip(String msg)
	{
		Toast.makeText(AcceptActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(AcceptActivity AcceptActivity)
		{
			mActivity = new WeakReference<Activity>(AcceptActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((AcceptActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				AcceptActivity _AcceptActivity = (AcceptActivity) mActivity
						.get();
				_AcceptActivity.setDept();
				break;
			case 2:
				AcceptActivity _AcceptActivity1 = (AcceptActivity) mActivity
						.get();
				_AcceptActivity1.setUser();
				break;
			case 3:
				AcceptActivity _AcceptActivity2 = (AcceptActivity) mActivity
						.get();
				_AcceptActivity2.showTip("处理意见保存成功！");
				break;
			case 4:
				AcceptActivity _AcceptActivity3 = (AcceptActivity) mActivity
						.get();
				_AcceptActivity3.showTip("指定受理人成功！");
				break;
			case 5:
				AcceptActivity _AcceptActivity4 = (AcceptActivity) mActivity
						.get();
				Intent _Intent = new Intent(_AcceptActivity4,
						BusinessListActivity.class);
				_AcceptActivity4.startActivity(_Intent);
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
}
