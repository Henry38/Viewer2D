package viewer2D.geometry;

import java.awt.Graphics2D;

import math2D.Point2D;
import primitive.Edge;
import topology.BaseMeshTopology;
import viewer2D.graphic.Drawable;
import viewer2D.graphic.Viewer2D;

public class Topology2D<T extends Point2D> implements Drawable {
	
	private BaseMeshTopology<T> topology;
	
	/** Constructeur */
	public Topology2D(BaseMeshTopology<T> topology) {
		this.topology = topology;
	}
	
	@Override
	public void draw(Graphics2D g2, Viewer2D.DrawTool drawTool) {
		
		for (Point2D p : topology.getPositions()) {
			drawTool.drawPoint(g2, p);
		}
		
		for (Edge edge : topology.getEdges()) {
			Point2D p1 = topology.getPosition(edge.a);
			Point2D p2 = topology.getPosition(edge.b);
			drawTool.drawLine(g2, p1, p2);
		}
		
	}
}
