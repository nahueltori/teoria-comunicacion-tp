package problema;

import java.util.Random;

public class Auto {

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
	
	public void avanzar(int tiempo){
		//Vario aleatoriamente un poco la velocidad
		Random rand = new Random();
		velocidad *= (1 + rand.nextFloat());
		posicion += velocidad * tiempo * 1000 / 3600;
		if(posicion > posicionMax)
			posicion = posicionMax;
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
