public class NBody{
	public static double readRadius(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[numOfPlanets]; 
		for(int i = 0;i < numOfPlanets; i++){
			planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		}
		return planets;
	}

	public static void main(String[] args){
		StdDraw.enableDoubleBuffering();
		double T, dt, radius;
		Planet[] planets;

		T = Double.parseDouble(args[0]);
		dt = Double.parseDouble(args[1]);
		String filename = args[2];

		radius = readRadius(filename);
		planets = readPlanets(filename);

		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for(Planet p: planets){
			p.draw();
		}
		StdDraw.show();
		StdDraw.pause(10);
		//It can just show() once, otherwise there will just be the first
		//picture showed.

		double time = 0;
		while(Math.abs(time - T)>=1e-6){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int i = 0; i < planets.length; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			int k = 0;
			for(Planet p:planets){
				p.update(dt, xForces[k], yForces[k++]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for(Planet p: planets){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
	}

}