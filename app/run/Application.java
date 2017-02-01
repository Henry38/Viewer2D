package run;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import viewer2D.data.Camera;

public class Application {
	
	/** Lancement de l'application */
	public static void main(String[] args) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Camera camera = new Camera();
				final MainWindow fen = new MainWindow(camera);
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
