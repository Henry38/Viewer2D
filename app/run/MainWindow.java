package run;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import viewer2D.data.Camera;
import viewer2D.data.WorldModel;
import viewer2D.geometry.Rectangle;
import viewer2D.graphic.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D viewer;
	
	/** Constructeur */
	public MainWindow(Camera camera) {
		super("World2D");
		
		WorldModel world = new WorldModel();
		
		Rectangle rectangle = new Rectangle(4, 2);
		world.add(rectangle);
		
		viewer = new Viewer2D(world, 640, 480);
		
		panneau = new JPanel(new BorderLayout());
		panneau.setLayout(new BorderLayout());
		panneau.add(viewer, BorderLayout.CENTER);
		
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
