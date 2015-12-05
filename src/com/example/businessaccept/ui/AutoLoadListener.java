package com.example.businessaccept.ui;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class AutoLoadListener implements OnScrollListener
{

	public interface AutoLoadCallBack
	{
		void execute(String url);
	}

	private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
	private AutoLoadCallBack mCallback;

	public AutoLoadListener(AutoLoadCallBack callback)
	{
		this.mCallback = callback;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
			{
				// 滚动到底部
				if (view.getLastVisiblePosition() == (view.getCount() - 1))
				{
					View v = (View) view.getChildAt(view.getChildCount() - 1);
					int[] location = new int[2];
					v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
					int y = location[1];

					Log.e("x" + location[0], "y" + location[1]);
					if (view.getLastVisiblePosition() != getLastVisiblePosition
							&& lastVisiblePositionY != y) // 第一次拖至底部
					{
						Toast.makeText(view.getContext(), "再次拖至底部，即可翻页", 500)
								.show();
						getLastVisiblePosition = view.getLastVisiblePosition();
						lastVisiblePositionY = y;
						return;
					}
					else if (view
							.getLastVisiblePosition() == getLastVisiblePosition
							&& lastVisiblePositionY == y) // 第二次拖至底部
					{
						mCallback.execute(">>>>>拖至底部");
					}
				}

				// 未滚动到底部，第二次拖至底部都初始化
				getLastVisiblePosition = 0;
				lastVisiblePositionY = 0;
			}
			// 滚动到底部
			if (view.getLastVisiblePosition() == (view.getCount() - 1))
			{
				View v = (View) view.getChildAt(view.getChildCount() - 1);
				int[] location = new int[2];
				v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
				int y = location[1];

				Log.e("x" + location[0], "y" + location[1]);
				if (view.getLastVisiblePosition() != getLastVisiblePosition
						&& lastVisiblePositionY != y) // 第一次拖至底部
				{
					Toast.makeText(view.getContext(), "再次拖至底部，即可翻页", 500)
							.show();
					getLastVisiblePosition = view.getLastVisiblePosition();
					lastVisiblePositionY = y;
					return;
				}
				else if (view.getLastVisiblePosition() == getLastVisiblePosition
						&& lastVisiblePositionY == y) // 第二次拖至底部
				{
					mCallback.execute(">>>>>拖至底部");
				}
			}

			// 未滚动到底部，第二次拖至底部都初始化
			getLastVisiblePosition = 0;
			lastVisiblePositionY = 0;
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		// TODO Auto-generated method stub

	}

}