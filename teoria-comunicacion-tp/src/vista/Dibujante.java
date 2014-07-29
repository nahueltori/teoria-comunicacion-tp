package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import problema.Auto;
import problema.Avenida;
import problema.Tramo;
import problema.Semaforo;

@SuppressWarnings("serial")
public class Dibujante extends JPanel {
//	int x = 0;
	JFrame frame = null;
	Avenida avenida;
	private static int YTRAMO = 100;
	private static int ALTOTRAMO = 30;
	private static int ALTOSEMAFORO = 10;
	private static int ANCHOSEMAFORO = 10;
	private static int DIST_ENTRE_TRAMOS = 15;
	private static int YAUTO = YTRAMO + (ALTOTRAMO /2);
	
    public void setAvenida (Avenida a){
	    JFrame frame = new JFrame("Mini Tennis");
    	frame.add(this);
    	frame.setSize(1000, 200)	;
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.frame = frame;
    	this.avenida = a;
    }

	public void update() {
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		int xTramo = 0;
		List<Tramo> listaTramos = avenida.getTramos();
		for(Iterator<Tramo> t = listaTramos.iterator(); t.hasNext(); ){
			Tramo tramo = t.next();
			List<Auto> listaAutos = tramo.getAutos();
			
		// Dibujo del tramo
			g2d.setColor(java.awt.Color.GRAY);
			g2d.fillRect(xTramo, YTRAMO, tramo.getLongitud(), ALTOTRAMO);
			
		// Dibujo todos los autos del tramo
			for(Iterator<Auto> a = listaAutos.iterator(); a.hasNext(); ){
				Auto auto = a.next();
				int xAuto = auto.getPosicion() + xTramo;
				g2d.setColor(java.awt.Color.BLUE);
				g2d.fillOval(xAuto, YAUTO, 10, 10);
			}
		// Termina el tramo y dibujo el semaforo
			int xSemaforo = xTramo + tramo.getLongitud();
			Semaforo s = tramo.getSemaforo();
			problema.Semaforo.Color estadoSemaforo = s.getColor();
			if ( estadoSemaforo == problema.Semaforo.Color.ROJO ) 
				g2d.setColor(Color.RED);
			else if ( estadoSemaforo == problema.Semaforo.Color.AMARILLO ) 
				g2d.setColor(Color.YELLOW);
				else 
					g2d.setColor(Color.GREEN);
			g2d.fillOval(xSemaforo, YTRAMO, ANCHOSEMAFORO, ALTOSEMAFORO);
			
		// Me posiciono en el proximo tramo	
			xTramo = xTramo + tramo.getLongitud() + DIST_ENTRE_TRAMOS;
		}
	}	

}