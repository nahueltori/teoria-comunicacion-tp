package core;
import java.util.Iterator;
import java.util.List;


public class Evolucion extends Thread{
  
	public static final int APTITUD_MINIMA = 90;
	
  Poblacion poblacion;
  
  CriterioFin criterio;
  
  Seleccion seleccion;
  Reproduccion reproduccion;
  Mutacion mutacion;

  public Evolucion(List<Individuo> individuos, float porcMutacion){
    poblacion = new Poblacion(individuos);
//    poblacion.mostrarDatos();
   	criterio = new CriterioFinMinAptitud(poblacion, APTITUD_MINIMA);
    
    seleccion = new Seleccion(poblacion);
    reproduccion = new Reproduccion(poblacion);
    mutacion = new Mutacion(poblacion, porcMutacion);
  }
  
  public void evolucionar(){
    
    while(!criterio.terminado()){
      
      seleccion.seleccionar();

      reproduccion.multipunto();

      mutacion.mutar();

      poblacion.pasarDeGeneracion();

      poblacion.mostrarDatos();
      
    }
    
  }
  
  public Individuo getMejorIndividuo(){
    Individuo indiv;
    Individuo mejorIndiv;
    List<Individuo> individuos = poblacion.getListaIndividuos();
    Iterator<Individuo> it = individuos.iterator();
    /** Tomo el primer individuo, para comenzar a comparar. */
    mejorIndiv = (Individuo)it.next();
    
    /** Itero la lista de individuos. */
    while(it.hasNext()){
      indiv = (Individuo)it.next();
      
      /** Comparo sus aptitudes, y voy guardando el individuo de mayor aptitud. */
      if(mejorIndiv.getAptitud() < indiv.getAptitud()){
        mejorIndiv = indiv;
      }
    }
    return mejorIndiv;
  }

}

