package core;


public class CriterioFinTasaMinima extends CriterioFin{

  /** Atributo de tasa m�nima de crecimiento que se controlar�. */
  private float tasaMin;


  /** Constructor que recibe la poblaci�n y la tasa m�nima a controlar. */
  public CriterioFinTasaMinima(Poblacion poblacion, float tasa){
	  super(poblacion);
	  this.tasaMin = tasa;
  }

  /** M�todo espec�ficamente definido. */
  public boolean terminado(){
    return ((this.tasaMin >= this.poblacion.getTasaCrecimiento()) &&
    		(this.poblacion.getTasaCrecimiento() > 0));
  }
  
  
}

