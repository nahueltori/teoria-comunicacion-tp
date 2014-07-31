package problema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vista.Dibujante;
import core.Evolucion;
import core.Individuo;

public class Controlador {

	static private int TIEMPO_CICLO = 1;

	static private int CANT_INDIVIDUOS = 20;
	
	/** Funcion principal. */
	public static void main(String[] args) {
		
		Avenida avenida = new Avenida();
		Dibujante dibu = new Dibujante();
		dibu.setAvenida(avenida);
		
		List<Individuo> listaIndividuos = crearIndividuosIniciales(avenida);
		Evolucion evolucion = new Evolucion(listaIndividuos, 10);
		evolucion.run();
		
		for(int i=0; i<360; i++){
			avenida.cicloAvenida(TIEMPO_CICLO);
//			System.out.println("Ciclo N° " + i);
//			System.out.println(avenida.toString());
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
	private static List<Individuo> crearIndividuosIniciales(Avenida avenida) {
		List<Individuo> lista = new ArrayList<Individuo>();
		Random rVelocidad = new Random();
		Random rRojo = new Random();
		Random rVerde = new Random();
		for(int i=0; i<CANT_INDIVIDUOS; i++){
			lista.add(new Semaforo(avenida, rVelocidad.nextInt(70), rRojo.nextInt(90), rVerde.nextInt(90)));
		}
		return lista;
	}

	  
}
