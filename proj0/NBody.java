public class NBody
{
	public static double readRadius( String filePath )
	{
		In in1 = new In(filePath);

		int planetsNumber = in1.readInt();
		double universeRadius = in1.readDouble();

		return universeRadius;
	}

	public static Planet[] readPlanets( String filePath )
	{
		In in = new In(filePath);

		int planetsNumber = in.readInt();
		double universeRadius = in.readDouble();
		Planet[] planets = new Planet[planetsNumber];

		for(int i = 0 ; i < planetsNumber ; i++)
		{
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String planetName = in.readString();

			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, planetName);
		}

		return planets;
	}

	public static void main( String[] args )
	{
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		double universeRadius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int planetsNumber = planets.length;

		StdDraw.setScale(-universeRadius, universeRadius );
		StdDraw.picture(0, 0, "images/starfield.jpg");
		
		for(Planet planet : planets)
		{
			planet.draw();
		}

		StdDraw.enableDoubleBuffering();


		for(double time = 0 ; time < T ; time += dt)
		{
			double[] xForces = new double[planetsNumber];
			double[] yForces = new double[planetsNumber];

			for( int i = 0 ; i < planetsNumber ; i++ )
			{
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}

			for( int i = 0 ; i < planetsNumber ; i++ )
			{
				planets[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.picture(0, 0, "images/starfield.jpg");

			for(Planet planet : planets)
			{
				planet.draw();
			}

			StdDraw.show();

			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", universeRadius);
		for (int i = 0; i < planets.length; i++) 
		{
   			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            			planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            			planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

		return;
	}

}