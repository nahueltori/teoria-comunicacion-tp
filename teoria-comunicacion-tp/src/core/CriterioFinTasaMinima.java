package core;


public class CriterioFinTasaMinima extends CriterioFin{

  /** Atributo de tasa mínima de crecimiento que se controlará. */
  private float tasaMin;


  /** Constructor que recibe la población y la tasa mínima a controlar. */
  public CriterioFinTasaMinima(Poblacion poblacion, float tasa){
	  super(poblacion);
	  this.tasaMin = tasa;
  }

  /** Método específicamente definido. */
  public boolean terminado(){
    return ((this.tasaMin >= this.poblacion.getTasaCrecimiento()) &&
    		(this.poblacion.getTasaCrecimiento() > 0));
  }
  
  
}

