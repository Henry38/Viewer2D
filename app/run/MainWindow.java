package run;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Viewer2D.data.Camera;
import com.Viewer2D.geometry.Rectangle;
import com.Viewer2D.graphics.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D viewer;
	
	/** Constructeur */
	public MainWindow(Camera camera) {
		super("World2D");
		
		viewer = new Viewer2D(camera, 640, 480);
		
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
		c.rotate(Math.PI / 16);
		c.setOx(2);
		
		viewer.addShape(c);
	}
}
