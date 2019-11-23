package bru;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class TestarVelocidade {
		
		
		
		public static double conv(double rotMenor, int tempo) {
			double rotMaior;
			rotMenor =rotMenor/360; //transformando graus pra rotações
			rotMaior = (rotMenor*24)/40; // achando rotacoes na engrenagem maior
			rotMaior =2*Math.PI*2.05*rotMaior; //achando distancia percorrida 
			tempo = tempo/1000;
			rotMaior = rotMaior/tempo;
			return rotMaior;
		}
		
 		public static void main(String[] args) {
 			EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
 			EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
 			
 			motorB.setSpeed(360);
 	        motorC.setSpeed(360);
 			double rot;
 			int dist;
 			rot = 7.9;
 			//dist = 12;
 	 		//metodos met = new metodos();
 	 		//dist = metodos.dist(rot,dist);
 	 		
 	 		motorB.forward();
 	 		motorC.forward();
 	 		//motorB.rotate(360, true);
	 		//motorC.rotate(360,true);
 	 		//vel tem q ser = 7.67 cm/s aproximado
 	 		//form = 2*3.144444*2.05*x
 	 		
 	 		int tempo=5000;
	 		Delay.msDelay(tempo);
	 		
	 		double rotMenor = motorB.getTachoCount();//pegando graus de rotacoes dada
	 		System.out.println(rotMenor);
	 		double vel=0;
	 		vel = conv(rotMenor,tempo);//chamando metodo que calculo velocidade
	 		
	 		System.out.println(vel);
	 		motorB.stop(true);
	 		motorC.stop();
	 		while(Button.ESCAPE.isUp()) {
	 			
	 		}
	 		//while(motorB.isMoving()==true) {
	 			
	 		//}
	 		
		}
}
