package WoodPackage;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class RightArrowButton extends JButton {

	public RightArrowButton (){
		ScaledImageIcon x = new ScaledImageIcon ("right_arrow.png",25,25);
		this.setIcon(x);
		
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.WHITE);
		this.setOpaque(false);
	}
	
}
