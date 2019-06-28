package br.edu.ifms.ev3.exemplos;

//import java.rmi.RemoteException;

import br.edu.ifms.ev3.exemplos.GuidedDriver;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;


public class PIDlinha {
	
	
	public static void main (String [] args) {
		//EV3LargeRegulatedMotor me = new EV3LargeRegulatedMotor(MotorPort.A);
		//EV3LargeRegulatedMotor md = new EV3LargeRegulatedMotor(MotorPort.B);
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.C), new EV3LargeRegulatedMotor(MotorPort.D));
		ColorSensor CorD = new ColorSensor(SensorPort.S1);
			
		
		double error;
		double media = 0.2;
		int kp = 100, kd = 300;
		int dir,prop;
		double deriv, integral = 0, ki = 1;
		long time;
		long lastTime = 0;
		double lastError = 0.0f;
		int speed = 300, p;
		
		
		
		CorD.setRedMode();
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
			
			gd.move(dir, 200f);
			//Delay.msDelay(2000);
			
			/*if (dir >100) {
				dir =100;
			}
			if (dir>0) {
				me.setSpeed(speed);
				p = speed -(speed / 100*(dir*2));
				md.setSpeed(p);
				if (p>=0) {
					md.forward();
					me.forward();
				}
				else {
					md.backward();
					me.forward();
				}
				
			}else if (dir<0) {
				md.setSpeed(speed);
				p = speed -(speed / 100*(dir*2));
				me.setSpeed(p);
				if (p>=0) {
					md.forward();
					me.forward();
				}
				else {
					me.backward();
					md.forward();
				}
				
			}
			else {
				md.setSpeed(speed);
				me.setSpeed(speed);
				
				md.forward();
				me.forward();
			}*/
			
		}
		CorD.close();
		
		gd.getMd().close();
		gd.getMe().close();
		
		//gd.getMd().close();
		//gd.getMe().close();
		
		
		
	}
}