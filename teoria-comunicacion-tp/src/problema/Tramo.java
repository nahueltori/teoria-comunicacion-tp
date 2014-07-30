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
       * Maximo porcentaje posible.
       */
      public static final int PORC_TOTAL = 100;
      
      /**
       * Cantidad maxima de autos cada 100 metros.
       */
      private static final int MAX_DENS_AUTOS = 10;
      
      /**
       * Velocidad recomendada por la Onda Verde.
       */
      private int velocidadOndaVerde;
      
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
          this.semaforo = semaforo;
          this.velocidadOndaVerde = 20;
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
      public void cicloTrafico(int tiempo){
    	  semaforo.cicloSemaforo(tiempo);
    	  
    	  for(Iterator<Auto> i = autosTrafico.iterator(); i.hasNext(); ){
    		  Auto auto = i.next();
    		  
        	  /* Evaluo si el auto llegó al final del tramo, si el semaforo permite pasar y 
        	   * hay lugar en el siguiente tramo, lo paso al siguiente. */
    		  if(auto.getPosicion() >= longitud){
    			  if(semaforo.puedoPasar()){
    				  enviarTrafico(auto, i);
    			  }
    			  /* Si el semaforo detiene el trafico. */
    			  else{
    				  auto.setVelocidad(0);
    			  }
			  }
			  /* Si aun no llegue al fin de tramo, avanzo los autos. */ 
			  else{
        		  auto.avanzar(tiempo);
    		  }
    	  }
      }

      /**
       * Metodo que devuelve un estado del trafico en el tramo representado.
       * Unidades: Porcentaje de ocupacion del tramo.
       */
      public int estadoTrafico(){
    	  int densidadActual = autosTrafico.size() * 100 / longitud;
    	  return densidadActual * 100 / MAX_DENS_AUTOS;
      }
      
      /**
       * Metodo que agrega un auto al tramo de trafico actual.
       * Reinicia sus datos de posición, y le setea la velocidad recomendada por la Onda Verde.
       */
      public void recibirTrafico(Auto auto){
    	  auto.reiniciar(longitud);
    	  auto.setVelocidad(velocidadOndaVerde);
    	  autosTrafico.add(auto);
      }

      /**
       * Metodo que envia un auto al tramo de trafico siguiente.
       * Recibe el Auto a enviar, y el iterador necesario para borrarlo de la lista del tramo actual.
       */
      private void enviarTrafico(Auto auto, Iterator<Auto> i){
    	  if(tramoSiguiente != null){
    		  if(tramoSiguiente.estadoTrafico() < PORC_TOTAL){
				  i.remove();
				  tramoSiguiente.recibirTrafico(auto);
    		  }
    	  }
    	  // Si no hay un tramo siguiente, se llego al final de la avenida, por lo que se elimina el auto. 
    	  else{
    		  i.remove();
    	  }
    	  
      }

      /**
       * Setea la velocidad recomendada por la onda verde, para
       * que sea utilizada por los autos.
       * @param velocidad
       */
      public void setVelOndaVerde(int velocidad){
    	  velocidadOndaVerde = velocidad;
      }
      
      /**
       * Metodo que relaciona el tramo de trafico actual con el siguiente.
       */
      public void relacionarTrafico(Tramo trafico){
            tramoSiguiente = trafico;
      }
      
      /**
       * Devuelve el tramo que sigue al actual.
       */
      public Tramo getTramoSiguiente(){
    	  return tramoSiguiente;
      }

      @Override
      public String toString(){
    	  String tramo = "";
//    	  tramo += "Longitud: " + longitud + "\n";
//    	  tramo += "Autos: " + autosTrafico.size() + "\n";
//    	  tramo += "|" + new String(new char[(longitud/2)-2]) + "|" + "\n";
//    	  for(Auto auto : autosTrafico){
//    		  int pos = auto.getPosicion()/2;
//    		  String strAuto = new String(new char[pos]);
//    		  tramo += strAuto + "A\n";
//    	  }
    	  tramo += "Estado: " + estadoTrafico();
//    	  tramo += "Semaforo: " + semaforo.toString() + "\n";
    	  return tramo;
      }

      public List<Auto> getAutos(){
    	  return this.autosTrafico;
      }
      public int getLongitud(){
    	  return this.longitud;
      }
      public Semaforo getSemaforo(){
    	  return this.semaforo;
      }
}
