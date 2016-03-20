package com.Viewer2D.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import com.Viewer2D.data.Camera;
import com.Viewer2D.data.Viewport;
import com.Viewer2D.data.World2D;
import com.Viewer2D.geometry.Point;
import com.Viewer2D.geometry.Shape2D;

import math2D.Transformation2D;
import math2D.Point2D;
import math2D.Vecteur2D;

public class Viewer2D extends JComponent implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, Observer {
	
	private static final long serialVersionUID = 1L;
	private World2D world;
	private Camera camera;
	private Viewport viewport;
	
	private BasicStroke stroke, axeStroke;
	private Transformation2D screenMVP;
	private int lineClicked = 0;
	private int columnClicked = 0;
	
	private int unityGrid = 1;
	private int eventButton = 0;
	private boolean movable = true;
	private boolean spinnable = true;
	private boolean zoomable = true;
	
	/** Contructeur */
	public Viewer2D(World2D world, Camera camera, int width, int height) {
		super();
		this.world = world;
		this.camera = camera;
		
		this.viewport = new Viewport(0, 0, width, height);
		
		this.stroke = new BasicStroke();
		this.axeStroke = new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
		
		this.setPreferredSize(new Dimension(width, height));
	}
	
	/** Contructeur */
	public Viewer2D(World2D world, Camera camera) {
		this(world, camera, 640, 480);
	}
	
	/** Contructeur */
	public Viewer2D(World2D world) {
		this(world, new Camera());
	}
	
	/** Contructeur */
	public Viewer2D() {
		this(new World2D());
	}
	
	/** Retourne le model */
	public World2D getModel() {
		return world;
	}
	
	/** Retourne l'unite e la grile */
	public int getUnity() {
		return unityGrid;
	}
	
	/** Indique si la camera est translatable */
	public boolean getMoveable() {
		return movable;
	}
	
	/** Indique si la camera est tournable */
	public boolean getSpinnable() {
		return spinnable;
	}
	
	/** Indique si la camera peut zoomer */
	public boolean getZoomable() {
		return zoomable;
	}
	
	/** Met a jour l'unite de la grille */
	public void setUnity(int value) {
		unityGrid = Math.max(1, value);
	}
	
	/** Permet la translation de la camera */
	public void setMoveable(boolean value) {
		this.movable = value;
	}
	
	/** Permet la rotation de la camera */
	public void setSpinnable(boolean value) {
		this.spinnable = value;
	}
	
	/** Permet le zoom de la camera */
	public void setZoomable(boolean value) {
		this.zoomable = value;
	}
	
	public void drawPoint(Graphics g2, Point2D point) {
		Point2D proj_p = screenMVP.transform(point);
		g2.fillOval((int) proj_p.getX() - 4, (int) proj_p.getY() - 4, 8, 8);
	}
	
	public void drawLine(Graphics g2, Point2D point1, Point2D point2) {
		Point2D proj_p1 = screenMVP.transform(point1);
		Point2D proj_p2 = screenMVP.transform(point2);
		g2.drawLine((int) proj_p1.getX(), (int) proj_p1.getY(), (int) proj_p2.getX(), (int) proj_p2.getY());
	}
	
	public void drawArrow(Graphics g2, Point2D point1, Point2D point2) {
		Point2D proj_p;
		Point2D proj_p1 = screenMVP.transform(point1);
		Point2D proj_p2 = screenMVP.transform(point2);
		g2.drawLine((int) proj_p1.getX(), (int) proj_p1.getY(), (int) proj_p2.getX(), (int) proj_p2.getY());
		
		double dx = (0.10 * (point1.getX() - point2.getX()));
		double dy = (0.10 * (point1.getY() - point2.getY()));
		
		Point2D dot = new Point2D();
		dot.setX(dx);
		dot.setY(dy);
		dot.rotation(Math.PI / 8);
		dot.translation(point2.getX(), point2.getY());
		proj_p = screenMVP.transform(dot);
		g2.drawLine((int) proj_p2.getX(), (int) proj_p2.getY(), (int) proj_p.getX(), (int) proj_p.getY());
		
		dot.setX(dx);
		dot.setY(dy);
		dot.rotation(-Math.PI / 8);
		dot.translation(point2.getX(), point2.getY());
		proj_p = screenMVP.transform(dot);
		g2.drawLine((int) proj_p2.getX(), (int) proj_p2.getY(), (int) proj_p.getX(), (int) proj_p.getY());
	}
	
	public void drawShape(Graphics2D g2, Shape2D shape) {
		int[] xpoints = new int[shape.getNPoint()];
		int[] ypoints = new int[shape.getNPoint()];
		for (int i = 0; i < shape.getNPoint(); i++) {
			Point2D proj_p = screenMVP.transform(shape.getPoint(i));
			xpoints[i] = (int) proj_p.getX();
			ypoints[i] = (int) proj_p.getY();
		}
		g2.fillPolygon(xpoints, ypoints, shape.getNPoint());
	}
	
