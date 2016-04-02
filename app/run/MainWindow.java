package run;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import viewer2D.data.Camera;
import viewer2D.geometry.Rectangle;
import viewer2D.graphic.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D viewer;
	
	/** Constructeur */
	public MainWindow(Camera camera) {
		super("World2D");
		
		viewer = new Viewer2D(640, 480);
		
		panneau = new JPanel(new BorderLayout());
		panneau.setLayout(new BorderLayout());
		panneau.add(viewer, BorderLayout.CENTER);
		
		addShape();
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void addShape() {
		Rectangle c = new Rectangle(4, 2);
		//c.rotate(Math.PI / 16);
		
		viewer.getModel().add(c);
	}
}
