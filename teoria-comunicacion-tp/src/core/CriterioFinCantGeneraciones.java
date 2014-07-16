package core;


public class CriterioFinCantGeneraciones extends CriterioFin{

  /** Atributo de cantidad de generaciones que se controlar�. */
  private int cantidad;
  

  /** Constructor que recibe la poblaci�n y la cantidad de generaciones
      que debe esperar para terminar. */
  public CriterioFinCantGeneraciones(Poblacion poblacion, int cantidad){
	  super(poblacion);
	  this.cantidad = cantidad;
  }

  /** M�todo espec�ficamente definido. */
  public boolean terminado(){
    return ((this.cantidad <= this.poblacion.getNroGeneracion()) &&
    		(this.poblacion.getNroGeneracion() > 0));
  }
  
  
}