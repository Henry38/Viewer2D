package viewer2D.graphic;

import java.awt.Graphics;

import math2D.Transformation2D;

public interface Drawable {
	public void draw(Graphics g, Transformation2D viewprojscreen);
}
