package logica;


public class Cloto {

	private double L;
	private double R;
	private double A;
	private double Tao;
	private double Alfa;
	
	
	private double Xf = 0, Yf = 0, Yo, Xo, AR, Sl, Tc, Tl, o;
	
	public Cloto(){ // CONSTRUCTOR VACÍO
		
	}

	// PSEUDO-CONSTRUCTORES
	
	public void ClotoRL (double R, double L)
	{
		this.R = R;
		this.L = L;
		
		this.A = Math.sqrt(L*R);
		this.Tao = this.L/(2*this.R);
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	public void ClotoRT (double R, double Tao)
	{
		this.R = R;
		this.Tao = Tao*Math.PI/200;
		
		this.L = this.Tao*2*this.R;
		this.A = Math.sqrt(this.R * this.L);
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	public void ClotoRA (double R, double A)
	{
		this.R = R;
		this.A = A;
		
		this.L = (Math.pow(this.A, 2))/this.R;
		this.Tao  = this.L/(2*this.R);
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	public void ClotoLT (double L, double Tao)
	{
		this.L = L;
		this.Tao = Tao*Math.PI/200;
		
		this.R = this.L/(2*this.Tao);
		this.A = Math.sqrt(this.R * this.L);
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	public void ClotoLA (double L, double A)
	{
		this.L = L;
		this.A = A;
		
		this.R = Math.pow(A, 2)/this.L;
		this.Tao = this.L/(2*this.R);
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	public void ClotoAT (double A, double Tao)
	{
		this.A = A;
		this.Tao = Tao*Math.PI/200;
		
		this.R = this.A/Math.sqrt(2*this.Tao);
		this.L = Math.pow(this.A, 2)/this.R;
		this.Alfa = (Math.pow(this.L, 2))/(2*Math.pow(this.A, 2));
		
		getResult();
	}
	
	// 
	
	
	
	private double factorial(double numero)
	{
		int factorial = 1;
		
		for (int i = 1; i <= numero; i++)
		{
			factorial = factorial*i;
		}
		return factorial;
	}
	
	private void getResult ()
	{
		// Bucle y demás resultados
		double Xant=1;
		double Xsig=0;
		int i = 1;
		while(Math.abs(Xant-Xsig)>0.00001)
		{
			Xant=Xsig;
			Xsig = Xsig + (Math.pow(-1, i+1)*Math.pow(L, 4*i - 3))/(Math.pow(2*R*L, 2*i - 2)*(factorial(2*i-2))*(4*i - 3));
			i++;
		}
		
		
		double Yant=1;
		double Ysig=0;
		i = 1;
		while(Math.abs(Yant-Ysig)>0.00001)
		{
			Yant = Ysig;
			Ysig = Ysig + (Math.pow(-1, i+1)*Math.pow(L, 4*i - 1))/(Math.pow(2*R*L, 2*i - 1)*(factorial(2*i-1))*(4*i - 1));
			i++;
		}
		
		this.Xf = Xsig;
		this.Yf = Ysig;
		
		this.Xo = this.Xf - (this.R * Math.sin(this.Tao));
		this.Yo = this.Yf + (this.R * Math.cos(this.Tao));
		
		this.Tc = this.Yf/(Math.sin(this.Tao));
		this.Tl = this.Xf - (this.Yf/(Math.tan(this.Tao)));
		
		this.AR = this.Yf + (this.R*(Math.cos(this.Tao) - 1));
		this.Sl = (Math.sqrt(Math.pow(this.Xf, 2) + Math.pow(this.Yf, 2) ));
		this.o = Math.atan2(this.Yf,this.Xf);
		
		
//		for (i = 1; i<=10 ; i++)
//		{
//			Xf = Xf + (Math.pow(-1, i+1)*Math.pow(L, 4*i -3))/(Math.pow(2*R*L, 2*i - 2)*(factorial(2*i-2))*(4*i - 3));
//		}
		
//		for (i = 1; i<=10 ; i++)
//		{
//			Yf = Yf + (Math.pow(-1, i+1)*Math.pow(L, 4*i - 1))/(Math.pow(2*R*L, 2*i - 1)*(factorial(2*i-1))*(4*i - 1));
//		}
//		System.out.println(Xsig);
//		Log.v(String.valueOf(Xsig),"-----------------------------------------------");
		
	}
	
	// GETTERS DE LA CLASE
	
	public double getXf()
	{
		return this.Xf;
	}
	
	public double getYf()
	{
		return this.Yf;
	}
	
	public double getXo()
	{
		return this.Xo;
	}
	
	public double getYo()
	{
		return this.Yo;
	}
	
	public double getTc()
	{
		return this.Tc;
	}
	
	public double getTl()
	{
		return this.Tl;
	}
	
	public double getAR()
	{
		return this.AR;
	}
	
	public double getSl()
	{
		return this.Sl;
	}
	
	public double getO()
	{
		this.o = this.o*200.0/Math.PI;
		if (this.o > 400.0)
		{
			return this.o - 400.0;
		}
		
		else if (this.o < 0.0)
		{
			return this.o + 400.0;
		}
		else {
			return this.o;
		}
	}
	
	
	// 
	
	
	public double getL()
	{
		return this.L;
	}
	
	public double getR()
	{
		return this.R;
	}
	
	public double getA()
	{
		return this.A;
	}
	
	public double getTao()
	{
		this.Tao = this.Tao*200/Math.PI;
		if (this.Tao >= 400.0)
		{
			return this.Tao - 400.0;
		}
		
		else if (this.Tao < 0.0)
		{
			return this.Tao + 400.0;
		}
		else {
			return this.Tao;
		}
	}
	
	public double getAlfa()
	{
		this.Alfa = this.Alfa*200.0/Math.PI;
		if (this.Alfa > 400.0)
		{
			return this.Alfa - 400.0;
		}
		
		else if (this.Alfa < 0.0)
		{
			return this.Alfa + 400.0;
		}
		else {
			return this.Alfa;
		}
	}
	
}
