package viewer2D.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

import viewer2D.controler.CameraListener;
import viewer2D.controler.WorldModelListener;
import viewer2D.data.Camera;
import viewer2D.data.Viewport;
import viewer2D.data.WorldModel;
import viewer2D.geometry.Shape2D;
import math2D.Base2D;
import math2D.Transformation2D;
import math2D.Point2D;
import math2D.Vecteur2D;

public class Viewer2D extends JComponent {
	
	private static final long serialVersionUID = 1L;
	public static final String MODEL_CHANGED_PROPERTY = "model";
	
	private WorldModel model;
	private Camera camera;
	private Viewport viewport;
	private Handler handler;
	protected Transformation2D screenMVP;
	
	protected BasicStroke gridStroke, axisStroke;
	protected boolean drawAxis, drawGrid;
	
	private int lineClicked = 0;
	private int columnClicked = 0;
	private int unityGrid = 1;
	private int eventButton = 0;
	
	protected Color backgroundColor = new Color(240, 240, 240, 255);
	protected Color axisColor = new Color(0, 0, 0, 255);
	
	/** Contructeur */
	public Viewer2D(WorldModel model, int width, int height) {
		super();
		this.model = null;
		this.camera = new Camera();
		this.viewport = new Viewport(0, 0, width, height);
		this.handler = new Handler();
		this.screenMVP = null;
		
		this.gridStroke = new BasicStroke();
		this.axisStroke = new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
		this.drawAxis = true;
		this.drawGrid = true;
		
		setModel(model);
		
		this.addMouseListener(getHandler());
		this.addMouseMotionListener(getHandler());
		this.addMouseWheelListener(getHandler());
		this.addComponentListener(getHandler());
		
		this.camera.addCameraListener(getHandler());
		
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
	}
	
	/** Contructeur */
	public Viewer2D(int width, int height) {
		this(new WorldModel(), width, height);
	}
	
	/** Contructeur */
	public Viewer2D() {
		this(0, 0);
	}
	
	/** Retourne le modele */
	public WorldModel getModel() {
		return model;
	}
	
	/** Retourne la camera */
	public Camera getCamera() {
		return camera;
	}
	
	/** Retourne l'unite de la grille */
	public int getUnity() {
		return unityGrid;
	}
	
	/** Retourne le handler sur le Viewer2D */
	private Handler getHandler() {
		return handler;
	}
	
	/** Met a jour le modele */
	public void setModel(WorldModel newModel) {
		WorldModel oldModel = getModel();
		
		if (oldModel != null) {
			oldModel.removeWorldListener(getHandler());
		}
		
		model = newModel;
		
		if (newModel != null) {
			newModel.addWorldListener(getHandler());
		}
		
		firePropertyChange(MODEL_CHANGED_PROPERTY, oldModel, model);
	}
	
	/** Met a jour l'unite de la grille */
	public void setUnity(int value) {
		unityGrid = Math.max(1, value);
	}
	
	
	public Point2D mapToWorld(double x, double y) {
		Transformation2D viewProj = Transformation2D.addTransformation(camera.projMat(), camera.viewMat());
		Transformation2D viewProjScreen = Transformation2D.addTransformation(viewport.screenMat(), viewProj);
		
		Transformation2D inverse = viewProjScreen.getInverseTransformation();
		
		return inverse.transform(new Point2D(x, y));
	}
	
