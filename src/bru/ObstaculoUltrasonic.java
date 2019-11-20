package bru;

import br.edu.ifms.ev3.wrappers.GyroSensor;
import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class ObstaculoUltrasonic {
	public static void main(String[] args) {
		
		
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
        UnregulatedMotor motorC = new UnregulatedMotor(MotorPort.C);
        UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S4);
        UltraSonicSensor     uss2 = new UltraSonicSensor(SensorPort.S2);
		GyroSensor gy = new GyroSensor(SensorPort.S1);
		
		
			
		while(Button.ESCAPE.isUp()) {
	 	int a;
	 	float range;
	 	motorB.setPower(40);
        motorC.setPower(40);
        motorB.forward();
        motorC.forward();
	 	a = gy.getAngle();
	 	System.out.println(a);
	 	range = uss.getRange();
	 	
	 	if(range <= 0.15) {
	 		while(a<90) {
		 		
		        motorB.backward();
		        motorC.forward();
		        a = gy.getAngle();
			 	System.out.println(a);
			 	
		 	}
	 		while(uss2.getRange()<0.4) {
 				motorB.forward();
	 			motorC.forward();
 			}
	 			
	 			
	 			gy.reset();
	 			a=0;
	 			a = gy.getAngle();
	 		while(a>-90) {
		 		
		        motorB.forward();
		        motorC.backward();
		        a = gy.getAngle();
			 	System.out.println(a);
			 	
		 	}
		 		
	 		while(uss2.getRange()>0.4) {
 				motorB.forward();
	 			motorC.forward();
 			}
 			while(uss2.getRange()<0.4) {
 				motorB.forward();
	 			motorC.forward();
 			}
		 		gy.reset();
	 			a=0;
	 			a = gy.getAngle();
	 		while(a>-90) 
	 			{
			 		
			        motorB.forward();
			        motorC.backward();
			        a = gy.getAngle();
				 	System.out.println(a);
				 	
			 	}
		 		gy.reset();
		 		a=0;
		 		a = gy.getAngle();
	 			
		 		while(uss2.getRange()>0.4) {
	 				motorB.forward();
		 			motorC.forward();
	 			}
	 			while(uss2.getRange()<0.4) {
	 				motorB.forward();
		 			motorC.forward();
	 			}
	 		while(a<90) 
	 			{
			 		
			        motorB.backward();
			        motorC.forward();
			        a = gy.getAngle();
				 	System.out.println(a);
				 	
			 	}	
	 	}
		}
	 	gy.reset();
	 	System.out.println("saiu");
	 	motorB.stop();
	 	motorC.stop();
	 	gy.close();
	 	uss.close();
	 	uss2.close();
	}
	 	
}
