package problema;

import java.util.Hashtable;

import core.Individuo;

public class Semaforo extends Individuo {
	public enum Color {
		ROJO, AMARILLO, VERDE
	}

	/**
	 * Variable para que el retraso sea un poco menor que el tiempo justo
	 */
	private final int AJUSTE_RETRASO = 2;
	
	private final double PESO_APTITUD_RETRASO = 0.3; 
	
	/**
	 * Color del semaforo.
	 */
	private Color estado;
	
	private int contadorEstado;
	private int retraso;
	private int retrasoUsado;
	
	private int velOndaVerde;
	private Tramo tramoAct;
	private Hashtable<Color, Integer> tiempoEstado;
	
	public Semaforo(Tramo tramo, int retraso){
		contadorEstado = 0;
		retrasoUsado = 0;
		this.retraso = retraso;
		this.estado = Color.ROJO;
		tramoAct = tramo;
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(15));
		tiempoEstado.put(Color.AMARILLO, new Integer(2));
		tiempoEstado.put(Color.VERDE, new Integer(30));
	}

	public int getVelOndaVerde(){
		return velOndaVerde;
	}
	
	public void setVelOndaVerde(int vel){
		velOndaVerde = vel;
	}
	
	private void setRetraso(int ret){
		this.retraso = ret;
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
	 * Indica si el sem�foro est� en verde, para poder avanzar.
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
	 * M�todo que decide cu�l es el pr�ximo estado del sem�foro.
	 * @param estActual es el estado actual del sem�foro
	 * @return el proximo estado que debe tener.
	 */
	public Color getProximoEstado(Color estActual){
		switch(estActual){
		case ROJO:
			return Color.VERDE;
		case AMARILLO:
			return Color.ROJO;
		case VERDE:
			return Color.AMARILLO;
		}
		return Color.ROJO;
	}
	
	/**
	 * Actualizar los tiempos que dura el ciclo de cada color.
	 * Actualiza el sem�foro con los valores indicados.
	 * Se actualiza concurrentemente desde el hilo del algoritmo gen�tico.
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
	
	
	/**
	 * Se apunta a lograr aproximadamente un 70% de ocupacion permanente de la calle,
	 * de forma de optimizar el transito.
	 * Se evalua:
	 * - El retraso
	 * - Los tiempos del rojo y verde
	 */
	@Override
	public void evaluarAptitud() {
		aptitud = 0;
		
		//El retraso ideal es la longitud del tramo dividido los metros por segundo que puede 
		//avanzar un auto, con unos segundos de ajuste. Unidades: segundos.
		double retrasoIdeal = ( tramoAct.getLongitud() / (velOndaVerde * 1000 / 3600) ) - AJUSTE_RETRASO;
		aptitud += (retrasoIdeal / (double) retraso) * PESO_APTITUD_RETRASO;
		
		
	}
	
	/**
	 * Asigna una aptitud tomada de la población considerada de las mejores.
	 */
	@Override
	public void mutar(double aptitud) {		
		this.aptitud = aptitud;
	}

	/**
	 * Intenta mejorar la aptitud comparando los retrasos de los individuos padre.
	 */
	@Override
	public Individuo reproducir(Individuo ind) {
		
		evaluarAptitud();
		Semaforo nuevo = new Semaforo(this.tramoAct, this.retraso);
		if(this.retraso < ind.getAptitud()){
			nuevo.setRetraso((int)ind.getAptitud());
		}
		return nuevo;
	}

}
