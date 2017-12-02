package viewer2D.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import math2D.Point2D;
import math2D.Transformation2D;
import math2D.Vecteur2D;
import viewer2D.graphic.Drawable;

public class Base2D extends Transformation2D implements Drawable {
	
	/** Constructeur */
	public Base2D(Transformation2D transform) {
		super(transform);
	}
	
	/** Constructeur */
	public Base2D(Point2D origin, Vecteur2D ox, Vecteur2D oy) {
		super();
		matrix.set(0, 2, origin.getX());
		matrix.set(1, 2, origin.getY());
		matrix.set(0, 0, ox.getDx());
		matrix.set(1, 0, ox.getDy());
		matrix.set(0, 1, oy.getDx());
		matrix.set(1, 1, oy.getDy());
	}
	
	/** Constructeur */
	public Base2D(Point2D origin) {
		this(origin, new Vecteur2D(1, 0), new Vecteur2D(0, 1));
	}
	
	/** Constructeur par defaut */
	public Base2D() {
		this(new Point2D(0, 0));
	}
	
	/** Retourne l'origine */
	public Point2D getOrigin() {
		double x = matrix.get(0, 2);
		double y = matrix.get(1, 2);
		return new Point2D(x, y);
	}
	
	/** Retourne l'axe Ox */
	public Vecteur2D getOx() {
		double x1 = matrix.get(0, 0);
		double y1 = matrix.get(1, 0);
		return new Vecteur2D(x1, y1);
	}
	
	/** Retourne l'axe Oy */
	public Vecteur2D getOy() {
		double x2 = matrix.get(0, 1);
		double y2 = matrix.get(1, 1);
		return new Vecteur2D(x2, y2);
	}
	
	/** Retourne les deux vecteurs de la base */
	public Vecteur2D[] getVecteurs() {
		return new Vecteur2D[] {getOx(), getOy()};
	}
	
	@Override
	public void draw(Graphics g, Transformation2D viewprojscreen) {
		Graphics2D g2 = (Graphics2D) g;
		
		Point2D o = getOrigin();
		Vecteur2D ox = getOx();
		Vecteur2D oy = getOy();
		Point2D p1 = o.translation(ox);
		Point2D p2 = o.translation(oy);
		Point2D proj_o = viewprojscreen.transform(o);
		Point2D proj_p1 = viewprojscreen.transform(p1);
		Point2D proj_p2 = viewprojscreen.transform(p2);
		
		g2.setColor(Color.red);
		g2.drawLine((int) proj_o.getX(), (int) proj_o.getY(), (int) proj_p1.getX(), (int) proj_p1.getY());
		
		g2.setColor(Color.green);
		g2.drawLine((int) proj_o.getX(), (int) proj_o.getY(), (int) proj_p2.getX(), (int) proj_p2.getY());
	}
}
