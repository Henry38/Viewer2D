package com.Viewer2D.geometry;

import java.awt.Color;

import math2D.Point2D;
import math2D.Transformation2D;

public class Point {
	
	private Transformation2D transform;
	private Point2D point;
	private Color color;
	
	/** Constructeur */
	public Point(double x, double y) {
		this.transform = new Transformation2D();
		this.point = new Point2D(x, y);
		this.color = Color.red;
	}
	
	/** Constructeur */
	public Point() {
		this(0, 0);
	}
	
	public final Transformation2D getModel() {
		return transform;
	}
	
	public final Point2D getPoint2D() {
		return point;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void translate(double dx, double dy) {
		transform.addTranslation(dx, dy);
	}
	
	public void rotate(double radian) {
		transform.addRotation(radian);
	}
	
	public void scale(double sx, double sy) {
		transform.addScale(sx, sy);
	}
}
