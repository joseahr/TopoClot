package logica;

public class Radia {

	private double Xe, Ye, AX, AY, acimut, distancia, Xf, Yf;
	
	public Radia (double xe, double ye, double acim, double dis)
	{
		this.Xe = xe;
		this.Ye = ye;
		this.acimut = acim;
		this.distancia = dis;
		
		this.AX = this.distancia * Math.sin(this.acimut*Math.PI/200);
		this.AY = this.distancia * Math.cos(this.acimut*Math.PI/200);
		
		this.Xf = this.Xe + this.AX;
		this.Yf = this.Ye + this.AY;
		
	}
	
	public double getAX ()
	{
		return this.AX;
	}
	
	public double getAY ()
	{
		return this.AY;
	}
	
	public double getXf ()
	{
		return this.Xf;
	}
	
	public double getYf ()
	{
		return this.Yf;
	}
}
