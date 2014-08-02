package problema;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import vista.Dibujante;
import core.Evolucion;
import core.Individuo;

public class Controlador {

	static private int MIL_MILISEG = 1000;
	static private int UN_SEG = 1;
	
	static double multiploCiclo;
	static double multiploDelay;
	
	static int cantIndividuos;
	
	/** Funcion principal. */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Scanner parametros;
		try {
			parametros = new Scanner(new BufferedReader(new FileReader("data/controlador.txt")));
			multiploCiclo = parametros.nextDouble();
			multiploDelay = parametros.nextDouble();
			cantIndividuos = parametros.nextInt();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Avenida avenida = new Avenida();
		Dibujante dibu = new Dibujante();
		dibu.setAvenida(avenida);
		
//		List<Individuo> listaIndividuos = avenida.getListaSemaforos();
		//crearIndividuosIniciales(avenida);
//		Evolucion evolucion = new Evolucion(listaIndividuos, 10);
//		evolucion.run();
		
		for(int i=0; ; i++){
			avenida.cicloAvenida((int) (multiploCiclo * UN_SEG));
//			System.out.println("Ciclo N° " + i);
//			System.out.println(avenida.toString());
			dibu.update(i);
			try {
				Thread.sleep((long) (multiploDelay * MIL_MILISEG));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
/*		evolucion.evolucionar();
		
		Individuo resultado = evolucion.getMejorIndividuo();
*/		
//		System.exit(0);
	}
	
	//Para que crear individuos iniciales si ya la avenida los tiene????
/*	private static List<Individuo> crearIndividuosIniciales(Avenida avenida) {
		List<Individuo> lista = new ArrayList<Individuo>();
		Random rVelocidad = new Random();
		Random rRojo = new Random();
		Random rVerde = new Random();
		for(int i=0; i<cantIndividuos; i++){
			lista.add(new Semaforo(avenida, rVelocidad.nextInt(70) + 1, rRojo.nextInt(90), rVerde.nextInt(90)));
		}
		return lista;
	}
*/
	  
}
