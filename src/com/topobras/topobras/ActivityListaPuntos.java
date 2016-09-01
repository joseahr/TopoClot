package com.topobras.topobras;

import com.topobras.topobras.R.layout;

import logica.DatabaseManager;
import logica.GPSManager;
import logica.Punto;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListaPuntos extends ActionBarActivity {
	
	
	// Instancia de la base de datos
	private DatabaseManager manager;
	private Cursor cursor;
	private ListView listView;
	SimpleCursorAdapter adaptador;
	public Punto ptoBorrar;
	public Punto ptoEditar;

	SearchView buscador;
	
	EditText et_edit_nombre_pto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_lista_puntos);
		
		buscador = (SearchView)findViewById(R.id.searchView1);
		buscador.setQueryHint("Escribe para buscar...");
		buscador.setOnQueryTextListener(listenerTextoBuscar);
		
		manager = new DatabaseManager(this);
		
		cursor = manager.DevolverCursorPuntos();
		
		listView = (ListView)findViewById(R.id.lv_listaPuntos);
		
		@SuppressWarnings("static-access")
		String [] from = new String[]{manager.CAMPO_NOMBRE_PUNTO, manager.CAMPO_PUNTO_X, manager.CAMPO_PUNTO_Y};
		int [] to = new int[]{R.id.tv_nombre_punto, R.id.tv_X, R.id.tv_Y};
		
		adaptador = new SimpleCursorAdapter(this, layout.item_punto_view, cursor, from, to,0);
		listView.setAdapter(adaptador);
		
		AdapterView.OnItemLongClickListener listenerBorrarPunto = new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object o = listView.getItemAtPosition(position);
				@SuppressWarnings("resource")
				final Cursor c = (Cursor)o;
