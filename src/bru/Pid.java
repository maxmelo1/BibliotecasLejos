package bru;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class Pid {
	
	public Pid (){
		EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		
		
        ColorSensor s2 = new ColorSensor(SensorPort.S2);
        
       s2.setRedMode();
       
       motorB.forward();;
        motorC.forward();
        System.out.println("passou desta linha");
       while(Button.ESCAPE.isUp()) {
       int sp,at,erro;
       int vetor[], i,kp=13;
       vetor = new int[2];
      
       //preto = 4;
       //branco = 33;
       sp=18;
       at=(int) (s2.getAmbient()*100);
       erro = sp-at;
       
	    	   vetor[0]=16 - erro;
		       vetor[1]= 16 + erro;
		       for(i=0;i<=1;i++) {
		    	   vetor[i]=vetor[i]*kp;
		    	   System.out.println(vetor[i]);
		       }
		       motorB.setSpeed(vetor[0]);
		        motorC.setSpeed(vetor[1]);
		        
		        motorB.stop();
		        motorC.stop();
	      
	       }
	}
	
	
	
	public static void main(String[] args) {
			Pid pid = new  Pid();
	       
	       }
	}

