package core;
import java.util.ArrayList;
import java.util.List;


public class Seleccion{
  
  Poblacion poblacion;
  float porcentaje;
  int cantASeleccionar;
  List<Individuo> indivSeleccionados;

  public Seleccion(Poblacion pob, float porc){
	poblacion = pob;
    porcentaje = porc;
    int cantIndividuos = poblacion.getListaIndividuos().size();
    cantASeleccionar = Math.round(cantIndividuos * porcentaje / 100);
    /** Divido por 2, ya que aplicar� dos m�todos de selecci�n, por lo que cada
        uno seleccionar� la mitad de lo necesario. */
    cantASeleccionar = cantASeleccionar / 2;
  }

  /**
   * Se aplican diversos criterios de selecci�n a la poblaci�n, dejando los elementos seleccionados 
   * en una lista interna (indivSeleccionados), para luego reemplazarla por la poblaci�n.
   */
  public void seleccionar(){
	  
	  indivSeleccionados = new ArrayList<Individuo>();
	  torneo();
      ruleta();
      grabarSeleccion();

  }
  
  /**
   * M�todo que selecciona un individuo entre dos, comparando sus aptitudes y eligiendo
   * el mejor.
   * TODO: Programar el m�todo teniendo en cuenta de utilizar solamente la interfaz Individuo.
   */
  private void torneo(){

  }


  /**
   * https://www.google.com.ar/search?q=algoritmo+genetico+seleccion+ruleta
   * TODO: Programar el m�todo teniendo en cuenta de utilizar solamente la interfaz Individuo.
   */
  private void ruleta(){
	poblacion.recalcularAptitud();

  }

  private void grabarSeleccion(){
	  List<Individuo> individuos = poblacion.getListaIndividuos();
	  individuos.clear();
	  individuos.addAll(indivSeleccionados);
  }
  
}







