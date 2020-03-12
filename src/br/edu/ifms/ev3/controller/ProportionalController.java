package br.edu.ifms.ev3.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.edu.ifms.ev3.wrappers.ColorSensor;
import br.edu.ifms.ev3.wrappers.GyroSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.utility.Delay;
/**
 * Example of proportional controller. Constants may need calibration before testing.
 * @author gin
 *
 */
public class ProportionalController {
	private RMISampleProvider sampleProvider = null;
	private RemoteEV3 ev3 = null;
	private RMIRegulatedMotor me;
	private RMIRegulatedMotor md;
	private ColorSensor cs;
	private ColorSensor csd;
	//private GyroSensor gyro;
	private File file;
	private File file2;
	private File filec;
	private File filed;
	/**
	 * constants definition
	 */
	private final Float setPoint = 0.45f;
	private final Integer TP = 80;
	private final Float KP = 12f, KD = 60f, KI = 0f;
	//needed because of lego speed seetings (0 - 700 degrees/sec)
	private final Float SCALE_FACTOR = 22f;
	
	
	
	//TODO to implement graphics of: position error, linear and angular velocities.
	//TODO to compute RMS error metric.
	//TODO to test with literature known trajectories.
	public ProportionalController() {
		
		file = new File("C:\\Users\\armando\\Desktop\\OBR Java\\proportional.txt");
		file2 = new File("C:\\Users\\armando\\Desktop\\OBR Java\\erro.txt");
		filec = new File("C:\\Users\\armando\\Desktop\\OBR Java\\controle.txt");
		filed = new File("C:\\Users\\armando\\Desktop\\OBR Java\\derivative.txt");
		try {
			file.delete();
			file2.delete();
			filec.delete();
			filed.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		try {
			file.createNewFile();
			file2.createNewFile();
			filec.createNewFile();
			filed.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ev3 == null) {
			System.out.println("ev3 não conectado");
			System.exit(-1);
		}
		
		//L -> large, M-> medium
		me = ev3.createRegulatedMotor("C", 'L');
		md = ev3.createRegulatedMotor("D", 'L');
		
		cs = new ColorSensor(SensorPort.S3);
		csd = new ColorSensor(SensorPort.S1);
		//gyro = new GyroSensor(SensorPort.S3);
		//sampleProvider 	= ev3.createSampleProvider("S3", "lejos.hardware.sensor.EV3GyroSensor",null);

		cs.setRedMode();
		cs.setFloodLight(Color.RED);
		csd.setRedMode();
		csd.setFloodLight(Color.RED);
		
		try {
			proportional();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			me.close();
			md.close();
			cs.close();
			csd.close();
			//gyro.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void proportional() throws RemoteException {
		float actual, e, cord, lastError=0f, deriv,integral=0;
		long time, lastTime=0;
		
		//smooth starting
		me.setAcceleration(100);
		md.setAcceleration(100);
		
		
		//me.setSpeed(250);
		//md.setSpeed(250);
		
		//me.backward();
		//md.backward();
		//gyro.resetGyro();
		while(Button.UP.isUp()) {
       		//System.out.println("Angulo: " + gyro.getAngle());

			actual = cs.getAmbient();
			
			/*if (csd.getAmbient()>0.7&&cs.getAmbient()>0.7) {
				boolean aux = verifica90();
				if (aux==true) {
					Sound.beep();
				}
			}*/
			
			/*if (csd.getAmbient()<0.15) {
				me.stop(true);
				md.stop(true);
				Delay.msDelay(300);
				gyro.resetGyro();
				
				Sound.beepSequenceUp();

				me.setSpeed(150);
				md.setSpeed(150);
				me.backward();
	       		md.forward();
	       		
				while (gyro.getAngle()>-90 && Button.UP.isUp()) {
		       		System.out.println("Angulo: " + gyro.getAngle());
		       		
		       	}
				me.stop(true);
				md.stop(true);
								
			}*/
			
			e = setPoint-actual;
			float g = (float) ((float) SCALE_FACTOR*(e+0.33));
			time = System.currentTimeMillis();//parcela derivativa
			deriv = KD*(e*SCALE_FACTOR - lastError)/(time - lastTime);
			lastTime = time;
			lastError = e;
			integral = KI*(e*SCALE_FACTOR + integral);
			gravar(e,file2);
			gravar(deriv,filed);
			gravar(e*KP*SCALE_FACTOR, file);
			
			turn(SCALE_FACTOR*e*KP+deriv+integral);
			
			gravar(SCALE_FACTOR*e*KP+deriv, filec);
			System.out.println("error " + (SCALE_FACTOR*KP*e));
			
		}
	}
	
	
	private void turn(float error) throws RemoteException {
		me.setSpeed((int)(TP - error));
		md.setSpeed((int)(TP + error));
		
		if (TP - error>0) {
			me.backward();
		}
		else {
			me.forward();
		}
		
		if (TP+error>0) {
			md.backward();
		}
		else {
			md.forward();
		}
		
	}
	
	private boolean verifica90 () {
		//while (cs.getAmbient()>0.2 && gyro.getAngle()<45) {
			try {
				md.backward();
				me.forward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		try {
			me.stop(true);
			md.stop(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cs.getAmbient()<=0.2) 
			return true;
		
		return false;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ProportionalController();

	}

}
