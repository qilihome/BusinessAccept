package com.example.businessaccept.ui;

import java.util.List;

import com.example.businessaccept.R;
import com.example.businessaccept.ui.vo.BusinessInfoVo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PendingListViewAdapter extends BaseAdapter
{
	private Context mContext;
	private List<BusinessInfoVo> mList;

	public PendingListViewAdapter(Context context, List<BusinessInfoVo> list)
	{
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ListItemView mListItemView = null;
		if (mListItemView == null)
		{
			mListItemView = new ListItemView();
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.index_businesses_item, null);
			mListItemView.type = (TextView) convertView
					.findViewById(R.id.textview_index_list_type);
			mListItemView.desc = (TextView) convertView
					.findViewById(R.id.textview_index_list_desc);
			mListItemView.user = (TextView) convertView
					.findViewById(R.id.textview_index_list_user);
			convertView.setTag(mListItemView);
		}
		else
		{
			mListItemView = (ListItemView) convertView.getTag();
		}
		mListItemView.type.setText(mList.get(position).getBusinessTypeName());
		mListItemView.desc.setText(mList.get(position).getBusinessContent());
		mListItemView.user.setText(mList.get(position).getName());
		return convertView;
	}

	public final class ListItemView
	{ // 自定义控件集合
		public TextView type;
		public TextView desc;
		public TextView user;
	}

}
