package problema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Avenida {

	private static int MANANA = 120;
	private static int TARDE = 240;
	private static int NOCHE = 360;
	
	private int multiplicador;
	
	private List<Semaforo> listaSemaforos;
	
	public List<Tramo> listaTramos;
	
	private Tramo inicioAv;
	
	private int hora;

	public Avenida(){
		Scanner parametros;
		listaSemaforos = new ArrayList<Semaforo>();
		listaTramos = new ArrayList<Tramo>();
		try {
			parametros = new Scanner(new BufferedReader(new FileReader("data/config.txt")));
			multiplicador = parametros.nextInt();
			Tramo tramoAnt = null;
			while(parametros.hasNext()){
				int longitud = parametros.nextInt();
				
				//Creo los tramos de Trafico y los relaciono entre si
				Tramo tramo = new Tramo(longitud);
				listaTramos.add(tramo);

				//Creo los semaforos para cada tramo de trafico
				Semaforo semaforo = new Semaforo(this, tramo);
				//Asigno una velocidad inicial para la onda verde
				semaforo.setVelOndaVerde(20);
				listaSemaforos.add(semaforo);
				tramo.setSemaforo(semaforo);
				
				if(tramoAnt != null){
					tramoAnt.relacionarTrafico(tramo);
				}else{
					inicioAv = tramo;
				}
				tramoAnt = tramo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se encontro el archivo de configuracion.");
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
		tiempo *= multiplicador;
		crearTraficoAleatoriamente(tiempo);
		for(Tramo tramo : listaTramos){
			tramo.cicloTrafico(tiempo);
		}
	}
	
	/**
	 * Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
	 */
	private void crearTraficoAleatoriamente(int tiempo){
		Random rand = new Random();
		int varAleatCantidad = 0;
		hora += tiempo;
		if(hora < MANANA){
			varAleatCantidad = rand.nextInt(6); 
		}
		else{
			if(hora < TARDE){
				varAleatCantidad = rand.nextInt(2);
			}
			else{
				if(hora < NOCHE){
					varAleatCantidad = rand.nextInt(4);
				}
			}
		}
		for(int i=0; i<varAleatCantidad; i++){
			if(inicioAv.estadoTrafico() < Tramo.PORC_TOTAL){
				inicioAv.recibirTrafico(new Auto());
			}
		}
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
