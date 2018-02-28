package com.icecream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuList {

	private Context mContext;
	private SlidingMenu mMenu;
    private static final int IMAGE_CODE = 1;
    private int ITEM_COUNTS = 5;
    private ListView list;
    private String[] titles = new String[]{"联系我们", "设置", 
    	
    		"分享到其它app", "打开相机", "设置手势"};
    private int images[] = new int[]{R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, 
    		
    		R.drawable.img_4, R.drawable.img_5, 
    		R.drawable.img_1, R.drawable.img_2, R.drawable.img_3};
 
    public MenuList(Context context, SlidingMenu menu) {
		
        mContext = context;
        list = (ListView)menu.findViewById(R.id.menu_list);
        
        //用于存储每个ListView的Item数据 
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ITEM_COUNTS; ++i) {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", titles[i]);
        	map.put("image", images[i]);
        	data.add(map);
        }
    
        SimpleAdapter adapter = new SimpleAdapter((Context)mContext, data, R.layout.list_item,
        		new String[]{"title", "image"}, new int[]{R.id.menu_item_title, R.id.menu_item_image});
       
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
					onClick(arg2);
			    
			}    
        });
    }
	public void onClick(int arg) {
		switch(arg) {
		//调用系统拨打电话
			case 0:
	    
            Intent intent1 = new Intent();
            intent1.setAction(Intent.ACTION_DIAL);
            mContext.startActivity(intent1);
            //intent1.setData(Uri.parse("tel:  "));
	         
    		break;
	        //进入github联系我们
			case 1:
	        
	                Intent intent2 = new Intent();
	                intent2.setAction(Intent.ACTION_VIEW);
	                intent2.setData(Uri.parse("https://github.com/A207-03"));
	                mContext.startActivity(intent2);
	                break;
	        //打开相机
    		case 2:
 
	                Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                mContext.startActivity(intent3);
	                break;
	        //打开编辑短信
    		case 3:
	   
	                Intent intent4 = new Intent(Intent.ACTION_VIEW);
	                intent4.setType("vnd.android-dir/mms-sms");
	                mContext.startActivity(intent4);
	                break;
	        //打开系统设置
    		case 4:

	                Intent intent = new Intent();
	                intent.setClassName("com.android.settings", "com.android.settings.Settings");
	                mContext.startActivity(intent);
	                break;
	        //返回桌面
    		case 5:
	       
	                Intent intent6 = new Intent(Intent.ACTION_MAIN);
	                intent6.addCategory(Intent.CATEGORY_HOME);
	                mContext.startActivity(intent6);
	                break;
	        //分享到系统其他的app
    		case 6:

	                Intent intent7 = new Intent();
	                intent7.setAction(Intent.ACTION_SEND);
	                intent7.putExtra(Intent.EXTRA_TEXT, "TEXT");
	                intent7.setType("text/plain");
	                mContext.startActivity(Intent.createChooser(intent7, "Chosing a platform"));
	                break;
	        //点击更换头像
    		case 7:

	                Intent intent8  = new Intent();
	                intent8.setType("image/*");
	                intent8.setAction(Intent.ACTION_GET_CONTENT);
	                ((Activity) mContext).startActivityForResult(intent8,IMAGE_CODE);
        
		}
	}
}



