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
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
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
	private File file;
	private File file2;
	/**
	 * constants definition
	 */
	private final Float setPoint = 0.41f;
	private final Integer TP = 20;
	private final Float KP = 5f;
	//needed because of lego speed seetings (0 - 700 degrees/sec)
	private final Float SCALE_FACTOR = 10f;
	
	
	
	//TODO to implement graphics of: position error, linear and angular velocities.
	//TODO to compute RMS error metric.
	//TODO to test with literature known trajectories.
	public ProportionalController() {
		
		file = new File("C:\\Users\\armando\\Desktop\\OBR Java\\vsensor.txt");
		file2 = new File("C:\\Users\\armando\\Desktop\\OBR Java\\erro.txt");
		try {
			file.delete();
			file2.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		try {
			file.createNewFile();
			file2.createNewFile();
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
		me = ev3.createRegulatedMotor("A", 'L');
		md = ev3.createRegulatedMotor("B", 'L');
		
		cs = new ColorSensor(SensorPort.S1);
		csd = new ColorSensor(SensorPort.S2);
		
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
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void proportional() throws RemoteException {
		float actual, e;
		
		//smooth starting
		me.setAcceleration(100);
		md.setAcceleration(100);
		
		me.setSpeed(200);
		md.setSpeed(200);
		
		me.backward();
		md.backward();
		
		while(Button.UP.isUp()) {
			actual = cs.getAmbient();
			
			if (csd.getAmbient()>0.7&&cs.getAmbient()>0.7) {
				boolean aux = verifica90();
				if (aux==true) {
					Sound.beep();
				}
			}
			
			if (csd.getAmbient()<0.15) {
				me.stop(true);
				md.stop(true);
				break;
			}
			
			e = setPoint-actual;
			float g = (float) ((float) SCALE_FACTOR*(e+0.33));
			gravarErro(e);
			gravarValor(actual);
			turn(SCALE_FACTOR*e*KP);
			
			System.out.println("error " + (SCALE_FACTOR*KP*e*g) );
			
		}
	}
	
	
	private void turn(float error) throws RemoteException {
		me.setSpeed((int)(TP - error));
		md.setSpeed((int)(TP + error));
		
	}
	
	private boolean verifica90 () {
		while (cs.getAmbient()>0.2 && System.currentTimeMillis()/1000>15) {
			try {
				md.backward();
				me.forward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	
	private void gravarErro (float valor) {
		
		FileWriter fw;
		PrintWriter pw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(file2, true);
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
	
	private void gravarValor (float valor) {
		
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
