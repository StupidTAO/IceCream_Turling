package com.icecream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.icecream.R;
import com.icecream.bean.ChatMessage;
import com.icecream.bean.HttpUtils;
import com.icecream.bean.ChatMessage.Type;

public class MainActivity extends Activity
{
	/**
	 * 用于显示聊天纪录
	 */
	private ListView mChatView;
	/**
	 * 用于输入信息
	 */
	private EditText mMsg;
	/**
	 * 用于存储信息的ListView
	 */
	private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
	/**
	 * 用于绑定数据与用户界面的适配器
	 */
	private ChatMessageAdapter mAdapter;
	private SlidingMenu mMenu;
	private int MID;
	private String string;
	private int postion;
	private TextView textView1;
	private TextView textView2;
	/**
	 * Handle主要接受子线程发送的数据，并用此数据配合主线程（UI线程）更新UI
	 */
	//此Handle用于不断地更新聊天界面的内容
	//疑问：Handle是一个线程吗？为什么说UI中只能有一个线程？
	private Handler mHandler = new Handler() {
		/**
		 * 作用：
		 * 1.处理从第三方获取的信息
		 * 2.动态的加入适配器中，并刷新界面
		 */
		public void handleMessage(android.os.Message msg)
		{
			//向下强制类型转换
			ChatMessage from = (ChatMessage) msg.obj;
			mDatas.add(from);
			//msg.obj将返回一个Object类型的应用，ChatMessage是Object的子类
			//但是，Message并不一定是ChatMessage类型，或是其子类啊？？？？
			
			mAdapter.notifyDataSetChanged();
			//用于定位最新的聊天信息
			//改进：可以把跳转的形式改为平缓滑动，以增强用户体验（ep：smoothScrollByOffset,
			//smoothToPosition）
			mChatView.setSelection(mDatas.size() - 1);
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
	/*	
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, MenuView.class);
		startActivity(intent);
	*/	
		
		
		initView();
		
		mChatView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{	
				textView1=(TextView)arg1.findViewById(R.id.chat_send_content);
				textView2=(TextView)arg1.findViewById(R.id.chat_from_content);
				postion=(int) arg3;
				//长按listview，弹出菜单，菜单有3项，查看，复制，查案大图
				mChatView.setOnCreateContextMenuListener(new OnCreateContextMenuListener()
				{
					
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo)
					{
						menu.add(0, 0, 0, "查看");
						menu.add(0, 1, 0, "复制");
						// TODO Auto-generated method stub
						
					}
				});
				// TODO Auto-generated method stub
				return false;
			}
		});
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mChatView.setAdapter(mAdapter);

	}
	
	public boolean onContextItemSelected(MenuItem item)
	{	
	
		AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		MID =(int)info.id;
		if (postion%2==1)
			string=textView1.getText().toString();
		else
			string=textView2.getText().toString();
		
		switch (item.getItemId())
		{
			case 0:
				Intent intent=new Intent(MainActivity.this,Look.class);
				intent.putExtra("text", string);
				startActivity(intent);
			break;
			case 1:			
				if (string!="")
				{							
					ClipboardManager clip=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
					clip.setText(string);
					Toast.makeText(MainActivity.this,"已复制到剪贴板", Toast.LENGTH_LONG).show();
				}
				break;
	
		default:
			break;
		}
		
		return false;
	}
	
	private void initView()
	{
		mMenu = (SlidingMenu) findViewById(R.id.menu);
		mChatView = (ListView) findViewById(R.id.id_chat_listView);
		mMsg = (EditText) findViewById(R.id.id_chat_msg);
		mDatas.add(new ChatMessage(Type.INPUT, "你好，欢迎使用图灵聊天机器人！"));
	}

	//供用户使用，用于发送用户的消息
	/**
	 * 作用：
	 * 1.刷新界面，并显示新消息
	 * 2.将消息发送给第三方，并获取第三方返回的消息
	 */
	public void sendMessage(View view)
	{
		final String msg = mMsg.getText().toString();
		if (TextUtils.isEmpty(msg)) {
			Toast.makeText(this, "不可以发送空信息...", Toast.LENGTH_SHORT).show();
			return;
		}

		ChatMessage to = new ChatMessage(Type.OUTPUT, msg);
		to.setDate(new Date());
		mDatas.add(to);
		
		//notifyDataSetChanged()方法，可以不用重新刷新Activity,通知Activity更新ListView
		//为什么把此方法注释掉，同样可以更新listView呢？？？（可能是在Handle中刷新了Activity的界面）
		mAdapter.notifyDataSetChanged();
		//使得最新消息一直可见，不需要手动下滑
		mChatView.setSelection(mDatas.size() - 1);

		mMsg.setText("");
	
		new Thread()
		{	
			//每次发送消息都要新建一个线程
			//新建一个线程用于接受第三方的回复
			public void run()
			{
				ChatMessage from = null;
				try {
					from = HttpUtils.sendMsg( MainActivity.this, msg);
					
				} catch (Exception e) {
					from = new ChatMessage(Type.INPUT, "网络连接出错了，不能回复你喽..." );//+ e.toString());
				}
				Message message = Message.obtain();
				message.obj = from;
				mHandler.sendMessage(message);
				
	
			};
		}.start(); 
	}
	public void bigPictureFrom(View view) {
		Intent intent=new Intent(MainActivity.this,BigPicture.class);
			intent.putExtra("picture", R.drawable.icon);
		
		startActivity(intent);
	}
	
	public void bigPictureSend(View view) {
		Intent intent=new Intent(MainActivity.this,BigPicture.class);
			intent.putExtra("picture", R.drawable.my);
		
		startActivity(intent);
	}
	
	public void toggleMenu(View view)
	{
		mMenu.toggle();
	}

}
