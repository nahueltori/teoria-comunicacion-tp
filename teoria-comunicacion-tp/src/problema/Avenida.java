package problema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Avenida {

	private int multiplicador;
	
	private List<Semaforo> listaSemaforos;
	
	private List<Tramo> listaTramos;
	
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
			int i = 0;
			while(parametros.hasNext()){
				int longitud = parametros.nextInt();
				
				//Creo los semáforos para cada tramo de tráfico
				Semaforo semaforo = new Semaforo(i);
				listaSemaforos.add(semaforo);
				
				//Creo los tramos de Tráfico y los relaciono entre sí
				Tramo tramo = new Tramo(longitud, semaforo);
				listaTramos.add(tramo);
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
			/* TODO: linkear con la velocidad de la Onda Verde. */
			tramo.setVelOndaVerde(20);
			tramo.cicloTrafico(tiempo);
		}
	}
	
	/**
	 * Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
	 * TODO: Pasar Hardcode a parametros.
	 */
	private void crearTraficoAleatoriamente(int tiempo){
		Random rand = new Random();
		int varAleatCantidad = 0;
		hora += tiempo;
		final int MANANA = 120;
		final int TARDE = 240;
		final int NOCHE = 360;
		if(hora < MANANA){
			varAleatCantidad = rand.nextInt(5); 
		}
		else{
			if(hora < TARDE){
				varAleatCantidad = rand.nextInt(1);
			}
			else{
				if(hora < NOCHE){
					varAleatCantidad = rand.nextInt(6);
				}
			}
		}
		for(int i=0; i<varAleatCantidad; i++){
			inicioAv.enviarTrafico(new Auto());
		}
	}
	
	@Override
	public String toString(){
		String avenida = "";
		Tramo tramo = inicioAv;
		int i = 1;
		while(tramo != null){
			if(tramo.estadoTrafico() > 0){
				avenida += "Tramo " + i + ":\n" + tramo.toString();
			}
			i++;
			tramo = tramo.getTramoSiguiente();
		}
		return avenida;
	}
	
}
