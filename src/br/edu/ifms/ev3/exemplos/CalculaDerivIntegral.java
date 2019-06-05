package br.edu.ifms.ev3.exemplos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;




import br.edu.ifms.ev3.wrappers.MathUtils;

public class CalculaDerivIntegral {
	
	public static void calcDerivative() {
		int params = 1;
		int order = 1;
		double x= 3.; 
		
		/*DerivativeStructure dx = new DerivativeStructure(params, order, 0, x);
		
		//combinação linear y = X^2 + 0.x
		DerivativeStructure y = new DerivativeStructure( 1., dx.pow(2), 0., dx );
		
		System.out.println( "y: " + y.getValue() );
		System.out.println( "y':" + y.getPartialDerivative(1) );
		
		
		//combinação linear y = 2X + 0.x
	    DerivativeStructure y2 = new DerivativeStructure( 0., dx, 2., dx );
		UnivariateDifferentiableFunction udf = new UnivariateDifferentiableFunction() {
			
			@Override
			public double value(double x) {
				// TODO Auto-generated method stub
				return 2*x;
			}
			
			@Override
			public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
				// TODO Auto-generated method stub
				return t;
			}
		};
	    
		double[] res2 = new double[10]; 
		
		TrapezoidIntegrator ti = new TrapezoidIntegrator();
		SimpsonIntegrator   si = new SimpsonIntegrator();
		double res  = ti.integrate(100, udf, 0, 2);
		//double res2 = si.integrate(10, udf, 0, 2);
		
		float j=0.2f;
		res2[0] = 0;
		double acc=0;
		for(int i = 1; i < 10; i++, j+=0.2f) {
			//TODO testar com senoidal e comparar com matlab
			res2[i] = si.integrate(10, udf, 0, j);
			acc    += res2[i];
		}
				
		System.out.println( "y2: " + udf.value(x) );
		System.out.println( "int(y2): " + res );
		
		for(int i = 0; i < 10; i++) {
			System.out.println( "int(y2)(simp): " + res2[i] );
		}
		System.out.println( "int(y2)(simp)(acc): " + acc );
		
		
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
		}*/
		
		
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
