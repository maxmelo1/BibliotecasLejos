package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class PIDlinha {
	public static void main (String[] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.C));
		ColorSensor  colorE = new ColorSensor(SensorPort.S2);
		while (Button.ESCAPE.isUp()) {
			
			double error; 
			double media = 0.5;
			double corE;
			// 17 39 0
			int kp = 17, kd = 45;
			int dir,prop;
			double deriv, integral = 0, ki = 0.9;
			long time;
			long lastTime = 0;
			double lastError = 0.0f;
			float speed=250;
			
			colorE.setRedMode();
			corE = colorE.getAmbient();
			
			error = 10*(corE - media); //parcela proporcional
			prop = (int)(error * kp);

			time = System.currentTimeMillis();//parcela derivativa
			deriv = kd*(error - lastError)/(time - lastTime);
			lastTime = time;
			lastError = error;				
			integral = ki*(error + integral); //parcela integral

			System.out.println("int: "+integral);
			dir = (int)(deriv + prop + integral);//movimento
			System.out.println("dir: "+dir);
			dir = dir< 100? dir : 100;
			gd.moveAng(dir, speed);

		}
		gd.getMe().close();
		gd.getMd().close();
		colorE.close();
	}
}