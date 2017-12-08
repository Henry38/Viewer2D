package geometry;

import geometry.Shape2D;

public class Rectangle extends Shape2D {
	
	private double width;
	private double height;
	
	/** Constructeur */
	public Rectangle(double width, double height) {
		super(new double[] {}, new double[] {}, 0);
		this.width = width;
		this.height = height;
		double xpoints[] = {-width/2, width/2, width/2, -width/2};
		double ypoints[] = {-height/2, -height/2, height/2, height/2};
		setPoints(xpoints, ypoints, 4);
	}
	
	/** Retourne la largeur */
	public double getWidth() {
		return width;
	}
	
	/** Retourne la hauteur */
	public double getHeight() {
		return height;
	}
}
