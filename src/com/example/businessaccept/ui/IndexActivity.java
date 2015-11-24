package com.example.businessaccept.ui;

import com.example.businessaccept.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity
{
	private Button newBusiness;
@Override
protected void onCreate(Bundle savedInstanceState)
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_index);
	newBusiness = (Button)findViewById(R.id.button_index_new_business);
	newBusiness.setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent _Intent = new Intent(IndexActivity.this, NewBusinessActivity.class);
			startActivity(_Intent);
		}
	});
}
}
