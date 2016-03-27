package com.Viewer2D.geometry;

public class Oval extends Shape2D {
	
	private double a;
	private double b;
	
	/** Constructeur */
	public Oval(double a, double b, int npoints) {
		super(new double[] {}, new double[] {}, 0);
		this.a = a;
		this.b = b;
		
		double xpoints[] = new double[npoints];
		double ypoints[] = new double[npoints];
		double step = (2 * Math.PI) / npoints;
		double radian = 0.0;
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = a * Math.cos(radian);
			ypoints[i] = b * Math.sin(radian);
			radian += step;
		}
		setPoint(xpoints, ypoints, npoints);
	}
	
	@Override
	public double computeArea() {
		return (Math.PI * a * b); 
	}
}
