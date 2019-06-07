package br.edu.ifms.ev3.exemplos;

import java.rmi.RemoteException;

import br.edu.ifms.ev3.exemplos.GuidedDriver;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class PIDlinha {
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		ColorSensor CorD = new ColorSensor(SensorPort.S1);
		
		
		
		
			
			
		
		double error;
		double media = 0.2;
		int kp = 100, kd = 300;
		int dir,prop;
		double deriv, integral = 0, ki = 0.5;
		long time;
		long lastTime = 0;
		double lastError = 0.0f;
		
		
		
		//CorD.setRedMode();
		while (Button.ESCAPE.isUp()) {
			error = (CorD.getAmbient() - media); //parcela proporcional
			prop = (int)(error * kp);
			
			//System.out.println("erro: "+error);
			//System.out.println("proporcional: "+prop);
			
			time = System.currentTimeMillis();//parcela derivativa
			deriv = kd*(error - lastError)/(time - lastTime);
			lastTime = time;
			lastError = error;
			//System.out.println("derivativo: "+deriv);
			
			integral = ki*(error + integral); //parcela integral
		
			
			dir = (int)(deriv + prop + integral);//movimento
			gd.move(dir, 100.0f);
			//Delay.msDelay(2000);
			
			
		}
		CorD.close();
		
		gd.getMd().close();
		gd.getMe().close();
		
		
		
	}
}