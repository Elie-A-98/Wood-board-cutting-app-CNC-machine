package WoodPackage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ScaledImageIcon extends ImageIcon  {
	
	String imageName ;
	
	public ScaledImageIcon (String imageName , int sx , int sy){
		
		this.imageName = imageName ;
		
		try {
			
			Image image ;
			Path p = FileSystems.getDefault().getPath(imageName).toAbsolutePath();
			image =  ImageIO.read(new File(p.toString())).getScaledInstance(sx, sy,Image.SCALE_DEFAULT);
			super.setImage(image);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	

}
