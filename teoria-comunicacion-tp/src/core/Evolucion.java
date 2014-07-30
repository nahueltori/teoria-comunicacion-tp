package core;
import java.util.Iterator;
import java.util.List;


public class Evolucion{
  
	public static final int CANT_GENER = 1;
	
	public static final int MIN_APTITUD = 2;
	
	public static final int TASA_MIN = 3;
	
  Poblacion poblacion;
  
  CriterioFin criterio;
  
  Seleccion seleccion;
  Reproduccion reproduccion;
  Mutacion mutacion;

  public Evolucion(List<Individuo> individuos, float porcSeleccion,
                   float porcMutacion, int condFin, float finDato){
    poblacion = new Poblacion(individuos);
    poblacion.mostrarDatos();
    switch(condFin){
    case CANT_GENER:
    	criterio = new CriterioFinCantGeneraciones(poblacion,(int)finDato);
    	break;
    case MIN_APTITUD:
    	criterio = new CriterioFinMinAptitud(poblacion,finDato);
    	break;
    case TASA_MIN:
    	criterio = new CriterioFinTasaMinima(poblacion,finDato);
    	break;
    }
    
    seleccion = new Seleccion(poblacion, porcSeleccion);
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

