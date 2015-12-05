package com.example.businessaccept.ui;

import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.entity.BusinessInfo;
import com.example.businessaccept.ui.PendingListViewAdapter.ListItemView;
import com.example.businessaccept.ui.vo.BusinessInfoVo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusinessListAdapter extends BaseAdapter
{
	private Context context;

	private List<BusinessInfoVo> list;

	public BusinessListAdapter()
	{

	}

	public BusinessListAdapter(Context context, List<BusinessInfoVo> list)
	{
		this.context = context;
		this.list = list;
	}

	public void bindData(List<BusinessInfoVo> list)
	{
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
		ListItemView mListItemView = null;
		if (convertView == null)
		{
			mListItemView = new ListItemView();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.layout_business_list_item, null);
			mListItemView.textView01 = (TextView) convertView
					.findViewById(R.id.textview_business_type_item);
			mListItemView.textView02 = (TextView) convertView
					.findViewById(R.id.textview_business_user_item);
			mListItemView.textView03 = (TextView) convertView
					.findViewById(R.id.textview_business_content_item);
			convertView.setTag(mListItemView);

		}
		else
		{
			mListItemView = (ListItemView) convertView.getTag();
		}
		mListItemView.textView01
				.setText(list.get(position).getBusinessTypeName());
		mListItemView.textView02.setText(list.get(position).getName());
		mListItemView.textView03
				.setText(list.get(position).getBusinessContent());
		return convertView;
	}

	public final class ListItemView
	{ // 自定义控件集合
		public TextView textView01;
		public TextView textView02;
		public TextView textView03;
	}
}
