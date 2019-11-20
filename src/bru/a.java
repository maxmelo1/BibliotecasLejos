package bru;

import java.awt.Color;
import java.util.Random;


import br.edu.ifms.ev3.wrappers.*;
import lejos.hardware.*;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;
import lejos.hardware.port.*;
public class a
{
    public static void main(String[] args)
    {
       System.out.println("clique");
        Button.waitForAnyPress();
        float range,se,sd;
        int a,x = 0;
        UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S4);
        // create two motor objects to control the motors.
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
        UnregulatedMotor motorC = new UnregulatedMotor(MotorPort.C);
        ColorSensor s2 = new ColorSensor(SensorPort.S2);
        ColorSensor s3 = new ColorSensor(SensorPort.S3);
       s2.setRedMode();
       s3.setRedMode();
        
        while(Button.ESCAPE.isUp()) {
        se = s2.getAmbient();
        sd = s3.getAmbient();
        System.out.println("se"+se);
        System.out.println("sd"+sd);
        if(se<0.25) {
        	if(sd<0.26) {
        		
        		motorB.setPower(40);
    	        motorC.setPower(40);
    	       
    	        motorB.forward();
    	        motorC.forward();
    	        
        	}else {
        		while(se<0.25) {
        		motorB.setPower(40);
    	        motorC.setPower(40);
    	       
    	        
    	        
    	        motorB.backward();
    	        motorC.forward();
    	        se = s2.getAmbient();
    	        sd = s3.getAmbient();
        		}
        	}
        	
        }else {
        	if(sd<0.26) {
        		while(sd<0.26) {
        		motorB.setPower(40);
    	        motorC.setPower(40);
    	        
    	        motorB.forward();
    	        motorC.backward();
    	        se = s2.getAmbient();
    	        sd = s3.getAmbient();
        		}
        	}else {
        		motorB.setPower(40);
    	        motorC.setPower(40);
    	       
    	        motorB.forward();
    	        motorC.forward();
        	}
        }
        }
        
        
        
        
        
        // stop motors with brakes on. 
        motorB.stop();
        motorC.stop();
       
        // free up motor resources. 
        motorB.close(); 
        motorC.close();
 
        Sound.beepSequence(); // we are done.
    }
}


