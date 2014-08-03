package problema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Tramo {
	
	private static int ANCHOAUTO     =   10;	
      /**
       * Largo del tramo representado.
       */ 
      private int longitud;
      
      /**
       * Largo total desde el inicio de la avenida.
       */
      private int longitudTotal;
      
      /**
       * Maximo porcentaje posible.
       */
      public static final int PORC_TOTAL = 100;
      
      /**
       * Cantidad maxima de autos cada 100 metros.
       */
      public static final int MAX_DENS_AUTOS = 10;
      
      /**
       * Lista que contiene los autos que estan transitando ese momento por el tramo representado.
       */ 
      private List<Auto> autosTrafico;
      
      /**
       * Conexion al tramo anterior de trafico.
       */ 
      private Tramo tramoAnterior;
      
      /**
       * Conexion al tramo siguiente de trafico, para enviarle los autos que llegaron al fin de 
       * este tramo.
       */ 
      private Tramo tramoSiguiente;
      
      /**
       * Semaforo que esta al final del tramo.
       */
      private Semaforo semaforo;
      
      public Tramo(int longitud){
    	  autosTrafico = new ArrayList<Auto>();
          this.longitud = longitud;
          this.longitudTotal = longitud;
      }

      public void setSemaforo(Semaforo sem){
    	  this.semaforo = sem;
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
      public synchronized void cicloTrafico(int tiempo){
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
					  auto.avanzar(tiempo,this);
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
    	  auto.setVelocidad(semaforo.getVelOndaVerde());
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
       * Metodo que relaciona el tramo de trafico actual con el siguiente.
       */
      public void relacionarTrafico(Tramo trafico){
            tramoSiguiente = trafico;
            trafico.tramoAnterior = this;
            actualizarLongitudTotal();
      }
      
      /**
       * Devuelve el tramo que sigue al actual.
       */
      public Tramo getTramoSiguiente(){
    	  return tramoSiguiente;
      }

      /**
       * Devuelve el tramo anterior al actual.
       */
      public Tramo getTramoAnterior(){
    	  return tramoAnterior;
      }

      private void actualizarLongitudTotal(){
    	  if(tramoAnterior != null)
    		  longitudTotal += getTramoAnterior().longitudTotal;
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
      public int getLongitudTotal(){
    	  return this.longitudTotal;
      }
      public Semaforo getSemaforo(){
    	  return this.semaforo;
      }
      
      public synchronized int verificarAvance(Auto autoActual, int distAvance){
    	   /**
           * Retorna el avance permitido verificando si tiene autos delante. 
           * Regresa la menor de las distancias
           * Resta ANCHOAUTO para tener en cuenta el tamanio del auto delantero
           */ 
       	  for(Iterator<Auto> i = autosTrafico.iterator(); i.hasNext(); ){
    		  Auto autoOtro = i.next();
    		  int distEntreAutos = autoOtro.getPosicion() - autoActual.getPosicion();
    		  if ( distEntreAutos >= 1 && distEntreAutos - ANCHOAUTO < distAvance ){
 //   			  System.out.print("No puedo avanzar. Desde la posicion "+autoActual.getPosicion()+ " quiero avanzar " + distAvance + " y choco con el auto en la posicion " + autoOtro.getPosicion() ); 
    			  distAvance = distEntreAutos - ANCHOAUTO;
 //   			  System.out.println("Nueva distancia avance: "+distAvance);
    		  }
       	  }
    	  return distAvance;
      }
}
