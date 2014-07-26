package problema;

public class Auto {

	/**
	 * Posición en metros en el tramo actual.
	 */
	private int posicion = 0;
	
	/**
	 * Velocidad del auto en kilómetros por hora.
	 */
	private int velocidad = 0;
	
	public void avanzar(int tiempo){
		posicion = velocidad * tiempo * 1000 / 3600;
	}
	
	public int getPosicion(){
		return posicion;
	}
	
	public void reiniciar(){
		posicion = 0;
	}
	
	public void setVelocidad(int vel){
		velocidad = vel;
	}
	
	public int getVelocidad(){
		return velocidad;
	}
	
}
