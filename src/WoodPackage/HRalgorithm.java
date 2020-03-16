package WoodPackage;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

class Space {
	Point pos ;
	int width ;
	int height ;
	
	int area ;
	
	boolean bounded ;
	
	ArrayList <Square> squares = new ArrayList <Square> ();
	
	public Space(Point pos, int width, int height,boolean _bounded) {
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.bounded = _bounded;
		
		area = width * height ;
	}
	
	boolean canAdd (Square x){
		
		for ( int i = 0 ; i < 2 ; i ++){
			if ( x.width <= width && x.height <= height ){
				return true ;
			}
			
			x.Rotate90();
			
		}
		
		return false ;
	}
	
	public void Add (Square s){
		s.pos.x = pos.x;
		s.pos.y = pos.y;
		
		//this.squares.add(s);
		
		this.bounded = true ;
	}
	
}

public class HRalgorithm {
	
	Space initialSpace ;
	
	Point topLeft ;
	
	int spaceCovered = 0;
	int spaceWasted  ;
	int totalNbPieces = 0;
	int nbAddedPieces = 0;
	int overflow = 0;
	
	String notes ;
	
	ArrayList <Space> shelfs = new ArrayList <Space> ();
	ArrayList <Square> squares = new ArrayList <Square> ();
	
	ArrayList <Square> newSquares = new ArrayList <Square> () ;
	
	private Comparator <Square> comp = new Comparator<Square> () {
		@Override
		public int compare(Square a, Square b) {
			if ( a.air < b.air ){
				return 1 ;
			}
			else return -1 ;
		}
	};
	
	public HRalgorithm (Point _topLeft ,int width , int height){
		this.topLeft = _topLeft;
		initialSpace = new Space (new Point (0,0),width,height,false);
		
		spaceWasted = width*height;
		
		notes = "no notes";
	}
	
	public void setSquares (ArrayList <Square> _s){
		squares = _s;
		squares.sort(comp);
		
		this.totalNbPieces = squares.size();
	}
	
	public void Run () {
		long startTime = System.nanoTime();
		
		UnboundedPacking(initialSpace);
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Running time : " + totalTime);
		
		this.overflow = this.totalNbPieces - this.nbAddedPieces;
		if (overflow > 0){
			this.notes = "There are "+overflow+" piece(s) not added, change the main piece width or height";
		}else {
			this.notes = "no notes";
		}
		
		RightPanel.rightPanel().UpdateResults("Heuristic Algorithm", nbAddedPieces, spaceCovered, overflow, spaceWasted, notes);
	}

	private void BoundedPacking (Space s2){
		
		Square pickedSquare = null ;
		int indexPickedSquare = 0;
		
		for ( int i = 0 ; i < squares.size() ; i ++){
			Square currentSquare = squares.get(i);
			
			// Can add to Space s2
			if (s2.canAdd(currentSquare)){
				System.out.println("R CA");
				pickedSquare = currentSquare ;
				indexPickedSquare = i;
				break ;
			}
			
		}
		
		if (pickedSquare == null ){
			// No square that fit was found
			return ;
		}else {
			s2.Add(pickedSquare);
			squares.remove(indexPickedSquare);
			
			this.newSquares.add(pickedSquare);
			
			this.nbAddedPieces ++;
			this.spaceCovered += pickedSquare.air;
			
			Point s3Pos = new Point (s2.pos.x,s2.pos.y+pickedSquare.height);
			int s3Width = pickedSquare.width;
			int s3Height = s2.height - pickedSquare.height;
			
			Point s4Pos = new Point (s2.pos.x + pickedSquare.width , s2.pos.y);
			int s4Width = s2.width - pickedSquare.width;
			int s4Height = s2.height;
			
			Space s3 = new Space (s3Pos , s3Width , s3Height , true);
			Space s4 = new Space (s4Pos , s4Width , s4Height , true);
			
			
			if (s3.area > s4.area) {
				BoundedPacking (s3);
				BoundedPacking (s4);
			}else {
				BoundedPacking (s4);
				BoundedPacking (s3);
			}
		}
		
	}
	
	private void UnboundedPacking (Space s) {
			if (squares.size() == 0) return ;

			Square pickedSquare = null ;
			int pickedIndex = 0 ;
			
			for ( int i = 0 ; i < squares.size() ; i ++ ){
				Square currentSquare = squares.get(i);

				if ( currentSquare.width < currentSquare.height){
					//heuristic packing strategy --> pack it flat
					currentSquare.Rotate90();
				}
				if ( s.canAdd(currentSquare) ){
					System.out.println("P CA " + currentSquare.width);
					pickedSquare = currentSquare ;
					pickedIndex = i;
					break;
				}
				
				
			}
			
			if (pickedSquare == null){
				// No square that fits was found
				return ;
			}else {
				
				s.Add(pickedSquare);
				squares.remove(pickedIndex);
				
				this.newSquares.add(pickedSquare);
				
				this.nbAddedPieces ++;
				this.spaceCovered += pickedSquare.air;
				
				Point s1Pos = new Point (s.pos.x , s.pos.y + pickedSquare.height);
				int s1Width = s.width;
				int s1Height = s.height - pickedSquare.height;
				
				Point s2Pos = new Point (s.pos.x + pickedSquare.width , s.pos.y);
				int s2Width = s.width - pickedSquare.width;
				int s2Height = pickedSquare.height;
				
				Space s1 = new Space (s1Pos,s1Width,s1Height,false);
				Space s2 = new Space (s2Pos,s2Width,s2Height,true);
				
				s = s1 ;
				
				BoundedPacking (s2);
				
				UnboundedPacking(s);
				
			}
			
		
	}
	
	public ArrayList <Square> getNewSquares () {
		return this.newSquares;
	}
	
	public void Draw (Graphics g ){
		for ( int i = 0 ; i < newSquares.size() ; i ++ ){
			newSquares.get(i).Draw(g,topLeft);
		}
	}

}
