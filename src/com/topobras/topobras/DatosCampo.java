package com.topobras.topobras;

import java.text.DecimalFormat;

import logica.DatabaseManager;
import logica.DatosDeCampo;
import logica.GPSManager;
import logica.Punto;
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

public class DatosCampo extends ActionBarActivity {
	

	EditText xe, ye, xf, yf;
	TextView resultados;
	
	private double Xe, Ye, Xf, Yf;
	
	Button blimp;

	DatosDeCampo dc;
	private DatabaseManager manager;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		
		setContentView(R.layout.activity_datos_campo);
		
		manager = new DatabaseManager(this);
		
		Button cargarPe = (Button)findViewById(R.id.bt_cargar_pe);
		Button borrarPe = (Button)findViewById(R.id.bt_borrar_pe);
		Button guardarPe = (Button)findViewById(R.id.bt_guardar_pe);
		
		Button cargarPf = (Button)findViewById(R.id.bt_cargar_pf);
		Button borrarPf = (Button)findViewById(R.id.bt_borrar_pf);
		Button guardarPf = (Button)findViewById(R.id.bt_guardar_pf);
		
		
		Button gpsPe = (Button)findViewById(R.id.gpsPe);
		Button gpsPf = (Button)findViewById(R.id.gpsPf);
		
		
		xe = (EditText)findViewById(R.id.et_xe);
		ye = (EditText)findViewById(R.id.et_ye);
		xf = (EditText)findViewById(R.id.et_xf);
		yf = (EditText)findViewById(R.id.et_yf);
		
		xe.setWidth(width);
		ye.setWidth(width);
		xf.setWidth(width);
		yf.setWidth(width);
//		xe.setBackgroundColor(Color.WHITE);
//		ye.setBackgroundColor(Color.WHITE);
//		xf.setBackgroundColor(Color.WHITE);
//		yf.setBackgroundColor(Color.WHITE);
		
		xe.addTextChangedListener(t);
		ye.addTextChangedListener(t);
		xf.addTextChangedListener(t);
		yf.addTextChangedListener(t);
		
		resultados = (TextView)findViewById(R.id.tvres);
		
		blimp = (Button)findViewById(R.id.blimp);
		
		blimp.setOnClickListener(listenerLimp);
		
		cargarPe.setOnClickListener(listenerCargarPe);
		guardarPe.setOnClickListener(listenerGuardarPe);
		borrarPe.setOnClickListener(listenerBorrarPe);
		
		cargarPf.setOnClickListener(listenerCargarPf);
		guardarPf.setOnClickListener(listenerGuardarPf);
		borrarPf.setOnClickListener(listenerBorrarPf);
		
		gpsPe.setOnClickListener(listenerGpsPe);
		gpsPf.setOnClickListener(listenerGpsPf);
		
		// Comprobar si nos han pasado un punto para cargar y en ese caso cargarlo donde toca.
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String xe_extra = extras.getString("XE");
		    String ye_extra = extras.getString("YE");
		    String xf_extra = extras.getString("XF");
		    String yf_extra = extras.getString("YF");
		    
