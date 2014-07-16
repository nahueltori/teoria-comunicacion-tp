package core;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Poblacion{

  List<Individuo> individuos;
  
  int tamanioPob;
  
  int nroGeneracion;
  float tasaCrecimiento;
  float aptitudPob;
  float sumaAptitudes;

  public Poblacion(int tam){
    tamanioPob = 0;
    individuos = new ArrayList<Individuo>();
    nroGeneracion = 0;
    tasaCrecimiento = 0;
  }

  public void pasarDeGeneracion(){
    if(tamanioPob == individuos.size()){
      nroGeneracion++;
      float aptitudAnt = aptitudPob;
      recalcularAptitud();
      tasaCrecimiento = (aptitudPob - aptitudAnt) / aptitudAnt * 100;
    }
    else{
      System.out.print("La cantidad de individuos no es igual al tamaño de la poblacion");
    }
  }

  public void recalcularAptitud(){
    Individuo indiv;
    float suma = 0;
    Iterator<Individuo> it = individuos.iterator();
    while(it.hasNext()){
      indiv = (Individuo)it.next();
      suma += indiv.getAptitud();
    }
    sumaAptitudes = suma;
    aptitudPob = sumaAptitudes / individuos.size();
  }

  public void mostrarDatos(){
	  System.out.println("Número de generación: " + nroGeneracion);
	  System.out.println("Aptitud de la población: " + aptitudPob);
	  System.out.println("Tasa de crecimiento: " + tasaCrecimiento);
	  System.out.println("");
  }
  
  public List<Individuo> getListaIndividuos(){
	  return individuos;
  }
  
  public int getTamanioPob(){
	  return tamanioPob;
  }
  
  public float getNroGeneracion(){
	  return nroGeneracion;
  }
  
  public float getTasaCrecimiento(){
	  return tasaCrecimiento;
  }
  
  public float getAptitudPob(){
	  return aptitudPob;
  }
  
  public float getSumaAptitudes(){
	  return sumaAptitudes;
  }
  
}


