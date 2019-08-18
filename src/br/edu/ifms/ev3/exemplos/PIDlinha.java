package br.edu.ifms.ev3.exemplos;

//import java.rmi.RemoteException;

import br.edu.ifms.ev3.exemplos.GuidedDriver;
import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.GyroSensor;
import br.edu.ifms.ev3.wrappers.UltraSonicSensor;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;


public class PIDlinha {
	
	public static void main (String [] args) {
		GuidedDriver gd = new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.C), new EV3LargeRegulatedMotor(MotorPort.D));
		UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
		ColorSensor  colorD = new ColorSensor(SensorPort.S1);
		ColorSensor  colorE = new ColorSensor(SensorPort.S2);
		GyroSensor    gyro = new GyroSensor(SensorPort.S3);
		
		double error;
		double media = 0.45;
		double corD, corE;
		// 17 39 0
		int kp = 19, kd = 45;
		int dir,prop;
		double deriv, integral = 0, ki = 1;
		long time;
		long lastTime = 0;
		double lastError = 0.0;
		float speed=200, range;
		
		while (Button.ESCAPE.isUp()) {
		
		colorD.setRedMode();
		colorE.setRedMode();
		corD = colorD.getAmbient();
		corE = colorE.getAmbient();
		range= uss.getRange();
		
		if (corD<0.15) {
			if (corE<0.2) {
				while ((corD<0.18 || corE<0.18)&& range>.15) {
					colorD.setRedMode();
					colorE.setRedMode();
					corD = colorD.getAmbient();
					corE = colorE.getAmbient();
					range = uss.getRange();
					gd.moveAng(0, speed);
				}
			}
			else {
				colorD.setColorIdMode();
				System.out.println("Id da cor: " + colorE.getColorID());
				if (colorD.getColorID() == 2) {
					while (corE>0.7) {
						gd.moveAng(-100, speed);
					}
				}
				}
		}
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

	
	/*private GuidedDriver gd;
	private ColorSensor colorE;
	private ColorSensor colorD;
	
	public PIDlinha(GuidedDriver gd,ColorSensor colorE,ColorSensor colorD) {
		this.gd = gd;
		this.colorD = colorD;
		this.colorE = colorE;
	}
	
	double error;
	double media = 0.4;
	int kp = 17, kd = 39;
	int dir,prop;
	double deriv, integral = 0, ki = 0.9;
	long time;
	long lastTime = 0;
	double lastError = 0.0f;
	
	public void pidEsquerdo() {
		colorE.setRedMode();
		error = 10*(colorE.getAmbient() - media); //parcela proporcional
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
		gd.moveAng(dir, 100f);
	}
	
	public void pidDireito () {
		colorD.setRedMode();
		error = 10*(colorD.getAmbient() - media); //parcela proporcional
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
		dir = dir * (-1);
		gd.moveAng(dir, 100f);
	}
	
	public void verDoisPretos() {
		
	}
	
	public void closeAll() {
		colorD.close();
		colorE.close();
		gd.getMd().close();
		gd.getMe().close();
	}
	
	public static void main (String [] args) {

		PIDlinha pid = new PIDlinha(new GuidedDriver(new EV3LargeRegulatedMotor(MotorPort.C), new EV3LargeRegulatedMotor(MotorPort.D)),
				new ColorSensor(SensorPort.S1),new ColorSensor(SensorPort.S2));
		
		
		while (Button.ESCAPE.isUp()) {

			pid.pidDireito();

			
		}

		pid.closeAll();
		
		
		
	*/}
	/*while ( Button.ESCAPE.isUp()) {
		colorD = sampleProvider2.fetchSample()[0];
		System.out.println("sensor direito: "+ colorD);
		colorE = sampleProvider.fetchSample()[0];
		System.out.println("sensor esquerdo: "+ colorE);
		
		if (colorE<0.4) {
			if (colorD<0.4) {
				System.out.println("dois brancos");
				gd.moveAng(0, 100);
			}
			else {
				
				integral=0;
				//pid direito
				while ((colorD>=0.4 || integral<26) && (colorD>=0.5 || colorE>=0.8)) {
					colorD = sampleProvider2.fetchSample()[0];
					System.out.println("reflexao: "+ colorD);
					System.out.println("to no pid direito");
					error = 10*(media-colorD); //parcela proporcional
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
					gd.moveAng(dir, 100);
				}

			}
		}
		else {
			if (colorD<0.4) {
			integral =0;
			//pid esquerdo
			while ((colorE>=0.4 || integral>-26)) {
				colorE = sampleProvider.fetchSample()[0];
				System.out.println("reflexao: "+colorE);
				System.out.println("to no pid esquerdo");
				error = 10*(colorE - media); //parcela proporcional
				prop = (int)(error * kp);
				time = System.currentTimeMillis();//parcela derivativa
				deriv = kd*(error - lastError)/(time - lastTime);
				integral = ki*(error + integral); //parcela integral							
				System.out.println("int: "+integral);
				dir = (int)(deriv + prop + integral);//movimento
				System.out.println("dir: "+dir);
				dir = dir< 100? dir : 100;

				gd.moveAng(dir, 100);
				lastTime = time;
				lastError = error;
			}
			}
			else {
				a=(a+1)%2;
				switch (a) {
				case 0:
					x=0;
					while (colorD>media && x<25) {
						gd.moveAng(-30, 100);
						x++;
					}
					break;
				case 1:
					x=0;
					while (colorE>media && x<25) {
						gd.moveAng(30, 100);
						x++;
					}
					break;

				default:
					break;
				}
				
			}
		}
				
	}*/
