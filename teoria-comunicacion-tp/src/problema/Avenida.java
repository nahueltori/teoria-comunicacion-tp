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
	
	public List<Tramo> listaTramos;
	
	private Tramo inicioAv;

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
				
				//Creo los semáforos para cada tramo de tráfico
				Semaforo semaforo = new Semaforo();
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
		crearTraficoAleatoriamente();
		for(Tramo tramo : listaTramos){
			/* TODO: linkear con la velocidad de la Onda Verde. */
			tramo.setVelOndaVerde(20);
			tramo.cicloTrafico(tiempo);
		}
	}
	
	/**
	 * TODO: Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
	 */
	private void crearTraficoAleatoriamente(){
		Random rand = new Random();
		for(int i=0; i<rand.nextInt(5); i++){
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
	public List<Tramo> getTramos(){
		return this.listaTramos;
		
	}
}
