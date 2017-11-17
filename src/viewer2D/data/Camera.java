package viewer2D.data;

import java.awt.geom.Rectangle2D;

import javax.swing.event.EventListenerList;

import math2D.Point2D;
import math2D.Transformation2D;
import math2D.Vecteur2D;
import viewer2D.controler.CameraListener;

public class Camera {
	
	private boolean movable;
	private boolean spinnable;
	private boolean zoomable;
	
	private Transformation2D viewMat, projMat;
	private Rectangle2D.Double rect;
	private double distance, fovy, ratio;
	private double left, right, bottom, top;
	
	protected EventListenerList listenerList;
	
	/** Constructeur */
	public Camera(double x, double y) {
		this.viewMat = new Transformation2D();
		this.projMat = new Transformation2D();
		this.rect = new Rectangle2D.Double();
		this.movable = true;
		this.spinnable = true;
		this.zoomable = true;
		
		this.listenerList = new EventListenerList();
		
		addTranslation(x, y);
		setFrustrum(5.0, Math.PI/4, 4.0/3.0);
	}
	
	/** Constructeur */
	public Camera() {
		this(0, 0);
	}

	/** Ajoute un listener sur la camera */
	public void addCameraListener(CameraListener l) {
		listenerList.add(CameraListener.class, l);
	}
	
	/** Retire un listener sur la camera */
	public void removeCameraListener(CameraListener l) {
		listenerList.remove(CameraListener.class, l);
	}
	
	protected void fireCameraChanged() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof CameraListener) {
				((CameraListener) listeners[i]).cameraChanged();
			}
		}
	}
	
	/** Indique si la camera est translatable */
	public boolean getMoveable() {
		return movable;
	}
	
	/** Indique si la camera est tournable */
	public boolean getSpinnable() {
		return spinnable;
	}
	
	/** Indique si la camera peut zoomer */
	public boolean getZoomable() {
		return zoomable;
	}
	
	/** Retourne la transformation camera */
	public Transformation2D viewMat() {
		return viewMat;
	}
	
	/** Retourne la transformation de projection */
	public Transformation2D projMat() {
		return projMat;
	}
	
	/** Retourne la hauteur de la camera par rapport au World2D */
	public double getZ() {
		return distance;
	}
	
	/** Retourne le rectangle observe dans le repere camera */
	public Rectangle2D.Double getRectangle() {
		return rect;
	}
	
	/** Permet la translation de la camera */
	public void setMoveable(boolean value) {
		this.movable = value;
	}
	
	/** Permet la rotation de la camera */
	public void setSpinnable(boolean value) {
		this.spinnable = value;
	}
	
	/** Permet le zoom de la camera */
	public void setZoomable(boolean value) {
		this.zoomable = value;
	}
	
	/** Regarde le point du monde (x,y) */
	public void lookAt(double x, double y) {
		Point2D p = viewMat.transform(0, 0);
		viewMat.addTranslation(p.getX(), p.getY());
		p = viewMat.transform(x, y);
		viewMat.addTranslation(-p.getX(), -p.getY());
		fireCameraChanged();
	}
	
	/** Regarde le point du monde p */
	public void lookAt(Point2D p) {
		lookAt(p.getX(), p.getY());
	}
	
	/** Definit le vecteur up de la camera */
	public void lookTowards(double dx, double dy) {
		double radian = Math.atan2(-dx, dy);
		Point2D p = viewMat.getInverseTransformation().transform(0, 0);
		viewMat.clear();
		viewMat.addRotation(-radian);
		p = viewMat.transform(p);
		viewMat.addTranslation(-p.getX(), -p.getY());
		fireCameraChanged();
	}
	
	/** Defenit le vecteur up de la camera */
	public void lookTowards(Vecteur2D vect) {
		lookTowards(vect.getDx(), vect.getDy());
	}
	
	/** Translate la camera */
	public void addTranslation(double dx, double dy) {
		viewMat.addTranslation(-dx, -dy);
		fireCameraChanged();
	}
	
	/** Pivote la camera */
	public void addRotation(double radian) {
		viewMat.addRotation(-radian);
		fireCameraChanged();
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
		left = -u;
		right = u;
		bottom = -v;
		top = v;
		rect.setRect(left, bottom, right-left, top-bottom);
		updateProjMatrix();
		fireCameraChanged();
	}
	
	private void updateProjMatrix() {
		projMat.clear();
		double dx = -(right+left)/(right-left);
		double dy = -(top+bottom)/(top-bottom);
		projMat.addTranslation(dx, dy);
		double sx = 2.0/(right-left);
		double sy = -2.0/(top-bottom);
		projMat.addScale(sx, sy);
		
//		Matrix3D m = new Matrix3D(new double[][] {
//				{2/(r-l),       0, -(r+l)/(r-l)},
//				{      0, 2/(t-b), -(t+b)/(t-b)},
//				{      0,       0,            1}
//		});
	}
}
