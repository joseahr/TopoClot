package com.topobras.topobras;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	
	Button brad;
	Button bcc;
	Button bclot;
	Button bdcamp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		brad = (Button)findViewById(R.id.brp);
		bcc = (Button)findViewById(R.id.bcc);
		bclot = (Button)findViewById(R.id.bclot);
		bdcamp = (Button)findViewById(R.id.bdcamp);
		
		brad.setOnClickListener(listener1);
		bcc.setOnClickListener(listener2);
		bclot.setOnClickListener(listener3);
		bdcamp.setOnClickListener(listener4);
		
	}
	
	OnClickListener listener1 = new OnClickListener() 
	{
        public void onClick(View v) 
        {
        	ejecutar1(null);
        }
        
	};
	
	public void ejecutar1(View view){
		Intent i = new Intent(this,Radiar.class);
		startActivity(i);
	}
	
	OnClickListener listener2 = new OnClickListener() 
	{
        public void onClick(View v) 
        {
        	ejecutar2(null);
        }
        
	};
	
	public void ejecutar2(View view){
		Intent i = new Intent(this,CurvaCircular.class);
		startActivity(i);
	}
	
	OnClickListener listener3 = new OnClickListener() 
	{
        public void onClick(View v) 
        {
        	ejecutar3(null);
        }
        
	};
	
	public void ejecutar3(View view){
		Intent i = new Intent(this,Clotoide.class);
		startActivity(i);
	}
	
	OnClickListener listener4 = new OnClickListener() 
	{
        public void onClick(View v) 
        {
        	ejecutar4(null);
        }
        
	};
	
	public void ejecutar4(View view){
		Intent i = new Intent(this,DatosCampo.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	

}
