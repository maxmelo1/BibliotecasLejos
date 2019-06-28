package br.edu.ifms.ev3.exemplos;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import br.edu.ifms.ev3.exemplos.RMIGuidedDriver;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class MonitorLego {
	
	private RMISampleProvider sampleProvider = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	RMIGuidedDriver gd;

	public MonitorLego() {
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
			
			System.out.println("ev3 nulo? " + (ev3 == null));
			
			sampleProvider = ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "Red" );
			float sample = sampleProvider.fetchSample()[0];
			
			System.out.println("cor -> "+ sample);
			
			me = ev3.createRegulatedMotor("D", 'L');
			md = ev3.createRegulatedMotor("C", 'L');
			
			this.gd = new RMIGuidedDriver(me,md);
			
			double error;
			double media = 0.2;
			int kp = 100, kd = 300;
			int dir,prop;
			double deriv, integral = 0, ki = 0.8;
			long time;
			long lastTime = 0;
			double lastError = 0.0f;
			int speed = 200,x=0;
				
			while (x<10) {
				sample = sampleProvider.fetchSample()[0];
				System.out.println("cor refletida: "+sample);
				error = (sample - media); //parcela proporcional
				prop = (int)(error * kp);
				
				time = System.currentTimeMillis();//parcela derivativa
				deriv = kd*(error - lastError)/(time - lastTime);
				lastTime = time;
				lastError = error;				
				integral = ki*(error + integral); //parcela integral
			
				
				dir = (int)(deriv + prop + integral);//movimento
				System.out.println("dir: "+dir);
				gd.move(dir, 200f);
				x++;

			}	
			me.stop(true);
			me.stop(true);
					
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				sampleProvider.close();
				
				me.close();
				md.close();
				
				
				
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
