package br.edu.ifms.ev3.exemplos;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import br.edu.ifms.ev3.exemplos.RMIGuidedDriver;
import lejos.hardware.Button;
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;
//import lejos.utility.Delay;

public class MonitorLego {
	
	private RMISampleProvider sampleProvider = null;
	private RMISampleProvider sampleProvider2 = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	private RMIGuidedDriver gd;

	
	double error;
	double media = 0.45;
	int kp = 17, kd = 39;
	int dir,prop;
	double deriv, integral = 0, ki = 0.9;
	long time;
	long lastTime = 0;
	double lastError = 0.0;

	

	public MonitorLego() {
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
			
			System.out.println("ev3 nulo? " + (ev3 == null));
			
			sampleProvider = ev3.createSampleProvider("S3", "lejos.hardware.sensor.EV3ColorSensor", "Red" );
			sampleProvider2 = ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "Red" );
			if (sampleProvider == null) {
				System.out.println("bbbb");
			}
			if (sampleProvider2 == null) {
				System.out.println("aaaa");
			}
			float colorE = sampleProvider.fetchSample()[0];
			float colorD = sampleProvider2.fetchSample()[0];
			
			me = ev3.createRegulatedMotor("B", 'L');
			md = ev3.createRegulatedMotor("A", 'L');

			
			this.gd = new RMIGuidedDriver(me,md);
			
			while ( Button.ESCAPE.isUp()) {
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
						while (colorD>=0.4 || integral<26) {
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
					//if (colorD<0.4) {
					integral =0;
					//pid esquerdo
					while (colorE>=0.4 || integral>-26) {
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
					//}
				}
				}
						
			}
			gd.getMd().stop(true);
			gd.getMe().stop(true);
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				sampleProvider.close();
				sampleProvider2.close();
				gd.getMd().close();
				gd.getMe().close();
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	

	public static void main(String[] args) {
		new MonitorLego();
	}
}
