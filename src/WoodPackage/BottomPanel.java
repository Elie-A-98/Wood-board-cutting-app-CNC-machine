package WoodPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;

import javax.swing.*;


public class BottomPanel extends JPanel {
	
	private static BottomPanel sn = null ;
		
	JButton applyB = new JButton ("Apply");
	JButton cncB = new JButton ("CNC");
	
	JButton zinB = new JButton ("+");
	JButton zoutB = new JButton ("-");
	
	JLabel zoomL = new JLabel ();
	
	Thread zoomThread ;
	CRunnable zoomRunnable ;
	// To control the speed of increase and decrease of zoom factor
	int counter ;
	
	
	private BottomPanel () {
		
		TopPanel.resetEvent.Subscribe(this.getClass(), this, "Refresh");
		
		this.setLayout(new FlowLayout (FlowLayout.LEADING));
		this.setBackground(Color.LIGHT_GRAY);
		
		LoadGraphics () ;
		SetListeners () ;
		
	}

	void LoadGraphics (){
		
		zoomL.setText(new DecimalFormat("#.##").format(Settings.getSettings().zoomFactor));
		
		this.setLayout(new GridLayout(1,2));
		
		JPanel leftP = new JPanel (new FlowLayout (FlowLayout.LEADING));
		JPanel rightP = new JPanel (new FlowLayout (FlowLayout.TRAILING));
		JPanel centerP = new JPanel () ;
		
		leftP.add(applyB);
		
		centerP.add(this.cncB);
		
		rightP.add(zoutB);
		rightP.add(zoomL);
		rightP.add(zinB);
		
		this.add(leftP);
		this.add(centerP);
		this.add(rightP);
		
	}
	
	void SetListeners () {
		
		applyB.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ArrayList <Square> squares = new ArrayList <Square> () ;
				
				for ( int i = 0 ; i < LeftPanel.get().coordPanels.size() ; i ++ ){
					int sx = LeftPanel.get().coordPanels.get(i).getDataX();
					int sy = LeftPanel.get().coordPanels.get(i).getDataY();
					if ( sx == 0 || sy == 0 ) continue;
					Square x = new Square (sx,sy);
					
					squares.add(x);
				}
				
				/*
				Random rand = new Random () ;
				for ( int i = 0 ; i < 500 ; i ++ ){
					int sx = rand.nextInt(50);
					int sy = rand.nextInt(50);
					
					Square x = new Square (sx,sy);
					
					squares.add(x);
				}*/
				
				ViewPanel.get().RunDrawAlgorithm(squares);
				
			}
		});
		
		MouseAdapter mouseAdapter = new MouseAdapter () {
			
			@Override
			public void mousePressed(MouseEvent e) {
				counter = 0 ;
				
				zoomRunnable = new CRunnable () {
					@Override
					public void ToDo() {
						
						// Change zoom factor and then wait
						try {
							float x = 0.1F ;
							if ( e.getSource().equals(zoutB)){
								x = -0.1F ;
							}
							
							Settings.getSettings().zoomFactor += x;
							zoomL.setText(new DecimalFormat("#.##").format(Settings.getSettings().zoomFactor));
							Thread.currentThread().sleep(100-counter);
							
							// To control the speed of increase or decrease
							if ( counter < 50 ) {
								counter ++ ;
							}
							
							ViewPanel.get().ZoomChanged();
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				};
				
				zoomThread = new Thread (zoomRunnable);
				
				zoomThread.start();
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				zoomRunnable.Terminate();
			}
		};
		
		zinB.addMouseListener(mouseAdapter);
		zoutB.addMouseListener(mouseAdapter);
		
		cncB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CNCWindow () ;
			}
		});
		
		
	}
	
	public static BottomPanel get () {
		if ( sn == null ){
			sn = new BottomPanel () ;
		}
		return sn ;
	}
	
	public void Refresh () {
		
		this.removeAll();
		
		LoadGraphics () ;
		
		this.repaint();
		this.revalidate();
		
	}

}
