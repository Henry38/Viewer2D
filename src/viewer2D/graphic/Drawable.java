package viewer2D.graphic;

import java.awt.Graphics2D;

public interface Drawable {
	public void draw(Graphics2D g2, Viewer2D.DrawTool drawTool);
}
