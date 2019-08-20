package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
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
		
		int x;
		
		//abre a garra 
		mg.setSpeed(100);
		mg.backward();
		Delay.msDelay(1000);
		while (mg.isMoving());
		
		//procura a area segura 
		md.setSpeed(200);
		me.setSpeed(200);
		me.forward();
		Delay.msDelay(200);
		me.stop();
		float range = uss.getRange();
		
		while (Button.ESCAPE.isUp()) {
			x=0;
			// espalha as bolinhas
			
			while (x<3) {
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
			while (range>0.045) {
				if (range<0.06) {
					md.rotate(-200, true);
					me.rotate(-200);
					while (mg.isMoving());
					
					mg.forward();
					Delay.msDelay(1500);
					while (mg.isMoving());
					
					mg.backward();
					Delay.msDelay(1000);
					while (mg.isMoving());
					
					md.forward();
					me.forward();
					Delay.msDelay(1250);
					md.stop();
					me.stop();
				}
				else {
					me.forward();
					md.forward();
					Delay.msDelay(500);
					md.stop();
					me.stop();
				}
				
				range = uss.getRange();
				System.out.println("range "+range);
			}
			md.rotate(-200, true);
			me.rotate(-200);
			while (mg.isMoving());

			mg.forward();
			Delay.msDelay(1500);
			while (mg.isMoving());
			
			md.forward();
			me.forward();
			Delay.msDelay(1500);
			md.stop();
			me.stop();
			
			range = uss.getRange();
			System.out.println("range "+range);

			if (range>0.09) {
				//parede 
				
				Sound.beepSequenceUp();
				md.rotate(-400, true);
				me.rotate(-400);
				while (me.isMoving() || md.isMoving());

				mg.setSpeed(100);
				mg.backward();
				Delay.msDelay(1000);
				while (mg.isMoving());
				
				md.rotate(850, true);
				me.rotate(10);
				while (me.isMoving() || md.isMoving());
				
				me.forward();
				Delay.msDelay(200);
				me.stop();
				
			}
			else {
				//area de deposito 
				
				Button.LEDPattern(4);
				
				me.rotate(-200, true);
				md.rotate(-200);
				while (me.isMoving() || md.isMoving());
				
				me.rotate(-400, true);
				md.rotate(480);
				while (me.isMoving() || md.isMoving());
				
				me.rotate(-400, true);
				md.rotate(480);
				while (me.isMoving() || md.isMoving());
				
				me.setSpeed(200);
				md.setSpeed(200);
				me.backward();
				md.backward();
				Delay.msDelay(1000);
				me.stop();
				md.stop();
				
				// parte de abrir a cancela do motor medio que o ayllon nao fez 
				
				me.rotate(300,true);
				md.rotate(300);
				
				me.rotate(-400, true);
				md.rotate(480);
				while (me.isMoving() || md.isMoving());
				
				me.rotate(-400, true);
				md.rotate(480);
				while (me.isMoving() || md.isMoving());
				
				mg.setSpeed(100);
				mg.backward();
				Delay.msDelay(1000);
				while (mg.isMoving());
				
				md.rotate(900, true);
				me.rotate(10);
				while (me.isMoving() || md.isMoving());
			}
			x++;
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
