package core;


public abstract class CriterioFin{
  
  /** Referencia a la Poblaci�n sobre la que se aplicar�
      el criterio de finalizaci�n. */
  protected Poblacion poblacion;


  /** Constructor de la clase. */
  public CriterioFin(Poblacion poblacion){
    this.poblacion = poblacion;
  }
  
  /** M�todo que determina cu�ndo el proceso est� terminado.
      �ste debe implementarlo cada subclase seg�n el criterio. */

  public abstract boolean terminado();

}

