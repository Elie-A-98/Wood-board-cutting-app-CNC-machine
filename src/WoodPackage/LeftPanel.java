package WoodPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPanel extends JPanel {
	
	private static LeftPanel sn = null ;
	
	private static int counter = 1 ;
	
	private Settings s = Settings.getSettings();
	
	int width ;
	
	AddButton addB = new AddButton () ;
	float addBWidth = (float) addB.getPreferredSize().getWidth();
	
	JTextField addt;
		
	JPanel mp = new JPanel () ;
	
	ArrayList <CoordPanel> coordPanels ;
	
	GridBagConstraints gc;
	
	private LeftPanel (){		
		this.width = Math.round(s.width/2) ;
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		TopPanel.resetEvent.Subscribe(this.getClass(), this, "Refresh");
		
		Load () ;
	}
	
	public static  LeftPanel get (){
		if ( sn == null ){
			sn = new LeftPanel () ;
		}
		return sn ;
	}
	
	private void Load () {
		
		addB = new AddButton () ;
		
		addB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ( !addt.getText().isEmpty() ){
					int x = Integer.parseInt(addt.getText().toString());
					for ( int i = 0 ; i < x ; i ++ ){
						AddCoord () ;
					}
				}else {
					// TODO : Write warning in the bottom left corner of the window
				}
			}
		});
		
		counter = 1;
		coordPanels = new ArrayList <CoordPanel> ();
		
		mp = new JPanel (new GridBagLayout () );
		gc = new GridBagConstraints () ;
		
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		
		gc.gridwidth = GridBagConstraints.REMAINDER;
		JLabel addPiecesL = new JLabel ("Add multiple pieces");
		addPiecesL.setForeground(Color.RED);
		mp.add(addPiecesL,gc);
		mp.add(Box.createRigidArea(new Dimension(1,10)),gc);
		
		JLabel addL = new JLabel ("Add");
		
		int tWidth = (int)( (width - (addBWidth+addL.getPreferredSize().width))*Settings.getSettings().scaleFactor );
		
		addt = new JTextField (tWidth);
		
		JPanel addP = new JPanel (new FlowLayout (FlowLayout.LEADING));
		addP.add(addL);
		addP.add(addt);
		
		gc.gridwidth = 3;
		mp.add(addP,gc);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		mp.add(addB, gc);
		
		mp.add(Box.createRigidArea(new Dimension(1,20)),gc);
		
		JLabel widthL  = new JLabel ("Width");
		widthL.setForeground(Color.RED);
		JLabel heightL  = new JLabel ("Height");
		heightL.setForeground(Color.RED);

		gc.gridwidth = 1 ;
		mp.add(new JLabel (""),gc);
		gc.anchor = GridBagConstraints.CENTER;
		mp.add(widthL,gc);
		mp.add(heightL,gc);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		mp.add(new JLabel (""),gc);
		
		AddCoord();
		
		this.add( mp);
		
	}
	
	private void AddCoord () {
		
		JLabel counterL = new JLabel (String.valueOf(counter++));
		
		float tWidth =(float) (width-addBWidth-counterL.getPreferredSize().getWidth() - 96.0F);
		CoordPanel newCoordPanel = new CoordPanel (tWidth) ;
		
		gc.gridwidth = 1;
		mp.add(counterL, gc);
		
		gc.gridwidth = GridBagConstraints.RELATIVE;
		mp.add(newCoordPanel,gc);
		
		AddButton newAddButton = new AddButton ();
		newAddButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddCoord () ;
			}
		});
		
		gc.gridwidth = GridBagConstraints.REMAINDER;
		mp.add(newAddButton , gc);

		coordPanels.add(newCoordPanel);
		
		this.repaint();
		this.revalidate();
	}
	
	public void Refresh () {
		
		this.removeAll();
		
		Load () ;
		
		this.repaint();
		this.revalidate();
		
	}

	
}
