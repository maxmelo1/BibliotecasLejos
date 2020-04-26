package br.edu.ifms.ev3.exemplos;

import br.edu.ifms.ev3.wrappers.EV3MuxUltrasonicSensor;
import lejos.hardware.Button;
import lejos.hardware.device.SensorMux;
import lejos.hardware.port.I2CPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;
import lejos.utility.Delay;

public class MuxTester {
	
	public void testUltrassonic() {
		
		
		EV3MuxUltrasonicSensor ultra = new EV3MuxUltrasonicSensor(EV3MuxUltrasonicSensor.CH_1, EV3MuxUltrasonicSensor.MODE_SONAR_CM, SensorPort.S1);
		
		
		
		//I2CPort port =  SensorPort.S1.open(I2CPort.class);
		//port.setType(I2CSensor.TYPE_LOWSPEED);
		//port.setMode(0);
		
		
		//SensorMux m = new SensorMux(port);
		
		
		
		//segunda porta
		//m.setAddress(0xA0);
		//System.out.println("deu certo");
		//System.out.println(m.getAvailableModes());
		
		//setmode dst in cm
		//m.sendData(0x52, (byte) 0x00);
		//m.configurate();
		
		//m.getData(0x08, buf, 1);
		
		
		while (Button.ESCAPE.isUp()) {
			
			System.out.println(ultra.getRange());
			//byte[] buf = new byte[1];
			/*byte aux[] = new byte[1];
			int ready=0;
			
			while(ready!=1) {
				m.getData(0x74, aux, 1);
				
				ready = aux[0];
			}*/
			
			
			//m.getData(0x52, buf, 1);
			//System.out.println("mode: "+buf[0]);
			
			//m.getData(0x54, buf, 1);
			
			//int analogVal = buf[0] & 0xff;
			
			//analogVal = m.getDistance(1);
			
			
			//System.out.println("valor lido+"+new String(buf));
			//System.out.println("valor lido"+buf[0]);
			//System.out.println("valor lido2"+buf[1]);
			//System.out.println("Analog: " + analogVal);
			//Delay.msDelay(100);
		}
		//m.close();
	}
	
	public static void main(String[] args) {
		MuxTester t = new MuxTester();
		t.testUltrassonic();
	}

}
