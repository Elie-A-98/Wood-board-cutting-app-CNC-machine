package WoodPackage;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class DownArrowButton extends JButton {

	public DownArrowButton (){
		ScaledImageIcon x = new ScaledImageIcon ("down_arrow.png",25,25);
		this.setIcon(x);
		
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.WHITE);
		this.setOpaque(false);
	}
	
}
