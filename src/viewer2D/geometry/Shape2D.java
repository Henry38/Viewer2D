package viewer2D.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import math2D.Point2D;
import math2D.Transformation2D;
import viewer2D.graphic.Drawable;

public class Shape2D implements Drawable {
	
	private static Color defaultColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	protected Transformation2D transform;
	protected Point2D[] points;
	protected Point2D barycenter;
	protected double ox, oy;
	
	protected Color color;
	protected Stroke stroke;
	protected boolean drawWireframe;	
	protected boolean drawTransform;
	
	/** Constructeur */
	public Shape2D(Point2D... points) {
		this.transform = new Transformation2D();
		this.points = points;
		this.barycenter = new Point2D();
		setOx(0);
		setOy(0);
		setColor(defaultColor);
		setStroke(null);
		drawWireframe(false);
		drawTransform(false);
		calculateBarycenter();
	}
	
	/** Constructeur */
	public Shape2D(double[] xpoints, double[] ypoints, int npoints) {
		this(new Point2D[] {});
	}
	
	/** Retourne la Transformation2D de la Shape2D */
	public Transformation2D getModel() {
		return transform;
	}
	
	/** Retourne le centre d'inertie de la Shaped2D */
	public Point2D getBarycenter() {
		return barycenter;
		setPoints(xpoints, ypoints, npoints);
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
	
	/** Retourne vrai si la Shape2D doit etre rendu en wireframe */
	public boolean drawWireframe() {
		return drawWireframe;
	}
	
	/** Retourne vrai si la transform doit etre dessine */
	public boolean drawTransform() {
		return drawTransform;
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
	public void drawWireframe(boolean wireframe) {
		this.drawWireframe = wireframe;
	}
	
	/** Met a jour le rendu de la transform */
	public void drawTransform(boolean transform) {
		this.drawTransform = transform;
	}
	
	/** Met a jour les points formant la Shape2D */
	public void setPoints(double[] xpoints, double[] ypoints, int npoints) {
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
	
	/** Retourne le point d'indice i dans les coordonnees du monde */
	private Point2D getPoint2D(int i) {
		Point2D pt = new Point2D(points[i].getX() - getOx(), points[i].getY() - getOy());
		return getTransform().transform(pt);
	}
	
	/** Calcule le barycentre des Point2D */
	private void calculateBarycenter() {
		int npoints = getNbPoint();
		double bx = 0.0;
		double by = 0.0;
		for (int i = 0; i < npoints; i++) {
			bx += points[i].x;
			by += points[i].y;
	@Override
	public void draw(Graphics g, Transformation2D viewprojscreen) {
		Graphics2D g2 = (Graphics2D) g;
		
		int[] xpoints = new int[getNbPoint()];
		int[] ypoints = new int[getNbPoint()];
		
		for (int i = 0; i < getNbPoint(); i++) {
			Point2D proj_p = viewprojscreen.transform(getPoint2D(i));
			xpoints[i] = (int) proj_p.getX();
			ypoints[i] = (int) proj_p.getY();
		}
		this.barycenter.set(bx / npoints, by / npoints);
		
		g2.setColor(getColor());
		
		if (drawWireframe()) {
//			if (getStroke() != null) {
//				g2.setStroke(getStroke());
//			}
			g2.drawPolygon(xpoints, ypoints, getNbPoint());
		} else {
			g2.fillPolygon(xpoints, ypoints, getNbPoint());
		}
	}
}
