package problema;

import java.util.Random;

public class Auto{

	private int idAuto;
	
	public Auto(int id){
		idAuto = id;
	}
	
	public int getId(){
		
		return idAuto;
	}
	/**
	 * Posición en metros en el tramo actual.
	 */
	private int posicion = 0;
	
	/**
	 * Posición máxima en metros en el tramo actual.
	 */
	private int posicionMax = 0;

	/**
	 * Velocidad del auto en kilómetros por hora.
	 */
	private int velocidad = 0;
	
	public void avanzar(int tiempo, Tramo tramo){
		//Vario aleatoriamente un poco la velocidad
		int distAvance = velocidad * tiempo * 1000 / 3600;

//		System.out.println("Distancia de avance sin correccion: "+ distAvance);
		// Correccion de la posicion si me pase del final del tramo
		if(posicion + distAvance > posicionMax)
			distAvance = posicionMax - posicion;
//		System.out.println("Distancia de avance con correccion del final: "+ distAvance);
		
		// Correccion de autos adelante
		distAvance = tramo.verificarAvance(this, distAvance);
//		System.out.println("Distancia de avance corregida: "+distAvance);
		
		// Asigno la nueva posicion
		posicion += distAvance;
//		System.out.println("Avanzo desde la "+(posicion-distAvance)+ " a la posicion " + posicion ); 
	}
	
	public int getPosicion(){
		return posicion;
	}
	
	public void reiniciar(int posMax){
		posicion = 0;
		posicionMax = posMax;
	}
	
	public void setVelocidad(int vel){
		Random rand = new Random();
		velocidad = vel;
		velocidad += (rand.nextInt(4) - 2);
	}
	
	public int getVelocidad(){
		return velocidad;
	}
	
}
