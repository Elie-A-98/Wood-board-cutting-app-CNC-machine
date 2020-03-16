package WoodPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import jssc.SerialPortException;

import processing.serial.*;
import processing.core.*;

public class CNCWindow extends JFrame {
	
	Arduino arduino = Arduino.arduino() ;
	
	boolean connected = false ;
	
	// Top Panel
	JPanel topP = new JPanel () ;
	JLabel testComL = new JLabel ("Select COM:");
	JComboBox comCB = new JComboBox (arduino.getComList());
	JButton testB = new JButton ("Test Connection");
	
	// Center Panel
	JPanel centerP = new JPanel ();
	JPanel xyCalibrateP = new JPanel () ;
	JPanel zCalibrateP = new JPanel () ;
	UpArrowButton yupB = new UpArrowButton () ;
	DownArrowButton ydownB = new DownArrowButton () ;
	LeftArrowButton xleftB = new LeftArrowButton () ;
	RightArrowButton xrightB = new RightArrowButton () ;
	
	UpArrowButton zupB = new UpArrowButton () ;
	DownArrowButton zdownB = new DownArrowButton () ;
	
	// Bottom Panel
	JPanel bottomP = new JPanel (new GridLayout (1,2)) ;
	JPanel bottomLeftP = new JPanel (new FlowLayout(FlowLayout.LEADING));
	JPanel bottomRightP = new JPanel (new FlowLayout(FlowLayout.TRAILING));
	JPanel bottomCenterP = new JPanel () ;
	JButton setOriginB = new JButton ("Set Origin(0,0,0)");
	JButton drawB = new JButton ("Draw");
	JButton stopB = new JButton ("Stop");

	public  CNCWindow  () {
		this.setTitle("CNC");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		
		LoadGraphics () ;
		AddListeners () ;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
		this.setVisible(true);
		
	}
	
	void LoadGraphics () {
		
		topP.removeAll () ;
		
		topP.add(testComL);
		topP.add(comCB);
		topP.add(testB);
		if (!this.connected ){
			topP.add(new FalseLabel ());
			centerP.setVisible(false);
		}else {
			topP.add(new CorrectLabel ());
			centerP.setVisible(true);
		}
		
		// Calibrate Panel
		xyCalibrateP.removeAll();
		xyCalibrateP.setLayout(new GridLayout (3,3));
		xyCalibrateP.add(new JLabel (""));
		xyCalibrateP.add(yupB);
		xyCalibrateP.add(new JLabel (""));
		xyCalibrateP.add(xleftB);
		xyCalibrateP.add(new JLabel (""));
		xyCalibrateP.add(xrightB);
		xyCalibrateP.add(new JLabel (""));
		xyCalibrateP.add(ydownB);
		xyCalibrateP.add(new JLabel (""));
		
		zCalibrateP.removeAll();
		zCalibrateP.setLayout(new GridLayout (3,1));
		zCalibrateP.add(zupB);
		zCalibrateP.add(new JLabel (""));
		zCalibrateP.add(zdownB);
		
		centerP.add(xyCalibrateP);
		centerP.add(zCalibrateP);
		
		bottomLeftP.removeAll();
		bottomRightP.removeAll();
		bottomP.removeAll();
		
		bottomLeftP.add(this.setOriginB);
		bottomCenterP.add(this.stopB);
		bottomRightP.add(this.drawB);
		
		bottomP.add(bottomLeftP);
		bottomP.add(this.bottomCenterP);
		bottomP.add(bottomRightP);
		
		this.getContentPane().add(topP,BorderLayout.NORTH);
		this.getContentPane().add(centerP,BorderLayout.CENTER);
		this.getContentPane().add(bottomP, BorderLayout.SOUTH);
		
	}
	
	void AddListeners () {
		
		testB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				try {
					arduino.Connect(comCB.getSelectedItem().toString());
					connected = true ;
				}catch (Exception e){
					connected = false ;
					JOptionPane.showMessageDialog(null, e.getMessage());
				}finally {
					getContentPane().removeAll();
					
					
					getContentPane().repaint();
					getContentPane().revalidate();
					
					LoadGraphics();
					
				}
			}
		});
		
		this.yupB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				
				arduino.WriteChar('u');
			}
		});
		
		this.ydownB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				arduino.WriteChar('d');
			}
		});
		
		this.xleftB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				arduino.WriteChar('l');
			}
		});
		
		this.xrightB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				arduino.WriteChar('r');
			}
		});
		
		
		this.zupB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				arduino.WriteChar('f');
			}
		});
		
		this.zdownB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('c');
				arduino.WriteChar('t');
			}
		});
		
		this.setOriginB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('z');
			}
		});
		
		this.stopB.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.Stop();
			}
		});
		
		this.drawB.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				arduino.WriteChar('d');
				ArrayList <Square> squares = new ArrayList <Square> () ;
				
				arduino.WriteInt(squares.size ());
				for ( int i = 0 ; i < squares.size() ; i ++ ){
					Square currentSquare = squares.get(i);
					arduino.WriteInt(currentSquare.pos.x);
					arduino.WriteInt(currentSquare.pos.y);
					arduino.WriteInt(currentSquare.width);
					arduino.WriteInt(currentSquare.height);
				}
			}
		});
		
	}
	
}
