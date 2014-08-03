package core;
import java.util.List;

import problema.Avenida;


public class Evolucion extends Thread{
  
	public static final int APTITUD_MINIMA = 90;
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
   	criterio = new CriterioFinMinAptitud(poblacion, PORC_MUTACION);
    
    seleccion = new Seleccion(poblacion);
    reproduccion = new Reproduccion(poblacion);
    mutacion = new Mutacion(poblacion, PORC_MUTACION);
  }
  
  @Override
  public void run(){
    
	while(true){
	    while(!criterio.terminado()){
	        
	        seleccion.seleccionar();

	        reproduccion.multipunto();

	        mutacion.mutar();

	        poblacion.pasarDeGeneracion();

	        poblacion.mostrarDatos();
	        
	    }
	    
	}
    
    
  }
  
}

