package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.GyroSensor;
import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class PID {
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.C), new EV3LargeRegulatedMotor(MotorPort.D));
		UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
		ColorSensor  colorD = new ColorSensor(SensorPort.S1);
		ColorSensor  colorE = new ColorSensor(SensorPort.S2);
		GyroSensor giro = new GyroSensor(SensorPort.S3);
		
		double error; 
		double media = 0.5;
		double corD, corE;
		// 17 39 0
		int kp = 18, kd = 45;
		int dir,prop;
		double deriv, integral = 0, ki = 0.9;
		long time;
		long lastTime = 0;
		double lastError = 0.0f;
		float speed=100, range;
		
		// variaveis de controle 
		
		Button.waitForAnyPress();
		while (Button.ENTER.isUp()) {
		
		while (Button.ESCAPE.isUp()) {
			
			range = uss.getRange();
			 /*if (giro.getAngularVelocity()>-10f) {
				speed= 400;
				colorD.setRedMode();
				colorE.setRedMode();
				corE = colorE.getAmbient();
				corD = colorD.getAmbient();
				
				if ((corD>0.65 && corE>0.8) || (corD>0.8 && corE>0.6)) {
					while ((corD>0.6 && corE>0.6) && range>0.05) {
						range = uss.getRange();
						colorD.setRedMode();
						colorE.setRedMode();
						corD = colorD.getAmbient();
						corE = colorE.getAmbient();
						gd.moveAng(0, speed);
						
					}
				}
				
				//parte de dois pretos 
				else if (corD<0.3 && corE<0.33) {
					while ((corD<0.2 || corE<0.2)&& range>.15) {
						colorD.setRedMode();
						colorE.setRedMode();
						corD = colorD.getAmbient();
						corE = colorE.getAmbient();
						range = uss.getRange();
						gd.moveAng(0, speed);
					}
					}
				colorD.setRedMode();
				colorE.setRedMode();
				corD = colorD.getAmbient();
				corE = colorE.getAmbient();
				range = uss.getRange();
				
				error = 10*(corD - media); //parcela proporcional
				prop = (int)(error * kp);

				time = System.currentTimeMillis();//parcela derivativa
				deriv = kd*(error - lastError)/(time - lastTime);
				lastTime = time;
				lastError = error;				
				integral = ki*(error + integral); //parcela integral

				System.out.println("int: "+integral);
				dir = (int)(deriv + prop + integral);//movimento
				System.out.println("dir: "+dir);
				dir = dir< 100? dir : 100;
				dir= dir*(-1);
				gd.moveAng(dir, 150f);
				}*/
			 
			System.out.println("distancia: " + range);
			if (range<=0.04) {
				gd.getMd().setSpeed(200);
				gd.getMe().setSpeed(200);
				
				gd.getMd().rotate(-400, true);
				gd.getMe().rotate(480);
				while (gd.getMe().isMoving());
				
				gd.getMe().rotate(300, true);
				gd.getMd().rotate(300);
				while (gd.getMe().isMoving());
				
				gd.getMd().rotate(880, true);
				gd.getMe().stop();
				while (gd.getMd().isMoving());
				
				gd.getMd().rotate(480, true);
				gd.getMe().rotate(480);
				while (gd.getMe().isMoving());
				
				gd.getMd().rotate(880, true);
				gd.getMe().stop();
				while (gd.getMd().isMoving());
				
				gd.getMd().rotate(300, true);
				gd.getMe().rotate(300);
				while (gd.getMe().isMoving());
				
				gd.getMd().rotate(-400, true);
				gd.getMe().rotate(480);
				while (gd.getMe().isMoving());
				
				gd.getMd().rotate(-300, true);
				gd.getMe().rotate(-300);
				while (gd.getMe().isMoving());

			}               
			else {
			colorD.setRedMode();
			colorE.setRedMode();
			corE = colorE.getAmbient();
			corD = colorD.getAmbient();
			range = uss.getRange();
			
			// gap
			if (corD>=0.7 && corE>=0.7) {
				while ((corD>0.6 && corE>0.6) && range>0.04 && Button.ESCAPE.isUp()) {
					range = uss.getRange();
					colorD.setRedMode();
					colorE.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					gd.moveAng(0, speed);
				}
			}
			
			//parte de dois pretos 
			else if (corD<=0.2 && corE<=0.2) {
				
				colorD.setColorIdMode();
				colorE.setColorIdMode();
				System.out.println(colorE.getColorID() + "  "+ colorD.getColorID());
				if (colorE.getColorID() == 2 && colorD.getColorID() == 1) {
						Button.LEDPattern(4);
						gd.getMd().setSpeed(200);
						gd.getMe().setSpeed(200);
						
						gd.getMd().rotate(-500, true);
						gd.getMe().rotate(500);
						while (gd.getMe().isMoving()||gd.getMd().isMoving());
						gd.getMd().rotate(-500, true);
						gd.getMe().rotate(500);
						while (gd.getMe().isMoving()||gd.getMd().isMoving());
												
					while (corE>0.5) {
						gd.moveAng(100, speed);
						colorE.setRedMode();
						corE = colorE.getAmbient();
					}
				}
				else {
				while ((corD<0.4 && corE<0.4)&& range>.04) {
					colorD.setRedMode();
					colorE.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					range = uss.getRange();
					gd.moveAng(0, speed);
				}
				colorD.setColorIdMode();
				if (colorD.getColorID() == 1) {
					colorD.setRedMode();
					while (colorD.getAmbient()<0.3) {
						gd.moveAng(0, speed);
					}
					}
				colorE.setColorIdMode();
				if (colorE.getColorID() == 2) {
					colorE.setRedMode();
					while (colorE.getAmbient()<0.3) {
						gd.moveAng(0, speed);
					}
				}
				}
			}
			
			//curva com verde para a esquerda
			else if (corE<=0.2) {
					colorE.setColorIdMode();

		        
				if (colorE.getColorID() == 2) { 
					integral=0;
											
					
					while ((corD<0.65 || corE>0.45) && range>=0.04 && Button.ESCAPE.isUp()) {
						
						range = uss.getRange();
						colorD.setRedMode();
						colorE.setRedMode();
						corD = colorD.getAmbient();
						corE = colorE.getAmbient();

						error = 10*(corE - media); //parcela proporcional
						prop = (int)(error * kp);
						
						time = System.currentTimeMillis();//parcela derivativa
						deriv = kd*(error - lastError)/(time - lastTime);
						lastTime = time;
						lastError = error;
						integral = ki*(error + integral); //parcela integral

						System.out.println("int: "+integral);
						dir = (int)(deriv + prop + integral);//movimento
						System.out.println("dir: "+dir);
						dir = dir< 100? dir : 100;
						gd.moveAng(dir, speed);
					}
				}
				//curva sem verde para a esquerda  
				else {
				integral = 2;
				while ((corD>0.5 || corE<0.4 )&& range>0.04 && Button.ESCAPE.isUp()) {
				range = uss.getRange();
				colorE.setRedMode();
				colorD.setRedMode();
				corE = colorE.getAmbient();
				corD = colorD.getAmbient();
				
				error = 10*(corD - media); //parcela proporcional
				prop = (int)(error * kp);

				time = System.currentTimeMillis();//parcela derivativa
				deriv = kd*(error - lastError)/(time - lastTime);
				lastTime = time;
				lastError = error;				
				integral = ki*(error + integral); //parcela integral

				System.out.println("int: "+integral);
				dir = (int)(deriv + prop + integral);//movimento
				System.out.println("dir: "+dir);
				dir = dir< 100? dir : 100;
				dir= dir*(-1);
				gd.moveAng(dir, speed);
				}
				}
			}
			
			// curva com verde para a direita
			else if (corD<=0.2) {
				colorD.setColorIdMode();

		       
		        
				if (colorD.getColorID() == 1) {
					Sound.beepSequenceUp();
					integral = 0;
					while ((corE<0.65 || corD>0.45)&& range>=.04 && Button.ESCAPE.isUp()) {
					range = uss.getRange();
					colorE.setRedMode();
					colorD.setRedMode();
					corE = colorE.getAmbient();
					corD = colorD.getAmbient();

					error = 10*(corD - media); //parcela proporcional
					prop = (int)(error * kp);

					time = System.currentTimeMillis();//parcela derivativa
					deriv = kd*(error - lastError)/(time - lastTime);
					lastTime = time;
					lastError = error;			
					integral = ki*(error + integral); //parcela integral

					System.out.println("int: "+integral);
					dir = (int)(deriv + prop + integral);//movimento
					System.out.println("dir: "+dir);
					dir = dir< 100? dir : 100;
					dir= dir*(-1);
					gd.moveAng(dir, speed);
					}
				}
				//curva sem verde para a direita 
				else {
				integral = 2;
				while ((corE>0.4 && corD<0.6) && range>=.04 && Button.ESCAPE.isUp()) {
					range = uss.getRange();
					colorE.setRedMode();
					colorD.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					
					error = 10*(corE - media); //parcela proporcional
					prop = (int)(error * kp);
					
					time = System.currentTimeMillis();//parcela derivativa
					deriv = kd*(error - lastError)/(time - lastTime);
					lastTime = time;
					lastError = error;				
					integral = ki*(error + integral); //parcela integral

					System.out.println("int: "+integral);
					dir = (int)(deriv + prop + integral);//movimento
					System.out.println("dir: "+dir);
					dir = dir< 100? dir : 100;
					gd.moveAng(dir, speed);
					}
				}
				
			}
			
		
			// segue linha normal com pid direito
			else {
				integral = 0;
				while ((corD>0.2 && corE>0.2) && (corD<0.7||corE<0.7) && range>=.04 && Button.ESCAPE.isUp()) {
				colorD.setRedMode();
				colorE.setRedMode();
				corD = colorD.getAmbient();
				corE = colorE.getAmbient();
				range = uss.getRange();
				
				error = 10*(corD - media); //parcela proporcional
				prop = (int)(error * kp);

				time = System.currentTimeMillis();//parcela derivativa
				deriv = kd*(error - lastError)/(time - lastTime);
				lastTime = time;
				lastError = error;				
				integral = ki*(error + integral); //parcela integral

				System.out.println("int: "+integral);
				dir = (int)(deriv + prop + integral);//movimento
				System.out.println("dir: "+dir);
				dir = dir< 100? dir : 100;
				dir= dir*(-1);
				gd.moveAng(dir, 150f);
				}
			}
			}		 
			
		}
		gd.getMd().stop(true);
		gd.getMe().stop(true);
		
		Button.waitForAnyPress();
		
		}
		gd.getMe().close();
		gd.getMd().close();
		colorD.close();
		colorE.close();
		uss.close();	
		giro.close();
	}
	}

