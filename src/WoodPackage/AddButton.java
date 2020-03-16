package WoodPackage;

import javax.swing.*;
import java.awt.*;

public class AddButton extends JButton {
	
	

	public AddButton (){		
		ScaledImageIcon x = new ScaledImageIcon ("Add.png",14,14);
		this.setIcon(x);
		
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.WHITE);
		this.setOpaque(false);
		
	}
	
}
