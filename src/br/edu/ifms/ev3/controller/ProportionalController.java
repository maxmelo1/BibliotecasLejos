package br.edu.ifms.ev3.controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class ProportionalController {
	private RMISampleProvider sampleProvider = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	private ColorSensor cs;
	
	/**
	 * constants definition
	 */
	private final Float setPoint = 0.37f;
	private final Integer TP = 20;
	private final Float KP = 4f;
	//needed because of lego speed seetings (0 - 700 degrees/sec)
	private final Float SCALE_FACTOR = 10f;
	
	public ProportionalController() {
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
		
		//L -> large, M-> medium
		me = ev3.createRegulatedMotor("D", 'L');
		md = ev3.createRegulatedMotor("C", 'L');
		
		cs = new ColorSensor(SensorPort.S1);
		
		
		cs.setRedMode();
		cs.setFloodLight(Color.RED);
		
		try {
			proportional();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			me.close();
			md.close();
			cs.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void proportional() throws RemoteException {
		float actual, e;
		
		//smooth starting
		me.setAcceleration(200);
		md.setAcceleration(200);
		
		me.forward();
		md.forward();
		
		while(Button.UP.isUp()) {
			actual = cs.getAmbient();
			
			e = setPoint-actual;
			
			turn(SCALE_FACTOR*KP*e);
			
			System.out.println("error " + (SCALE_FACTOR*KP*e) );
			
		}
	}
	
	
	private void turn(float error) throws RemoteException {
		me.setSpeed((int)(TP - error));
		md.setSpeed((int)(TP + error));
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ProportionalController();

	}

}
