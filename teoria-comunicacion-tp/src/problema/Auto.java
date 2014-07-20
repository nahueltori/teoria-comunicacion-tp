package problema;

public class Auto {

	/**
	 * Posición en metros en el tramo actual.
	 */
	private int posicion;
	
	/**
	 * Velocidad del auto en kilómetros por hora.
	 */
	private int velocidad;
	
	public void avanzar(int tiempo){
		posicion = velocidad * tiempo * 1000 / 3600;
	}
	
	public int getPosicion(){
		return posicion;
	}
	
	public void setVelocidad(int vel){
		velocidad = vel;
	}
	
	public int getVelocidad(){
		return velocidad;
	}
	
}
