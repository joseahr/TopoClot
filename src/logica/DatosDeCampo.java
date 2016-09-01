package logica;

public class DatosDeCampo {

	private double Xe, Ye, Xf, Yf, acimut, distancia, AX, AY;
	
	public DatosDeCampo(double xe, double ye, double xf, double yf)
	{
		this.Xe = xe;
		this.Xf = xf;
		
		this.Ye = ye;
		this.Yf = yf;
		
		this.AX = this.Xf - this.Xe;
		this.AY = this.Yf - this.Ye;
		
		this.distancia = Math.sqrt(Math.pow(this.AX, 2) + Math.pow(this.AY, 2));
		
		this.acimut = Math.atan2(this.AX, this.AY);
		
	}
	
	public double getAX()
	{
		return this.AX;
	}
	
	public double getAY()
	{
		return this.AY;
	}
	
	public double getAci()
	{
		this.acimut = this.acimut*200/Math.PI;
		if (this.acimut > 400)
		{
			return this.acimut - 400;
		}
		
		else if (this.acimut < 0)
		{
			return this.acimut + 400;
		}
		else {
			return this.acimut;
		}
	}
	
	public double getDis()
	{
		return this.distancia;
	}
	
}
