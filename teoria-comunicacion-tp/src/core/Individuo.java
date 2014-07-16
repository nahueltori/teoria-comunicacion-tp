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
	 * M�todo que evalua la aptitud del individuo.
	 * Cada implementacion debe implementarlo. */
  public abstract void evaluarAptitud();

	/** 
	 * M�todo para mutar al individuo. */
  public abstract void mutar();

	/** 
	 * M�todo para reproducir un nuevo individuo, cruz�ndolo con otro. */
  public abstract Individuo reproducir(Individuo ind);


}
