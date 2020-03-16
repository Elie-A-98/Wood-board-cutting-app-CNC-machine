package WoodPackage;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Settings {
	
	public static enum Unit {mm,inch};
	
	private static Settings s = null ;
	
	public int initialWidth , initialHeight; // The width and height which the application was built on
	
	public int width , height ;
	public Unit unit ;
	
	// the view panel piece scale factor ( multiply this by 1 to get vpWidth and vpHeight)
	public float vpScaleFactor ;
	
	public float zoomFactor = 1 ;
	
	public float scaleFactor = 0.036F ;
	
	private Settings () {
		Main.WindowSizeChangedEvent.Subscribe(this.getClass(), this, "Refresh");
	}
	
	public void SetSize (int width , int height ){
		this.width = width;
		this.height = height ;
	}
	
	public static Settings getSettings () {
		if ( s == null ){
			s = new Settings () ;
		}
		return s ;
	}
	
	public void Refresh () {
		//SetSize(MainFrame.getFrame().getWidth() , MainFrame.getFrame().getHeight());
	}
	
	/*
	 
	 private boolean firstLoad = true ;
	 
	 f.addComponentListener(new ComponentAdapter () {
			@Override
			public void componentResized (ComponentEvent e) {
				if ( !firstLoad ){
					Main.WindowSizeChangedEvent.Launch();
				}
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				firstLoad = false ;
			}
		});
	 */

}
