package WoodPackage;

import java.awt.*;
import java.net.URL;
import java.nio.file.FileSystems;

import javax.swing.*;

public class CoordPanel extends JPanel {
	
	Settings s = Settings.getSettings() ;

	JLabel xl = new JLabel ("X");
	JLabel yl = new JLabel ("Y");
	
	JTextField xt;
	JTextField yt;
	
	int width ;
	
	boolean dd = true ;
	
	public CoordPanel (float width) {
		
		this.width = (int) width ;
		
		Main.WindowSizeChangedEvent.Subscribe(this.getClass(), this, "Refresh");

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		Load () ;
				
	}
	
	void Load(){
				
		xl.setForeground(Color.red);
		yl.setForeground(Color.red);
				
		int tWidth = Math.round ( ((width-2*xl.getPreferredSize().width )/2.0F ) * s.scaleFactor ) ;
				
		xt = new JTextField (tWidth);
		yt = new JTextField (tWidth);
		
		JPanel xp = new JPanel ();
		JPanel yp = new JPanel ();
		
		xp.setLayout(new FlowLayout(FlowLayout.LEADING));
		yp.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		xp.add(xl);
		xp.add(xt);
		
		yp.add(yl);
		yp.add(yt);
		
		this.add(xp);
		this.add(yp);
		
	}
	

	public void SetEditable (boolean enabled) {
		xt.setEditable(enabled);
		yt.setEditable(enabled);		
	}
	
	
	public JTextField getXt() {
		return xt;
	}

	public JTextField getYt() {
		return yt;
	}
	
	public int getDataX () {
		if ( xt.getText().isEmpty() ) return 0 ;
		return Integer.parseInt(xt.getText().toString());
	}
	
	public int getDataY () {
		if ( yt.getText().isEmpty() ) return 0 ;
		return Integer.parseInt(yt.getText().toString());
	}
	
	public void Refresh (){
				
		this.removeAll();
		
		Load ();
		
		this.repaint();
		this.revalidate();
		
	}

	
}
