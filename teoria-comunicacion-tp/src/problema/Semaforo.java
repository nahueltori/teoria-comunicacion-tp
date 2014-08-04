package problema;

import java.util.Hashtable;
import java.util.Random;

import core.Individuo;

public class Semaforo extends Individuo {
	public enum Color {
		ROJO, AMARILLO, VERDE
	}

	/**
	 * Variable para que el retraso sea un poco menor que el tiempo justo
	 */
	private final int AJUSTE_RETRASO = 2;
	private final double PORC_IDEAL_TRAFICO = 0.7;

	private final int MIN_VELOCIDAD = 10;
	private final int MAX_VELOCIDAD = 60;
	private final int MIN_TIEMPO_ROJO = 10;
	private final int MAX_TIEMPO_ROJO = 120;
	private final int MIN_TIEMPO_VERDE = 20;
	private final int MAX_TIEMPO_VERDE = 180;
	private final int TIEMPO_ROJO_PREDET = 30;
	private final int TIEMPO_AMARILLO_PREDET = 2;
	private final int TIEMPO_VERDE_PREDET = 60;
		
	/**
	 * Color del semaforo.
	 */
	private Color estado;
	
	private int contadorEstado;
	private int retraso;
	private int retrasoUsado;
	
	private int velOndaVerde;
	private int posicion;
	private Avenida avenida;
	private Hashtable<Color, Integer> tiempoEstado;
	
