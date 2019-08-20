package br.edu.ifms.ev3.exemplos;


import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class LeituraSensorCor {
	public static void main(String[] args) {
		ColorSensor    colorD = new ColorSensor(SensorPort.S1);
		ColorSensor	   colorE = new ColorSensor(SensorPort.S2);
        
        
        //será deus????
        /*color.setAmbientMode();
        
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
        }*/

        /*Delay.msDelay(1000);

        colorE.setRGBMode();
        colorD.setRGBMode();
        colorD.setFloodLight(Color.WHITE);
        colorE.setFloodLight(Color.WHITE);
        
        Color rgb1;
        Color rgb2;
        
        while (Button.ESCAPE.isUp())
        {
            rgb1 = colorE.getColor();
            rgb2 = colorD.getColor();
            
            //Lcd.clear(6);
            //Lcd.print(6, "r=%d g=%d b=%d", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
            System.out.printf("esuqerdo red=%d \ngreen=%d \nblue=%d \n", rgb1.getRed(), rgb1.getGreen(), rgb1.getBlue());
            System.out.printf("direito red=%d \ngreen=%d \nblue=%d \n", rgb2.getRed(), rgb2.getGreen(), rgb2.getBlue());
            Delay.msDelay(300);
        }

        Delay.msDelay(1000);*/

        colorE.setColorIdMode();
        colorD.setColorIdMode();
        colorE.setFloodLight(false);
        colorD.setFloodLight(false);
        
        while (Button.ESCAPE.isUp())
        {
            //Lcd.clear(7);
            //Lcd.print(7, "id=%s", ColorSensor.colorName(color.getColorID()));
        	//System.out.printf("id=%s\n", ColorSensor.colorName(color.getColorID()));
            System.out.println("id da cor direita: " + colorD.getColorID());
            System.out.println("id da cor esquerda: " + colorE.getColorID());

            Delay.msDelay(250);
        }
        
        Delay.msDelay(250);
        
        Button.waitForAnyPress();

        while (Button.ESCAPE.isUp()) {
        	colorD.setRedMode();
        	colorE.setRedMode();
        	System.out.println("ambiente da cor direita: " + colorD.getAmbient());
            System.out.println("ambiente da cor esquerda: " + colorE.getAmbient());
            Delay.msDelay(1000);
        }

        // free up resources.
        colorD.close();
        colorE.close();
       
	}
}
