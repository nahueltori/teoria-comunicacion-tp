package core;


public abstract class Individuo implements Comparable<Individuo>{

  protected double aptitud;

  public double getAptitud(){
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
  public abstract void mutar(double aptitud);

	/** 
	 * M�todo para reproducir un nuevo individuo, cruz�ndolo con otro. */
  public abstract Individuo reproducir(Individuo ind);

  
  	@Override
	public int compareTo(Individuo o) {
		return Double.compare(this.aptitud, o.getAptitud());
	}
}