	public void drawGrid(Graphics2D g2) {
		Point2D point1 = new Point2D();
		Point2D point2 = new Point2D();
		
		// Calcul des quatres points du rectangle (repere camera)
		Rectangle2D.Double rect = camera.getRectangle();
		Point2D bottomLeft = new Point2D(rect.getX(), rect.getY());
		Point2D bottomRight = new Point2D(rect.getX() + rect.getWidth(), rect.getY());
		Point2D topRight = new Point2D(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
		Point2D topLeft = new Point2D(rect.getX(), rect.getY() + rect.getHeight());
		
		// Calcul des quatres points du rectangle (repere monde)
		Transformation2D inverseView = camera.viewMat().getInverseTransformation();
		bottomLeft = inverseView.transform(bottomLeft);
		bottomRight = inverseView.transform(bottomRight);
		topRight = inverseView.transform(topRight);
		topLeft = inverseView.transform(topLeft);
		
		// Unite de la grille
		double step = getUnity();
		
		// Calcul de la bounding box reguliere (repere monde)
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		//double step = 1;//(bottomRight.getX() - bottomLeft.getX()) / 10;
		for (Point2D point : new Point2D[] { bottomLeft, bottomRight, topRight, topLeft }) {
			if (point.getX() < minX) {
				minX = (int) (Math.floor(point.getX() / step) * step);
			}
			if (point.getX() > maxX) {
				maxX = (int) ((Math.floor(point.getX() / step) + 1) * step);
			}
			if (point.getY() < minY) {
				minY = (int) (Math.floor(point.getY() / step) * step);
			}
			if (point.getY() > maxY) {
				maxY = (int) ((Math.floor(point.getY() / step) + 1) * step);
			}
		}
		
		// Affichage de la grille reguliere
		point1.setY(minY);
		point2.setY(maxY);
		for (int x = minX; x <= maxX; x += step) {
			point1.setX(x);
			point2.setX(x);
			if (x == 0) {
				g2.setColor(Color.black);
				g2.setStroke(axeStroke);
			} else {
				g2.setColor(Color.gray);
				g2.setStroke(stroke);
			}
			drawLine(g2, point1, point2);
		}
		point1.setX(minX);
		point2.setX(maxX);
		for (int y = minY; y <= maxY; y += step) {
			point1.setY(y);
			point2.setY(y);
			if (y == 0) {
				g2.setColor(Color.black);
				g2.setStroke(axeStroke);
			} else {
				g2.setColor(Color.gray);
				g2.setStroke(stroke);
			}
			drawLine(g2, point1, point2);
		}
		
		// Affichage du repere canonique
		point1.setX(0);
		point1.setY(0);
		g2.setColor(Color.red);
		g2.setStroke(axeStroke);
		point2.setX(1);
		point2.setY(0);
		drawArrow(g2, point1, point2);
		point2.setX(0);
		point2.setY(1);
		drawArrow(g2, point1, point2);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		Transformation2D viewProj = Transformation2D.addTransformation(camera.projMat(), camera.viewMat());
		Transformation2D viewProjScreen = Transformation2D.addTransformation(viewport.screenMat(), viewProj);
		screenMVP = viewProjScreen;
		
		// Affichage de la grille
		drawGrid(g2);
		
		// Affichage des formes
		for (Shape2D shape : world.getListShape()) {
			g2.setColor(shape.getColor());
			drawShape(g2, shape);
		}
		
		// Afichage des points
		for (Point point : world.getListPoint()) {
			screenMVP = Transformation2D.addTransformation(viewProjScreen, point.getModel());
			g2.setColor(point.getColor());
			drawPoint(g2, new Point2D(point.getPoint2D()));
		}
		
		if (eventButton == 2) {
			g2.setColor(Color.darkGray);
			g2.setStroke(stroke);
			g2.drawLine(getWidth() / 2, getHeight() / 2, columnClicked, lineClicked);
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent ev) {}
	
	@Override
	public void mouseEntered(MouseEvent ev) {}
	
	@Override
	public void mouseExited(MouseEvent ev) {}
	
	@Override
	public void mousePressed(MouseEvent ev) {
		columnClicked = ev.getX();
		lineClicked = ev.getY();
		eventButton = ev.getButton();
		
		if (eventButton == 1 && !getMoveable()) {
			columnClicked = 0;
			lineClicked = 0;
			eventButton = 0;
		}
		
		if (eventButton == 2 && !getSpinnable()) {
			columnClicked = 0;
			lineClicked = 0;
			eventButton = 0;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent ev) {
		if (eventButton == 2) {
			repaint();
		}
		eventButton = 0;
	}
	
	@Override
	public void mouseDragged(MouseEvent ev) {
		Rectangle2D.Double rect = camera.getRectangle();
		double dx = ((double) (columnClicked - ev.getX()) / getWidth()) * rect.getWidth();
		double dy = ((double) (lineClicked - ev.getY()) / getHeight()) * rect.getHeight();
		
		// Left click
		if (eventButton == MouseEvent.BUTTON1) {
			camera.addTranslation(dx, -dy);
		}
		
		// Right click
		if (eventButton == MouseEvent.BUTTON2) {
			Vecteur2D or = new Vecteur2D(columnClicked - getWidth() / 2, lineClicked - getHeight() / 2);
			Vecteur2D op = new Vecteur2D(ev.getX() - getWidth() / 2, ev.getY() - getHeight() / 2);
			if (or.getNorme() > 0 && op.getNorme() > 0) {
				or.normalized();
				op.normalized();
				double radianOR = Math.atan2(or.getDy(), or.getDx());
				double radianOP = Math.atan2(op.getDy(), op.getDx());
				camera.addRotation(radianOP - radianOR);
			}
		}
		
		repaint();
		columnClicked = ev.getX();
		lineClicked = ev.getY();
	}
	
	@Override
	public void mouseMoved(MouseEvent ev) {}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent ev) {
		if (getZoomable()) {
			camera.addZoom(-ev.getWheelRotation() * (camera.getZ() / 10.0));
			repaint();
		}
	}
	
	@Override
	public void componentHidden(ComponentEvent ev) {}
	
	@Override
	public void componentMoved(ComponentEvent ev) {}
	
	@Override
	public void componentResized(ComponentEvent ev) {
		viewport.setWidth(getWidth());
		viewport.setHeight(getHeight());
		repaint();
	}
	
	@Override
	public void componentShown(ComponentEvent ev) {}
	
	@Override
	public void update(Observable obs, Object obj) {
		repaint();
	}
}
