package geometry;

import geometry.Shape2D;

public class Oval extends Shape2D {
	
	private double axisA;
	private double axisB;
	
	/** Constructeur */
	public Oval(double axisA, double axisB, int npoints) {
		super(new double[] {}, new double[] {}, 0);
		this.axisA = axisA;
		this.axisB = axisB;
		
		double xpoints[] = new double[npoints];
		double ypoints[] = new double[npoints];
		double step = (2 * Math.PI) / npoints;
		double radian = 0.0;
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = axisA * Math.cos(radian);
			ypoints[i] = axisB * Math.sin(radian);
			radian += step;
		}
		setPoints(xpoints, ypoints, npoints);
	}
	
	/** Retourne le demi-grand axe */
	public double getAxisA() {
		return axisA;
	}
	
	/** Retourne le demi-petit axe */
	public double getAxisB() {
		return axisB;
	}
}
