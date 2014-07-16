package core;


public class CriterioFinMinAptitud extends CriterioFin{

  /** Atributo de aptitud mínima que se controlará. */
  private float aptitudMin;


  /** Constructor que recibe la población y la aptitud minima a controlar. */
  public CriterioFinMinAptitud(Poblacion poblacion, float aptitud){
	  super(poblacion);
	  this.aptitudMin = aptitud;
  }

  /** Método específicamente definido. */
  public boolean terminado(){
    return ((this.aptitudMin <= this.poblacion.getAptitudPob()) &&
    		(this.poblacion.getAptitudPob() > 0));
  }
  
  
}

