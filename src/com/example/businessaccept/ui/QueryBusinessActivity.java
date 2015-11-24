package com.example.businessaccept.ui;

import com.example.businessaccept.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QueryBusinessActivity extends Activity
{
	private Button queryButton;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_query_business);
		queryButton = (Button)findViewById(R.id.button_query_business_query);
		queryButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent _Intent = new Intent(QueryBusinessActivity.this, BusinessListActivity.class);
				startActivity(_Intent);
			}
		});
	}
}
