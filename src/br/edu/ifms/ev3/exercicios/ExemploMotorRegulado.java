package br.edu.ifms.ev3.exercicios;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

// extraído de: https://stemrobotics.cs.pdx.edu/node/5189

public class ExemploMotorRegulado {

	public static void main(String[] args) {
		System.out.println("Teste com motor regulado!");
		
		
		//frescura com o padrão de leds do brick
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		
		Button.waitForAnyPress();
		
		EV3LargeRegulatedMotor me = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor md = new EV3LargeRegulatedMotor(MotorPort.B);
		
		
		// set motors to 500 degrees/second rotation.
        //motorA.setAcceleration(500);
		me.setSpeed(500);
		me.forward();    // starts rotation.

        //motorA.setAcceleration(500);
		md.setSpeed(500);
		md.forward();    // starts rotation.
        
        // wait 3 seconds.
        Delay.msDelay(3000);

        // stop motors with brakes on.
        me.stop();
        md.stop();

        Button.waitForAnyPress();

        // demonstrate rotate degrees with wait. One motor runs then other.
        me.rotate(360);
        md.rotate(360);

        Button.waitForAnyPress();

        // demonstrate rotate degrees without wait. Motors run together.
        me.rotate(360, true);
        md.rotate(360, true);

        Button.waitForAnyPress();

        // demonstrate rotate to target angle without wait.
        me.resetTachoCount();
        md.resetTachoCount();
        
        me.rotateTo(180, true);
        md.rotateTo(180, true);

        Button.waitForAnyPress();

        System.out.println("tach=" + me.getTachoCount());
        
        Button.waitForAnyPress();
        
        // free up motor resources.
        me.close();
        md.close();
        
        Sound.beepSequence();    // we are done.
		
		
		
	}
	
}
