package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;

public class UltraSonicDemo
{
    public static void main(String[] args)
    {
        float                range;
        UltraSonicSensor     uss = new UltraSonicSensor(SensorPort.S3);
        
        System.out.println("UltraSonic Demo");
        System.out.println("Press any key to start");

         while (Button.ESCAPE.isUp()) {
			range =uss.getRange();
			System.out.println("range: "+range);
			Delay.msDelay(100);
		}
    
        
        // free up resources.
        uss.close();
        
        Sound.beepSequence();    // we are done.

       

        Button.waitForAnyPress();
    }
}



