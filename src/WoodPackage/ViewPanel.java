package WoodPackage;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class ViewPanel extends JPanel {
	
	private static ViewPanel sn = null ;
	
	RecursiveShelfAlgorithm rsa ;
	HRalgorithm hr ;
	
	// String x and String y
	int sx = 0 ; 
	int sy = 0 ; 
	
	int padding = 20 ;
	
	Point topLeft ;
	int width = 0 ;
	int height = 0 ;
	
	boolean tempClear = false ;
				
	private ViewPanel () {
		
		TopPanel.resetEvent.Subscribe(this.getClass(), this, "Clear");
				
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		this.setPreferredSize(new Dimension(1000,1000));
		
		topLeft = new Point (padding,padding);
	}
	
	public static ViewPanel get () {
		if ( sn == null ){
			sn = new ViewPanel () ;
		}
		return sn ;
	}
	
	public void Clear () {
		tempClear = true ;
		
		rsa = null;
		hr = null ;
		
		this.repaint();
		this.revalidate();
	}
	
	public void DrawMainPiece (int _sx , int _sy){
		sx = _sx;
		sy = _sy;
		
		width  = this.getWidth() - 2*padding ;
		height = this.getHeight() - 2*padding ;
		
		// Fixing size to match width , height , and shape
		int minWH = (width<height)? width : height ;
				
		if ( sx > sy ){
			// Cast sx to float so if < 1 --> don't be equal to 0.0
			Settings.getSettings().vpScaleFactor = minWH/(float)sx ;
		}else {
			Settings.getSettings().vpScaleFactor = minWH/(float)sy;
		}
		
		width = Math.round(sx * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor);
		height = Math.round(sy * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor);
		
		
		// Clamp width and height to be > 0 --> if 0-->  later --> 0*float = 0 ;
		width = Math.max(1, width);
		height = Math.max(1, height);
		
		repaint ();
		revalidate();
		
	}
	
	public void RunDrawAlgorithm (ArrayList <Square> s){
		/*
		rsa = new RecursiveShelfAlgorithm (sx,sy,topLeft) ;
		
		rsa.setSquares(s);
		
		rsa.Run();*/
		
		hr = new HRalgorithm (topLeft,sx,sy);
		hr.setSquares(s);
		hr.Run();
		
		repaint () ;
		revalidate();
		
	}
	
	public void ZoomChanged () {
		this.repaint();
		// To notify JScrolPane
		this.revalidate();
	}
	
	
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		
		int recSizeX = Math.round(width * Settings.getSettings().zoomFactor);
		int recSizeY = Math.round(height  * Settings.getSettings().zoomFactor);
		
		// Has to stay here !!!
		this.setPreferredSize(new Dimension(recSizeX + 2*padding, recSizeY + 2*padding));
		
		if ( tempClear ){
			g.setColor(Color.WHITE);
			g.fillRect(topLeft.x , topLeft.y , recSizeX , recSizeY );
			tempClear = false ;
			return ;
		}
		
		// Drawing Main Piece
		g.drawRect(topLeft.x , topLeft.y , recSizeX , recSizeY );
		
		int centerX = topLeft.x + recSizeX/2;
		int centerY = topLeft.y + recSizeY/2 ;
		g.drawString(String.valueOf(sx), centerX, padding);
		g.drawString(String.valueOf(sy), padding/2 , centerY);
		
		//Draw Algorithm
		/*if ( rsa != null ){
			rsa.Draw(g);
		}*/
		
		if (hr != null ){
			hr.Draw(g);
		}
		
	}
	


}
