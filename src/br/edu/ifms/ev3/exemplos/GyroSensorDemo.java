package br.edu.ifms.ev3.exemplos;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.GyroSensor;

public class GyroSensorDemo {
		public static void main(String[] args)
	    {
	       GyroSensor    gyro = new GyroSensor(SensorPort.S3);
	       EV3LargeRegulatedMotor me = new EV3LargeRegulatedMotor(MotorPort.A);
	       EV3LargeRegulatedMotor md = new EV3LargeRegulatedMotor(MotorPort.B);
	       ColorSensor cd = new ColorSensor(SensorPort.S2);
	       ColorSensor ce = new ColorSensor(SensorPort.S1);
	       
	       /*Integer TP = 20;
	       Integer KP = 5;
	       Float SCALE_FACTOR = 10f;
	       float setPoint = .5f;
	       
	       ce.setRedMode();
	       ce.setFloodLight(Color.RED);
	       cd.setRedMode();
	       cd.setFloodLight(Color.RED);
	       
	       
	       float actual, e, cord;
			
			//smooth starting
			me.setAcceleration(100);
			md.setAcceleration(100);
			
			
			//me.setSpeed(250);
			//md.setSpeed(250);
			
			//me.backward();
			//md.backward();
			gyro.resetGyro();
			while(Button.UP.isUp()) {
	       		System.out.println("Angulo: " + gyro.getAngle());

				actual = ce.getAmbient();
				cord = cd.getAmbient();
				/*if (csd.getAmbient()>0.7&&cs.getAmbient()>0.7) {
					boolean aux = verifica90();
					if (aux==true) {
						Sound.beep();
					}
				}*/
				
				/*if (cd.getAmbient()<0.15) {
					me.stop(true);
					md.stop(true);
					me.setSpeed(150);
					md.setSpeed(150);
					
						
						Delay.msDelay(300);
						gyro.resetGyro();
						
						Sound.beepSequenceUp();

						me.backward();
			       		md.forward();
			       		
						while (gyro.getAngle()>-90 && Button.UP.isUp()) {
				       		System.out.println("Angulo: " + gyro.getAngle());
				       		
				       	}
						me.stop(true);
						md.stop(true);
										
									
				}*/
				/*if (cord>0.7 && actual>0.7) {
					
				
				me.setSpeed(200);
				md.setSpeed(200);
				gyro.resetGyro();
				md.backward();
				me.forward();
				
				while (ce.getAmbient()>0.2 && gyro.getAngle()<45);
				
				me.stop(true);
				md.stop(true);
				
				if (ce.getAmbient()>0.2) {
					gyro.resetGyro();
					
					me.backward();
					md.forward();
					
					while (gyro.getAngle()>-45);
					
					me.stop(true);
					md.stop(true);
					
					md.backward();
					me.backward();
					
					while (ce.getAmbient()>0.8 && cd.getAmbient()<0.8);
					
					me.stop(true);
					md.stop(true);

				}
				else {
					Button.LEDPattern(0);
				}
				
				
			}
				e = setPoint-actual;
				
				float error = (SCALE_FACTOR*e*KP);
				
				me.setSpeed((int)(TP - error));
				md.setSpeed((int)(TP + error));
				
				if (TP - error>0) {
					me.backward();
				}
				else {
					me.forward();
				}
				
				if (TP+error>0) {
					md.backward();
				}
				
				else {
					md.forward();
				}
				
				System.out.println("error "+(SCALE_FACTOR*KP*e));
			}
				
	        /*System.out.println("Gyro Demo");
	        System.out.println("Press to start");
	        
	        me.setSpeed(200);
	        md.setSpeed(200);
	        
	        gyro.resetGyro();
	        
	        me.backward();
       		md.forward();

	        //curva para direita
	        while (gyro.getAngle()>-90 && Button.ESCAPE.isUp()) {
	       		System.out.println("Angulo: " + gyro.getAngle());
	       		
	       	}
	        	
	        me.stop(true);
	       	md.stop(true);*/
	        	gyro.resetGyro();
	        	while (gyro.getAngle()<30 && Button.ESCAPE.isUp()) {
	        		System.out.println("Angulo: " + gyro.getAngle());
	        		md.backward();
	        		me.forward();
	        	}
	        // free up resources.
	        gyro.close();
	        md.close();
	        me.close();
	        ce.close();
	        cd.close();
	    }
	}