//				System.out.println(c.getString(1));
				ptoBorrar = new Punto(c.getString(1), c.getDouble(2),c.getDouble(3));
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaPuntos.this, android.R.style.Theme_DeviceDefault_Light_Panel);
				builder.setTitle("¿Desea eliminar este Punto?");
				LayoutInflater inflater =  getLayoutInflater();
				View customDialog = inflater.inflate(R.layout.item_punto_view_borrar, null);

				TextView tvnom = (TextView)customDialog.findViewById(R.id.tv_nombre_punto_borrar);
				TextView tvX = (TextView)customDialog.findViewById(R.id.tv_X_borrar);
				TextView tvY = (TextView)customDialog.findViewById(R.id.tv_Y_borrar);
				
				tvnom.setText(c.getString(1));
				tvX.setText(c.getString(2));
				tvY.setText(c.getString(3));
				
				builder.setView(customDialog);
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						manager.eliminarSeleccionado(ptoBorrar);
						
						refrescarListView();
						
						Toast.makeText(ActivityListaPuntos.this, "¡ '" + ptoBorrar.getNombrePunto() + "' se ha eliminado correctamente !", 1000).show();
						
					}
				});
				builder.setNegativeButton("Cancelar", null);
				builder.setNeutralButton("Editar",  new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						// Aparecerá otro Dialog para Editar el Punto
						
						AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityListaPuntos.this, android.R.style.Theme_DeviceDefault_Light_Panel);
						builder1.setTitle("Editar Punto");
						
						LayoutInflater inflater =  getLayoutInflater();
						View customDialog1 = inflater.inflate(R.layout.custom_editar, null);
						builder1.setView(customDialog1);
						
						final EditText et_edit_nombre_pto = (EditText)customDialog1.findViewById(R.id.et_edit_nombre);
						final EditText et_edit_X = (EditText)customDialog1.findViewById(R.id.et_X_edit);
						final EditText et_edit_Y = (EditText)customDialog1.findViewById(R.id.et_Y_edit);
						Button botonGPS = (Button)customDialog1.findViewById(R.id.gpsEditPunto);
						
						//listerner GPS
						
						OnClickListener listenerGPS = new OnClickListener ()
						{

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								GPSManager gps = new GPSManager (ActivityListaPuntos.this);
								
								et_edit_X.setText(String.valueOf(gps.getLocation().getLatitude()));
								et_edit_Y.setText(String.valueOf(gps.getLocation().getLongitude()));
								
								gps.stopUsingGPS();
							}
							
						};
						
						
						//
						
						botonGPS.setOnClickListener(listenerGPS);
						
						
						et_edit_nombre_pto.setText(c.getString(1));
						et_edit_X.setText(c.getString(2));
						et_edit_Y.setText(c.getString(3));
						
						ptoEditar = new Punto(c.getString(1), c.getDouble(2),c.getDouble(3));
						
						builder1.setNegativeButton("Cancelar", null);
						builder1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								String nom = ptoEditar.getNombrePunto();
								double stX = ptoEditar.getX();
								double stY = ptoEditar.getY();
								
								
								if (!et_edit_X.getText().toString().equals(stX))
								{
									Cursor c = manager.DevolverCursorSeleccionNombre(et_edit_X.getText().toString());
									if (c.moveToFirst()==true)
									{
										Toast.makeText(ActivityListaPuntos.this, "Punto no guardado. Ya tienes un punto llamado así! Ponle otro nombre!", 1000).show();
									}
									else
									{
										manager.modificarX(ptoEditar,  Double.valueOf(et_edit_X.getText().toString()));
									}
								}
								if (!et_edit_Y.getText().toString().equals(stY))
								{
									manager.modificarY(ptoEditar,  Double.valueOf(et_edit_Y.getText().toString()));
								}
								if (!et_edit_nombre_pto.getText().toString().equals(nom))
								{
									manager.modificarNombre(ptoEditar, et_edit_nombre_pto.getText().toString());
								}
								
								refrescarListView();
								
							}
							
						});
						
						AlertDialog dialog1 = builder1.create();
						dialog1.show();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
		};
		
		AdapterView.OnItemClickListener listenerCargarPunto = new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object o = listView.getItemAtPosition(position);
				@SuppressWarnings("resource")
				Cursor c = (Cursor)o;
				String valor;
