package problema;

import java.util.Hashtable;

import core.Individuo;

public class Semaforo extends Individuo {
	public enum Color {
		ROJO, AMARILLO, VERDE
	}

	private Color estado;
	private int contadorEstado;
	private int retraso;
	private int retrasoUsado;
	private Hashtable<Color, Integer> tiempoEstado;
	
	public Semaforo(int retraso){
		contadorEstado = 0;
		retrasoUsado = 0;
		this.estado = Color.ROJO;
		this.retraso = retraso;
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(30));
		tiempoEstado.put(Color.AMARILLO, new Integer(5));
		tiempoEstado.put(Color.VERDE, new Integer(30));
	}
	
	/**
	 * Metodo que realiza un ciclo del tiempo indicado, cambiando al color siguiente si corresponde.
	 * @param tiempo
	 */
	synchronized public void cicloSemaforo(int tiempo){
		if(retrasoUsado < retraso){
			retrasoUsado += tiempo;
		}
		else{
			contadorEstado += tiempo;
			/* Si el contador del estado actual llego al limite, cambio de estado. */
			if(contadorEstado >= (tiempoEstado.get(estado)).intValue()){
				estado = getProximoEstado(estado);
				contadorEstado = 0;
			}
		}
	}
	
	/**
	 * Indica si el semáforo está en verde, para poder avanzar.
	 * @return true si el estado es Color.VERDE; false en cualquier otro caso.
	 */
	public boolean puedoPasar(){
		return (estado == Color.VERDE);
	}

	/**
	 * Devuelve el color del estado del semaforo.
	 * @return
	 */
	public Color getColor(){
		return estado;
	}
	
	/**
	 * Método que decide cuál es el próximo estado del semáforo.
	 * @param estActual es el estado actual del semáforo
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
	 * Actualiza el semáforo con los valores indicados.
	 * Se actualiza concurrentemente desde el hilo del algoritmo genético.
	 */
	public synchronized void setearTiempos(int retraso, int rojo, int amarillo, int verde){
		this.retraso = retraso;
		retrasoUsado = 0;
		tiempoEstado.put(Color.ROJO, new Integer(rojo));
		tiempoEstado.put(Color.AMARILLO, new Integer(amarillo));
		tiempoEstado.put(Color.VERDE, new Integer(verde));
	}

	/**
	 * Obtiene el tiempo que dura el ciclo del semaforo en el color especificado.
	 */
	public synchronized int getTiempo(Color color){
		return (tiempoEstado.get(color)).intValue();
	}

	@Override
	public String toString(){
		String semaforo = "";
		semaforo += estado.toString() + "\n";
		semaforo += "Tiempo ROJO: " + tiempoEstado.get(Color.ROJO).toString() + "\n";
		semaforo += "Tiempo AMARILLO: " + tiempoEstado.get(Color.AMARILLO).toString() + "\n";
		semaforo += "Tiempo VERDE: " + tiempoEstado.get(Color.VERDE).toString() + "\n";
		return semaforo;
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
