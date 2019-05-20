package br.edu.ifms.ev3.wrappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MathUtils {

	// TODO implementar a derivada pela definição do limite y' = (f(x+h) - f(x))/ (h)
	/**
	 * Calcula a derivada numérica a partir da definição de limite 
	 * @param m um método a ser invocado como f(x)
	 * @param x o valor de referencia da função
	 * @return o valor da derivada numérica
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Double derivate(Method m, Double x) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		double h 	= 1e-10;
		double fx 	= (Double)m.invoke(null, x);
		double fxh	= (Double)m.invoke(null, x+h);
		
		return (fxh-fx)/h;
	}
}
