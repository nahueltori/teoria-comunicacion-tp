package problema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Avenida {

	private static int MANANA = 500;
	private static int TARDE = 1000;
	private static int NOCHE = 1500;
	
	static private int UN_MILISEG = 1;
	static private int MIL_MILISEG = 1000;
	
	private List<Semaforo> listaSemaforos;
	
	public List<Tramo> listaTramos;
	
	private Tramo inicioAv;
	private Trafico trafico;
	
	private int contAutos;
	private int contSegundos;
	private float indTrafico;
	private int hora;
	private int id = 0;
	
	private class Trafico extends Thread{
			
		private int hora;
		private int tiempo;
		private boolean ejecutar;
		
		Trafico(){
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
			while(ejecutar){
				if(tiempo > 0){
					int varAleatCantidad = 0;
					hora += tiempo;
					if(hora < MANANA){
						varAleatCantidad = rand.nextInt(4); 
					}
					else{
						if(hora < TARDE){
							varAleatCantidad = rand.nextInt(1);
						}
						else{
							if(hora < NOCHE){
								varAleatCantidad = rand.nextInt(4);
							}else{
									varAleatCantidad = rand.nextInt(2);
								}
						}
					}
					for(int i=0; i<varAleatCantidad; i++){
						recibirAuto();
						try {
							sleep(MIL_MILISEG / varAleatCantidad);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					sleep(UN_MILISEG);
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
		trafico.start();
		listaSemaforos = new ArrayList<Semaforo>();
		listaTramos = new ArrayList<Tramo>();
		try {
			parametros = new Scanner(new BufferedReader(new FileReader("data/tramos.txt")));
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
	 * Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
	 */
	private void recibirAuto(){
		Random idAuto = new Random();
		if(inicioAv.estadoTrafico() < Tramo.PORC_TOTAL){
			inicioAv.recibirTrafico(new Auto(idAuto.nextInt()));
			contAutos++;
		}
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
