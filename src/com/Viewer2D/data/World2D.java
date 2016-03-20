package com.Viewer2D.data;

import java.util.ArrayList;
import java.util.Observable;

import com.Viewer2D.geometry.Point;
import com.Viewer2D.geometry.Shape2D;

public class World2D extends Observable {
	
	private Point centre;
	private ArrayList<Point> listPoint;
	private ArrayList<Shape2D> listShape;
	
	/** Contructeur */
	public World2D() {
		this.centre = new Point(0, 0);
		this.listPoint = new ArrayList<Point>(0);
		this.listShape = new ArrayList<Shape2D>(0);
	}
	
	/** Retourne le centre du world2D */
	public final Point getCentre() {
		return centre;
	}
	
	/** Retourne la liste des points du world2D */
	public final ArrayList<Point> getListPoint() {
		return listPoint;
	}
	
	/** Retourne la liste des shapes du world2D */
	public final ArrayList<Shape2D> getListShape() {
		return listShape;
	}
	
	/** Ajoute un point au world2D */
	public void addPoint(Point point2D) {
		listPoint.add(point2D);
	}
	
	/** Ajoute une shape au world2D */
	public void addShape(Shape2D Shape2D) {
		listShape.add(Shape2D);
	}
	
	/** Envoie un signal pour un rafraichissement de la fenetre graphique */
	public void needRefresh() {
		setChanged();
		this.notifyObservers();
	}
}
