package WoodPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TopPanel extends JPanel {
	
	public static Event resetEvent = new Event () ;
	
	private static TopPanel sn = null ;
	
	float width ;

	Settings s = Settings.getSettings();
	
	JLabel l = new JLabel ("Main Piece Size");
	JLabel unitl = new JLabel ();
	
	JButton applyB = new JButton ("Apply");
	JButton resetB = new JButton ("Reset");
	
	JPanel rightP ;
	
	CoordPanel coordP ;
	
	Color bColor = Color.LIGHT_GRAY;
	
	private TopPanel () {
		this.width = s.width/2 ;
		
		TopPanel.resetEvent.Subscribe(this.getClass(), this, "Refresh");
		
		Load ();
		SetListener () ;
		
	}
	
	public static TopPanel getPanel () {
		if ( sn == null ){
			sn = new TopPanel ();
		}
		return sn;
	}
	
	private void Load () {
		
		coordP = new CoordPanel (width);
		
		rightP = new JPanel () ;
		rightP.setBackground(bColor);
		rightP.setLayout(new FlowLayout (FlowLayout.RIGHT));
		rightP.add(l);
		rightP.add(coordP);
		rightP.add(applyB);
		rightP.add(resetB);
		
		unitl.setForeground(Color.blue);
		unitl.setText("  Unit  ("+Settings.getSettings().unit.toString()+")");
		
		this.setBackground(bColor);
		this.setLayout(new BorderLayout());
		
		this.add(unitl , BorderLayout.WEST);
		this.add(rightP,BorderLayout.EAST);
		
	}
	
	private void SetListener () {
		applyB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ( !coordP.getXt().getText().isEmpty() && !coordP.getYt().getText().isEmpty()){
					coordP.SetEditable(false);
					
					int sx = Integer.parseInt(coordP.getXt().getText());
					int sy = Integer.parseInt(coordP.getYt().getText());
					ViewPanel.get().DrawMainPiece(sx,sy);
					
					return ;
				}
				
				JOptionPane.showMessageDialog(null, "Enter main piece width and height");
			}
		});
		
		resetB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				resetEvent.Launch();
			}
		});
	}
	
	public void Refresh () {
		this.removeAll();
		
		Load () ;
		
		this.repaint();
		this.revalidate();
	}
	
}
