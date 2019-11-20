package bru;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;

public class TestarVelocidade {
		
		
		
 		public static void main(String[] args) {
 			EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
 			EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
 			double rot;
 			int dist;
 			rot = 7.9;
 			dist = 12;
 	 		metodos met = new metodos();
 	 		dist = metodos.dist(rot,dist);
 	 		motorB.forward();
 	 		motorC.forward();
 	 		motorB.rotateTo(dist, true);
	 		motorC.rotateTo(dist,true);
	 		while(motorB.isMoving()==true) {
	 			
	 		}
	 		motorB.stop(true);
	 		motorC.stop();
		}
}
