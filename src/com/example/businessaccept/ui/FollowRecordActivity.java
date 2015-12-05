package com.example.businessaccept.ui;

import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.WorkFlow;
import com.example.businessaccept.service.IWorkFlowService;
import com.example.businessaccept.service.impl.WorkFlowServiceImpl;

import android.app.Activity;
import android.os.Bundle;

public class FollowRecordActivity extends Activity
{
	private int businessInfoId;
	
	private List<WorkFlow> list;
	
	private IWorkFlowService workFlowServiceImpl = new WorkFlowServiceImpl();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		businessInfoId = getIntent().getExtras().getInt("businessInfoId");
		setContentView(R.layout.layout_follow_record);
		
		//workFlowServiceImpl.queryByBusinessInfoId(businessInfoId);
	}
}