		    String xe_extra_ya_escrita = extras.getString("XE_YA_ESCRITA");
		    String ye_extra_ya_escrita = extras.getString("YE_YA_ESCRITA");
		    String xf_extra_ya_escrita = extras.getString("XF_YA_ESCRITA");
		    String yf_extra_ya_escrita = extras.getString("YF_YA_ESCRITA");
		    // PE
		    if(extras.getString("TIPO").equals("Pe")) 
		    {
		    	xe.setText(xe_extra);
		    	ye.setText(ye_extra);
		    	
		    	if (extras.getBoolean("ESCRITO_FIN") == true)
		    	{
		    		xf.setText(xf_extra_ya_escrita);
		    		yf.setText(yf_extra_ya_escrita);
		    	}
		    	
		    }
		    // PF
		    else 
		    {
		    	xf.setText(xf_extra);
		    	yf.setText(yf_extra);
		    	
		    	if (extras.getBoolean("ESCRITO_EST") == true)
		    	{
		    		xe.setText(xe_extra_ya_escrita);
		    		ye.setText(ye_extra_ya_escrita);
		    	}
		    }
		} // 
		
	}

	TextWatcher t = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) 
		{
					
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) 
		{
					
			
		}

		@Override
		public void afterTextChanged(Editable s) 
		{
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
			
			if(!xf.getText().toString().equals(""))
			{
				try
				{
					Xf = Double.parseDouble(xf.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
			if(!yf.getText().toString().equals(""))
			{
				try
				{
					Yf = Double.parseDouble(yf.getText().toString());
				}
				catch(NumberFormatException e)
				{
				  //not a double
				}
			}
			
			printRes();
		}
	};  // EDIT TEXT LIST.
	
	OnClickListener listenerLimp = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			xe.setText("");
			ye.setText("");
			xf.setText("");
			yf.setText("");
			resultados.setText("");
			
		}
	};
	
	OnClickListener listenerCargarPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i = new Intent (DatosCampo.this, ActivityListaPuntos.class);
			if (!xf.getText().toString().equals("") || !yf.getText().toString().equals(""))
			{
				i.putExtra("ESCRITO_FIN", true);
				i.putExtra("XF_YA_ESCRITA", xf.getText().toString());
				i.putExtra("YF_YA_ESCRITA", yf.getText().toString());
			}
			else 
			{
				i.putExtra("ESCRITO_FIN", false);
			}
			i.putExtra("ACTIVITY", "DC");
			i.putExtra("TIPO", "Pe");
			startActivity(i);
			
		}
	};
	
	OnClickListener listenerCargarPf = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent (DatosCampo.this, ActivityListaPuntos.class);
			i.putExtra("TIPO", "Pf");
			if (!xe.getText().toString().equals("") || !ye.getText().toString().equals(""))
			{
				i.putExtra("ESCRITO_EST", true);
				i.putExtra("XE_YA_ESCRITA", xe.getText().toString());
				i.putExtra("YE_YA_ESCRITA", ye.getText().toString());
			}
			else 
			{
				i.putExtra("ESCRITO_EST", false);
			}
			startActivity(i);
			
		}
	};
	
	OnClickListener listenerGuardarPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (xe.getText().toString().equals("") || ye.getText().toString().equals(""))
			{
				Toast.makeText(DatosCampo.this, "Introduce las coordenadas", 1000).show();
			}
			else 
			{
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DatosCampo.this, android.R.style.Theme_Holo_Light_Dialog);
				builder.setTitle("Nombre del punto");
				
				LayoutInflater inflater =  getLayoutInflater();
				View customDialog = inflater.inflate(R.layout.custom_dialog_nombre_punto, null);
				builder.setView(customDialog);
				builder.setNegativeButton("Cancelar", null);
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Dialog f = (Dialog) dialog;
						EditText et_nombre_pto = (EditText)f.findViewById(R.id.et_nombre_pto);
						Cursor cursor = manager.DevolverCursorSeleccionNombre(et_nombre_pto.getText().toString());
