package geometry;

import java.awt.Graphics2D;

import data.Drawable;
import graphic.Viewer2D;
import math2D.Point2D;
import primitive.Edge;
import primitive.Face;
import primitive.Quad;
import primitive.Triangle;
import topology.BaseMeshTopology;

public class Topology2D<T extends Point2D> implements Drawable {
	
	protected BaseMeshTopology<T> topology;
	public boolean drawPositions;
	public boolean drawEdges;
	public boolean drawTriangles;
	public boolean drawQuads;
	public boolean drawFaces;
	
	/** Constructeur */
	public Topology2D(BaseMeshTopology<T> topology) {
		this.topology = topology;
		this.drawPositions = true;
		this.drawEdges = false;
		this.drawTriangles = false;
		this.drawQuads = false;
		this.drawFaces = false;
	}
	
	@Override
	public void draw(Graphics2D g2, Viewer2D.DrawTool drawTool) {
		
		if (drawPositions) {
			for (Point2D p : topology.getPositions()) {
				drawTool.drawPoint(g2, p);
			}
		}
		
		if (drawEdges) {
			for (Edge edge : topology.getEdges()) {
				Point2D p1 = topology.getPosition(edge.a);
				Point2D p2 = topology.getPosition(edge.b);
				drawTool.drawLine(g2, p1, p2);
			}
		}
		
		if (drawTriangles) {
			for (Triangle triangle : topology.getTriangles()) {
				Point2D p1 = topology.getPosition(triangle.a);
				Point2D p2 = topology.getPosition(triangle.b);
				Point2D p3 = topology.getPosition(triangle.c);
				drawTool.drawLine(g2, p1, p2);
				drawTool.drawLine(g2, p2, p3);
				drawTool.drawLine(g2, p3, p1);
			}
		}
		
		if (drawQuads) {
			for (Quad quad : topology.getQuads()) {
				Point2D p1 = topology.getPosition(quad.a);
				Point2D p2 = topology.getPosition(quad.b);
				Point2D p3 = topology.getPosition(quad.c);
				Point2D p4 = topology.getPosition(quad.d);
				drawTool.drawLine(g2, p1, p2);
				drawTool.drawLine(g2, p2, p3);
				drawTool.drawLine(g2, p3, p4);
				drawTool.drawLine(g2, p4, p1);
			}
		}
		
		if (drawFaces) {
			for (Face face : topology.getFaces()) {
				for (int i = 0; i < face.getNbPoints(); i++) {
					int j = (i+1) % face.getNbPoints();
					Point2D p1 = topology.getPosition(face.get(i));
					Point2D p2 = topology.getPosition(face.get(j));
					drawTool.drawLine(g2, p1, p2);
				}
			}
		}
	}
}
