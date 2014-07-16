package core;


public abstract class Individuo{

  protected float aptitud;

  public Individuo(){
    evaluarAptitud();
  }

  public float getAptitud(){
    return aptitud;
  }

  public String toString(){
	  String salida = "Individuo - Aptitud: ";
	  salida += (new Float(aptitud)).toString();
	  return salida;
  }

	/** 
	 * Método que evalua la aptitud del individuo.
	 * Cada implementacion debe implementarlo. */
  public abstract void evaluarAptitud();

	/** 
	 * Método para mutar al individuo. */
  public abstract void mutar();

	/** 
	 * Método para reproducir un nuevo individuo, cruzándolo con otro. */
  public abstract Individuo reproducir(Individuo ind);


}
