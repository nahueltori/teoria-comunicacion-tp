package core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Mutacion{

	Poblacion poblacion;
	float porcentaje;
	int cantAMutar;
	
	public Mutacion(Poblacion pob, float porc){
		poblacion = pob;
	    porcentaje = porc;
	    int cantIndividuos = poblacion.getListaIndividuos().size();
	    cantAMutar = Math.round(cantIndividuos * porcentaje / 100);
	}
	
	public void mutar(){
		int cantMutados = 0;
		Random random = new Random();
		List<Individuo> individuos = poblacion.getListaIndividuos();
		List<Individuo> cantAMutarMejores = seleccionarXMejoresAptitudesAMutar();
		
		while(cantMutados < cantAMutar){
			for (Individuo individuo : cantAMutarMejores) {
				Individuo ind = (Individuo)individuos.get(random.nextInt(individuos.size() - 1));
				ind.mutar(individuo.getAptitud());
				cantMutados++;
			}
		}
		
	}
	
	/**
	 * Selecciona los X mejores siendo X la cantidad a mutar seteada.
	 */
	private List<Individuo> seleccionarXMejoresAptitudesAMutar() {
		
		List<Individuo> lista = new ArrayList<Individuo>(poblacion.getListaIndividuos());
		Collections.sort(lista);
		int from = lista.size() - cantAMutar;
		
		return lista.subList(from, lista.size());
		
	}

}