	public Point2D mapFromWorld(double x, double y) {
		Transformation2D viewProj = Transformation2D.addTransformation(camera.projMat(), camera.viewMat());
		Transformation2D viewProjScreen = Transformation2D.addTransformation(viewport.screenMat(), viewProj);
		
		return viewProjScreen.transform(new Point2D(x, y));
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
	
	public void drawArrow(Graphics g2, Point2D point, Vecteur2D vect) {
		Point2D p = new Point2D(point);
		p.translation(vect);
		drawArrow(g2, point, p);
	}
	
	public void drawBase(Graphics2D g2, Base2D base) {
		g2.setStroke(axisStroke);
		Point2D o = base.getOrigine();
		Vecteur2D ox = base.getOx();
		Vecteur2D oy = base.getOy();
		g2.setColor(Color.green);
		drawArrow(g2, o, ox);
		g2.setColor(Color.blue);
		drawArrow(g2, o, oy);
	}
	
	public void drawShape(Graphics2D g2, Shape2D shape) {
		int[] xpoints = new int[shape.getNbPoint()];
		int[] ypoints = new int[shape.getNbPoint()];
		for (int i = 0; i < shape.getNbPoint(); i++) {
			Point2D proj_p = screenMVP.transform(shape.getPoint2D(i));
			xpoints[i] = (int) proj_p.getX();
			ypoints[i] = (int) proj_p.getY();
		}
		g2.setColor(shape.getColor());
		if (shape.drawWireframe()) {
			if (shape.getStroke() != null) {
				g2.setStroke(shape.getStroke());
			}
			g2.drawPolygon(xpoints, ypoints, shape.getNbPoint());
		} else {
			g2.fillPolygon(xpoints, ypoints, shape.getNbPoint());
		}
		if (shape.drawTransform()) {
			drawBase(g2, shape.getModel().toBase2D());
		}
	}
	
	public void drawAxis(Graphics2D g2) {
		Point2D point1 = new Point2D();
		Point2D point2 = new Point2D();
		
		// Affichage du repere canonique
		point1.setX(0);
		point1.setY(0);
		g2.setColor(axisColor);
		g2.setStroke(axisStroke);
		point2.setX(1);
		point2.setY(0);
		drawArrow(g2, point1, point2);
		point2.setX(0);
		point2.setY(1);
		drawArrow(g2, point1, point2);
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
		for (Point2D point : new Point2D[] { bottomLeft, bottomRight, topRight, topLeft }) {
			if (point.x < minX) {
				minX = (int) (Math.floor(point.x / step) * step);
			}
			if (point.x > maxX) {
				maxX = (int) ((Math.floor(point.x / step) + 1) * step);
			}
			if (point.y < minY) {
				minY = (int) (Math.floor(point.y / step) * step);
			}
			if (point.y > maxY) {
				maxY = (int) ((Math.floor(point.y / step) + 1) * step);
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
				g2.setStroke(axisStroke);
			} else {
				g2.setColor(Color.gray);
				g2.setStroke(gridStroke);
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
				g2.setStroke(axisStroke);
			} else {
				g2.setColor(Color.gray);
				g2.setStroke(gridStroke);
			}
			drawLine(g2, point1, point2);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		Transformation2D viewProj = Transformation2D.addTransformation(camera.projMat(), camera.viewMat());
		Transformation2D viewProjScreen = Transformation2D.addTransformation(viewport.screenMat(), viewProj);
		screenMVP = viewProjScreen;
		
		Stroke defaultStroke = g2.getStroke();
		
		// Affichage de la grille
		if (drawGrid) {
			drawGrid(g2);
		}
		
		// Affichage des axes
		if (drawAxis) {
			drawAxis(g2);
		}
		
		g2.setStroke(defaultStroke);
		
		// Affichage des formes
		for (Shape2D shape : model.getListShape()) {
			drawShape(g2, shape);
		}
		
//		if (eventButton == 2) {
//			g2.setColor(Color.darkGray);
//			g2.setStroke(gridStroke);
//			g2.drawLine(getWidth() / 2, getHeight() / 2, columnClicked, lineClicked);
//		}
	}
	
	
	/** Classe qui ecoute le modele et les click utilisateur */
	private class Handler extends MouseAdapter implements WorldModelListener, CameraListener, MouseMotionListener, MouseWheelListener, ComponentListener {
		
		///
		/// WorldModelListener
		///
		@Override
		public void shapeAdded(Shape2D shape) { }

		@Override
		public void shapeRemoved(Shape2D shape) { }

		@Override
		public void needRefresh() {
			repaint();
		}
		
		///
		/// MouseListener
		///
		@Override
		public void cameraChanged() {
			repaint();
		}
		
		///
		/// MouseListener
		///
		@Override
		public void mousePressed(MouseEvent ev) {
			columnClicked = ev.getX();
			lineClicked = ev.getY();
			eventButton = ev.getButton();
		}
		
		@Override
		public void mouseReleased(MouseEvent ev) {
			eventButton = 0;
		}
		
		@Override
		public void mouseDragged(MouseEvent ev) {
			if (camera == null) {
				return;
			}
			
			Rectangle2D.Double rect = camera.getRectangle();
			double dx = ((double) (columnClicked - ev.getX()) / getWidth()) * rect.getWidth();
			double dy = ((double) (lineClicked - ev.getY()) / getHeight()) * rect.getHeight();
			
			// Left click
			if (eventButton == MouseEvent.BUTTON1 && camera.getMoveable()) {
				camera.addTranslation(dx, -dy);
			}
			
			// Right click
			if (eventButton == MouseEvent.BUTTON2 && camera.getSpinnable()) {
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
			
			columnClicked = ev.getX();
			lineClicked = ev.getY();
		}
		
		///
		/// MouseWheelEvent
		///
		@Override
		public void mouseWheelMoved(MouseWheelEvent ev) {
			if (camera == null) {
				return;
			}
			
			if (camera.getZoomable()) {
				camera.addZoom(-ev.getWheelRotation() * (camera.getZ() / 10.0));
			}
		}
		
		///
		/// ComponentListener
		///
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
	}
}
