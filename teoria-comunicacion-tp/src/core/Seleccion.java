package core;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

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
	  if(poblacion.getTamanioPob() > 0){	
		  indivSeleccionados = new ArrayList<Individuo>();
		  torneo();
	      ruleta();
	      grabarSeleccion();
	  }
  }
  
  /**
   * M�todo que selecciona un individuo entre dos, comparando sus aptitudes y eligiendo
   * el mejor.
   */
  private void torneo(){
	  List<Individuo> individuos = poblacion.getListaIndividuos();
	  float mejorAptitud = 0;
	  
	  if(!individuos.isEmpty()){
		  Individuo mejor = individuos.get(1);
		  Iterator<Individuo> it = individuos.iterator();
		    while(it.hasNext()){ 
			  if (mejorAptitud < mejor.getAptitud()) {
				  mejor = (Individuo)it.next();
			  }
		    }
		    
		    this.indivSeleccionados.add(mejor);
	  }
  }


  /**
   * https://www.google.com.ar/search?q=algoritmo+genetico+seleccion+ruleta
   * 
   * A cada uno de los individuos de la población se le asigna una parte proporcional a su ajuste de una ruleta, 
   * de tal forma que la suma de todos los porcentajes sea la unidad
   * Para seleccionar un individuo basta con generar un número aleatorio del intervalo [0..1]
   * Devolver el individuo situado en esa posición de la ruleta.
   * Esta posición se suele obtener recorriendo los individuos de la población
   * acumulando sus proporciones de ruleta hasta que la suma exceda el valor obtenido.
   * 
   */
  private void ruleta(){
	poblacion.recalcularAptitud();
	
	TreeMap<Float,Individuo> individuos = getListaIndividuosEnRuleta();
	
	//Genero una posición aleatoria para seleccionar al individuo.
	Random random = new Random();
	int posicionBuscada = random.nextInt();
	double aptitudAcumulada = 1; /* La mayor aptitud es 1 */
	
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

  private void grabarSeleccion(){
	  List<Individuo> individuos = poblacion.getListaIndividuos();
	  individuos.clear();
	  individuos.addAll(indivSeleccionados);
  }
  
  /** 
   * Método auxiliar para generar los individuos asociados a la ruleta representada
   * por un mapa cuya clave es la proporción de la aptitud del individuo respecto
   * de la aptitud total de la población a la que pertenece.
   */
  private TreeMap<Float,Individuo> getListaIndividuosEnRuleta(){
	TreeMap<Float,Individuo> ruleta = new TreeMap<Float,Individuo>();	
	List<Individuo> individuos = poblacion.getListaIndividuos();
	
	//Creo el mapa cuya clave es la proporcion de aptitud respecto del total de la población.
	Iterator<Individuo> it = individuos.iterator();
	while(it.hasNext()){
		Individuo enRuleta = (Individuo)it.next();
		ruleta.put(new Float(enRuleta.getAptitud() / individuos.size()), enRuleta);
	}
	
	return ruleta;
  }
  
}







