package viewer2D.controler;

import java.util.EventListener;

import viewer2D.graphic.Drawable;

public interface WorldModelListener extends EventListener {
	public void drawableAdded(Drawable drawable);
	public void drawableRemoved(Drawable drawable);
	public void needRefresh();
}
