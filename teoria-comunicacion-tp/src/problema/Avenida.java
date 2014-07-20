package problema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Avenida {

	private List<Semaforo> listaSemaforos;
	
	private List<Trafico> listaTraficos;


	public Avenida(){
		Scanner parametros;
		try {
			parametros = new Scanner(new BufferedReader(new FileReader("data/config.txt")));
			Trafico tramoAnt = null;
			while(parametros.hasNext()){
				int longitud = parametros.nextInt();
				
				//Creo los tramos de Tr�fico y los relaciono entre s�
				Trafico tramo = new Trafico(longitud);
				listaTraficos.add(tramo);
				if(tramoAnt != null){
					tramoAnt.relacionarTrafico(tramo);
				}
				tramoAnt = tramo;
				
				//Creo los sem�foros para cada tramo de tr�fico
				listaSemaforos.add(new Semaforo(tramo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO: Metodo que se encarga de generar aleatoriamente trafico, dependiendo del horario del dia.
	 */
	private void crearTraficoAleatoriamente(Date hora){
		
	}
}
