package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.exemplos.GuidedDriver;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class PIDlinha {
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		ColorSensor CorD = new ColorSensor(SensorPort.S1);
		
		float error;
		float media = 0.42f;
		int kp = 50;
		int kd = 10;
		int dir,prop;
		float deriv;
		long time;
		long lastTime;
		float lastError = 0.0f;
		
		CorD.setRedMode();
		lastTime = System.currentTimeMillis();
		while (Button.ESCAPE.isUp()) {
			error = CorD.getAmbient() - media;
			prop = (int)error * kp;
			
			//System.out.println(error);
			
			time = System.currentTimeMillis();
			deriv = kd*(error - lastError)/(time - lastTime);
			lastTime = time;
			lastError = error;
			
			dir = (int)deriv + prop;
			gd.move(dir, 200.0f);
			
			}
	}
}