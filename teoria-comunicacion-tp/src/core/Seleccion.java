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
    /** Divido por 2, ya que aplicaré dos métodos de selección, por lo que cada
        uno seleccionará la mitad de lo necesario. */
    cantASeleccionar = cantASeleccionar / 2;
  }

  /**
   * Se aplican diversos criterios de selección a la población, dejando los elementos seleccionados 
   * en una lista interna (indivSeleccionados), para luego reemplazarla por la población.
   */
  public void seleccionar(){
	  
	  indivSeleccionados = new ArrayList<Individuo>();
	  torneo();
      ruleta();
      grabarSeleccion();

  }
  
  /**
   * Método que selecciona un individuo entre dos, comparando sus aptitudes y eligiendo
   * el mejor.
   * TODO: Programar el método teniendo en cuenta de utilizar solamente la interfaz Individuo.
   */
  private void torneo(){

  }


  /**
   * https://www.google.com.ar/search?q=algoritmo+genetico+seleccion+ruleta
   * TODO: Programar el método teniendo en cuenta de utilizar solamente la interfaz Individuo.
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







