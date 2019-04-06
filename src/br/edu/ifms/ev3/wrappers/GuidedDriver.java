package br.edu.ifms.ev3.wrappers;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;


public class GuidedDriver {

	private EV3LargeRegulatedMotor me;
	private EV3LargeRegulatedMotor md;
	
	public GuidedDriver(EV3LargeRegulatedMotor me, EV3LargeRegulatedMotor md) {
		this.me = me;
		this.md = md;
	}
	
	
	/**
	 * Move o robô para a direita ou esquerda
	 * @param dir 0 frente, direita(100), esquerda(-100)
	 * @param power 0 - 700
	 */
	public void move(Integer dir, Float power){
		Float powerD = 0f;
		Float powerE = 0f;
		Float ratioD = 0f;
		Float ratioE = 0f;
		
		/*if(dir==0) {
			ratioD = 1f;
			ratioE = 1f;
		}
		else if(dir<0) {
			ratioD = 0.5*dir +50;
			ratioE = 1 - ratioD;
		}*/
		
		ratioD = -0.005f*dir + 0.5f;
		ratioE = 1-ratioD;
		
		powerD = power*ratioD;
		powerE = power*ratioE;
		
		me.setSpeed(powerE);
		md.setSpeed(powerD);
		
		if(power>=0) {
			me.forward();
			md.forward();
		}
		else {
			me.backward();
			md.backward();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
		SensorMode touch =  touchSensor.getTouchMode();
		float[] sample = new float[touch.sampleSize()];
		
		float i=0;
		
		do{
			
			
			int k = 90;
			Float steer = (float) ((float)k*Math.sin(i));
					
			gd.move( steer.intValue(), 400f);
			
			touch.fetchSample(sample, 0);
			
			i += 0.1;
			
			Delay.msDelay(30);
			
			if( i > Math.PI  ) {
				i = (float) -Math.PI;
			}
		}
		while( sample[0] == 0 );

	}

}
