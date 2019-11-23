package bru;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class teste {
public static void main(String[] args) {
	EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
	
	
	
    ColorSensor s2 = new ColorSensor(SensorPort.S2);
    ColorSensor s3 = new ColorSensor(SensorPort.S3);
    s2.setRedMode();
    while(Button.ESCAPE.isUp()) {
    float at = s2.getAmbient()*100;
    System.out.println(at);
    //
    }
}
}
