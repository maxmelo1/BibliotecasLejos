package br.edu.ifms.ev3.exemplos;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class MonitorLego {
	
	private RMISampleProvider sampleProvider = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;

	public MonitorLego() {
		
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
			
			System.out.println("ev3 nulo? " + (ev3 == null));
			
			sampleProvider = ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "Red" );
			float sample = sampleProvider.fetchSample()[0];
			
			System.out.println("cor -> "+ sample);
			
			me = ev3.createRegulatedMotor("A", 'L');
			md = ev3.createRegulatedMotor("B", 'L');
			
			//me.setSpeed(100);
			//md.setSpeed(100);
			
			me.rotateTo(360, true);
			md.rotateTo(360, true);
			
			while(me.isMoving());
			
			me.forward();
			md.forward();			
			
			
			me.stop(true);
			md.stop(true);
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				sampleProvider.close();
				
				me.close();
				md.close();
				
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		new MonitorLego();
	}
}
