package com.icecream;
 
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.icecream.R;
import com.icecream.bean.ChatMessage;
import com.icecream.bean.ChatMessage.Type;

public class ChatMessageAdapter extends BaseAdapter
{
	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;
	private Context context;
	public ChatMessageAdapter(Context context, List<ChatMessage> datas)
	{
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
		this.context = context;
	}

	
	public int getCount()
	{
		return mDatas.size();
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	//返回的值如果是position为什么要使用此函数，不是多了一个流程吗？？？
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * 获取ListView中Item的来源信息，便于规划布局
	 */
	@Override
	public int getItemViewType(int position)
	{
		ChatMessage msg = mDatas.get(position);
		return msg.getType() == Type.INPUT ? 1 : 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}
	
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = mDatas.get(position);
		HolderView holder = null;
	
		//当convertView不为空时，如何确定使用的是哪一个
		if (convertView == null) {	
			//新建UI视图并且与数据建立关联
			holder = new HolderView();
			if (chatMessage.getType() == Type.INPUT) {
				//建立一个新的item的UI
				convertView = (View) mInflater.
						inflate(R.layout.main_chat_from_msg, parent, false);
				//建立数据与前台UI的关联
				holder.dateView = (TextView) convertView.
						findViewById(R.id.chat_from_createDate);
				holder.contentView = (TextView) convertView.
						findViewById(R.id.chat_from_content);
				convertView.setTag(holder);
			} else {
				convertView = (View) mInflater.
						inflate(R.layout.main_chat_send_msg, parent, false);
				
				holder.dateView = (TextView) convertView.
						findViewById(R.id.chat_send_createDate);
				holder.contentView = (TextView) convertView.
						findViewById(R.id.chat_send_content);
				convertView.setTag(holder);
			}
			
		} else {
			holder = (HolderView)convertView.getTag();
		}
		//对已经建立关联的数据进行填充
		holder.dateView.setText(chatMessage.getDateStr());
		holder.contentView.setText(chatMessage.getMsg());
	
		holder.contentView.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				
				return false;
			}
		});
		return convertView;
	}
	private class HolderView{
		TextView dateView;
		TextView contentView;
		TextView name;
	}
}
