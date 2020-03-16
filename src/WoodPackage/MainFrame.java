package WoodPackage;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class MainFrame extends JFrame  {
	
	private Settings s = Settings.getSettings();
	
	public MainFrame () {
		//Main.WindowSizeChangedEvent.Subscribe(this.getClass(), this, "Refresh");
		
		LoadGraphics () ;
		
		this.setSize(Settings.getSettings().width, Settings.getSettings().height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	private void LoadGraphics () {
		this.getContentPane().setBackground(Color.white);
		
		this.getContentPane().add(TopPanel.getPanel() , BorderLayout.NORTH);
		this.add(new JScrollPane (LeftPanel.get(),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) , BorderLayout.WEST);
		this.add(new JScrollPane (ViewPanel.get(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		this.add(BottomPanel.get(),BorderLayout.SOUTH);
		this.add(RightPanel.rightPanel(),BorderLayout.EAST);
		
	}
	
	public void RefreshViewPanel () {
		this.add(new JScrollPane (ViewPanel.get(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}
	
	public void Refresh () {
		
		System.out.println("ASDASD");

		//this.getContentPane().removeAll();
		
		LoadGraphics ();
		
		this.repaint();
		this.revalidate();
	}
	
}
