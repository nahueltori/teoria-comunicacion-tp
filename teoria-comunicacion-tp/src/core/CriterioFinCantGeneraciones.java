package core;


public class CriterioFinCantGeneraciones extends CriterioFin{

  /** Atributo de cantidad de generaciones que se controlará. */
  private int cantidad;
  

  /** Constructor que recibe la población y la cantidad de generaciones
      que debe esperar para terminar. */
  public CriterioFinCantGeneraciones(Poblacion poblacion, int cantidad){
	  super(poblacion);
	  this.cantidad = cantidad;
  }

  /** Método específicamente definido. */
  public boolean terminado(){
    return ((this.cantidad <= this.poblacion.getNroGeneracion()) &&
    		(this.poblacion.getNroGeneracion() > 0));
  }
  
  
}