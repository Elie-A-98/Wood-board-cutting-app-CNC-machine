package WoodPackage;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;

public class Main {
	
	public static Event WindowSizeChangedEvent = new Event () ;
	public static boolean firstLoad = true ;
		
	public static void main(String[] args) throws Exception {
		
		Settings s = Settings.getSettings();
		s.initialWidth = 1000 ;
		s.initialHeight = 800 ;
		s.SetSize(1000,800);
		s.unit = Settings.Unit.mm;

		MainFrame f = new MainFrame () ;
		
	}
	


}
