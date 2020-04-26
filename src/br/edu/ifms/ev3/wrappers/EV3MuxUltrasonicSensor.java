package br.edu.ifms.ev3.wrappers;

import lejos.hardware.device.SensorMux;
import lejos.hardware.port.I2CPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;
import lejos.utility.Delay;

public class EV3MuxUltrasonicSensor {
	
	public static final byte MODE_SONAR_CM       = 0X00;
	public static final byte MODE_SONAR_INCHES   = 0x01;
	public static final byte MODE_SONAR_PRESENCE = 0x02;
	
	public static final int CH_1 = 0xA0;
	public static final int CH_2 = 0xA2;
	public static final int CH_3 = 0xA4;
	
	//do not expose internal registers
	private final int REG_MODE = 0x52;
	private final int REG_READ = 0x54;
	
	private byte mode;
	private int address;
	private I2CPort port;
	private SensorMux m;
	
	public EV3MuxUltrasonicSensor(int address, byte mode, Port port) {
		this.address = address;
		this.mode = mode;
		
		this.port =  port.open(I2CPort.class);
		this.port.setType(I2CSensor.TYPE_LOWSPEED);
		

		m = new SensorMux(this.port);
		
		
		m.setAddress(this.address);
		System.out.println("Porta vinculada");
		
		//setmode dst in cm
		m.sendData(REG_MODE, this.MODE_SONAR_CM);
		m.configurate();
	}
	
	/**
	 * Retorna a leitura do ultrassom, filtrado por média.
	 * Leituras realizadas a cada 10ms.
	 * TODO portar para outros filtros serem aplicáveis
	 * TODO verificar o range do sensor para limitar corretamente
	 * @author Max
	 * @return valor médio de leitura
	 */
	public float getRange() {
		byte[] buf = new byte[1];
		int acc = 0;
		
		
		for(int i=0; i<5; i++) {
			m.getData(0x54, buf, 1);
			acc += buf[0];
			
			Delay.msDelay(10);
		}
		acc /= 5;
		
		float value = acc / 10.0f;
		
		value = value <= 0? 0f: value<20?value:20;
		
		return value;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		port.close();
	}

}
