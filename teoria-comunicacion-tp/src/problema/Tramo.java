package problema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tramo {

      /**
       * Largo del tramo representado.
       */ 
      private int longitud;
      
      /**
       * Porcentaje indicador del estado del trafico en el momento en el estado actual.
       */ 
      private float estadoTrafico;
      
      /**
       * Lista que contiene los autos que estan transitando ese momento por el tramo representado.
       */ 
      private List<Auto> autosTrafico;
      
      /**
       * Conexion al tramo siguiente de trafico, para enviarle los autos que llegaron al fin de 
       * este tramo.
       */ 
      private Tramo tramoSiguiente;
      
      /**
       * Semaforo que esta al final del tramo.
       */
      private Semaforo semaforo;
      
      public Tramo(int longitud, Semaforo semaforo){
    	  autosTrafico = new ArrayList<Auto>();
          this.longitud = longitud;
          this.estadoTrafico = 0;
          this.semaforo = semaforo;
      }

      /**
       * Metodo que realiza un ciclo en el semaforo y en el tramo. Esto significa que avanza los autos el tiempo dado.
       * Cada auto avanza su posicion en metros segun la velocidad que lleva y el tiempo transcurrido.
       * Si algun auto llega hasta el final del tramo, se envia al tramo siguiente. Si no hay tramo 
       * siguiente, se elimina.
       * Tambien setea a cada auto la velocidad aconsejada por la Onda Verde.
       * @param tiempo El parametro tiempo es la cantidad de segundos transcurridos en el ciclo.
       * @param velocidadEl parametro velocidad está en KM/H, y es la sugerida por la Onda Verde para que transiten los autos.
       */
      public void cicloTrafico(int tiempo, int velocidad){
    	  semaforo.cicloSemaforo(tiempo);
    	  
    	  for(Iterator<Auto> i = autosTrafico.iterator(); i.hasNext(); ){
    		  Auto auto = i.next();
    		  
        	  /* Evaluo si el auto llegó al final del tramo, si el semaforo permite pasar,
        	   * lo paso al siguiente tramo. */
    		  if(auto.getPosicion() >= longitud){
    			  if(semaforo.puedoPasar()){
    				  i.remove();
    				  tramoSiguiente.enviarTrafico(auto);
    			  }
    			  /* Si el semaforo detiene el trafico. */
    			  else{
    				  velocidad = 0;
    			  }
			  }
			  /* Si aun no llegue al fin de tramo, avanzo los autos 
			   * y seteo su velocidad recomendada. */
			  else{
	    		  auto.setVelocidad(velocidad);
        		  auto.avanzar(tiempo);
    		  }
    	  }
      }

      /**
       * TODO: Metodo que devuelve un porcentaje de cantidad de trafico en el tramo representado.
       */
      public float estadoTrafico(){
            return estadoTrafico;
      }
      
      /**
       * Metodo que agrega un auto al tramo de trafico actual.
       */
      public void enviarTrafico(Auto auto){
    	  auto.reiniciar();
    	  autosTrafico.add(auto);
      }

      /**
       * Metodo que relaciona el tramo de trafico actual con el siguiente.
       */
      public void relacionarTrafico(Tramo trafico){
            tramoSiguiente = trafico;
      }

  	@Override
  	public String toString(){
  		String tramo = "";
/*  		for(Tramo tramo : listaTramos){
  			avenida += tramo.toString();
  		}
*/  		return tramo;
  	}
      
}
