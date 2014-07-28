package problema;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Controlador {

	static private int TIEMPO_CICLO = 1;
	/** Funcion principal. */
	public static void main(String[] args) {
		
		Avenida avenida = new Avenida();
		
		for(int i=0; i<60; i++){
			avenida.cicloAvenida(TIEMPO_CICLO);
			System.out.println("Ciclo N° " + i);
			System.out.println(avenida.toString());
			try {
				Thread.sleep(500 * TIEMPO_CICLO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
/*		int cantIndividuos;
		int condFin;
		float datoFin;
		float porcSeleccion;
		float porcMutacion;
		
		System.out.println("Ingrese la cantidad de individuos de la poblacion:");
		cantIndividuos = getNextInteger();
		
		System.out.println("Ingrese la Condicion de finalizacion:");
		System.out.println("1 - Cantidad de Generaciones");
		System.out.println("2 - Aptitud promedio mínima");
		System.out.println("3 - Tasa mínima de crecimiento de la aptitud");
		condFin = getNextInteger();
		
		System.out.println("El dato parámetro para la Condicion de finalizacion:");
		System.out.println("Para 1: la Cantidad de Generaciones");
		System.out.println("Para 2: la Aptitud promedio mínima");
		System.out.println("Para 3: la Tasa mínima de crecimiento de la aptitud");
		datoFin = getNextFloat();
		
		System.out.println("Ingrese el porcentaje de seleccion:");
		porcSeleccion = getNextFloat();
		
		System.out.println("Ingrese el porcentaje de mutacion:");
		porcMutacion = getNextFloat();
		
		
		Evolucion evolucion = new Evolucion(cantIndividuos, porcSeleccion, porcMutacion,
				condFin, datoFin);
		evolucion.evolucionar();
		
		Individuo resultado = evolucion.getMejorIndividuo();
		System.out.println("Mejor Individuo encontrado: " + resultado.toString());
*/		
		System.exit(0);
	}
	
	static int getNextInteger() { 
	    String line; 

	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	    try { 
	      line = in.readLine(); 
	      int i = Integer.valueOf(line).intValue(); 
	      return i; 
	    } 
	    catch (Exception e) { 
	      return -1; 
	    } 

	}
	
	static float getNextFloat() { 
	    String line; 

	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	    try { 
	      line = in.readLine(); 
	      float i = (new Float(line)).floatValue(); 
	      return i; 
	    } 
	    catch (Exception e) { 
	      return -1; 
	    } 

	  }

	  
}
