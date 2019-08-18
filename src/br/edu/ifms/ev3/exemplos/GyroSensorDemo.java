package br.edu.ifms.ev3.exemplos;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import br.edu.ifms.ev3.wrappers.GyroSensor;

public class GyroSensorDemo {
		public static void main(String[] args)
	    {
	       GyroSensor    gyro = new GyroSensor(SensorPort.S3);

	        System.out.println("Gyro Demo");
	        System.out.println("Press to start");

	        while (Button.ESCAPE.isUp())
	        {
	            
	            System.out.println("Angulo: " + gyro.getAngle());

	            Delay.msDelay(250);
	        }

	        // free up resources.
	        gyro.close();

	    }
	}

