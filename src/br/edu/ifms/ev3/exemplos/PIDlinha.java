package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.exemplos.GuidedDriver;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class PIDlinha {
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		ColorSensor CorD = new ColorSensor(SensorPort.S1);
		
		float error;
		float media = 0.45f;
		int kp = 10, kd = 50,ki = 10;
		int dir,prop;
		float deriv, integral;
		long time;
		long lastTime = 0;
		float lastError = 0.0f;
		
		CorD.setRedMode();
		while (Button.ESCAPE.isUp()) {
			error = 10*(CorD.getAmbient() - media); //parcela proporcional
			prop = (int)(error * kp);
			
			//System.out.println("erro: "+error);
			//System.out.println("proporcional: "+prop);
			
			time = System.currentTimeMillis();//parcela derivativa
			deriv = kd*(error - lastError)/(time - lastTime);
			lastTime = time;
			lastError = error;
			//System.out.println("derivativo: "+deriv);
		
			
			dir = (int)(deriv + prop);//movimento
			gd.move(dir, 300.0f);
			//Delay.msDelay(2000);
			
			}
	}
}