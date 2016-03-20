package run;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Viewer2D.data.Camera;
import com.Viewer2D.data.World2D;
import com.Viewer2D.graphics.Viewer2D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	private Viewer2D scene;
	
	/** Constructeur */
	public MainWindow(World2D world, Camera camera) {
		super("World2D");
		
		scene = new Viewer2D(world, camera, 640, 480);
		world.addObserver(scene);
		
		panneau = new JPanel(new BorderLayout());
		panneau.setLayout(new BorderLayout());
		panneau.add(scene, BorderLayout.CENTER);
		
		setContentPane(panneau);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
