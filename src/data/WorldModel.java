package data;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import controler.ViewerListener;
import controler.WorldModelListener;
import graphic.Drawable;

public class WorldModel implements ViewerListener {
	
	protected ArrayList<Drawable> listDrawable;
	protected EventListenerList listenerList;
	
	/** Constructeur */
	public WorldModel() {
		this.listDrawable = new ArrayList<Drawable>();
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
	
	protected void fireDrawableAdded(Drawable drawable) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof WorldModelListener) {
				((WorldModelListener) listeners[i]).drawableAdded(drawable);
			}
		}
	}
	
	protected void fireDrawableRemoved(Drawable drawable) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof WorldModelListener) {
				((WorldModelListener) listeners[i]).drawableRemoved(drawable);
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
	
	/** Retourne la liste des Drawable du monde */
	public ArrayList<Drawable> getListDrawable() {
		return listDrawable;
	}
	
	/** Retourne la Drawable a l'indice index */
	public Drawable getDrawable(int index) {
		return listDrawable.get(index);
	}
	
	/** Retourne le nombre de Drawable present dans le monde */
	public int getNbDrawable() {
		return listDrawable.size();
	}
	
	/** Ajoute une Drawable au monde */
	public void addDrawable(Drawable drawable) {
		listDrawable.add(drawable);
		fireDrawableAdded(drawable);
	}
	
	/** Retire une Drawable au monde */
	public void removeDrawable(Drawable drawable) {
		listDrawable.remove(drawable);
		fireDrawableRemoved(drawable);
	}
	
	/** Retire toutes les drawables du monde */
	public void removeAll() {
		for (int i = getNbDrawable()-1; i >= 0; i--) {
			Drawable drawable = getDrawable(i);
			removeDrawable(drawable);
		}
	}
	
	public void pointPressed(double px, double py) { }
	
}
