package core;


public class CriterioFinMinAptitud extends CriterioFin{

  /** Atributo de aptitud m�nima que se controlar�. */
  private float aptitudMin;


  /** Constructor que recibe la poblaci�n y la aptitud minima a controlar. */
  public CriterioFinMinAptitud(Poblacion poblacion, float aptitud){
	  super(poblacion);
	  this.aptitudMin = aptitud;
  }

  /** M�todo espec�ficamente definido. */
  public boolean terminado(){
    return ((this.aptitudMin <= this.poblacion.getAptitudPob()) &&
    		(this.poblacion.getAptitudPob() > 0));
  }
  
  
}

