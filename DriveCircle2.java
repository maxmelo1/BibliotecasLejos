package ev3.exercises;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.utility.Delay;
import library.*;

public class DriveCircle2
{
    public static void main(String[] args)
    {
        TouchSensor touchSensor = new TouchSensor(SensorPort.S3);

        System.out.println("Drive Circle (2)\nand Stop\n");
        System.out.println("Press any key to start");

        Button.LEDPattern(4);    // flash green led and
        Sound.beepSequenceUp();  // make sound when ready.

        Button.waitForAnyPress();

       // create two motor objects to control the motors.
       UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
       UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
       motorA.setPower(70);
       motorB.setPower(70);
       
      while ( true ) {
       
       // set motors to different power levels. Adjust to get a circle.
       // wait doing nothing for touch sensor to stop driving.
     while (touchSensor.isTouched()) {
    	 motorA.stop();
         motorB.stop();
         Delay.msDelay(100);
       }
    
    	  motorA.setPower(70);
          motorB.setPower(70);
          
          motorA.forward();
          motorB.forward();
          //Delay.msDelay(100);
     
     
       }
      
       // stop motors with brakes on.
      //motorA.close();
      //motorB.close();

       // free up resources.

       //Sound.beepSequence(); // we are done.
   }
}
