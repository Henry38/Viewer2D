package run;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import viewer2D.data.WorldModel;
import viewer2D.geometry.Rectangle;
import viewer2D.graphic.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D viewer, viewer2;
	
	/** Constructeur */
	public MainWindow() {
		super("World2D");
		
		WorldModel world = new WorldModel();
		
		Rectangle rectangle = new Rectangle(4, 2);
		rectangle.drawTransform(true);
		rectangle.setColor(Color.yellow);
		world.add(rectangle);
		
		viewer = new Viewer2D(world, 640, 480);
		viewer2 = new Viewer2D(world, 640, 480);
		
		viewer.addExternCamera(viewer2.getCamera());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewer, viewer2);
		
		panneau = new JPanel(new BorderLayout());
		panneau.setLayout(new BorderLayout());
		panneau.add(splitPane, BorderLayout.CENTER);
		
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