//						String nombreCoincide = cursor.getString(1);
//						System.out.println(cursor.moveToFirst());
						if (et_nombre_pto.getText().toString().equals(""))
						{
							Toast.makeText(DatosCampo.this, "Punto no guardado.Debe introducir un nombre", 1000).show();
						}
						else if(cursor.moveToFirst() == true)
						{
							Toast.makeText(DatosCampo.this, "Punto no guardado. Ya tienes un punto llamado así! Ponle otro nombre!", 1000).show();
						}
						else 
						{
							Punto p = new Punto(et_nombre_pto.getText().toString(), Double.valueOf(xe.getText().toString()),Double.valueOf(ye.getText().toString()) );
							manager.insertar(p);
							dialog.dismiss();
							Toast.makeText(DatosCampo.this,"¡ '" + et_nombre_pto.getText().toString() + "' se ha guardado correctamente !", 1000).show();
						}
						
					}
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			
			
		}
	};
	
	OnClickListener listenerGuardarPf = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (xf.getText().toString().equals("") || yf.getText().toString().equals(""))
			{
				Toast.makeText(DatosCampo.this, "Introduce las coordenadas", 1000).show();
			}
			else 
			{
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DatosCampo.this, android.R.style.Theme_Holo_Light_Dialog);
				builder.setTitle("Nombre del punto");
				
				LayoutInflater inflater =  getLayoutInflater();
				View customDialog = inflater.inflate(R.layout.custom_dialog_nombre_punto, null);
				builder.setView(customDialog);
				builder.setNegativeButton("Cancelar", null);
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Dialog f = (Dialog) dialog;
						EditText et_nombre_pto = (EditText)f.findViewById(R.id.et_nombre_pto);
						Cursor cursor = manager.DevolverCursorSeleccionNombre(et_nombre_pto.getText().toString());
//						String nombreCoincide = cursor.getString(1);
//						System.out.println(cursor.moveToFirst());
						if (et_nombre_pto.getText().toString().equals(""))
						{
							Toast.makeText(DatosCampo.this, "Punto no guardado.Debe introducir un nombre", 1000).show();
						}
						else if(cursor.moveToFirst() == true)
						{
							Toast.makeText(DatosCampo.this, "Punto no guardado. Ya tienes un punto llamado así! Ponle otro nombre!", 1000).show();
						}
						else 
						{
							Punto p = new Punto(et_nombre_pto.getText().toString(), Double.valueOf(xf.getText().toString()),Double.valueOf(yf.getText().toString()) );
							manager.insertar(p);
							dialog.dismiss();
							Toast.makeText(DatosCampo.this,"¡ '" + et_nombre_pto.getText().toString() + "' se ha guardado correctamente !", 1000).show();
						}
						
					}
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			
		}
	};
	
	OnClickListener listenerBorrarPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			xe.setText("");
			ye.setText("");
			
		}
	};
	
	OnClickListener listenerBorrarPf = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			xf.setText("");
			yf.setText("");

			
		}
	};
	
	public void onBackPressed() 
	{
		super.onBackPressed();
		Intent i = new Intent(DatosCampo.this, MainActivity.class);
		startActivity(i);
		
	};
	
	private void printRes()
	{
		if (!xe.getText().toString().equals("") && !ye.getText().toString().equals("") &&
		    !xf.getText().toString().equals("") && !yf.getText().toString().equals(""))
		{
		    DecimalFormat numberFormat = new DecimalFormat("#.0000");
        	
		    dc = new DatosDeCampo(Xe, Ye, Xf, Yf);
		    
         	resultados.setTextColor(Color.rgb(150, 50, 50));
		    	
		    resultados.setText("AX  :       " + numberFormat.format(dc.getAX()) + " metros" + System.getProperty("line.separator") + 
		    		           "AY  :       " + numberFormat.format(dc.getAY()) + " metros" + System.getProperty("line.separator") +
		    		           "AZI :       " + numberFormat.format(dc.getAci())  + " g" + System.getProperty("line.separator") +
		    		           "DIS :       " + numberFormat.format(dc.getDis()) + " metros" + System.getProperty("line.separator"));
		}
	}
	
	
	
	OnClickListener listenerGpsPe = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			GPSManager gps = new GPSManager(DatosCampo.this);
			
			xe.setText(String.valueOf(gps.getLocation().getLatitude()));
			ye.setText(String.valueOf(gps.getLocation().getLongitude()));
			
		}
		
	};
	
	OnClickListener listenerGpsPf = new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			GPSManager gps = new GPSManager(DatosCampo.this);
			
			xf.setText(String.valueOf(gps.getLocation().getLatitude()));
			yf.setText(String.valueOf(gps.getLocation().getLongitude()));
			
		}
		
	};
	
	
	
}
