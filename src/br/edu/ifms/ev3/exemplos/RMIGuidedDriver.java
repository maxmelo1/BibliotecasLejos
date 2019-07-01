package br.edu.ifms.ev3.exemplos;

import java.rmi.RemoteException;

import br.edu.ifms.ev3.wrappers.TouchSensorWrapper;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;


public class RMIGuidedDriver {

	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	
	public RMIGuidedDriver(RMIRegulatedMotor me, RMIRegulatedMotor md) {
		this.me = me;
		this.md = md;
		
		//zigzagNavigate();
		
		//zigZagSinCosNavigate();
	}
	
	public void zigzagNavigate() {
		TouchSensorWrapper touchSensor = new TouchSensorWrapper(SensorPort.S2);
		
		float i=0;
		
		do{
			
			
			int k = 90;
			Float steer = (float) ((float)k*Math.sin(i));
					
			this.move( steer.intValue(), 400f);
			
			
			i += 0.1;
			
			Delay.msDelay(30);
			
			if( i > Math.PI  ) {
				i = (float) -Math.PI;
			}
		}
		while(! touchSensor.isPressed() );
	}
	
	
	public void zigZagSinCosNavigate() {
		TouchSensorWrapper touchSensor = new TouchSensorWrapper(SensorPort.S2);
				
		float i=0;	
		
		do{
			
					
			try {
				md.setSpeed( (int)(Math.sin(i)*300) );
				me.setSpeed( (int)(Math.cos(i)*300) );
				
				md.forward();
				me.forward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			i += 0.05;
			
			Delay.msDelay(60);
			
			if( i > Math.PI  ) {
				i = (float) -Math.PI;
			}
		}
		while(! touchSensor.isPressed() );
	}
	
	
	/**
	 * Move o robô para a direita ou esquerda
	 * @param dir 0 frente, direita(100), esquerda(-100)
	 * @param abs(power) 0 - 700
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
		
		powerD = powerD< 700f? powerD : 700f;
		powerE = powerE< 700f? powerE : 700f;
		
		Integer pd = new Integer(powerD.intValue());
		Integer pe = new Integer(powerE.intValue());
		
		try {
		me.setSpeed(pe);
		md.setSpeed(pd);
		
		if(power>=0) {
			me.forward();
			md.forward();
		}
		else {
			me.backward();
			md.backward();
		}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void moveAng (Integer dir, Integer speed) {
		int ang = (speed/100)*dir*2;
		System.out.println("ang: "+ang);
			try {
				if (dir>0) {
				me.setSpeed(speed);
				md.setSpeed(speed-ang);
				
				if(speed-ang<0) {
					me.forward();
					md.backward();
				}
				else {
					me.forward();
					md.forward();
				}
				}
				else {
					me.setSpeed(speed+ang);
					md.setSpeed(speed);
					
					if(speed+ang<0) {
						md.forward();
						me.backward();
					}
					else {
						me.forward();
						md.forward();
					}
				}
				System.out.println("md: "+speed);
				System.out.println("me: "+(speed+ang));
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	
	public RMIRegulatedMotor getMe() {
		return me;
	}

	public void setMe(RMIRegulatedMotor me) {
		this.me = me;
	}

	public RMIRegulatedMotor getMd() {
		return md;
	}

	public void setMd(RMIRegulatedMotor md) {
		
		this.md = md;
	}

	public static void main(String[] args) {
		//GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));

	}

}
