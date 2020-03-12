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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import lejos.utility.Delay;

public class MonitorLego {
	
	private RMISampleProvider sampleProvider = null;
	private RMISampleProvider sampleProvider2 = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	private RMIGuidedDriver gd;

	
	double error;
	double media = 0.33;
	int kp = 17, kd = 39;
	int dir,prop,x,a=0;
	double deriv, integral = 0, ki = 0.9;
	long time;
	long lastTime = 0;
	double lastError = 0.0;
	int speed = 200, curva = 0;
	
	private void pid (float corD) {
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
		dir = dir * (-1);
		gd.moveAng(dir, 100);
	}
	
	private void gravar (float valor, File file) {

		FileWriter fw;
		PrintWriter pw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			bw = new BufferedWriter(pw);
			String v = String.valueOf(valor);
			bw.write(v);
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void andarReto () {
		gd.moveAng(0, speed);
	}

	public MonitorLego() {
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
			
			System.out.println("ev3 nulo? " + (ev3 == null));
			sampleProvider 	= ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "Red");
			//sampleProvider2 = ev3.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "Red");

			float corE = sampleProvider.fetchSample()[0];
			//float corD = sampleProvider2.fetchSample()[0];
			
			me = ev3.createRegulatedMotor("A", 'L');
			md = ev3.createRegulatedMotor("B", 'L');

			File file = new File("C:\\Users\\armando\\Desktop\\OBR Java\\erros.txt");
			try {
				file.delete();
			} catch (Exception e) {
				// TODO: handle exception
			}
				
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.gd = new RMIGuidedDriver(me,md);
			
			
			while (Button.DOWN.isUp()) {
				corE = sampleProvider.fetchSample()[0];
				gravar (corE,file);
				Delay.msDelay(20);
			}
			
			/*while (Button.ESCAPE.isUp()) {
				corD = sampleProvider2.fetchSample()[0];
				corE = sampleProvider.fetchSample()[0];
				System.out.println("cor direita: " + corD);
				System.out.println("cor esquerda: " + corE);
			}		*/
			gd.getMd().stop(true);
			gd.getMe().stop(true);
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				sampleProvider.close();
				//sampleProvider2.close();
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
