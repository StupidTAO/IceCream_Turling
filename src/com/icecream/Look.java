package com.icecream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Look extends Activity
{	
	private TextView tView;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.look);
		tView=(TextView)findViewById(R.id.tv);
		Intent intent=getIntent();
		String string=intent.getStringExtra("text");		
		tView.setText(string);		
	}
}
