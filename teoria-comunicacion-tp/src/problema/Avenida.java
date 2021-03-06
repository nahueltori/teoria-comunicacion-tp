package problema;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import problema.Semaforo.Color;
import core.Individuo;

public class Avenida {

	static private int UN_MILISEG = 1;
	static private int CIEN_MILISEG = 1000;
	static private int MIL_MILISEG = 1000;
	
	private List<Semaforo> listaSemaforos;
	
	public List<Tramo> listaTramos;
	
	private Tramo inicioAv;
	private Trafico trafico;
	private String paramTrafico;
	
	private int contAutos;
	private int contSegundos;
	private float indTrafico;
	
	private class Trafico extends Thread{
			
		private int hora;
		private int tiempo;
		private boolean ejecutar;
		private int manana;
		private int tarde;
		private int noche;
		private int autosManana;
		private int autosTarde;
		private int autosNoche;
		private int delayManana;
		private int delayTarde;
		private int delayNoche;
				
		Trafico(){
			try {
				Scanner paramTrafico = new Scanner(new BufferedReader(new FileReader("data/trafico.txt")));
				manana = paramTrafico.nextInt();
				autosManana = paramTrafico.nextInt();
				delayManana = paramTrafico.nextInt();
				tarde = paramTrafico.nextInt();
				autosTarde = paramTrafico.nextInt();
				delayTarde = paramTrafico.nextInt();
				noche = paramTrafico.nextInt();
				autosNoche = paramTrafico.nextInt();
				delayNoche = paramTrafico.nextInt();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			tiempo = 0;
			hora = 0;
			ejecutar = true;
		}
		
		public void setParams(int tiempo){
			this.tiempo = tiempo;
		}
		
		@Override
		public void run(){
			/**
			 * Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
			 */
			Random rand = new Random();
			int delay = 0;
			while(ejecutar){
				//Si el metodo es automatico, creo autossegun el tiempo de ejecucion.
				if(tiempo > 0){
					int varAleatCantidad = 0;
					hora += tiempo;
					if(hora < manana){
//						varAleatCantidad = rand.nextInt(autosManana);
						varAleatCantidad = autosManana;
						delay = delayManana;
					}
					else{
						if(hora < tarde){
//							varAleatCantidad = rand.nextInt(autosTarde);
							varAleatCantidad = autosTarde;
							delay = delayTarde;
						}
						else{
							if(hora < noche){
								varAleatCantidad = rand.nextInt(autosNoche);
								delay = delayNoche;
							}else{
									varAleatCantidad = rand.nextInt(autosManana);
									delay = delayManana;
								}
						}
					}
					for(int i=0; i<varAleatCantidad; i++){
						recibirAuto();
						try {
							sleep(CIEN_MILISEG / varAleatCantidad);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					sleep(UN_MILISEG + (MIL_MILISEG * delay));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Avenida(){
		Scanner parametros;
		contAutos = 0;
		contSegundos = 0;
		indTrafico = 0;
		trafico = new Trafico();
		listaSemaforos = new ArrayList<Semaforo>();
		listaTramos = new CopyOnWriteArrayList<Tramo>();
		try {
			parametros = new Scanner(new BufferedReader(new FileReader("data/avenida.txt")));
			paramTrafico = parametros.next();
			if(paramTrafico.equals("A"))
				trafico.start();
			Tramo tramoAnt = null;
			int i = 0;
			while(parametros.hasNext()){
				int longitud = parametros.nextInt();
				
				//Creo los tramos de Trafico y los relaciono entre si
				Tramo tramo = new Tramo(longitud);
				listaTramos.add(tramo);

				//Creo los semaforos para cada tramo de trafico
				Semaforo semaforo = new Semaforo(this, i);
				listaSemaforos.add(semaforo);
				tramo.setSemaforo(semaforo);
				
				if(tramoAnt != null){
					tramoAnt.relacionarTrafico(tramo);
				}else{
					inicioAv = tramo;
				}
				tramoAnt = tramo;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se encontro el archivo de configuracion.");
		}
		for(Semaforo sem: listaSemaforos){
			//Asigno una velocidad inicial para la onda verde
			sem.setVelOndaVerde(20);
		}
	}
	
	/**
	 * @return Se obtiene la lista de semaforos de la avenida.
	 */
	public List<Semaforo> getListaSemaforos(){
		return listaSemaforos;
	}
	
	/**
	 * Ciclo de tiempo para la avenida, en donde se actualizan los estados de todos sus
	 * elementos. 
	 * @param tiempo Recibe como parametro el tiempo transcurrido desde el ultimo ciclo.
	 */
	public void cicloAvenida(int tiempo){
		contSegundos += tiempo;
		trafico.setParams(tiempo);
		for(Tramo tramo : listaTramos){
			tramo.cicloTrafico(tiempo);
		}
		if(contAutos > 0){
			indTrafico = (float)contAutos / (float)contSegundos;
			contAutos = contSegundos = 0;
		}
	}
	
	/**
	 * Metodo que recibe el trafico y crea autos en la avenida.
	 */
	private void recibirAuto(){
		Random idAuto = new Random();
		if(inicioAv.estadoTrafico() < Tramo.PORC_TOTAL){
			inicioAv.recibirTrafico(new Auto(idAuto.nextInt()));
			contAutos++;
		}
	}
	
	/**
	 * Metodo para recibir trafico desde el exterior, manualmente.
	 */
	public void crearAuto(){
		if(paramTrafico.equals("M"))
			recibirAuto();
	}
	
	/**
	 * Devuelve el estado del trafico a la entrada de la Avenida.
	 * La unidad es autos por segundo.
	 * @return
	 */
	public float getTrafico(){
		return indTrafico;
	}
	
	/**
	 * Se obtiene la longitud del tramo para un semaforo dado.
	 * @param sem para indicar la posicion que busco
	 * @return
	 */
	public int getLongitud(int pos){
		Tramo tramo = listaTramos.get(pos);
		return tramo.getLongitud();
	}
	
	/**
	 * Se obtiene la longitud total de la avenida hasta un semaforo dado.
	 * @param sem para indicar la posicion que busco
	 * @return
	 */
	public int getLongitudTotal(int pos){
		Tramo tramo = listaTramos.get(pos);
		return tramo.getLongitudTotal();
	}
	
	/** 
	 * Metodo usado para aplicar al modelo actual el mejor semaforo encontrado
	 * por el algoritmo genetico.
	 * @param ideal
	 */
	public synchronized void aplicarResultado(Individuo indIdeal){
		Semaforo ideal = (Semaforo)indIdeal;
		int velIdeal = ideal.getVelOndaVerde();
		int rojoIdeal = ideal.getTiempo(Color.ROJO);
		int amarilloIdeal = ideal.getTiempo(Color.AMARILLO);
		int verdeIdeal = ideal.getTiempo(Color.VERDE);
		for(Semaforo sem : listaSemaforos){
			sem.setVelOndaVerde(velIdeal);
			sem.setearTiempos(rojoIdeal, amarilloIdeal, verdeIdeal);
		}
		for(Tramo tramo : listaTramos){
			tramo.actualizarVelocidad(velIdeal);
		}
		System.out.println("Semaforo aplicado");
		System.out.println("Velocidad   : " + velIdeal);
		System.out.println("Tiempo Rojo : " + rojoIdeal);
		System.out.println("Tiempo Verde: " + verdeIdeal);
		System.out.println("");
	}
	
	@Override
	public String toString(){
		String avenida = "";
		Tramo tramo = inicioAv;
		int i = 1;
		while(tramo != null){
			avenida += "Tramo " + i + "      | ";
			i++;
			tramo = tramo.getTramoSiguiente();
		}
		avenida += "\n";
		tramo = inicioAv;
		while(tramo != null){
			avenida += tramo.toString() + "   | ";
			tramo = tramo.getTramoSiguiente();
		}
		avenida += "\n";
		return avenida;
	}
	public List<Tramo> getTramos(){
		return this.listaTramos;
		
	}
}
