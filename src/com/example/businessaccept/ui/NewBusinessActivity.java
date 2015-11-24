package com.example.businessaccept.ui;

import com.example.businessaccept.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewBusinessActivity extends Activity
{
	private Button submitButton;
	
	private Button queryBusinessButton;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_business);
		submitButton = (Button)findViewById(R.id.button_business_submit);
		submitButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(NewBusinessActivity.this, MatterActivity.class);
				startActivity(_Intent);
			}
		});
		queryBusinessButton = (Button)findViewById(R.id.button_business_query_business);
		queryBusinessButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent _Intent = new Intent(NewBusinessActivity.this, QueryBusinessActivity.class);
				startActivity(_Intent);
			}
		});
	}
	
}
