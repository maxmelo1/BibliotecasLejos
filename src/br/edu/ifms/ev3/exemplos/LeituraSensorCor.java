package br.edu.ifms.ev3.exemplos;


import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class LeituraSensorCor {
	public static void main(String[] args) {
		ColorSensor    color = new ColorSensor(SensorPort.S1);

        System.out.println("Color Demo");
        //LCD.print(2, "Press to start");
        
        Button.LEDPattern(4);    // flash green led and
        //Sound.beepSequenceUp();    // make sound when ready.

        Button.waitForAnyPress();
        Button.LEDPattern(0);
        
        // run until escape button pressed.
        
        
        //será deus????
        //color.setAmbientMode();
        
        Delay.msDelay(250);
        
        while (Button.ESCAPE.isUp())
        {
            //Lcd.clear(4);
            //Lcd.print(4, "ambient=%.3f", color.getAmbient());
        	System.out.printf("ambient = %.3f \n", color.getAmbient());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setRedMode();
        
        
        color.setFloodLight(Color.RED);
        color.setFloodLight(true);
        
        while (Button.ESCAPE.isUp())
        {
            //Lcd.clear(5);
            //Lcd.print(5, "red=%.3f", color.getRed());
        	System.out.printf("red=%.3f \n", color.getRed());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setRGBMode();
        color.setFloodLight(Color.WHITE);
        
        Color rgb;
        
        while (Button.ESCAPE.isUp())
        {
            rgb = color.getColor();
            
            //Lcd.clear(6);
            //Lcd.print(6, "r=%d g=%d b=%d", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
            System.out.printf("red=%d \ngreen=%d \nblue=%d \n", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
            Delay.msDelay(250);
        }

        Delay.msDelay(1000);

        color.setColorIdMode();
        color.setFloodLight(false);
        
        while (Button.ESCAPE.isUp())
        {
            //Lcd.clear(7);
            //Lcd.print(7, "id=%s", ColorSensor.colorName(color.getColorID()));
        	System.out.printf("id=%s\n", ColorSensor.colorName(color.getColorID()));
            Delay.msDelay(250);
        }

        // free up resources.
        color.close();
        
        Sound.beepSequence();    // we are done.

        Button.LEDPattern(4);
        Button.waitForAnyPress();
	}
}
