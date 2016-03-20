package com.Viewer2D.data;

import java.awt.geom.Rectangle2D;

import math2D.Transformation2D;

public class Camera {
	
	private Transformation2D viewMat, projMat;
	private Rectangle2D.Double rect;
	private double distance, fovy, ratio;
	private double l, r, b, t;
	
	/** Constructeur */
	public Camera(double x, double y) {
		this.viewMat = new Transformation2D();
		this.projMat = new Transformation2D();
		this.rect = new Rectangle2D.Double();
		addTranslation(x, y);
		setFrustrum(5.0, Math.PI/4, 4.0/3.0);
	}
	
	/** Constructeur */
	public Camera() {
		this(0, 0);
	}
	
	/** Retourne la transformation camera */
	public final Transformation2D viewMat() {
		return viewMat;
	}
	
	/** Retourne la transformation de projection */
	public final Transformation2D projMat() {
		return projMat;
	}
	
	/** Retourne la hauteur de la camera par rapport au World2D */
	public double getZ() {
		return distance;
	}
	
	/** Retourne le rectangle observe */
	public final Rectangle2D.Double getRectangle() {
		return rect;
	}
	
	/** Translate la camera */
	public void addTranslation(double dx, double dy) {
		viewMat.addTranslation(-dx, -dy);
	}
	
	/** Pivote la camera */
	public void addRotation(double radian) {
		viewMat.addRotation(-radian);
	}
	
	/** Change la hauteur de la camera */
	public void addZoom(double value) {
		if (distance - value > 0) {
			this.distance -= value;
			setFrustrum(distance, fovy, ratio);
		}
	}
	
	/** Change les parametres de la camera */
	public void setFrustrum(double distance, double fovy, double ratio) {
		this.distance = distance;
		this.fovy = fovy;
		this.ratio = ratio;
		double v = distance * Math.tan(fovy);
		double u = ratio * v;
		l = -u;
		r = u;
		b = -v;
		t = v;
		rect.setRect(l, b, r-l, t-b);
		updateProjMatrix();
	}
	
	private void updateProjMatrix() {
		projMat.clear();
		double dx = -(r+l)/(r-l);
		double dy = -(t+b)/(t-b);
		projMat.addTranslation(dx, dy);
		double sx = 2.0/(r-l);
		double sy = -2.0/(t-b);
		projMat.addScale(sx, sy);
		
//		Matrix3D m = new Matrix3D(new double[][] {
//				{2/(r-l),       0, -(r+l)/(r-l)},
//				{      0, 2/(t-b), -(t+b)/(t-b)},
//				{      0,       0,            1}
//		});
	}
}
