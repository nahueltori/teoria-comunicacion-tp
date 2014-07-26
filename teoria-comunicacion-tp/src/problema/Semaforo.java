package problema;

import java.util.Hashtable;

import core.Individuo;

public class Semaforo extends Individuo {
	public enum Color {
		ROJO, AMARILLO, VERDE
	}

	private Color estado;
	private int contadorEstado;
	private Hashtable<Color, Integer> tiempoEstado;
	
	public Semaforo(){
		contadorEstado = 0;
		this.estado = Color.ROJO;
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(120));
		tiempoEstado.put(Color.AMARILLO, new Integer(30));
		tiempoEstado.put(Color.VERDE, new Integer(120));
	}
	
	/**
	 * Metodo que realiza un ciclo del tiempo indicado, cambiando al color siguiente si corresponde.
	 * @param tiempo
	 */
	synchronized public void cicloSemaforo(int tiempo){
		contadorEstado += tiempo;
		/* Si el contador del estado actual llego al limite, cambio de estado. */
		if(contadorEstado >= (tiempoEstado.get(estado)).intValue()){
			estado = getProximoEstado(estado);
		}
	}
	
	/**
	 * Indica si el sem�foro est� en verde, para poder avanzar.
	 * @return true si el estado es Color.VERDE; false en cualquier otro caso.
	 */
	public boolean puedoPasar(){
		return (estado == Color.VERDE);
	}
	
	/**
	 * M�todo que decide cu�l es el pr�ximo estado del sem�foro.
	 * @param estActual es el estado actual del sem�foro
	 * @return el proximo estado que debe tener.
	 */
	public Color getProximoEstado(Color estActual){
		switch(estActual){
		case ROJO:
			return Color.AMARILLO;
		case AMARILLO:
			return Color.VERDE;
		case VERDE:
			return Color.ROJO;
		}
		return Color.ROJO;
	}
	
	/**
	 * Actualizar los tiempos que dura el ciclo de cada color.
	 * Actualiza el sem�foro con los valores indicados.
	 * Se actualiza concurrentemente desde el hilo del algoritmo gen�tico.
	 */
	synchronized public void setearTiempos(int rojo, int amarillo, int verde){
		tiempoEstado.put(Color.ROJO, new Integer(rojo));
		tiempoEstado.put(Color.AMARILLO, new Integer(amarillo));
		tiempoEstado.put(Color.VERDE, new Integer(verde));
	}

	/**
	 * Obtiene el tiempo que dura el ciclo del semaforo en el color especificado.
	 */
	public int getTiempo(Color color){
		return (tiempoEstado.get(color)).intValue();
	}
	
	/*
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
