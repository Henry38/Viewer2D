package com.Viewer2D.geometry;

import java.awt.Color;

import math2D.Point2D;
import math2D.Transformation2D;

public class Shape2D {
	
	private Transformation2D transform;
	private Point2D[] points;
	private int npoints;
	private double ox, oy;
	private Color color;
	
	/** Constructeur */
	public Shape2D(double[] xpoints, double[] ypoints, int npoints) {
		this.transform = new Transformation2D();
		setPoint(xpoints, ypoints, npoints);
		setOx(0);
		setOy(0);
		setColor(Color.red);
	}
	
	/** Retourne la Transforma<tion2D de la Shape2D */
	public final Transformation2D getModel() {
		return transform;
	}
	
	/** Retourne le point n°index */
	public final Point2D getPoint(int index) {
		Point2D p = new Point2D(points[index]);
		p.translation(-getOx(), -getOy());
		return getModel().transform(p);
	}
	
	/** Retourne le nombre de points */
	public int getNPoint() {
		return npoints;
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
	
	/** Met a jour les points formant la Shape2D */
	public void setPoint(double[] xpoints, double[] ypoints, int npoints) {
		this.points = new Point2D[npoints];
		for (int i = 0; i < npoints; i++) {
			points[i] = new Point2D(xpoints[i], ypoints[i]);
		}
		this.npoints = npoints;
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
}
