package problema;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

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
	private int posicion;
	private Avenida avenida;
	private Hashtable<Color, Integer> tiempoEstado;
	
    private File archivo = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private String linea = null;
	
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

		try{
			//Cargamos el archivo de la ruta relativa
			archivo = new File("data/semaforo.txt");
			//Cargamos el objeto FileReader
			fr = new FileReader(archivo);
			//Creamos un buffer de lectura
			br = new BufferedReader(fr);

			String[] datos = null;
			
			linea = br.readLine();
			datos = linea.split(";");
			
			tiempoEstado = new Hashtable<Color, Integer>();
			tiempoEstado.put(Color.ROJO, Integer.parseInt(datos[0]));
			tiempoEstado.put(Color.AMARILLO, Integer.parseInt(datos[1]));
			tiempoEstado.put(Color.VERDE, Integer.parseInt(datos[2]));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se encontro el archivo de configuracion de Semaforos.");
		}
	}

	public Semaforo(Avenida avenida, int velocidad, int tiempoRojo, int tiempoVerde){
		contadorEstado = 0;
		retrasoUsado = 0;
		this.avenida = avenida;
		this.estado = Color.ROJO;
		this.posicion = 0;
		setVelOndaVerde(velocidad);
		tiempoEstado = new Hashtable<Color, Integer>();
		tiempoEstado.put(Color.ROJO, new Integer(tiempoRojo));
		tiempoEstado.put(Color.AMARILLO, 2);
		tiempoEstado.put(Color.VERDE, new Integer(tiempoVerde));
		evaluarAptitud();
	}
	
	public int getPos(){
		return posicion;
	}
	
	public int getVelOndaVerde(){
		return velOndaVerde;
	}
	
	public void setVelOndaVerde(int vel){
		velOndaVerde = vel;
		setRetraso();
	}
	
	/**
	 * Calcula el retraso del semaforo en funcion de la velocidad de Onda Verde seteada.
	 */
	private void setRetraso(){
		this.retraso = Math.round(( (float)avenida.getLongitudTotal(posicion) 
									/ ((float)velOndaVerde * 1000 / 3600) ) - AJUSTE_RETRASO);
		retrasoUsado = 0;
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
	 * La aptitud ideal sera de aquel individuo que permita transitar a los autos
	 * por la avenida, en el menor tiempo posible, sin congestionarse.
	 * Variables ajustables:
	 * - Velocidad de la Onda Verde
	 * - Tiempo del semaforo en color Rojo
	 * - Tiempo del semaforo en color Verde
	 */
	@Override
	public void evaluarAptitud() {
		aptitud = 0;
		
		//Tiempo total de transito comenzando en el segundo 0, hasta el final de la AVENIDA
		float tiempoTotal = 0;
		for(posicion = 0; posicion<avenida.getTramos().size(); posicion++){
			tiempoTotal += (float)avenida.getLongitud(posicion) / ((float)velOndaVerde * 1000 / 3600);
		}
		aptitud += tiempoTotal;
		
		//Ecuacion de ajuste de los tiempos rojo y verde, y que relacione el estado del transito.
		
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
		Semaforo nuevo = new Semaforo(avenida, posicion);
//		if(this.retraso < ind.getAptitud()){
//			nuevo.setRetraso((int)ind.getAptitud());
//		}
		return nuevo;
	}

}
