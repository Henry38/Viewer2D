package viewer2D.data;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import viewer2D.controler.WorldModelListener;
import viewer2D.geometry.Shape2D;

public class WorldModel {
	
	protected ArrayList<Shape2D> listShape;
	protected EventListenerList listenerList;
	
	/** Constructeur */
	public WorldModel() {
		this.listShape = new ArrayList<Shape2D>();
		this.listenerList = new EventListenerList();
	}
	
	/** Ajoute un listener selection sur le modele */
	public void addWorldListener(WorldModelListener l) {
		listenerList.add(WorldModelListener.class, l);
	}
	
	/** Retire un listener selection sur le modele */
	public void removeWorldListener(WorldModelListener l) {
		listenerList.remove(WorldModelListener.class, l);
	}
	
	protected void fireShapeAdded(Shape2D shape) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof WorldModelListener) {
				((WorldModelListener) listeners[i]).shapeAdded(shape);
			}
		}
	}
	
	protected void fireShapeRemoved(Shape2D shape) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof WorldModelListener) {
				((WorldModelListener) listeners[i]).shapeRemoved(shape);
			}
		}
	}
	
	protected void fireNeedRefresh() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof WorldModelListener) {
				((WorldModelListener) listeners[i]).needRefresh();
			}
		}
	}
	
	/** Retourne la liste des Shape2D du monde */
	public ArrayList<Shape2D> getListShape() {
		return listShape;
	}
	
	/** Retourne la Shape2D a l'indice index */
	public Shape2D getShape(int index) {
		return listShape.get(index);
	}
	
	/** Retourne le nombre de Shape2D present dans le monde */
	public int getNbShape() {
		return listShape.size();
	}
	
	/** Ajoute une Shape2D au monde */
	public void add(Shape2D shape) {
		listShape.add(shape);
		fireShapeAdded(shape);
	}
	
	/** Retire une Shape2D au monde */
	public void remove(Shape2D shape) {
		listShape.remove(shape);
		fireShapeRemoved(shape);
	}
	
	/** Retire toutes les shapes du monde */
	public void removeAll() {
		for (int i = getNbShape()-1; i >= 0; i--) {
			Shape2D shape = getShape(i);
			remove(shape);
		}
	}
}
