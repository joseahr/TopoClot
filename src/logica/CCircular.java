package logica;

public class CCircular 
{

	private double TV; // TANGENTE
	private double VB; 
	private double C; // CUERDA
	private double BM;

	private double R;
	private double Alfa;
	
	public CCircular() // El Alfa se introduce en grad cente
	{
		
	}
	
	public void CCircularRAlfa(double RADIO, double alfa)
	{
		this.R = RADIO;
		this.Alfa = alfa*Math.PI/200; //Ponemos el Alfa en radianes
		
		this.TV = this.R * Math.tan(this.Alfa/2);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.C = 2*R*Math.sin(this.Alfa/2);
		this.BM = R - (R*Math.cos(this.Alfa/2));
	}
	
	public void CCircularRTan (double Radio, double Tan)
	{
		this.R = Radio;
		this.TV = Tan;
		
		this.Alfa = 2*Math.atan2(this.TV, this.R);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.C = 2*R*Math.sin(this.Alfa/2);
		this.BM = R - (R*Math.cos(this.Alfa/2));
		
	}
	
	public void CCircularRDist (double Radio, double Dist)
	{
		this.R = Radio;
		this.VB = Dist;
		
		this.Alfa = 2*Math.acos(this.R/(this.R+this.VB));
		this.C = 2*R*Math.sin(this.Alfa/2);
		this.BM = R - (R*Math.cos(this.Alfa/2));
		this.TV = this.R * Math.tan(this.Alfa/2);
		
	}
	
	public void CCircularRCU (double Radio, double Cuerda)
	{
		this.R = Radio;
		this.C = Cuerda;
		
		this.Alfa = 2*Math.asin(this.C/(2*this.R));
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.BM = R - (R*Math.cos(this.Alfa/2));
		this.TV = this.R * Math.tan(this.Alfa/2);
		
	}
	
	public void CCircularRFlech (double Radio, double Flecha)
	{
		this.R = Radio;
		this.BM = Flecha;
		
		this.Alfa = 2*Math.acos((this.R - this.BM)/this.R);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.TV = this.R * Math.tan(this.Alfa/2);
		this.C = 2*R*Math.sin(this.Alfa/2);
		
	}
	
	public void CCircularAlfaTan (double alfa, double Tan)
	{
		this.Alfa = alfa*Math.PI/200;
		this.TV = Tan;
		
		this.R = this.TV/(Math.tan(this.Alfa/2));
		this.C = 2*R*Math.sin(this.Alfa/2);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.BM = R - (R*Math.cos(this.Alfa/2));
		
	}
	
	
	public void CCircularAlfaDist (double alfa, double Dist)
	{
		this.Alfa = alfa*Math.PI/200;
		this.VB = Dist;
		
		this.R = (this.VB*Math.cos(this.Alfa/2))/(1 - Math.cos(this.Alfa/2));
		this.TV = this.R * Math.tan(this.Alfa/2);
		this.C = 2*R*Math.sin(this.Alfa/2);
		this.BM = R - (R*Math.cos(this.Alfa/2));
		
	}
	
	public void CCircularAlfaCU (double alfa, double Cuerda)
	{
		this.Alfa = alfa*Math.PI/200;
		this.C = Cuerda;
		
		this.R = this.C/(2*Math.sin(this.Alfa/2));
		this.TV = this.R * Math.tan(this.Alfa/2);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.BM = R - (R*Math.cos(this.Alfa/2));
		
	}
	
	public void CCircularAlfaFlech (double alfa, double Flecha)
	{
		this.Alfa = alfa*Math.PI/200;
		this.BM = Flecha;
		
		this.R = this.BM/(1 - Math.cos(this.Alfa/2));
		this.TV = this.R * Math.tan(this.Alfa/2);
		this.VB = (this.R/Math.cos(this.Alfa/2)) - R;
		this.C = 2*R*Math.sin(this.Alfa/2);
		
	}
	
	
	public double getTV()
	{
		return this.TV;		
	}
	
	public double getVB()
	{
		return this.VB;		
	}
	
	public double getC()
	{
		return this.C;		
	}
	
	public double getBM()
	{
		return this.BM;		
	}
	
	
	
	public double getR()
	{
		return this.R;		
	}
	
	public double getAlfa()
	{
		return this.Alfa*200/Math.PI;		
	}
	
	public double getDisDes()
	{
		return this.R*this.Alfa;
	}
	
	public double getV()
	{
		return 200 - this.Alfa*200/Math.PI;
	}
	
}