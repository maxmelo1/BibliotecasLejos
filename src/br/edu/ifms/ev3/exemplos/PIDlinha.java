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
	
	private GuidedDriver gd;
	private ColorSensor colorE;
	private ColorSensor colorD;
	
	public PIDlinha(GuidedDriver gd,ColorSensor colorE,ColorSensor colorD) {
		this.gd = gd;
		this.colorD = colorD;
		this.colorE = colorE;
	}
	
	double error;
	double media = 0.4;
	int kp = 17, kd = 39;
	int dir,prop;
	double deriv, integral = 0, ki = 0.9;
	long time;
	long lastTime = 0;
	double lastError = 0.0f;
	
	public void pidEsquerdo() {
		colorE.setRedMode();
		error = 10*(colorE.getAmbient() - media); //parcela proporcional
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
		gd.moveAng(dir, 100f);
	}
	
	public void pidDireito () {
		colorD.setRedMode();
		error = 10*(colorD.getAmbient() - media); //parcela proporcional
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
		dir = dir * (-1);
		gd.moveAng(dir, 100f);
	}
	
	public void verDoisPretos() {
		
	}
	
	public void closeAll() {
		colorD.close();
		colorE.close();
		gd.getMd().close();
		gd.getMe().close();
	}
	
	public static void main (String [] args) {
		PIDlinha pid = new PIDlinha(new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.C), new EV3LargeRegulatedMotor(MotorPort.D)),
				new ColorSensor(SensorPort.S1),new ColorSensor(SensorPort.S2));
		
		while (Button.ESCAPE.isUp()) {
			pid.pidDireito();
			
		}
		
		
		
	}
}