package com.topobras.topobras;

import java.text.DecimalFormat;

import logica.DatabaseManager;
import logica.Punto;
import logica.Radia;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Radiar extends ActionBarActivity {
	
	EditText xe, ye, az, dis;

	TextView resultados;
	
	Button blimp;
	
	double Xe, Ye, Az, Dis;
	
	Radia r;
	
	DatabaseManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		
		setContentView(R.layout.activity_radiar);
		
		
		manager = new DatabaseManager(this);
		
		xe = (EditText) findViewById(R.id.et_xe);
		ye = (EditText) findViewById(R.id.et_ye);
		az = (EditText) findViewById(R.id.et_az);
		dis = (EditText) findViewById(R.id.et_d);
		
		xe.setWidth(width);
		ye.setWidth(width);
		az.setWidth(width);
		dis.setWidth(width);
//		xe.setBackgroundColor(Color.WHITE);
//		ye.setBackgroundColor(Color.WHITE);
//		az.setBackgroundColor(Color.WHITE);
//		dis.setBackgroundColor(Color.WHITE);
		
		resultados = (TextView) findViewById(R.id.tvres);
		
		blimp = (Button)findViewById(R.id.blimp);
		
		blimp.setOnClickListener(listener);
		
	    xe.addTextChangedListener(t); 
	    ye.addTextChangedListener(t);
	    az.addTextChangedListener(t);
	    dis.addTextChangedListener(t);
	    
		Button cargarPe = (Button)findViewById(R.id.bCargPe);
		Button guardarPe = (Button)findViewById(R.id.bGuardarPe);
	    
		cargarPe.setOnClickListener(listenerCargarPe);
		guardarPe.setOnClickListener(listenerGuardarPe);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String xe_extra = extras.getString("XE");
		    String ye_extra = extras.getString("YE");
		    // PE
		    if(extras.getString("TIPO").equals("Pe")) 
		    {
		    	xe.setText(xe_extra);
		    	ye.setText(ye_extra);
		    }
		}
	}
	
	
	OnClickListener listenerCargarPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i = new Intent (Radiar.this, ActivityListaPuntos.class);
			i.putExtra("TIPO", "Pe");
			i.putExtra("ACTIVITY", "Radiar");
			startActivity(i);
			
		}
	};
	
	OnClickListener listenerGuardarPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (xe.getText().toString().equals("") || ye.getText().toString().equals(""))
			{
				Toast.makeText(Radiar.this, "Introduce las coordenadas", 1000).show();
			}
			else 
			{
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Radiar.this, android.R.style.Theme_Holo_Light_Dialog);
				builder.setTitle("Nombre del punto");
				
				LayoutInflater inflater =  getLayoutInflater();
				View customDialog = inflater.inflate(R.layout.custom_dialog_nombre_punto, null);
				builder.setView(customDialog);
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Dialog f = (Dialog) dialog;
						EditText et_nombre_pto = (EditText)f.findViewById(R.id.et_nombre_pto);
						Cursor cursor = manager.DevolverCursorSeleccionNombre(et_nombre_pto.getText().toString());
						if (et_nombre_pto.getText().toString().equals(""))
						{
							Toast.makeText(Radiar.this, "Punto no guardado.Debe introducir un nombre", 1000).show();
						}
						else if(cursor.moveToFirst() == true)
						{
							Toast.makeText(Radiar.this, "Punto no guardado. Ya tienes un punto llamado así! Ponle otro nombre!", 1000).show();
						}
						else 
						{
							Punto p = new Punto(et_nombre_pto.getText().toString(), Double.valueOf(xe.getText().toString()),Double.valueOf(ye.getText().toString()) );
							manager.insertar(p);
							dialog.dismiss();
							Toast.makeText(Radiar.this,"¡ '" + et_nombre_pto.getText().toString() + "' se ha guardado correctamente !", 1000).show();
						}
						
					}
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			
			
		}
	};

	OnClickListener listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			xe.setText("");
			ye.setText("");
			az.setText("");
			dis.setText("");
			resultados.setText("");
			
		}};
	
	
	
    TextWatcher t = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
			if(!xe.getText().toString().equals(""))
			{
				try
				{
					Xe = Double.parseDouble(xe.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
			
			if(!ye.getText().toString().equals(""))
			{
				try
				{
					Ye = Double.parseDouble(ye.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
			
			if(!az.getText().toString().equals(""))
			{
				try
				{
					Az = Double.parseDouble(az.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
			
			if(!dis.getText().toString().equals(""))
			{
				try
				{
					Dis = Double.parseDouble(dis.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (!xe.getText().toString().equals("") && !ye.getText().toString().equals("") 
				&& !az.getText().toString().equals("") && !dis.getText().toString().equals(""))
				{
			
				
			    r = new Radia(Xe, Ye, Az, Dis);
			    DecimalFormat numberFormat = new DecimalFormat("#.0000");
			    resultados.setTextColor(Color.rgb(150, 50, 50));
			    
			    resultados.setText("AX :       " + numberFormat.format(r.getAX()) + " metros" + System.getProperty("line.separator") + 
			    		           "AY :       " + numberFormat.format(r.getAY()) + " metros" + System.getProperty("line.separator") +
			    		           "XF :       " + numberFormat.format(r.getXf()) + " metros" + System.getProperty("line.separator") +
			    		           "YF :       " + numberFormat.format(r.getYf()) + " metros" + System.getProperty("line.separator"));
				}	
		}
    };
    
	public void onBackPressed() 
	{
		super.onBackPressed();
		Intent i = new Intent(Radiar.this, MainActivity.class);
		startActivity(i);
		
	};

}
