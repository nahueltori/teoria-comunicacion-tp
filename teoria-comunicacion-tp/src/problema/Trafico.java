package problema;

import java.util.List;

public class Trafico {

      /**
       * Largo del tramo representado.
       */ 
      private int longitud;
      
      /**
       * Porcentaje indicador del estado del tráfico en el momento en el estado actual.
       */ 
      private float estadoTrafico;
      
      /**
       * Lista que contiene los autos que estan transitando ese momento por el tramo representado.
       */ 
      List<Auto> autosTrafico;
      
      /**
       * Conexion al tramo siguiente de tráfico, para enviarle los autos que llegaron al fin de 
       * este tramo.
       */ 
      Trafico traficoSiguiente;
      
      public Trafico(int longitud){
            this.longitud = longitud;
            this.porcEstado = 0;
      }

      /**
       * TODO: Método que realiza un ciclo en el trafico. Esto significa que avanza los autos el tiempo dado.
       * Cada auto avanza su posicion en metros según la velocidad que lleva y el tiempo transcurrido.
       * Si algún auto llega hasta el final del tramo, se envía al tramo siguiente. Si no hay tramo 
       * siguiente, se elimina.
       * También setea a cada auto la velocidad aconsejada por la Onda Verde.
       * A la vez, recolecta datos para guardar la estadística del estado de trafico.
       * El parametro tiempo es la cantidad de segundos transcurridos en el ciclo.
       * El parametro velocidad, es la sugerida por la Onda Verde para que transiten los autos.
       */
      public void cicloTrafico(int tiempo, int velocidad){
            
      }

      /**
       * TODO: Método que devuelve un porcentaje de cantidad de tráfico en el tramo representado.
       */
      public float estadoTrafico(){
            
      }
      
      /**
       * Método que agrega un auto al tramo de tráfico actual.
       */
      public void agregarTrafico(Auto auto){
            autosTransito.add(auto);
      }

      /**
       * Método que relaciona el tramo de tráfico actual con el siguiente.
       */
      public void relacionarTrafico(Trafico trafico){
            traficoSiguiente = trafico;
      }

}
