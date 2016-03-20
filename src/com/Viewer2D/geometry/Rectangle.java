package com.Viewer2D.geometry;

public class Rectangle extends Shape2D {
	
	/** Constructeur */
	public Rectangle(double a, double b) {
		super(new double[] {}, new double[] {}, 0);
		
		double xpoints[] = {0, a, a, 0};
		double ypoints[] = {0, 0, b, b};
		setPoint(xpoints, ypoints, 4);
	}
}
