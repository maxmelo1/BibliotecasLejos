package br.edu.ifms.ev3.exemplos;

import java.rmi.RemoteException;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.GyroSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class Novo {
	private EV3LargeRegulatedMotor me;
	private EV3LargeRegulatedMotor md;
	private ColorSensor cd;
	private ColorSensor ce;
	private GyroSensor gyro;
	
	private final Float setPoint = 0.45f;
	private final Integer TP = 80;
	private final Float KP = 12f, KD = 60f, KI=0f;
	//needed because of lego speed seetings (0 - 700 degrees/sec)
	private final Float SCALE_FACTOR = 20f;
	private int speed = 130;
	private long a, time, lastTime;
	private float deriv;
	
	public Novo() {
		me = new EV3LargeRegulatedMotor(MotorPort.C);
		md = new EV3LargeRegulatedMotor(MotorPort.D);
		cd = new ColorSensor(SensorPort.S1);
		ce = new ColorSensor(SensorPort.S3);
		gyro = new GyroSensor(SensorPort.S2);
		
		cd.setRedMode();
		ce.setRedMode();
		
		proporcional();
		
		fim();
	}
	
	public void proporcional() {
		
		//smooth starting
		me.setAcceleration(100);
		md.setAcceleration(100);
		
		
		//me.setSpeed(250);
		//md.setSpeed(250);
		
		//me.backward();
		//md.backward();
		//gyro.resetGyro();
		while(Button.UP.isUp()) {
       		//System.out.println("Angulo: " + gyro.getAngle());
			
			if (cd.getAmbient()<0.56 && cd.getAmbient()>0.3 && ce.getAmbient()<0.26) {
				Sound.beep();
				
				boolean aux = verificaCurvaE();
				if (aux==true) {
					Sound.beepSequence();
					curvaEsquerda();
				}
			}
			
			else if (cd.getAmbient()<.15 && ce.getAmbient()>.15 && ce.getAmbient()<0.62) {
				boolean aux = verificaCurvaD();
				
				if (aux==true) {
					curvaDireita();
				}
			}
			/*
			else if (cd.getAmbient()<0.15) {
				boolean aux = verificaCurvaE();
				aux = verificaCurvaD();	
				if (aux==true) {
					curvaDireita();
				}
			}*/
			
			pid();		
		}
	}
	
	public void pid ( ) {
		
		float actual, e, cord, lastError=0f, deriv,integral=0;
		long time, lastTime=0;
		
		actual = ce.getAmbient();
		
		e = setPoint-actual;
		float g = (float) ((float) SCALE_FACTOR*(e+0.33));
		time = System.currentTimeMillis();//parcela derivativa
		deriv = KD*(e*SCALE_FACTOR - lastError)/(time - lastTime);
		lastTime = time;
		lastError = e;
		integral = KI*(e*SCALE_FACTOR + integral);
		
		
		turn(SCALE_FACTOR*e*KP+deriv+integral);
		
		System.out.println("error " + (SCALE_FACTOR*KP*e));

	}
		
	
	public void turn (float error) {
		me.setSpeed((TP - error));
		md.setSpeed((TP + error));
		
		if (TP - error>0)
			me.backward();
		else
			me.forward();

		if (TP+error>0) 
			md.backward();
		else 
			md.forward();
	}
	
	
	public void curvaDireita () {
		
		me.setSpeed(70);
		md.setSpeed(70);
		
		me.rotate(-50,true);
		md.rotate(-50);
		
		while (me.isMoving());
		me.stop(true);
		md.stop();
		
		gyro.resetGyro();
		
		me.setSpeed(speed);
		md.setSpeed(speed);
		
		me.backward();
   		md.forward();
   		
		while (gyro.getAngle()<60 && Button.UP.isUp()) {
       		System.out.println("Angulo: " + gyro.getAngle());
       	}
		
		me.stop(true);
		md.stop(true);
		
		me.setSpeed(70);
		md.setSpeed(70);
		
		me.rotate(60, true);
		md.rotate(60);
		
		while (me.isMoving());
		
		me.stop(true);
		md.stop();
	}

	
	public void curvaEsquerda () {
		me.setSpeed(70);
		md.setSpeed(70);
		
		me.rotate(-120, true);
		md.rotate(-120);
		
		while (md.isMoving());
		
		me.stop(true);
		md.stop();
		
		gyro.reset();
		
		md.backward();
       	me.forward();
       		
		while (gyro.getAngle()>-63 && Button.UP.isUp()) {
	      	System.out.println("Angulo: " + gyro.getAngle());
	       	
		}
		
		me.stop(true);
		md.stop();
		
		me.setSpeed(70);
		md.setSpeed(70);
		
		me.rotate(70, true);
		md.rotate(70);
		
		while (md.isMoving());
		
		me.stop(true);
		md.stop();
						
	}
	
	
	public void gap () {
		me.setSpeed(speed);
		md.setSpeed(speed);
		
		me.rotate(-20,true);
		md.rotate(-20);
		
		md.backward();
		me.backward();
		
		while (ce.getAmbient()>0.75 && cd.getAmbient()>0.8);
		
		me.stop(true);
		md.stop(true);
	}
	
	
	public void interssessao () {
		me.setSpeed(speed);
		md.setSpeed(speed);
		md.backward();
		me.backward();
		
		while (ce.getAmbient()<0.15 && cd.getAmbient()<0.4);
		
		me.stop(true);
		md.stop(true);
	}
	
	
	public boolean verificaCurvaE () {
		int ve;
		me.setSpeed(70);		
		md.setSpeed(70);
		
		me.rotate(-50,true);
		md.rotate(-50);
		
		gyro.resetGyro();
		me.resetTachoCount();
		
		me.backward();
		md.stop(true);
		
		while (ce.getAmbient()>0.3 && gyro.getAngle()<14);
		
		if (ce.getAmbient()>0.3) {
			me.stop(true);
			md.stop();
			ve= me.getTachoCount();
			Sound.twoBeeps();
			System.out.println(ce.getAmbient());
				me.resetTachoCount();
				me.forward();
				md.stop();
				while (me.getTachoCount()!=(-ve));
				return true;
		}
		
		me.stop(true);
		md.stop();
		
		return false;
	}
	
	
	public boolean verificaCurvaD (){
		float actual, e, cord;
		Sound.buzz();
		a = System.currentTimeMillis();
		
		while ((ce.getAmbient()<0.65||cd.getAmbient()<0.6) && System.currentTimeMillis()-a<1300) {
			pid();
			System.out.println(ce.getAmbient()+" esq ... dir "+cd.getAmbient());
		}
		
		if (ce.getAmbient()>=0.65&& cd.getAmbient()>=0.6) {
			md.stop(true);
			me.stop();
			return true;
		}

		md.stop(true);
		me.stop();
						
		return false;
		
	}		
	
	
	public void fim () {
		me.close();
		md.close();
		ce.close();
		cd.close();
		gyro.close();
	}
	
	public static void main (String[] args) {
		new Novo ();
	}
}
