package run;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import com.Viewer2D.data.Camera;
import com.Viewer2D.data.World2D;
import com.Viewer2D.geometry.Point;
import com.Viewer2D.geometry.Rectangle;

public class Application {
	
	/** Lancement de l'application */
	public static void main(String[] args) {
		
		final World2D world = new World2D();
		final Camera camera = new Camera();
		
		Rectangle c = new Rectangle(4, 2);
		c.rotate(Math.PI / 16);
		c.setOx(2);
		
		Point p1 = new Point(2, 0);
		p1.rotate(Math.PI / 8);
		p1.setColor(Color.GREEN);
		
		world.addShape(c);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MainWindow fen = new MainWindow(world, camera);
				fen.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent ev) {}
					
					public void keyReleased(KeyEvent ev) {}
					
					public void keyPressed(KeyEvent ev) {
						if (ev.getKeyCode() == KeyEvent.VK_ESCAPE) {
							fen.dispose();
							System.exit(0);
						}
					}
				});
				fen.setFocusable(true);
				fen.setVisible(true);
			}
		});
	}
}
