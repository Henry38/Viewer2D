package data;

import java.awt.Graphics2D;

import graphic.Viewer2D;
import graphic.Viewer2D.DrawTool;

public interface Drawable {
	public void draw(Graphics2D g2, DrawTool drawTool);
}
