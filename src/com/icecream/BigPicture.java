package com.icecream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class BigPicture extends Activity
{
	private ImageView iView;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.bigpicture);
		iView=(ImageView)findViewById(R.id.iv);
		Intent intent=getIntent();
		int i=intent.getIntExtra("picture", -1);
		iView.setBackgroundResource(i);
	}
}
