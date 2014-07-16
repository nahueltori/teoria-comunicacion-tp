package core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Reproduccion{

  Poblacion poblacion;

  public Reproduccion(Poblacion pob){
    poblacion = pob;
  }

  /**
   * Reproduzco individuos hasta volver a alcanzar el total deseado de población.
   * 
   */
  public void multipunto(){
    Individuo ind1;
    Individuo ind2;
    List<Individuo> individuosNuevos = new ArrayList<Individuo>();
    Random random = new Random();

    /** Individuos que forman parte de la poblacion. */
    List<Individuo> individuos = poblacion.getListaIndividuos();
    /** Tamaño de la poblacion antes de realizar la reproducción. */
    int totalPob = individuos.size();

    /** Mientras el tamaño actual de la población sea menor al tamaño que debe tener... */
    while(totalPob < poblacion.getTamanioPob()){
      /** Obtengo al azar dos individuos de la población. */
      ind1 = (Individuo)individuos.get(random.nextInt(individuos.size()));
      ind2 = (Individuo)individuos.get(random.nextInt(individuos.size()));

      /** A partir de los dos individuos, genero uno nuevo. */
      individuosNuevos.add(ind1.reproducir(ind2));
      totalPob++;
    }
    /** Si reproduje exitosamente individuos, los agrego a la población. */
    if(!individuosNuevos.isEmpty()){
      individuos.addAll(individuosNuevos);
    }
  }

}



