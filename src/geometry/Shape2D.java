package geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import data.Drawable;
import graphic.Viewer2D;
import math2D.Point2D;
import math2D.Transformation2D;

public class Shape2D implements Drawable {
	
	private static Color defaultColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	protected Base2D base;
	protected Point2D[] points;
	protected double ox, oy;
	
	protected Color color;
	protected Stroke stroke;
	protected boolean drawWireframe;
	protected boolean drawTransform;
	
	/** Constructeur */
	public Shape2D(Point2D... points) {
		this.base = new Base2D();
		this.points = points;
		setOx(0);
		setOy(0);
		setColor(defaultColor);
		setStroke(null);
		drawWireframe(false);
		drawTransform(false);
	}
	
	/** Constructeur */
	public Shape2D(double[] xpoints, double[] ypoints, int npoints) {
		this(new Point2D[] {});
		setPoints(xpoints, ypoints, npoints);
	}
	
	/** Retourne le nombre de points */
	public int getNbPoint() {
		return points.length;
	}
	
	/** Retourne la Transformation2D de la Shape2D */
	public Transformation2D getTransform() {
		return base;
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
	}
	
	
	/** Ajoute une translation a la transformation */
	public void translate(double dx, double dy) {
		getTransform().addTranslation(dx, dy);
	}
	
	/** Ajoute une rotation a la transformation */
	public void rotate(double radian) {
		getTransform().addRotation(radian);
	}
	
	/** Ajoute un changement d'echelle a la transformation */
	public void scale(double sx, double sy) {
		getTransform().addScale(sx, sy);
	}
	
	@Override
	public void draw(Graphics2D g2, Viewer2D.DrawTool drawTool) {
		g2.setColor(getColor());
		
		if (drawWireframe()) {
//			if (getStroke() != null) {
//				g2.setStroke(getStroke());
//			}
			drawTool.drawPolygon(g2, this.points);
		} else {
			drawTool.fillPolygon(g2, this.points);
		}
		
		if (drawTransform()) {
			base.draw(g2, drawTool);
		}
	}
}
