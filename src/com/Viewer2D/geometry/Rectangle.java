package com.Viewer2D.geometry;

public class Rectangle extends Shape2D {
	
	private double a;
	private double b;
	
	/** Constructeur */
	public Rectangle(double a, double b) {
		super(new double[] {}, new double[] {}, 0);
		this.a = a;
		this.b = b;
		double xpoints[] = {-a/2, a/2, a/2, -a/2};
		double ypoints[] = {-b/2, -b/2, b/2, b/2};
		setPoint(xpoints, ypoints, 4);
	}
	
	@Override
	public double computeArea() {
		return (a * b); 
	}
}
