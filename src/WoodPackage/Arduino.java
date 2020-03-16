package WoodPackage;

import javax.swing.JOptionPane;

import processing.core.*;
import processing.serial.*;

public class Arduino extends PApplet {
	
	private static Arduino sn = null ;
	
	private Serial serial;
		
	private Arduino ()  {}
	
	public static Arduino arduino () {
		if ( sn == null ){
			sn = new Arduino () ;
		}
		return sn ;
	}
	
	public String [] getComList () {
		return serial.list();
	}
	
	public void Connect (String com) throws Exception {
		serial = new Serial (this,com,9600);
	}
	
	public void Stop () {
		serial.stop();
	}
	
	public boolean isConnected () {
		if ( !serial.active() ){
			JOptionPane.showMessageDialog(null, "Arduino connection error");
			return false;
		}
		return true ;
	}
	
	public  void WriteInt (int x){
		if ( !isConnected ()) return ;
		
		String s = String.valueOf(x)+'-';
		serial.write(s);
	}
	
	public void WriteChar (char c){
		if ( !isConnected () ) return ;
		
		String s = String.valueOf(c)+'-';
		serial.write(s);
	}
	
	public char readChar () {
		if ( !isConnected () ) return 'e' ;
		
		while (serial.available() == 0) {delay (500);}
		
		String s = serial.readStringUntil('-');
		return s.charAt(0) ;
	}
	
	public  void WriteArray (int a []){
		if ( !isConnected ()) return ;
		
		WriteInt (a.length);
		for ( int i = 0 ; i < a.length ; i ++ ){
			WriteInt(a[i]);
		}
	}

}
