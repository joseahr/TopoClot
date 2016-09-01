package com.topobras.topobras;

import java.text.DecimalFormat;
import logica.Cloto;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Clotoide extends ActionBarActivity 
{

	private String [] param_cono = {"R y L", "R y Tao", "R y A", "L y Tao", "L y A", "A y Tao"};
	ArrayAdapter<String> adapter;
	Spinner s;
	
	TextView p1, p2, resultados;
	
	EditText et_p1, et_p2;
	
	Button blimp;
	
	private double param1, param2;
	private int seleccionado;
	
	Cloto c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
	    
		setContentView(R.layout.activity_clotoide);
		
		s = (Spinner)findViewById(R.id.sp_selec);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, param_cono);
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(listenerItem); // Item Selected Listener
		
		p1 = (TextView) findViewById(R.id.tvp1);
		p2 = (TextView) findViewById(R.id.tvp2);
		resultados = (TextView) findViewById(R.id.tvres);
		
		et_p1 = (EditText)findViewById(R.id.et_p1);
		et_p2 = (EditText)findViewById(R.id.et_p2);
		et_p1.setWidth(width);
		et_p2.setWidth(width);
//		et_p1.setBackgroundColor(Color.WHITE);
//		et_p2.setBackgroundColor(Color.WHITE);
		
		
		et_p1.addTextChangedListener(t);
		et_p2.addTextChangedListener(t); // Text changed listeners
		
		
		blimp = (Button)findViewById(R.id.blimp);
		blimp.setOnClickListener(listenerLimp);
		c = new Cloto();
		
	} // ONCREATE
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clotoide, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.itemcloto) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Clotoide.this, android.R.style.Theme_DeviceDefault_Wallpaper);
			builder.setTitle("Resumen Clotoide");
			
			LayoutInflater inflater =  getLayoutInflater();
			View customDialog = inflater.inflate(R.layout.customclot, null);
			builder.setView(customDialog);
			builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					dialog.dismiss();
					
				}
			});
			
			AlertDialog dialog = builder.create();
			dialog.show();
			
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	OnItemSelectedListener listenerItem = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			
			switch (position)
			{
			
			case 0 : p1.setText("Parámetro R"); p2.setText("Parámetro L"); seleccionado = 0; break;
			case 1 : p1.setText("Parámetro R"); p2.setText("Parámetro Tao"); seleccionado = 1; break;
			case 2 : p1.setText("Parámetro R"); p2.setText("Parámetro A"); seleccionado = 2; break;
			case 3 : p1.setText("Parámetro L"); p2.setText("Parámetro Tao"); seleccionado = 3; break;
			case 4 : p1.setText("Parámetro L"); p2.setText("Parámetro A"); seleccionado = 4; break;
			case 5 : p1.setText("Parámetro A"); p2.setText("Parámetro Tao"); seleccionado = 5; break;
			
			}
			
			
			printRes();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}};  // SPINNER LISTENER
		
		
	TextWatcher t = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) 
		{
					
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) 
		{
					
			if(!et_p1.getText().toString().equals(""))
			{
				try
				{
					param1 = Double.parseDouble(et_p1.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			if(!et_p2.getText().toString().equals(""))
			{
				try
				{
					param2 = Double.parseDouble(et_p2.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
		}

		@Override
		public void afterTextChanged(Editable s) 
		{
			printRes();
		}
	};  // EDIT TEXT LIST.		
	
	OnClickListener listenerLimp = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			et_p1.setText("");
			et_p2.setText("");
			resultados.setText("");
			
		}
	};
	
	private void printRes()
	{
		if (!et_p1.getText().toString().equals("") && !et_p2.getText().toString().equals(""))
		{
		    DecimalFormat numberFormat = new DecimalFormat("#.0000");
        	
		    if (seleccionado == 0)
		    {
		    	c.ClotoRL(param1, param2);
		    }
		    
		    else if (seleccionado == 1)
		    {
		    	c.ClotoRT(param1, param2);
		    }
		    
		    else if (seleccionado == 2)
		    {
		    	c.ClotoRA(param1, param2);
		    }
   	
		    else if (seleccionado == 3)
		    {
		    	c.ClotoLT(param1, param2);
		    }
		    
		    else if (seleccionado == 4)
		    {
		    	c.ClotoLA(param1, param2);
		    }
		    
		    else if (seleccionado == 5)
		    {
		    	c.ClotoAT(param1, param2);
		    }
		    
		    
         	resultados.setTextColor(Color.rgb(150, 50, 50));
		    	
		    resultados.setText("Xf :     " + numberFormat.format(c.getXf()) + " metros" + System.getProperty("line.separator") + 
		    		           "Yf :     " + numberFormat.format(c.getYf()) + " metros" + System.getProperty("line.separator") +
		    		           "Xo :     " + numberFormat.format(c.getXo())  + " metros" + System.getProperty("line.separator") +
		    		           "Yo :     " + numberFormat.format(c.getYo()) + " metros" + System.getProperty("line.separator") +
		    		           "Tl :     " + numberFormat.format(c.getTl()) + " metros" + System.getProperty("line.separator") +
		    		           "Tc :     " + numberFormat.format(c.getTc()) + " metros" + System.getProperty("line.separator") +
		    		           "AR :     " + numberFormat.format(c.getAR()) + " metros" + System.getProperty("line.separator") +
		    		           "Sl :     " + numberFormat.format(c.getSl()) + " metros" + System.getProperty("line.separator") +
		    		           "o  :     " + numberFormat.format(c.getO()) + " g" 
		    		           + System.getProperty("line.separator") 
		    		           + System.getProperty("line.separator") + 
		    		           "A  :     " + numberFormat.format(c.getA()) + " metros" + System.getProperty("line.separator") + 
		    		           "L  :     " + numberFormat.format(c.getL()) + " metros" + System.getProperty("line.separator") + 
		    		           "R  :     " + numberFormat.format(c.getR()) + " metros" + System.getProperty("line.separator") + 
		    		           "Tao  :   " + numberFormat.format(c.getTao()) + " g" + System.getProperty("line.separator") + 
		    		           "Alfa :   " + numberFormat.format(c.getA()) + " g" + System.getProperty("line.separator"));
			   
		}
	}
		
}