//				System.out.println(c.getString(1));
				
				Bundle extras = getIntent().getExtras();
			    valor = extras.getString("TIPO");
				
				if(valor.equals("Pe"))
				{

					if (extras.getString("ACTIVITY").equals("Radiar"))
					{
						Intent i = new Intent(ActivityListaPuntos.this, Radiar.class);
						i.putExtra("TIPO", "Pe");
						i.putExtra("XE", c.getString(2));
						i.putExtra("YE", c.getString(3));
						startActivity(i);
					}
					else 
					{
						Intent i = new Intent(ActivityListaPuntos.this, DatosCampo.class);
						i.putExtra("TIPO", "Pe");
						i.putExtra("XE", c.getString(2));
						i.putExtra("YE", c.getString(3));
						if (extras.getBoolean("ESCRITO_FIN") == true)
						{
							i.putExtra("ESCRITO_FIN", true);
							i.putExtra("XF_YA_ESCRITA", extras.getString("XF_YA_ESCRITA").toString());
							i.putExtra("YF_YA_ESCRITA", extras.getString("YF_YA_ESCRITA").toString());
							
						}
						startActivity(i);
					}

					
				}
				else 
				{
					Intent i = new Intent(ActivityListaPuntos.this, DatosCampo.class);
					i.putExtra("TIPO", "Pf");
					i.putExtra("XF", c.getString(2));
					i.putExtra("YF", c.getString(3));
					if (extras.getBoolean("ESCRITO_EST") == true)
					{
						i.putExtra("ESCRITO_EST", true);
						i.putExtra("XE_YA_ESCRITA", extras.getString("XE_YA_ESCRITA").toString());
						i.putExtra("YE_YA_ESCRITA", extras.getString("YE_YA_ESCRITA").toString());
						
					}
					startActivity(i);	
				}
				
				
			}
		};
		listView.setOnItemLongClickListener(listenerBorrarPunto);
		listView.setOnItemClickListener(listenerCargarPunto);
		}//FIN ONCREATE
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.datos_campo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Button btGPS;
		if (id == R.id.nuevo_punto) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaPuntos.this, android.R.style.Theme_DeviceDefault_Light_Panel);
			builder.setTitle("Nuevo Punto");
			
			LayoutInflater inflater =  getLayoutInflater();
			final View customDialog2 = inflater.inflate(R.layout.custom_editar, null);
			builder.setView(customDialog2);
			btGPS = (Button)customDialog2.findViewById(R.id.gpsEditPunto);
			final EditText et_edit_nombre_pto = (EditText)customDialog2.findViewById(R.id.et_edit_nombre);
			final EditText et_edit_X = (EditText)customDialog2.findViewById(R.id.et_X_edit);
			final EditText et_edit_Y = (EditText)customDialog2.findViewById(R.id.et_Y_edit);
			
			//listerner GPS
			
			OnClickListener listenerGPS = new OnClickListener ()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					GPSManager gps = new GPSManager (ActivityListaPuntos.this);
					
					et_edit_X.setText(String.valueOf(gps.getLocation().getLatitude()));
					et_edit_Y.setText(String.valueOf(gps.getLocation().getLongitude()));
					
					gps.stopUsingGPS();
					
				}
				
			};
			
			
			//
			
			btGPS.setOnClickListener(listenerGPS);
			builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {

					Cursor cursor = manager.DevolverCursorSeleccionNombre(et_edit_nombre_pto.getText().toString());
					if (et_edit_nombre_pto.getText().toString().length() == 0)
					{
						Toast.makeText(ActivityListaPuntos.this, "Punto no guardado. Debe introducir un nombre", Toast.LENGTH_LONG).show();
					}
					else if(et_edit_X.getText().toString().length() == 0)
					{
						Toast.makeText(ActivityListaPuntos.this, "Punto no guardado. Debe introducir un valor para la coordenada X", Toast.LENGTH_LONG).show();
					}
					else if(et_edit_Y.getText().toString().length() == 0)
					{
						Toast.makeText(ActivityListaPuntos.this, "Punto no guardado. Debe introducir un valor para la coordenada Y", Toast.LENGTH_LONG).show();
					}
					else if(cursor.moveToFirst() == true)
					{
						Toast.makeText(ActivityListaPuntos.this, "Punto no guardado. Ya tienes un punto llamado así! Ponle otro nombre!", Toast.LENGTH_LONG).show();
					}
					else 
					{
						manager.insertar(new Punto(et_edit_nombre_pto.getText().toString(),
								                   Double.valueOf(et_edit_X.getText().toString()),
								                   Double.valueOf(et_edit_Y.getText().toString())));
						refrescarListView();
						dialog.dismiss();
						Toast.makeText(ActivityListaPuntos.this,"¡ '" + et_edit_nombre_pto.getText().toString() + "' se ha guardado correctamente !", Toast.LENGTH_LONG).show();
					}
					
				}
			});
			
			AlertDialog dialog = builder.create();
			dialog.show();
			
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	SearchView.OnQueryTextListener listenerTextoBuscar = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextChange(String arg0) {
			// TODO Auto-generated method stub
			cursor = manager.obtenerPuntosQueEmpiecenPor(arg0);
			adaptador.changeCursor(cursor);
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		

	};
	
	private void refrescarListView ()
	{
		// Refrescar el listView
		
		listView = (ListView)findViewById(R.id.lv_listaPuntos);
		cursor = manager.DevolverCursorPuntos();
		adaptador.changeCursor(cursor);
		
		//
	}
	protected void onDestroy()
	{
		super.onDestroy();
		cursor.close();
	}
	
}
