package WoodPackage;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
	
	private static RightPanel sn = null ;
	
	JLabel results = new JLabel ();
	
	String algorithmused ;
	int nbAddedPieces ;
	int spaceCovered ;

	int overflowCount ;
	int spaceWasted ;
	
	String notes;
	 
	
	private RightPanel () {
		
		TopPanel.resetEvent.Subscribe(this.getClass(), this, "Refresh");
		
		Initialize () ;
		LoadGraphics () ;
	}
	
	public static RightPanel rightPanel () {
		if ( sn == null ){
			sn = new RightPanel () ;
		}
		return sn ;
	}
	
	void Initialize () {
		this.setLayout(new BorderLayout () {
			@Override
			public Dimension preferredLayoutSize (Container target) {
				Dimension sd = super.preferredLayoutSize(target);
				System.out.println(LeftPanel.get().preferredSize().width);
				sd.width = Math.min(150,sd.width);
				
				return sd ;
			}
		});
	}
	
	void LoadGraphics () {
		
		this.add(results,BorderLayout.NORTH);
		
		
	}
	
	void UpdateResults (String algorithmUsed , int nbAddedPieces , int spaceCovered , int overflowCount , int spaceWasted , String notes ) {
		
		this.algorithmused = algorithmUsed;
		this.nbAddedPieces = nbAddedPieces ;
		this.spaceCovered = spaceCovered ;
		this.overflowCount = overflowCount ;
		this.spaceWasted = spaceWasted ;
		this.notes = notes ;
		
		results.setText("<html>"
				+ "<p style='margin-left:40px; margin-top:25px; margin-right:40px;'>"
				
				+"<span style='color:red;'>Results:</span> <br><br>"
				
				+"<table border='0'>"
				
				+"<tr><td style='color:blue;'>Number of pieces</td> <td>" +this.nbAddedPieces+ "</td></tr>"
				+"<tr><td style='color:blue;'>Space covered</td> <td>" +this.spaceCovered+ "</td></tr>"
				
				+"<tr><td style='color:blue;'>Overflow</td> <td>" +this.overflowCount+ "</td></tr>"
				+"<tr><td style='color:blue;'>Space wasted</td> <td>" +this.spaceWasted+ "</td></tr>"
				
				+"</table> <br><br>"
				
				+"<span style=color:orange;'>Notes: </span>"+this.notes
				
				+ "</p>"
				+ "</html>");
	}
	
	public void Refresh () {
		
		this.removeAll();
		
		LoadGraphics () ;
		
		this.repaint();
		this.revalidate();
		
	}
}
