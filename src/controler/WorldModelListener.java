package controler;

import java.util.EventListener;

import graphic.Drawable;

public interface WorldModelListener extends EventListener {
	public void drawableAdded(Drawable drawable);
	public void drawableRemoved(Drawable drawable);
	public void needRefresh();
}
