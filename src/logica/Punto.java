package logica;

public class Punto {

	// vbles de clase
	private double x,y;
	private String NOMBRE_PUNTO;
	
	public Punto (String nombrePunto,double _x, double _y)
	{
		this.NOMBRE_PUNTO = nombrePunto;
		this.x = _x;
		this.y = _y;
	}
	public Punto (String nombrePunto)
	{
		this.NOMBRE_PUNTO = nombrePunto;
	}
	
	// métodos
	
	public String getNombrePunto ()
	{
		return this.NOMBRE_PUNTO;
	}
	
	public double getX ()
	{
		return this.x;
	}
	
	public double getY ()
	{
		return this.y;
	}	
	
	public void setNombrePunto (String nuevoNombre)
	{
		this.NOMBRE_PUNTO = nuevoNombre;
	}
	
	public void setX (double nuevaX)
	{
		this.x = nuevaX;
	}
	
	public void setY (double nuevaY)
	{
		this.y = nuevaY;
	}	
}
