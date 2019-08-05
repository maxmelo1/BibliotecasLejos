package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class PID {
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S3);
		ColorSensor  colorD = new ColorSensor(SensorPort.S1);
		ColorSensor  colorE = new ColorSensor(SensorPort.S2);
		
		double error;
		double media = 0.45;
		double corD, corE;
		// 17 39 0
		int kp = 18, kd = 43, aux= 0;
		int dir,prop;
		double deriv, integral = 0, ki = 0.9;
		long time;
		long lastTime = 0;
		double lastError = 0.0f;
		float speed=100, range;
		
		while (Button.ESCAPE.isUp()) {
			range = uss.getRange();
			if (range<.10) {
				gd.getMd().rotate(360, true);
				gd.getMe().rotate(-360, true);
				
				gd.getMd().rotate(360, true);
				gd.getMe().rotate(360, true);
			}
			else {
			colorD.setRedMode();
			colorE.setRedMode();
			corE = colorE.getAmbient();
			corD = colorD.getAmbient();
			range = uss.getRange();
			
			// gap nao deu certo
			if (corD>0.75 && corE>0.8) {
				while ((corD>0.6 && corE>0.6) && range>.15) {
					range = uss.getRange();
					colorD.setRedMode();
					colorE.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					gd.moveAng(0, speed);
				}
			}
			
			//curva com verde para a esquerda 
			else if (corE<0.25) {
				colorE.setColorIdMode();
				if (colorE.getColorID() == 1) {
					integral=0;
					while (integral>-3 && range>.15) {
						colorE.setRedMode();
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
				integral = 0;
				while (corD>0.6 && range>.15) {
				colorD.setRedMode();
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
			else if (corD<0.25) {
				colorD.setColorIdMode();
				if (colorD.getColorID() == 1) {
					integral = 0;
					while (integral<3 && range>.15) {
					colorD.setRedMode();
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
				integral = 0;
				while (corE>0.6 && range>.15) {
					colorE.setRedMode();
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
			
			//parte de dois pretos 
			else if (corD<0.3 && corE<0.3) {
				colorD.setColorIdMode();
				colorE.setColorIdMode();
				if (colorE.getColorID() == 1 && colorD.getColorID() == 1) {
					gd.getMd().rotate(180,true);
					gd.getMe().rotate(180, true);
					gd.getMe().rotate(720, true);
					gd.getMd().rotate(-720,true);
				}
				else {
				while ((corD<0.35 && corE<0.35)&& range>.15) {
					colorD.setRedMode();
					colorE.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					gd.moveAng(0, speed);
				}
				}
			}
			// segue linha normal com pid direito
			else {
				integral = 0;
				while ((corD>0.3 && corE>0.3) && (corD<0.65||corE<0.65) && range>.15) {
				colorD.setRedMode();
				colorE.setRedMode();
				corD = colorD.getAmbient();
				corE = colorE.getAmbient();
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
		}
		gd.getMe().close();
		gd.getMd().close();
		colorD.close();
		colorE.close();
		uss.close();	
	}
	}

