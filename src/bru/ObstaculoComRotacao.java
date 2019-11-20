package bru;

import br.edu.ifms.ev3.wrappers.GyroSensor;
import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class ObstaculoComRotacao {
	
	public void Curva90Esquerda() {
		EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S4);
		GyroSensor gy = new GyroSensor(SensorPort.S1);
		int a;
		gy.resetGyro();
	 	a = gy.getAngle();
	 	System.out.println(a);
	 	motorB.setAcceleration(6000);
		motorC.setAcceleration(6000);
 		 motorB.backward();
	     motorC.forward();
 		while(a<90) {
	 		
	       
	        a = gy.getAngle();
		 	System.out.println(a);
		 	
	 	}
 		motorB.stop(true);
 		motorC.stop();
	}
	
	public void Curva90Direita(){
		EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S4);
		GyroSensor gy = new GyroSensor(SensorPort.S1);
		int a;
		gy.reset();
			a=0;
			a = gy.getAngle();
			motorB.forward();
			motorC.backward();
			while(a>-90) {
	 		
		       
	        a = gy.getAngle();
		 	System.out.println(a);
		 	
	 	}
			motorB.stop(true);
 		motorC.stop();
	}
	
	
	public static void main(String[] args)
    {
		EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S4);
		GyroSensor gy = new GyroSensor(SensorPort.S1);
		motorB.setAcceleration(340);
		motorC.setAcceleration(340);
		motorB.setSpeed(360);
        motorC.setSpeed(360);
		while(Button.ESCAPE.isUp()) {
		 	int a;
		 	float range;
		 	
		 	
	        motorB.forward();
	        motorC.forward();
	        
		 	range = uss.getRange();
		 	
		 	if(range <= 0.15) {
		 		
		 		motorB.stop(true);
		 		motorC.stop();
		 		gy.resetGyro();
			 	a = gy.getAngle();
			 	System.out.println(a);
			 	motorB.setAcceleration(6000);
				motorC.setAcceleration(6000);
		 		 motorB.backward();
			     motorC.forward();
		 		while(a<90) {
			 		
			       
			        a = gy.getAngle();
				 	System.out.println(a);
				 	
			 	}
		 		motorB.stop(true);
		 		motorC.stop();
		 		System.out.println("saiu");
		 		
		 		//lateral 18cm 
		 		//frontal 8cm
		 		motorB.forward();
		 		motorC.forward();
		 		motorB.rotate(911, true);
		 		motorC.rotate(911,true);
		 		while(motorB.isMoving()==true) {
		 			
		 		}
		 		motorB.stop(true);
		 		motorC.stop();

		 			gy.reset();
		 			a=0;
		 			a = gy.getAngle();
		 			motorB.forward();
		 			motorC.backward();
		 			while(a>-90) {
				 		
					       
				        a = gy.getAngle();
					 	System.out.println(a);
					 	
				 	}
		 			motorB.stop(true);
			 		motorC.stop();
			 		
			 		
			 		System.out.println("saiu");
			 		
			 		//lateral 18cm 
			 		//frontal 8cm
			 		motorB.forward();
			 		motorC.forward();
			 		motorB.rotate(1913, true);
			 		motorC.rotate(1913,true);
			 		while(motorB.isMoving()==true) {
			 			
			 		}
			 		motorB.stop(true);
			 		motorC.stop();
			 		gy.reset();
		 			a=0;
		 			a = gy.getAngle();
		 			motorB.forward();
		 			motorC.backward();
		 			while(a>-90) {
				 		
					       
				        a = gy.getAngle();
					 	System.out.println(a);
					 	
				 	}
		 			motorB.stop(true);
			 		motorC.stop();
			 		
			 		motorB.forward();
			 		motorC.forward();
			 		motorB.rotate(911, true);
			 		motorC.rotate(911,true);
			 		while(motorB.isMoving()==true) {
			 			
			 		}
			 		motorB.stop(true);
			 		motorC.stop();
			 		gy.reset();
		 			a=0;
		 			a = gy.getAngle();
		 			motorB.backward();
				     motorC.forward();
			 		while(a<90) {
				 		
				       
				        a = gy.getAngle();
					 	System.out.println(a);
					 	
				 	}
			 		motorB.stop(true);
			 		motorC.stop();
			 		System.out.println("saiu");
			 		
			 		
while(Button.ESCAPE.isUp()) {
			 			
			 		}
		 	}
		 	
		 	
			}
		gy.reset();
	 	System.out.println("saiu");
	 	motorB.stop();
	 	motorC.stop();
	 	gy.close();
	 	uss.close();
        
    }
}
