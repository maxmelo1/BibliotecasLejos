package br.edu.ifms.ev3.exemplos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

import br.edu.ifms.ev3.wrappers.MathUtils;

public class CalculaDerivIntegral {
	
	public static void calcDerivative() {
		int params = 1;
		int order = 1;
		double x= 3.; 
		
		DerivativeStructure dx = new DerivativeStructure(params, order, 0, x);
		
		//combinação linear y = X^2 + 0.x
		DerivativeStructure y = new DerivativeStructure( 1., dx.pow(2), 0., dx );
		
		//combinação linear y = 2X + 0.x
	    DerivativeStructure y2 = new DerivativeStructure( 0., dx, 2., dx );
		
		System.out.println( "y: " + y.getValue() );
		System.out.println( "y':" + y.getPartialDerivative(1) );
		
		
		
		
		try {
			Method m = CalculaDerivIntegral.class.getDeclaredMethod("fx", Double.class);
			
			System.out.println("fx(3) = " + fx(x));
			System.out.println("fx(3) = " + MathUtils.derivate(m, x));
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Classe referencia para o cálculo de derivada na MathUtils
	 * Modificar conforme necessidade.
	 */
	public static Double fx(Double x) {
		return x*x;
	}

	public static void main(String[] args) {
		calcDerivative();
	}
}
