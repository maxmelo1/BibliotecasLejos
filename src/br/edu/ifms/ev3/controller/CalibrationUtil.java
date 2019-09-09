package br.edu.ifms.ev3.controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

/**
 * Used to calibrate white and black sensed values. Sensor distance may affect precision.
 * @author gin
 *
 */
public class CalibrationUtil {
	private RemoteEV3 ev3 = null;
	private ColorSensor cs;
	
	public CalibrationUtil() {
		try {
			ev3 = new RemoteEV3("10.0.1.1");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ev3 == null) {
			System.out.println("ev3 não conectado");
			System.exit(-1);
		}
		
		cs = new ColorSensor(SensorPort.S1);
		
		cs.setRedMode();
		cs.setFloodLight(Color.RED);
		
		System.out.println("aperte UP no lego para finalizar o programa");
		
		//TODO automate offset estimation as a global parameter.
		while(Button.UP.isUp()) {
			float aux = cs.getAmbient();
			System.out.println(aux);
		}
		
		cs.close();
		
	}
	
	
	public static void main(String[] args) {
		new CalibrationUtil();
	}

}
