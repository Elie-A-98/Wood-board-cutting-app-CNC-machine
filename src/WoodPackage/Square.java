package WoodPackage;

import java.awt.*;
import java.util.Random;

public class Square implements Comparable<Square> {

	Point pos = new Point () ;
	
	int width , height;
	int air ;

	boolean isDrawn = false ;
	int r, gg, b;
	
	public Square(int width, int height) {
 		this.width = width;
		this.height = height;
		
		air = width * height ;
	}
	
	public Square(Point pos , int width, int height) {
		this.pos = pos;
		this.width = width;
		this.height = height;
		
		air = width * height ;
	}
	
	public void SetPos ( int x , int y ){
		pos.x = x ;
		pos.y = y ;
	}
	
	public void Rotate90 () {
		int temp = width ;
		width = height ;
		height = temp ;
	}
	
	public void Draw (Graphics g , Point pieceTopLeft){
		
		int sx = Math.round(width * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor) ;
		int sy = Math.round(height * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor);
		
		// pos.x will be the shelf current width
		int x = pieceTopLeft.x + Math.round(pos.x * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor) ;
		int y = pieceTopLeft.y + Math.round(pos.y * Settings.getSettings().vpScaleFactor * Settings.getSettings().zoomFactor) ;
		
		if (!isDrawn){
			do {
				Random rand = new Random () ;
				r = rand.nextInt(255);
				gg = rand.nextInt(255);
				b = rand.nextInt(255);
			}while ( r!=gg && gg!=b && r!=255 && r>175 && gg<175 && b>175);
		}
		
		g.setColor(new Color(r,gg,b));
		g.fillRect(x,y,sx,sy);
		
		g.setColor(Color.white);
		g.drawString(width+"x"+height, x + Math.round(sx/2.0f) - 7 , y +Math.round(sy/2.0f) + 4 );
		
		this.isDrawn = true ;
	}

	@Override
	public int compareTo(Square x) {
		if ( this.air > x.air ){
			return 1 ;
		}
		
		return -1 ;
	}
	
}
