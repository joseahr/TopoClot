package logica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	
	// Campos de la Base de Datos
	
	public static final String NOMBRE_TABLA = "PUNTOS";
	public static final String CAMPO_ID = "_id";
	public static final String CAMPO_NOMBRE_PUNTO = "NOMBRE";
	public static final String CAMPO_PUNTO_X = "X";
	public static final String CAMPO_PUNTO_Y = "Y";
	
	// String que usaremos como sentencia para crear la tabla
	
	public static final String CREAR_TABLA = "create table " + NOMBRE_TABLA + " ("
			+ CAMPO_ID + " integer primary key autoincrement,"
			+ CAMPO_NOMBRE_PUNTO + " text not null," 
			+ CAMPO_PUNTO_X + " text not null,"
			+ CAMPO_PUNTO_Y + " text not null);";
	
	private DatabaseHelper helper; // "Ayudante" para crear la base de datos.
	private SQLiteDatabase db; // Instancia de la base de datos
	
	//Constructor
	public DatabaseManager (Context ctx)
	{
		//Creacion de la base de Datos si no existe
		
		helper = new DatabaseHelper(ctx);
		db = helper.getWritableDatabase(); // Método que devuelve una base de datos de lectura/escritura
		
		//
		
	}
	
	// Métodos para insertar, eliminar o actualizar valores de los puntos guardados
	
	private ContentValues getContentValues (Punto p)
	{
		ContentValues valores = new ContentValues();
		valores.put(CAMPO_NOMBRE_PUNTO, p.getNombrePunto());
		valores.put(CAMPO_PUNTO_X, p.getX());
		valores.put(CAMPO_PUNTO_Y, p.getY());
		return valores;
	}
	
	public void insertar (Punto p)  // Se llama a la funcion getContentValues para añadir en la base de datos
	{
		
		db.insert(NOMBRE_TABLA, null, getContentValues(p));
	}
	
	public void insertarSQL (Punto p) // Se añade a la base de datos utilizando sentencia SQL
	{
		try {
			db.execSQL("INSERT INTO " + NOMBRE_TABLA + " VALUES (null,'" + p.getNombrePunto() + "',"+ p.getX() + "," + p.getY() +");" );
			
		}
		catch (SQLException e){
			
			e.getMessage();
		}
		
		
	}
	
	
	public void eliminar (Punto p)
	{
		db.delete(NOMBRE_TABLA, CAMPO_NOMBRE_PUNTO + "=?",new String[]{p.getNombrePunto()});
	}
	
	public void modificarNombre (Punto p, String nuevoNombre)
	{
		String nombreOriginal = p.getNombrePunto();
		p.setNombrePunto(nuevoNombre);
		db.update(NOMBRE_TABLA, getContentValues(p), CAMPO_NOMBRE_PUNTO + "=?", new String[]{nombreOriginal});
	}
	
	public void modificarX (Punto p, double nuevaX)
	{
		p.setX(nuevaX);
		db.update(NOMBRE_TABLA, getContentValues(p), CAMPO_NOMBRE_PUNTO + "=?", new String[]{p.getNombrePunto()});
	}

	public void modificarY (Punto p, double nuevaY)
	{
		p.setY(nuevaY);
		db.update(NOMBRE_TABLA, getContentValues(p), CAMPO_NOMBRE_PUNTO + "=?", new String[]{p.getNombrePunto()});
	}

	
	
	public Cursor DevolverCursorPuntos()
	{
		String [] columnas = new String []{CAMPO_ID, CAMPO_NOMBRE_PUNTO, CAMPO_PUNTO_X, CAMPO_PUNTO_Y};
		return db.query(NOMBRE_TABLA, columnas, null, null, null, null, null);
		
	}
	
	public Cursor DevolverCursorSeleccion(Punto p)
	{
		String [] columnas = new String []{CAMPO_ID, CAMPO_NOMBRE_PUNTO, CAMPO_PUNTO_X, CAMPO_PUNTO_Y};
		return db.query(NOMBRE_TABLA, columnas,CAMPO_NOMBRE_PUNTO + "=? and " + CAMPO_PUNTO_X + "=? and " + CAMPO_PUNTO_Y + "=?" ,
				new String[]{p.getNombrePunto(),String.valueOf(p.getX()),String.valueOf(p.getY())} , null, null, null);
		
	}
	
	public Cursor DevolverCursorSeleccionNombre(String nombre)
	{
		String [] columnas = new String []{CAMPO_ID, CAMPO_NOMBRE_PUNTO, CAMPO_PUNTO_X, CAMPO_PUNTO_Y};
		return db.query(NOMBRE_TABLA, columnas,CAMPO_NOMBRE_PUNTO + "=?",new String[]{nombre} , null, null, null);
		
	}
	
	public void eliminarSeleccionado (Punto p)
	{
		db.delete(NOMBRE_TABLA, CAMPO_NOMBRE_PUNTO + "=? and " + CAMPO_PUNTO_X + "=? and " + CAMPO_PUNTO_Y + "=?" ,
				new String[]{p.getNombrePunto(),String.valueOf(p.getX()),String.valueOf(p.getY())});
	}
	
	public Cursor obtenerPuntosQueEmpiecenPor(String busqueda)
	{
		return db.rawQuery("SELECT * FROM " +
				NOMBRE_TABLA + " where " +CAMPO_NOMBRE_PUNTO+ " like '"+busqueda+"%'" , null);
	}
	
}
