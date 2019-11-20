package bru;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class metodos {
		public static int dist(double rot, int dist) {
			dist= (int) ((dist*360)/rot);
			return dist;
		}
		public void reto() {
			EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
			EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
			
			motorB.forward();
	 		motorC.forward();
		}
		public static void main(String[] args) {
		
			
			double rot;
			int dist;
			rot = 7.90;
			dist = 0;
			dist = dist(rot,dist);
		}
}
