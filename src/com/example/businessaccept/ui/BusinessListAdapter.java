package com.example.businessaccept.ui;

import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.BusinessInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusinessListAdapter extends BaseAdapter
{
	private Context context;
	
	private List<BusinessInfo> list;
	public BusinessListAdapter(Context context, List<BusinessInfo> list)
	{
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position).getBusinessID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.layout_business_list_item, null);
		TextView textView01 = (TextView)convertView.findViewById(R.id.textview_business_type_item);
		TextView textView02 = (TextView)convertView.findViewById(R.id.textview_business_user_item);
		TextView textView03 = (TextView)convertView.findViewById(R.id.textview_business_content_item);
		textView01.setText(list.get(position).getBusinessTypeID());
		textView02.setText(list.get(position).getOperatorID());
		textView03.setText(list.get(position).getBusinessContent());
		return convertView;
	}
}
