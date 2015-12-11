package com.example.businessaccept.ui;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.service.IWorkFlowService;
import com.example.businessaccept.service.ServiceRulesException;
import com.example.businessaccept.service.impl.WorkFlowServiceImpl;
import com.example.businessaccept.ui.vo.WorkFlowVo;
import com.example.businessaccept.util.DateHepler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FollowRecordActivity extends Activity
{
	private int businessInfoId;
	private int currentPageIndex = 0;
	private TextView deptTextView;
	private TextView userTextView;
	private TextView createTimeTextView;
	private TextView dealTimeTextView;
	private TextView statusTextView;
	private TextView bcontentTextView;
	private EditText dealContextEditText;
	private Button dealButton;
	private Button recordButton;
	private Button firstPageButton;
	private Button previousPageButton;
	private Button nextPageButton;
	private Button indexButton;
	private Button returnButton;
	private List<WorkFlowVo> list;
	private static AlertDialog.Builder dialog;
	private WorkFlow workFlow;

	private IWorkFlowService workFlowServiceImpl = new WorkFlowServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		businessInfoId = getIntent().getExtras().getInt("businessInfoId");
		setContentView(R.layout.layout_follow_record);
		setView();
		getData();

		setListener();

	}

	private void setView()
	{
		deptTextView = (TextView) findViewById(
				R.id.textview_follow_record_dept);
		userTextView = (TextView) findViewById(
				R.id.textview_follow_record_user);
		createTimeTextView = (TextView) findViewById(
				R.id.textview_follow_record_createTime);
		dealTimeTextView = (TextView) findViewById(
				R.id.textview_follow_record_dealTime);
		statusTextView = (TextView) findViewById(
				R.id.textview_follow_record_status);
		bcontentTextView = (TextView) findViewById(
				R.id.textview_follow_record_bcontent);
		dealContextEditText = (EditText) findViewById(
				R.id.edittext_follow_record_deal_context);
		dealButton = (Button) findViewById(R.id.button_follow_record_deal);
		recordButton = (Button) findViewById(R.id.button_follow_record_record);
		returnButton = (Button) findViewById(R.id.button_follow_record_return);
		firstPageButton = (Button) findViewById(
				R.id.button_follow_record_first_page);
		previousPageButton = (Button) findViewById(
				R.id.button_follow_record_previous_page);
		nextPageButton = (Button) findViewById(
				R.id.button_follow_record_next_page);
		indexButton = (Button) findViewById(R.id.button_follow_record_index);
	}

	private void setData(int index)
	{
		if (!list.isEmpty() && list.size() > 0)
		{
			currentPageIndex = index;
			deptTextView.setText(list.get(index).getDepartName());
			userTextView.setText(list.get(index).getName());
			String createTimeStr = list.get(index).getCreateTime() == null ? ""
					: DateHepler.dateToStrLong(list.get(index).getCreateTime());
			createTimeTextView.setText(createTimeStr);
			String dealTimeStr = list.get(index).getDealTime() == null ? ""
					: DateHepler.dateToStrLong(list.get(index).getDealTime());
			dealTimeTextView.setText(dealTimeStr);
			int status = list.get(index).getStatus();
			String statusStr = "";
			if (status == 0)
			{
				statusStr = "待处理";
			}else if (status == 1){
				statusStr = "已处理";
			}else if (status == 2){
				statusStr = "注销";
			}
			statusTextView.setText(statusStr);
			bcontentTextView.setText(list.get(index).getDealContent());
		}

	}

	private void setListener()
	{
		bcontentTextView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!list.isEmpty() && list.size() > 0){
					// TODO Auto-generated method stub
					if (dialog == null)
					{
						dialog = new AlertDialog.Builder(FollowRecordActivity.this);
						dialog.setTitle("处理描述");
						dialog.setMessage(list.get(currentPageIndex).getDealContent());
						//dialog.setCancelable(false);
						//dialog.setCanceledOnTouchOutside(true);
						dialog.show();
					}else{
						dialog.setMessage(list.get(currentPageIndex).getDealContent());
						dialog.show();
					}
				}
			}
		});
		
		dealButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				final String dealContext = dealContextEditText.getText().toString();
				if (null == dealContext || "".equals(dealContext)){
					Toast.makeText(FollowRecordActivity.this, "注销原因不能为空!", Toast.LENGTH_SHORT).show();
				}else{
					
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
								WorkFlowVo workFlowVo = list.get(list.size()-1);
								workFlow = new WorkFlow();
								workFlow.setBusinessId(workFlowVo.getBusinessId());
								workFlow.setCreateTime(workFlowVo.getCreateTime());
								workFlow.setDealContent(workFlowVo.getDealContent());
								workFlow.setDepartId(workFlowVo.getDepartId());
								workFlow.setOperatorId(workFlowVo.getOperatorId());
								workFlow.setOverContext(workFlowVo.getOverContext());
								workFlow.setOverTime(workFlowVo.getOverTime());
								workFlow.setpWorkFlowId(workFlowVo.getpWorkFlowId());
								workFlow.setpWorkFlowIds(workFlowVo.getpWorkFlowIds());
								workFlow.setStatus(2);
								workFlow.setWorkFlowId(workFlowVo.getWorkFlowId());
								workFlow.setDealContent(dealContext);
								workFlowServiceImpl.saveOrUpdate(workFlow);

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
			}
		});
		
		recordButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(FollowRecordActivity.this, AcceptActivity.class);
				Bundle _Bundle = new Bundle();
				_Bundle.putSerializable("workflow", list.get(currentPageIndex));
				_Intent.putExtras(_Bundle);
				startActivity(_Intent);
			}
		});
		
		firstPageButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				setData(0);
			}
		});
		previousPageButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if (currentPageIndex-1 >= 0){
					currentPageIndex--;
					setData(currentPageIndex);
				}
			}
		});
		nextPageButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if (currentPageIndex+1 < list.size()){
					currentPageIndex++;
					setData(currentPageIndex);
				}
			}
		});
		indexButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(FollowRecordActivity.this, IndexActivity.class);
				startActivity(_Intent);
			}
		});
		
		returnButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(FollowRecordActivity.this, BusinessMatterActivity.class);
				//startActivity(_Intent);
				setResult(12, _Intent);
				finish();
			}
		});
	}

	private void getData()
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
					list = workFlowServiceImpl
							.queryByBusinessInfoId(businessInfoId);

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

	private void showTip(String msg)
	{
		Toast.makeText(FollowRecordActivity.this, msg, Toast.LENGTH_SHORT)
				.show();
	}

	private static class IHandler extends Handler
	{
		private final WeakReference<Activity> mActivity;

		public IHandler(FollowRecordActivity followRecordActivity)
		{
			mActivity = new WeakReference<Activity>(followRecordActivity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			int flag = msg.what;
			switch (flag)
			{
			case 0:
				String msgStr = msg.getData().getString("errorMsg");
				((FollowRecordActivity) mActivity.get()).showTip(msgStr);
				break;
			case 1:
				FollowRecordActivity _FollowRecordActivity = (FollowRecordActivity) mActivity
						.get();
				_FollowRecordActivity.setData(_FollowRecordActivity.list.size()-1);
				break;
			case 2:
				((FollowRecordActivity) mActivity.get()).showTip("成功保存注销原因。");
				break;
			default:
				break;
			}
		}

	}

	private IHandler iHandler = new IHandler(this);
}
