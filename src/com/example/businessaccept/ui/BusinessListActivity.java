package com.example.businessaccept.ui;

import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.service.IBusinessInfoService;
import com.example.businessaccept.service.impl.BusinessInfoServiceImpl;
import com.example.businessaccept.ui.vo.BusinessCondition;
import com.example.businessaccept.ui.vo.BusinessInfoVo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

public class BusinessListActivity extends Activity
{
	IBusinessInfoService businessInfoService = new BusinessInfoServiceImpl();
	
	private BusinessListAdapter adapter ;
	
	private GridView buesinessGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		BusinessCondition businessCondition = (BusinessCondition)getIntent().getSerializableExtra("businessCondition");
		setContentView(R.layout.layout_business_list);
		if (businessCondition == null){
			Toast.makeText(BusinessListActivity.this, "获取条件失败，请稍后再试！",3000);
			return;
		}
		
		try
		{
			List<BusinessInfoVo> list = businessInfoService.query(businessCondition);
			adapter = new BusinessListAdapter(BusinessListActivity.this, list);
			buesinessGridView = (GridView)findViewById(R.id.gridview_grid_businesses);
			buesinessGridView.setAdapter(adapter);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
