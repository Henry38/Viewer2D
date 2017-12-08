package controler;

import java.util.EventListener;

public interface ViewerListener extends EventListener {
	public void pointPressed(double x, double y); 
}
