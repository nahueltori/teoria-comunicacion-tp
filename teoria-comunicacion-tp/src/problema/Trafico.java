package problema;

import java.util.List;

public class Trafico {

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
      List<Auto> autosTrafico;
      
      /**
       * Conexion al tramo siguiente de trafico, para enviarle los autos que llegaron al fin de 
       * este tramo.
       */ 
      Trafico traficoSiguiente;
      
      public Trafico(int longitud){
            this.longitud = longitud;
            this.estadoTrafico = 0;
      }

      /**
       * TODO: Metodo que realiza un ciclo en el trafico. Esto significa que avanza los autos el tiempo dado.
       * Cada auto avanza su posicion en metros segun la velocidad que lleva y el tiempo transcurrido.
       * Si algun auto llega hasta el final del tramo, se envia al tramo siguiente. Si no hay tramo 
       * siguiente, se elimina.
       * Tambien setea a cada auto la velocidad aconsejada por la Onda Verde.
       * A la vez, recolecta datos para guardar la estadistica del estado de trafico.
       * El parametro tiempo es la cantidad de segundos transcurridos en el ciclo.
       * El parametro velocidadestá en KM/H, y es la sugerida por la Onda Verde para que transiten los autos.
       */
      public void cicloTrafico(int tiempo, int velocidad){
            
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
      public void agregarTrafico(Auto auto){
    	  autosTrafico.add(auto);
      }

      /**
       * Metodo que relaciona el tramo de trafico actual con el siguiente.
       */
      public void relacionarTrafico(Trafico trafico){
            traficoSiguiente = trafico;
      }

}
