package core;


public abstract class CriterioFin{
  
  /** Referencia a la Población sobre la que se aplicará
      el criterio de finalización. */
  protected Poblacion poblacion;


  /** Constructor de la clase. */
  public CriterioFin(Poblacion poblacion){
    this.poblacion = poblacion;
  }
  
  /** Método que determina cuándo el proceso está terminado.
      Éste debe implementarlo cada subclase según el criterio. */

  public abstract boolean terminado();

}

