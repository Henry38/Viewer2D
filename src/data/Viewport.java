package data;

import math2D.Transformation2D;

public class Viewport {
	
	private int x, y, width, height;
	private Transformation2D screenMat;
	
	/** Constructeur */
	public Viewport(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.screenMat = new Transformation2D();
	}
	
	/** Retourne la coordonnee x du viewport */
	public int getX() {
		return x;
	}
	
	/** Retourne la coordonnee y du viewport */
	public int getY() {
		return y;
	}
	
	/** Retourne la largeur du viewport */
	public int getWidth() {
		return width;
	}
	
	/** Retourne la hauteur du viewport */
	public int getHeight() {
		return height;
	}
	
	/** Retourne la coordonnee x du viewport */
	public void setX(int x) {
		this.x = x;
	}
	
	/** Retourne la coordonnee y du viewport */
	public void setY(int y) {
		this.y = y;
	}
	
	/** Retourne la largeur du viewport */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/** Retourne la hauteur du viewport */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/** Retourne la screenMat */
	public Transformation2D screenMat() {
		screenMat.clear();
		double dx = x + (width / 2.0);
		double dy = y + (height / 2.0);
		screenMat.addTranslation(dx, dy);
		double sx = width / 2.0;
		double sy = height / 2.0;
		screenMat.addScale(sx, sy);
		return screenMat;
		
//		Matrix3D m = new Matrix3D(new double[][] {
//				{w/2,   0, x+w/2},
//				{  0, h/2, y+h/2},
//				{  0,   0,     1}
//		});
	}
}
