package core;
import java.util.Iterator;
import java.util.List;


public abstract class Individuo{

  protected List<Float> genes;

  protected float aptitud;


  public Individuo(){
    evaluarAptitud();
  }

  public List<Float> getListaGenes(){
    return genes;
  }

  public float getAptitud(){
    return aptitud;
  }

  public String toString(){
	  String salida = "Individuo - Aptitud: ";
	  Float gen;
	  salida += (new Float(aptitud)).toString();
	  salida += " | Genes: ";
	  Iterator<Float> it = genes.iterator();
	  while(it.hasNext()){
		  gen = (Float)it.next();
		  salida = salida + gen.toString() + " ";
	  }
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
