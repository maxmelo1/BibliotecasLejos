package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class Resgate {
	public static void main(String[] args){
		EV3LargeRegulatedMotor md = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3LargeRegulatedMotor me = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor mg = new EV3LargeRegulatedMotor(MotorPort.A);
		UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
		
		//abre a garra 
		mg.setSpeed(100);
		mg.backward();
		Delay.msDelay(1000);
		while (mg.isMoving());
		
		//procura a area segura 
		md.setSpeed(200);
		me.setSpeed(200);
		float range = uss.getRange();
		
		while (Button.ESCAPE.isUp()) {
			while (range>0.1) {
				md.forward();
				me.forward();
				range = uss.getRange();
			}
			md.stop();
			me.forward();
			Delay.msDelay(500);
			range = uss.getRange();
			System.out.println("range "+range);
			if (range<0.045) {
				md.rotate(-200, true);
				me.rotate(-200);
				while (mg.isMoving());
				
				mg.forward();
				Delay.msDelay(1500);
				while (mg.isMoving());
				
				md.forward();
				me.forward();
				Delay.msDelay(1500);
			}
			else {
				me.forward();
				md.forward();
				Delay.msDelay(600);
			}
			range = uss.getRange();
			System.out.println("range "+range);
			if (range>0.06) {
				md.rotate(-180, true);
				me.rotate(-180);
				while (me.isMoving() || md.isMoving());
				me.rotate(80,true);
				md.rotate(200);
				while (me.isMoving() || md.isMoving());


			}else if (range>.035) {
				// programacao de deposito 
			}
			else {
				md.rotate(-200, true);
				me.rotate(-200);
				while (mg.isMoving());
				
				mg.forward();
				Delay.msDelay(1500);
				while (mg.isMoving());
				
				md.forward();
				me.forward();
				Delay.msDelay(1500);
			}

			
			
		}
		/*while (md.getRotationSpeed()>190 && me.getRotationSpeed()>190) {
			range = uss.getRange();
			System.out.println("velocidade do motor direito" + md.getRotationSpeed()+ "velociade do motor esuqerdo" + md.getRotationSpeed());
		}
		
		me.rotate(-180,true );
		md.rotate(-180);
		while (md.isMoving() && me.isMoving());
		
		
		mg.forward();
		Delay.msDelay(1500);
		
		md.forward();
		me.forward();
		Delay.msDelay(2100);
		
		if (uss.getRange()>1) {
			
		}*/
		md.stop(true);
		me.stop();
		uss.close();
		me.close();
		md.close();
		mg.close();
	}
}
