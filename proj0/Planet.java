public class Planet
{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static double G = 6.67E-11;

	public Planet(double xP, double yP, double xV, 
				  double yV, double m, String img)
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p)
	{
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass  = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet other)
	{
		double dx = xxPos - other.xxPos;
		double dy = yyPos - other.yyPos;
		double r  = Math.sqrt( dx*dx + dy*dy );

		return r;
	}

	public double calcForceExertedBy(Planet other)
	{
		double r = calcDistance(other);
		double F = ( Planet.G * mass * other.mass)/(r*r);

		return F;
	}

	public double calcForceExertedByX(Planet other)
	{
		double dx = other.xxPos - xxPos;
		double r = calcDistance(other);
		double Fx = ( this.calcForceExertedBy(other) * dx )/r;

		return Fx;
	}

	public double calcForceExertedByY(Planet other)
	{
		double dy = other.yyPos - yyPos;
		double r = calcDistance(other);
		double Fy = ( this.calcForceExertedBy(other) * dy )/r;

		return Fy;
	}

	public double calcNetForceExertedByX(Planet[] others)
	{
		double NetForceX = 0;

		for( Planet curPlanet : others )
		{
			if( curPlanet.equals(this) ) continue;
			else 
			{
				NetForceX += this.calcForceExertedByX(curPlanet);
			}
		}

		return NetForceX;
	}

	public double calcNetForceExertedByY(Planet[] others)
	{
		double NetForceY = 0;

		for( Planet curPlanet : others )
		{
			if( curPlanet.equals(this) ) continue;
			else 
			{
				NetForceY += this.calcForceExertedByY(curPlanet);
			}
		}

		return NetForceY;
	}

	public void update( double dt, double fX, double fY )
	{
		double xxA = fX/mass;
		double yyA = fY/mass;
		xxVel += dt*xxA;
		yyVel += dt*yyA;

		xxPos += xxVel*dt;
		yyPos += yyVel*dt;
	}

	public void draw()
	{
		String filePath = "images/"+imgFileName;
		
		StdDraw.picture( xxPos, yyPos, filePath);
	}

}