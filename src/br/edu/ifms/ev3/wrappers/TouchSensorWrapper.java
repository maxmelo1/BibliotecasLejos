package br.edu.ifms.ev3.wrappers;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class TouchSensorWrapper {
	
	private EV3TouchSensor touchSensor;
	private float[] sample;
	private SensorMode touch;
	
	public TouchSensorWrapper( Port port ) {
		touchSensor =  new EV3TouchSensor( port );
		touch =  touchSensor.getTouchMode();
		sample = new float[touchSensor.sampleSize()];
	}
	
	
	public Boolean isPressed() {
		touch.fetchSample(sample, 0);
		
		return touchSensor != null? sample[0] == 1? true:false : null;
	}

}
