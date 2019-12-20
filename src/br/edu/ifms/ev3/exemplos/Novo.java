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
	
	private final Float setPoint = 0.52f;
	private final Integer TP = 40;
	private final Float KP = 4f, KD = 35f;
	//needed because of lego speed seetings (0 - 700 degrees/sec)
	private final Float SCALE_FACTOR = 10f;
	private int speed = 130;
	private long a, time, lastTime;
	private float deriv;
	
	public Novo() {
		me = new EV3LargeRegulatedMotor(MotorPort.A);
		md = new EV3LargeRegulatedMotor(MotorPort.B);
		cd = new ColorSensor(SensorPort.S2);
		ce = new ColorSensor(SensorPort.S1);
		gyro = new GyroSensor(SensorPort.S3);
		
		cd.setRedMode();
		ce.setRedMode();
		
		proporcional();
		
		fim();
	}
	
	public void proporcional() {
		float actual, e, cord, lastError=0f;
		
		//smooth starting
		me.setAcceleration(100);
		md.setAcceleration(100);
		
		
			while(Button.UP.isUp()) {
       		//System.out.println("Angulo: " + gyro.getAngle());

			actual = ce.getAmbient();
			cord = cd.getAmbient();
			boolean aux;
			
			
			/*else if (cord<0.4 && actual<0.15) {
				interssessao();
			}*/
			if (cord<=0.15) {
				aux = verificaCurvaD();
				if (aux==true) {
					curvaDireita();
				}
			}
			else if (cord>0.9 && actual>0.85) {
				aux = verificaCruvaE();
				if (aux==true) {
					curvaEsquerda();
				}
				else {
					gap();
				}
			}
				
			e = setPoint-actual;
			time = System.currentTimeMillis();//parcela derivativa
			deriv = KD*(e - lastError)/(time - lastTime);
			lastTime = time;
			lastError = e;
			turn(SCALE_FACTOR*(e+deriv)*KP);
			System.out.println(SCALE_FACTOR*(e+deriv)*KP);
		}
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
		
		me.setSpeed(speed);
		md.setSpeed(speed);
		me.rotate(50,true);
		md.rotate(50);
		
		while (me.isMoving());
		me.stop(true);
		md.stop();
		
		gyro.resetGyro();

		me.backward();
   		md.forward();
   		
		while (gyro.getAngle()>-60 && Button.UP.isUp()) {
       		System.out.println("Angulo: " + gyro.getAngle());
       	}
		
		me.stop(true);
		md.stop(true);
	}

	
	public void curvaEsquerda () {
		me.stop(true);
		md.stop(true);
		
		me.setSpeed(speed);
		md.setSpeed(speed);
		
		gyro.reset();
		
		md.backward();
       	me.forward();
       		
		while (gyro.getAngle()<60 && Button.UP.isUp()) {
	      	System.out.println("Angulo: " + gyro.getAngle());
	       	
		}
		
		me.stop(true);
		md.stop(true);
						
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
	
	/**
	 * @return
	 */
	
	public boolean verificaCruvaE () {
		me.setSpeed(70);
		md.setSpeed(70);
		me.rotate(-20,true);
		md.rotate(-20);
		gyro.resetGyro();
		me.forward();
		md.stop(true);
		
		a = System.currentTimeMillis();
		while (ce.getAmbient()>0.3 && System.currentTimeMillis()-a<1600);
		
		me.stop(true);
		md.stop(true);
		
		System.out.println(ce.getAmbient());
		if (ce.getAmbient()>0.3) {
			me.rotate(-60,true);
			md.stop();
			while (me.isMoving());
			return false;
		}
		
		me.rotate(-60);
		md.rotate(30);
		
		while (me.isMoving() || md.isMoving());
		return true;
	}
	
	
	public boolean verificaCurvaD (){
		float actual, e, cord;
		
		actual = ce.getAmbient();
		cord = cd.getAmbient();
		a = System.currentTimeMillis();
		
		while ((actual<0.85||cord<0.82) && System.currentTimeMillis()-a<2500) {
			actual = ce.getAmbient();
			cord = cd.getAmbient();
			System.out.println(actual);
			e = setPoint-actual;
			turn(SCALE_FACTOR*e*KP);
			
		}
		
		
		md.stop(true);
		me.stop();
				
		if (actual>=0.85 && cord>=0.82) {
			return true;
		}
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
