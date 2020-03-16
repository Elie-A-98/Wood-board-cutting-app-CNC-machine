package WoodPackage;

import java.util.*;
import java.awt.*;

// UNUSED !!!
// Hr algorithm is used instead

class Shelf {
	
	// Local position
	Point pos ;
	
	int currentWidth ;
	int currentHeight;
	
	int maxWidth ;
	
	ArrayList <Square> s = new ArrayList <Square> () ;
	
	boolean open ;
	
	public Shelf (Point pos , int maxWidth , int initialHeight){
		this.pos = pos;
		this.maxWidth = maxWidth ;
		this.open = true ;

		currentHeight = initialHeight ;
		
		currentWidth = 0 ;
		
	}
	
	public boolean CanAdd (Square x){
		if (currentWidth + x.width > maxWidth){
			return false;
		}
		return true ;
	}
	
	public void Add (Square x){
		
		x.SetPos(pos.x + currentWidth, pos.y);
		s.add(x);
				
		currentWidth += x.width;
		
		if ( x.height > this.currentHeight ){
			this.currentHeight = x.height;
		}
	}
	
	public void SetOpen (boolean open){
		this.open = open ;
	}
	
	public void Draw (Graphics g , Point pieceTopLeft ){
		for ( int i = 0 ; i < s.size() ; i ++ ){
			// Draw Square
			s.get(i).Draw(g,pieceTopLeft);
		}
	}
	
}

public class RecursiveShelfAlgorithm {
	
	private Comparator <Square> comp = new Comparator<Square> () {
		@Override
		public int compare(Square a, Square b) {
			return - a.compareTo(b);
		}
	};

	ArrayList <Shelf> shelfs = new ArrayList <Shelf> ();
	ArrayList <Square> s = new ArrayList <Square> ();
	
	Point topLeft ;
	int maxWidth;
	int maxHeight;
	
	// Used to get notes and recommendations
	int minWidth = 0;
	
	int spaceCovered = 0;
	int spaceWasted  ;
	int totalNbPieces = 0;
	int nbAddedPieces = 0;
	int overflow = 0;
	
	String notes ;
	
	public RecursiveShelfAlgorithm (int maxWidth , int maxHeight , Point topLeft) {
		this.topLeft = topLeft ;
		this.maxWidth = maxWidth ;
		this.maxHeight = maxHeight ;
		this.minWidth = 0 ;
		
		this.spaceWasted = this.maxHeight * this.maxWidth ;
		
		this.notes = "No Notes";
		
	}
	
	Comparator <Square> cp = new Comparator <Square> () {
		@Override
		public int compare(Square s1, Square s2) {
			if ( s1.height < s2.height ){
				return 1;
			}
			return -1 ;
		}
	};
	
	public void setSquares (ArrayList <Square> _s){
		s = _s;
		this.totalNbPieces = s.size();
		
		s.sort(cp);
	}
	
	public void Run () {
		FillShelf () ;
	}
	
	void FillShelf () {
		Shelf firstShelf = new Shelf (new Point (0,0),maxWidth,0);
		shelfs.add(firstShelf);
		FillShelf (0);
		
		RightPanel.rightPanel().UpdateResults("Recursive Shelf Algorithm", nbAddedPieces, spaceCovered, overflow, spaceWasted, notes);
	}
	
	void FillShelf (int squareIndex ){
		
		if ( squareIndex > s.size()-1 ){
			return ;
		}
		
		Shelf currentShelf = shelfs.get(shelfs.size()-1);
		Square currentSquare = s.get(squareIndex);
		
		if ( currentShelf.pos.y + currentShelf.currentHeight > maxHeight ){
			this.overflow = this.totalNbPieces - this.nbAddedPieces;
			
			System.out.println(this.minWidth+currentSquare.width);
			
			notes = "Try setting the width of the main piece to : " + (this.minWidth+currentSquare.width) ;
			
			return ;
		}
		
		if ( !currentShelf.CanAdd(currentSquare) ){
			currentShelf.SetOpen(false);
			
			int newPosX = currentShelf.pos.x;
			int newPosY =  currentShelf.pos.y + currentShelf.currentHeight ;
			Shelf f = new Shelf (new Point(newPosX,newPosY) , maxWidth , currentSquare.height);
			shelfs.add(f);
			FillShelf (squareIndex);
			return ;
		}
		
		currentShelf.Add(currentSquare);
		
		this.nbAddedPieces ++ ;
		this.spaceCovered += currentSquare.air;
		this.spaceWasted -= currentSquare.air ;
		
		FillShelf (++squareIndex);
		
		if ( currentShelf.currentWidth < minWidth ){
			minWidth = currentShelf.currentWidth ;
		}
		
	}
	
	public ArrayList<Integer> ExtractSqauresAsArray () {
		ArrayList <Integer> a = new ArrayList <Integer> () ;
		
		for ( int i = 0 ; i < s.size() ; i ++ ){
		}
		
		return a;
	}
	
	public void Draw (Graphics g ){
		for ( int i = 0 ; i < shelfs.size() ; i ++ ){
			shelfs.get(i).Draw(g,topLeft);
		}
	}

	
}
