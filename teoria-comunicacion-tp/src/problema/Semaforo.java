package problema;

import java.util.Date;
import java.util.Hashtable;

import core.Individuo;

public class Semaforo extends Individuo {
	public enum Color {
		ROJO, AMARILLO, VERDE
	}

	Trafico tramo;
	Color estado;
	Date reloj;
	Hashtable<Color, Integer> estadoTiempo;
	
	public Semaforo(Trafico tramo){
		this.tramo = tramo;
		this.estado = Color.ROJO;
		reloj = new Date(0);
		estadoTiempo = new Hashtable<Color, Integer>();
		estadoTiempo.put(Color.ROJO, new Integer(120));
		estadoTiempo.put(Color.AMARILLO, new Integer(30));
		estadoTiempo.put(Color.VERDE, new Integer(120));
	}
	
	/**
	 * Metodo que realiza un ciclo del tiempo indicado, cambiando al color siguiente si corresponde.
	 * @param tiempo
	 */
	public void cicloSemaforo(int tiempo){
		
	}
	
	/**
	 * Metodos para implementar el algoritmo genetico.
	 */
	@Override
	public void evaluarAptitud() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mutar() {
		// TODO Auto-generated method stub

	}

	@Override
	public Individuo reproducir(Individuo ind) {
		// TODO Auto-generated method stub
		return null;
	}

}
