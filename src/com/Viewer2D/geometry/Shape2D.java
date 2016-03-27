package com.Viewer2D.geometry;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

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
	
	/** Calcul et retourne la boundingBox */
	public Rectangle2D.Double computeBoundingBox() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		for (int i = 0; i < npoints; i++) {
			Point2D point = getPoint(i);
			minX = Math.min(minX, point.getX());
			minY = Math.min(minY, point.getY());
			maxX = Math.max(maxX, point.getX());
			maxY = Math.max(maxY, point.getY());
		}
		return new Rectangle2D.Double(minX, minY, maxX-minX, maxY-minY);
	}
	
	/** Calcul et retourne l'air du polygone */
	public double computeArea() {
		double area = 0.0;
		int j = getNPoint() - 1;
		for (int i = 0; i < getNPoint(); i++) {
			Point2D iPoint = points[i];
			Point2D jPoint = points[j];
			area += (jPoint.getX() + iPoint.getX()) * (jPoint.getY() - iPoint.getY());
			j = i;
		}
		return Math.abs(area / 2.0);
	}
	
	/** Retourne vrai si le point (x, y) est a l'interieur du polygone */
	public boolean isInside(double x, double y) {
		if (!computeBoundingBox().contains(x, y)) {
			return false;
		}
		boolean inside = false;
		int j = getNPoint() - 1;
		for (int i = 0; i < getNPoint(); i++) {
			Point2D iPoint = getPoint(i);
			Point2D jPoint = getPoint(j);
			if ( ((iPoint.getY() > y) != (jPoint.getY() > y)) &&
					(x < (jPoint.getX()-iPoint.getX()) * (y-iPoint.getY()) / (jPoint.getY()-iPoint.getY()) + iPoint.getX()) ) {
				inside = !inside;
			}
			j = i;
		}
		return inside;
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
