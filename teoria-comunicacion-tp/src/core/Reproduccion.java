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
   * Reproduzco individuos hasta volver a alcanzar el total deseado de poblaci�n.
   * 
   */
  public void multipunto(){
    Individuo ind1;
    Individuo ind2;
    List<Individuo> individuosNuevos = new ArrayList<Individuo>();
    Random random = new Random();

    /** Individuos que forman parte de la poblacion. */
    List<Individuo> individuos = poblacion.getListaIndividuos();

    /** Mientras el tama�o actual de la poblaci�n sea menor al tama�o que debe tener... */
    while((individuos.size() + individuosNuevos.size()) < poblacion.getTamanioPob()){
      /** Obtengo al azar dos individuos de la poblaci�n. */
      ind1 = (Individuo)individuos.get(random.nextInt(individuos.size() - 1));
      ind2 = (Individuo)individuos.get(random.nextInt(individuos.size() - 1));

      /** A partir de los dos individuos, genero uno nuevo. */
      individuosNuevos.add(ind1.reproducir(ind2));
    }
    /** Si reproduje exitosamente individuos, los agrego a la poblaci�n. */
    if(!individuosNuevos.isEmpty()){
      individuos.addAll(individuosNuevos);
    }
  }

}