	/**
	 * Constructor utilizado para el modelo a simular.
	 * @param avenida
	 * @param tramo
	 */
	public Semaforo(Avenida avenida, int posicion){
		contadorEstado = 0;
		retrasoUsado = 0;
		this.avenida = avenida;
		this.estado = Color.ROJO;
		this.posicion = posicion;
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(TIEMPO_ROJO_PREDET));
		tiempoEstado.put(Color.AMARILLO, new Integer(TIEMPO_AMARILLO_PREDET));
		tiempoEstado.put(Color.VERDE, new Integer(TIEMPO_VERDE_PREDET));
	}

	public Semaforo(Avenida avenida, int velocidad, int tiempoRojo, int tiempoVerde){
		contadorEstado = 0;
		retrasoUsado = 0;
		this.avenida = avenida;
		this.estado = Color.ROJO;
		this.posicion = 0;
		setParametrosLimite(velocidad, tiempoRojo, tiempoVerde);
		setVelOndaVerde(velocidad);
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(tiempoRojo));
		tiempoEstado.put(Color.AMARILLO, new Integer(TIEMPO_AMARILLO_PREDET));
		tiempoEstado.put(Color.VERDE, new Integer(tiempoVerde));
	}
	
	private void setParametrosLimite(int velocidad, int tiempoRojo, int tiempoVerde){
		if(velocidad < MIN_VELOCIDAD)
			velocidad = MIN_VELOCIDAD;
		if(velocidad > MAX_VELOCIDAD)
			velocidad = MAX_VELOCIDAD;
		if(tiempoRojo < MIN_TIEMPO_ROJO)
			tiempoRojo = MIN_TIEMPO_ROJO;
		if(tiempoRojo > MAX_TIEMPO_ROJO)
			tiempoRojo = MAX_TIEMPO_ROJO;
		if(tiempoVerde < MIN_TIEMPO_VERDE)
			tiempoVerde = MIN_TIEMPO_VERDE;
		if(tiempoVerde > MAX_TIEMPO_VERDE)
			tiempoVerde = MAX_TIEMPO_VERDE;
	}
	
	public int getPos(){
		return posicion;
	}
	
	public int getVelOndaVerde(){
		return velOndaVerde;
	}
	
	public synchronized void setVelOndaVerde(int vel){
		setParametrosLimite(vel,0,0);
		velOndaVerde = vel;
		setRetraso();
	}
	
	/**
	 * Calcula el retraso del semaforo en funcion de la velocidad de Onda Verde seteada.
	 */
	private void setRetraso(){
		this.retraso = Math.round(( (float)avenida.getLongitudTotal(posicion) 
									/ ((float)velOndaVerde * 1000 / 3600) ) - AJUSTE_RETRASO);
//		retrasoUsado = 0;
	}
	
	/**
	 * Metodo que realiza un ciclo del tiempo indicado, cambiando al color siguiente si corresponde.
	 * @param tiempo
	 */
	synchronized public void cicloSemaforo(int tiempo){
		if(retrasoUsado < retraso){
			retrasoUsado += tiempo;
			if(retrasoUsado >= retraso){
				cambiarColor();
			}
		}
		else{
			contadorEstado += tiempo;
			/* Si el contador del estado actual llego al limite, cambio de estado. */
			if(contadorEstado >= (tiempoEstado.get(estado)).intValue()){
				cambiarColor();
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
	public void cambiarColor(){
		switch(estado){
		case ROJO:
			estado = Color.VERDE;
			break;
		case AMARILLO:
			estado = Color.ROJO;
			break;
		case VERDE:
			estado = Color.AMARILLO;
			break;
		}
		contadorEstado = 0;
	}
	
	/**
	 * Actualizar los tiempos que dura el ciclo de cada color.
	 * Actualiza el sem�foro con los valores indicados.
	 * Se actualiza concurrentemente desde el hilo del algoritmo gen�tico.
	 */
	public synchronized void setearTiempos(int rojo, int amarillo, int verde){
		setParametrosLimite(0,rojo,verde);
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
	
	@Override
	public boolean equals(Object o){
		return posicion == ((Semaforo)o).posicion;
	}
	
	/**
	 * La aptitud ideal sera de 1 para una ocupacion ideal, y mas cercana a 0 para la menos apropiada.
	 * Variables ajustables:
	 * - Velocidad de la Onda Verde
	 * - Tiempo del semaforo en color Rojo
	 * - Tiempo del semaforo en color Verde
	 */
	@Override
	public void evaluarAptitud() {
		aptitud = 0;

		//Cantidad de autos estimada dentro del tramo, al momento de pasar al rojo.
		//No se considera el tiempo en verde, ya que supongo que hay otros que salen del tramo a la vez.
		int autosVerde = (int) ((retraso + AJUSTE_RETRASO) * avenida.getTrafico());

		//Cantidad de autos que ingresan al tramo durante el rojo
		int autosRojo = (int) (getTiempo(Color.ROJO) * avenida.getTrafico());
		int autosMax = avenida.getLongitud(posicion) / 100 * Tramo.MAX_DENS_AUTOS;
		
		//Considero que la cantidad ideal de trafico es el 70% de ocupacion
		float ocupacion = (float)(autosVerde + autosRojo) / (float)autosMax;
		if(ocupacion > 1)
			ocupacion = 1;
		ocupacion -= PORC_IDEAL_TRAFICO;
		if(ocupacion < 0)
			ocupacion *= (-1);
		//El valor de aptitud lo obtengo de restarle a 1 la diferencia entre la ocupacion estimada y la ideal
		aptitud = 1 - ocupacion;
	}
	
	/**
	 * Varia levemente los atributos clave.
	 */
	@Override
	public void mutar() {		
		Random rand = new Random();
		setVelOndaVerde(velOndaVerde + (rand.nextInt(4) - 2));
		setearTiempos(getTiempo(Color.ROJO) + (rand.nextInt(6) - 3), 
				getTiempo(Color.AMARILLO), 
				getTiempo(Color.VERDE) + (rand.nextInt(6) - 3));
	}

	/**
	 * Reproduce dos individuos, seleccionando al azar atributos de uno y otro.
	 */
	@Override
	public Individuo reproducir(Individuo ind) {
		Random rand = new Random();
		//Casteo el otro individuo a reproducir, ya que estoy seguro que son dos Semaforos.
		Semaforo otro = (Semaforo)ind;
		
		//Armo todos los datos a utilizar, para seleccionarlos al azar.
		int[] arrVeloc = {velOndaVerde, otro.velOndaVerde};
		int[] arrRojo = {getTiempo(Color.ROJO), otro.getTiempo(Color.ROJO)};
		int[] arrVerde = {getTiempo(Color.VERDE), otro.getTiempo(Color.VERDE)};
		
		//Creo un nuevo Semaforo con los datos
		return new Semaforo(avenida, arrVeloc[rand.nextInt(1)], 
				arrRojo[rand.nextInt(1)], arrVerde[rand.nextInt(1)]);
	}

}
