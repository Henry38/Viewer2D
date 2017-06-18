package viewer2D.geometry;

import java.awt.Color;
import java.awt.Stroke;
import math2D.Point2D;
import math2D.Transformation2D;

public class Shape2D {
	
	private static Color defaultColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	protected Transformation2D transform;
	protected Point2D[] points;
	protected Point2D barycenter;
	protected double ox, oy;
	
	protected Color color;
	protected Stroke stroke;
	protected boolean wireframe;
	
	/** Constructeur */
	public Shape2D(Point2D... points) {
		this.transform = new Transformation2D();
		this.points = points;
		this.barycenter = new Point2D();
		setOx(0);
		setOy(0);
		setColor(defaultColor);
		setStroke(null);
		calculateBarycenter();
	}
	
	/** Constructeur */
	public Shape2D(double[] xpoints, double[] ypoints, int npoints) {
		this(new Point2D[] {});
		setPoint(xpoints, ypoints, npoints);
	}
	
	/** Retourne la Transformation2D de la Shape2D */
	public Transformation2D getModel() {
		return transform;
	}
	
	/** Retourne le Point2D dans les coordonnees du monde */
	public Point2D getPoint2D(Point2D point) {
		Point2D p = new Point2D(point);
		p.translation(-getOx(), -getOy());
		return getModel().transform(p);
	}
	
	/** Retourne le point d'indice index dans les coordonnees du monde */
	public Point2D getPoint2D(int index) {
		return getPoint2D(points[index]);
	}
	
	/** Retourne le centre d'inertie de la Shaped2D */
	public Point2D getBarycenter() {
		return barycenter;
	}
	
	/** Retourne le nombre de points */
	public int getNbPoint() {
		return points.length;
	}
	
	/** Retourne la coordonnee X de l'oigine */
	public double getOx() {
		return ox;
	}
	
	/** Retourne la coordonnee Y de l'oigine */
	public double getOy() {
		return oy;
	}
	
	/** Retourne la couleur de la Shape2D */
	public Color getColor() {
		return color;
	}
	
	/** Retourne la couleur de la Shape2D */
	public Stroke getStroke() {
		return stroke;
	}
	
	/** Retourne le mode de rendu de la Shape2D */
	public boolean isWireframe() {
		return wireframe;
	}
	
	/** Met a jour la coordonnee X de l'oigine */
	public void setOx(double ox) {
		this.ox = ox;
	}
	
	/** Met a jour la coordonnee Y de l'oigine */
	public void setOy(double oy) {
		this.oy = oy;
	}
	
	/** Met a jour la couleur */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/** Met a jour la bordure */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	/** Met a jour le mode de rendu */
	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
	
	/** Met a jour les points formant la Shape2D */
	public void setPoint(double[] xpoints, double[] ypoints, int npoints) {
		this.points = new Point2D[npoints];
		for (int i = 0; i < npoints; i++) {
			points[i] = new Point2D(xpoints[i], ypoints[i]);
		}
		calculateBarycenter();
	}
	
	
	/** Ajoute une translation a la transformation */
	public void translate(double dx, double dy) {
		transform.addTranslation(dx, dy);
	}
	
	/** Ajoute une rotation a la transformation */
	public void rotate(double radian) {
		transform.addRotation(radian);
	}
	
	/** Ajoute un changement d'echelle a la transformation */
	public void scale(double sx, double sy) {
		transform.addScale(sx, sy);
	}
	
	/** Calcule le barycentre des Point2D */
	private void calculateBarycenter() {
		int npoints = getNbPoint();
		double bx = 0.0;
		double by = 0.0;
		for (int i = 0; i < npoints; i++) {
			bx += points[i].x;
			by += points[i].y;
		}
		this.barycenter.set(bx / npoints, by / npoints);
	}
}
