package problema;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import vista.Dibujante;
import core.Evolucion;
import core.Individuo;

public class Controlador {

	static private int TIEMPO_CICLO = 1;
	/** Funcion principal. */
	public static void main(String[] args) {
		
		Avenida avenida = new Avenida();
		Dibujante dibu = new Dibujante();
		dibu.setAvenida(avenida);
		
		Evolucion evolucion = new Evolucion(avenida.getListaIndividuos(), 10);
		evolucion.run();
		
		for(int i=0; i<360; i++){
			avenida.cicloAvenida(TIEMPO_CICLO);
			System.out.println("Ciclo N° " + i);
			System.out.println(avenida.toString());
			dibu.update(i);
			try {
				Thread.sleep(500 * TIEMPO_CICLO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
/*		evolucion.evolucionar();
		
		Individuo resultado = evolucion.getMejorIndividuo();
*/		
		System.exit(0);
	}

	  
}
