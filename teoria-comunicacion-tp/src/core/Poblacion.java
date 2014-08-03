package core;
import java.util.List;


public class Poblacion{

  List<Individuo> individuos;
  
  Individuo mejor;
  
  int tamanioPob;
  
  int nroGeneracion;
  float tasaCrecimiento;
  float aptitudPob;
  float sumaAptitudes;

  public Poblacion(List<Individuo> individuos){
    this.individuos = individuos;
    tamanioPob = individuos.size();
    nroGeneracion = 0;
    tasaCrecimiento = 0;
    mejor = null;
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

  private void recalcularAptitud(){
    float suma = 0;
    for(Individuo indiv : individuos){
    	float aptitud = indiv.getAptitud();
        suma += aptitud;
        if(mejor == null){
        	mejor = indiv;
        }
        if(mejor.getAptitud() < aptitud){
        	mejor = indiv;
        }
    }
    sumaAptitudes = suma;
    aptitudPob = sumaAptitudes / (float)individuos.size();
  }

  public void mostrarDatos(){
	  System.out.println("Número de generación: " + nroGeneracion);
	  System.out.println("Aptitud de la población: " + aptitudPob);
	  System.out.println("Tasa de crecimiento: " + tasaCrecimiento);
	  System.out.println("");
  }
  
  public void actualizarPoblacion(List<Individuo> individuosNuevos){
	  individuos.clear();
	  individuos.addAll(individuosNuevos);
  }
  
  public List<Individuo> getListaIndividuos(){
	  return individuos;
  }
  
  public Individuo getMejorIndividuo(){
	  return mejor;
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


