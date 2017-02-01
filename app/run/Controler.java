package run;

public class Controler extends Thread {
	
	private long framerate = 1000 / 60;
    // time the frame began. Edit the second value (60) to change the prefered FPS (i.e. change to 50 for 50 fps)
    private long frameStart;
    // number of frames counted this second
    private long frameCount = 0;
    // time elapsed during one frame
    private long elapsedTime;
    // accumulates elapsed time over multiple frames
    private long totalElapsedTime = 0;
    // the actual calculated framerate reported
    private long reportedFramerate;
    
	/** Constructeur */
	public Controler() {
		super();
		setDaemon(true);
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			frameStart = System.currentTimeMillis();
			/////////////////
			// Data performed
			/////////////////
			
			// calculate the time it took to render the frame
            elapsedTime = System.currentTimeMillis() - frameStart;
            // sync the framerate
            try {
                // make sure framerate milliseconds have passed this frame
                if (elapsedTime < framerate) {
                    Thread.sleep(framerate - elapsedTime);
                }
                else {
                    // don't starve the garbage collector
                    Thread.sleep(5);
                }
            }
            catch (InterruptedException e) {
                break;
            }
            ++frameCount;
            totalElapsedTime += (System.currentTimeMillis() - frameStart);
            if (totalElapsedTime > 1000) {
                reportedFramerate = (long) ((double) frameCount / (double) totalElapsedTime * 1000.0);
                // show the framerate in the applet status window
                System.out.println("fps: " + reportedFramerate);
                // repaint();
                frameCount = 0;
                totalElapsedTime = 0;
            }
		}
	}
}
