package core;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Seleccion{
  
  Poblacion poblacion;
  int cantASeleccionar;
  List<Individuo> indivSeleccionados;

  public Seleccion(Poblacion pob){
	poblacion = pob;
    /* Divido por 2, ya que aplico dos metodos de seleccion, por lo que cada
    uno selecciona la mitad de lo necesario. */
    cantASeleccionar = poblacion.getListaIndividuos().size() / 2;
  }

  /**
   * Se aplican diversos criterios de selecci�n a la poblaci�n, dejando los elementos seleccionados 
   * en una lista interna (indivSeleccionados), para luego reemplazarla por la poblaci�n.
   */
  public void seleccionar(){
	  if(poblacion.getTamanioPob() > 0){	
		  indivSeleccionados = new ArrayList<Individuo>();
		  torneo();
//Lo comento ya que me deja una posicion nula en la lista de individuos		  
//	      ruleta();
	      grabarSeleccion();
	  }
  }
  
  /**
   * Metodo que selecciona un individuo entre dos, comparando sus aptitudes y eligiendo
   * el mejor.
   */
  private void torneo(){
	  List<Individuo> individuos = poblacion.getListaIndividuos();
	  Individuo mejor;
	  Individuo anterior = null;
	  for(Individuo ind : individuos){
		  if(anterior == null){
			  anterior = ind;
		  }
		  else{
			  if(ind.getAptitud() > anterior.getAptitud()){
				  mejor = ind;
			  }
			  else{
				  mejor = anterior;
			  }
			  indivSeleccionados.add(mejor);
			  anterior = null;
		  }
	  }
  }

  /**
   * A cada uno de los individuos de la poblacion se le asigna una parte proporcional a su ajuste de una ruleta, 
   * de tal forma que la suma de todos los porcentajes sea la unidad
   * Para seleccionar un individuo basta con generar un numero aleatorio del intervalo [0..1]
   * Devolver el individuo situado en esa posicion de la ruleta.
   * Esta posicion se suele obtener recorriendo los individuos de la poblacion
   * acumulando sus proporciones de ruleta hasta que la suma exceda el valor obtenido.
   * 
   */
  private void ruleta(){
	TreeMap<Float,Individuo> individuos = getListaIndividuosEnRuleta();
	
	//Genero una posición aleatoria para seleccionar al individuo.
	Random random = new Random();
	float posicionBuscada = random.nextFloat();
	float aptitudAcumulada = 0; /* La mayor aptitud es 1 */
	
	//Itero hasta obtener la aptitud acumulada y el individuo correspondiente.
	Individuo mejor = null;	
	for(Map.Entry<Float,Individuo> entry : individuos.entrySet()) {
		if(posicionBuscada < aptitudAcumulada){ 
			aptitudAcumulada += entry.getKey();
			mejor = entry.getValue();
		}
	}
	
	 this.indivSeleccionados.add(mejor);
  }

  /** 
   * Metodo auxiliar para generar los individuos asociados a la ruleta representada
   * por un mapa cuya clave es la proporcion de la aptitud del individuo respecto
   * de la aptitud total de la poblacion a la que pertenece.
   */
  private TreeMap<Float,Individuo> getListaIndividuosEnRuleta(){
	TreeMap<Float,Individuo> ruleta = new TreeMap<Float,Individuo>();	
	List<Individuo> individuos = poblacion.getListaIndividuos();
	
	//Creo el mapa cuya clave es la proporcion de aptitud respecto del total de la poblacion.
	Iterator<Individuo> it = individuos.iterator();
	while(it.hasNext()){
		Individuo enRuleta = (Individuo)it.next();
		ruleta.put(new Float(enRuleta.getAptitud() / (float)individuos.size()), enRuleta);
	}
	
	return ruleta;
  }
  
  private void grabarSeleccion(){
	  poblacion.actualizarPoblacion(indivSeleccionados);
  }
  
}







