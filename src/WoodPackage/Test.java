package WoodPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import processing.serial.*;
import processing.core.*;

import javax.swing.*;

public class Test extends PApplet {
	
	public Test () {
		
		Arduino arduino = Arduino.arduino();
		
		try {
			
			
			arduino.Connect("COM7");
			
			delay (2000);
			
			
			arduino.WriteChar('c');
			
			arduino.WriteChar('c');
			
			delay (1000);
			
			arduino.WriteChar('c');
			
			
		} catch (Exception e1) {
			System.out.println("ERROR ");
			e1.printStackTrace();
		}finally {
			arduino.Stop();
		}
		
		
		
	}
	
}
