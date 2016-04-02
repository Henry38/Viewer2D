package viewer2D.controler;

import java.util.EventListener;

import viewer2D.geometry.Shape2D;

public interface WorldModelListener extends EventListener {
	public void shapeAdded(Shape2D shape);
	public void shapeRemoved(Shape2D shape);
	public void needRefresh();
}
