package com.topobras.topobras;

import java.text.DecimalFormat;
import logica.CCircular;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CurvaCircular extends ActionBarActivity {
	
	
	private String [] parametro = {"Radio y Alfa", "Radio y Tangente",
			                       "Radio y Distancia al vértice", "Radio y Cuerda", 
			                       "Radio y Flecha", "Alfa y Tangente", "Alfa y Distancia al vértice", 
			                       "Alfa y Cuerda", "Alfa y Flecha"};
	
	Spinner s;
	EditText et_p1;
	EditText et_p2;
	
	TextView resultados, p1, p2;
	double param1, param2;
	
	int seleccionado;
	
	CCircular c;
	Button b;
	
		
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
		
		setContentView(R.layout.activity_curva_circular);
		
		// Cargamos EditText y TextView
		
		et_p1 = (EditText)findViewById(R.id.et_p1);
		et_p2 = (EditText)findViewById(R.id.et_p2);
		et_p1.setWidth(width);
		et_p2.setWidth(width);
//		et_p1.setBackgroundColor(Color.WHITE);
//		et_p2.setBackgroundColor(Color.WHITE);
		
		
		p1 = (TextView)findViewById(R.id.tvparam1);
		p2 = (TextView)findViewById(R.id.tvparam2);
		
		resultados = (TextView)findViewById(R.id.tvres);
		
		b = (Button)findViewById(R.id.blimp);
		
		s = (Spinner)findViewById(R.id.sp_param);
		s.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parametro));
		s.setOnItemSelectedListener(listenerItem); 
		
		b.setOnClickListener(listener);
		et_p1.addTextChangedListener(t);
		et_p2.addTextChangedListener(t);
		
		c = new CCircular();
		
		
	} // onCreate
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.curva_circular, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.it_cuflech) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(CurvaCircular.this, android.R.style.Theme_DeviceDefault_Wallpaper);
			builder.setTitle("Cuerda y Flecha");
			
			LayoutInflater inflater =  getLayoutInflater();
			View customDialog = inflater.inflate(R.layout.custom, null);
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
		
		else if (id == R.id.it_tandis) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(CurvaCircular.this, android.R.style.Theme_DeviceDefault_Wallpaper);
			builder.setTitle("Tangente y Distancia al vértice");
			
			LayoutInflater inflater =  getLayoutInflater();
			View customDialog = inflater.inflate(R.layout.custom2, null);
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
	
	
	
	
	
	
	OnClickListener listener = new OnClickListener() 
	{
        public void onClick(View v) 
        {
        	et_p1.setText("");
        	et_p2.setText("");
        	resultados.setText("");
        	
        }
    };
	
	
    
    
    TextWatcher t = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) 
		{
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			printRes();
		}
    };  // EDIT TEXT LISTENER
    
    
	OnItemSelectedListener listenerItem = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			
			switch (position)
			{
			
			case 0 : p1.setText("Radio"); p2.setText("Alfa ");seleccionado = 0;break;
			case 1 : p1.setText("Radio"); p2.setText("Tangente ");seleccionado = 1;break;
			case 2 : p1.setText("Radio"); p2.setText("Distancia al vértice ");seleccionado = 2;break;
			case 3 : p1.setText("Radio"); p2.setText("Cuerda ");seleccionado = 3;break;
			case 4 : p1.setText("Radio"); p2.setText("Flecha ");seleccionado = 4;break;
			case 5 : p1.setText("Alfa"); p2.setText("Tangente ");seleccionado = 5;break;
			case 6 : p1.setText("Alfa"); p2.setText("Distancia al vértice ");seleccionado = 6;break;
			case 7 : p1.setText("Alfa"); p2.setText("Cuerda ");seleccionado = 7;break;
			case 8 : p1.setText("Alfa"); p2.setText("Flecha ");seleccionado = 8;break;
			
			
			}
			
			printRes();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}};  // SPINNER LISTENER
    
    
     private void printRes()
     {

			// TODO Auto-generated method stub
			if (!et_p1.getText().toString().equals("") && !et_p2.getText().toString().equals(""))
				{
				
				if (seleccionado == 0)
				{
					c.CCircularRAlfa(param1, param2);
				}
				
				if (seleccionado == 1)
				{
					c.CCircularRTan(param1, param2);
				}
				
				if (seleccionado == 2)
				{
					c.CCircularRDist(param1, param2);
				}
				
				if (seleccionado == 3)
				{
					c.CCircularRCU(param1, param2);
				}
				
				if (seleccionado == 4)
				{
					c.CCircularRFlech(param1, param2);
				}
				
				if (seleccionado == 5)
				{
					c.CCircularAlfaTan(param1, param2);
				}
				
				if (seleccionado == 6)
				{
					c.CCircularAlfaDist(param1, param2);
				}
				
				if (seleccionado == 7)
				{
					c.CCircularAlfaCU(param1, param2);
				}
				
				if (seleccionado == 8)
				{
					c.CCircularAlfaFlech(param1, param2);
				}
				DecimalFormat numberFormat = new DecimalFormat("#.0000");
		    	
		    	resultados.setTextColor(Color.rgb(150, 50, 50));
		    	
		    	resultados.setText("TV :      " + numberFormat.format(c.getTV()) + " metros" + System.getProperty("line.separator") + 
		    			           "VB :      " + numberFormat.format(c.getVB()) + " metros" + System.getProperty("line.separator") +
		    			           "CU :      " + numberFormat.format(c.getC())  + " metros" + System.getProperty("line.separator") +
		    			           "BM :      " + numberFormat.format(c.getBM()) + " metros" + System.getProperty("line.separator") + 
		    			           "Radio:    " + numberFormat.format(c.getR()) + " metros" + System.getProperty("line.separator")  +
		    			           "Alfa :    " + numberFormat.format(c.getAlfa()) + " g" + System.getProperty("line.separator") +
		    			           "V  :      " + numberFormat.format(c.getV()) + " g" + System.getProperty("line.separator")  +
		    			           "Dist. Desarrollo: " + numberFormat.format(c.getDisDes()) + " metros" + System.getProperty("line.separator"));
				}	
		
     }
}
