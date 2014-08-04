package core;
import java.util.List;

import problema.Avenida;


public class Evolucion extends Thread{
  
	public static final float APTITUD_MINIMA = (float) 0.9;
	public static final int PORC_MUTACION = 50;
	
  Poblacion poblacion;
  Avenida avenida;
  
  CriterioFin criterio;
  
  Seleccion seleccion;
  Reproduccion reproduccion;
  Mutacion mutacion;

  public Evolucion(List<Individuo> individuos, Avenida avenida){
    this.avenida = avenida;
    poblacion = new Poblacion(individuos);
   	criterio = new CriterioFinMinAptitud(poblacion, APTITUD_MINIMA);
    
    seleccion = new Seleccion(poblacion);
    reproduccion = new Reproduccion(poblacion);
    mutacion = new Mutacion(poblacion, PORC_MUTACION);
  }
  
  @Override
  public void run(){
    
	  //Espero medio segundo, a que comience la simulacion del modelo.
	  try {
		sleep(500);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
	while(true){
		  
	    while(!criterio.terminado()){
	        
	        poblacion.pasarDeGeneracion();

	        seleccion.seleccionar();

	        reproduccion.multipunto();

	        mutacion.mutar();

//	        poblacion.mostrarDatos();
	        
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    avenida.aplicarResultado(poblacion.getMejorIndividuo());
	    
	    //Recalculo un nuevo semaforo cada 10 segundos
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
  }
  
}

