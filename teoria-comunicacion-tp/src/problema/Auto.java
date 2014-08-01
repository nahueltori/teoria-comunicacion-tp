package problema;

import java.util.Random;

public class Auto {

	/**
	 * Posici�n en metros en el tramo actual.
	 */
	private int posicion = 0;
	
	/**
	 * Posici�n m�xima en metros en el tramo actual.
	 */
	private int posicionMax = 0;

	/**
	 * Velocidad del auto en kil�metros por hora.
	 */
	private int velocidad = 0;
	
	public void avanzar(int tiempo, Tramo tramo){
		//Vario aleatoriamente un poco la velocidad
		Random rand = new Random();
		velocidad *= (1 + rand.nextFloat());
		int distAvance = velocidad * tiempo * 1000 / 3600;

		System.out.println("Distancia de avance sin correccion: "+ distAvance);
		// Correccion de la posicion si me pase del final del tramo
		if(posicion + distAvance > posicionMax)
			distAvance = posicionMax - posicion;
		System.out.println("Distancia de avance con correccion del final: "+ distAvance);
		
		// Correccion de autos adelante
		distAvance = tramo.verificarAvance(this, distAvance);
		System.out.println("Distancia de avance corregida: "+distAvance);
		
		// Asigno la nueva posicion
		posicion += distAvance;
		System.out.println("Avanzo desde la "+(posicion-distAvance)+ " a la posicion " + posicion ); 
	}
	
	public int getPosicion(){
		return posicion;
	}
	
	public void reiniciar(int posMax){
		posicion = 0;
		posicionMax = posMax;
	}
	
	public void setVelocidad(int vel){
		velocidad = vel;
	}
	
	public int getVelocidad(){
		return velocidad;
	}
	
}
