package vista;

import java.awt.Color;
import java.awt.Font;
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

	JFrame frame = null;
	Avenida avenida;
	int ciclo = 0;
	float trafico = 0;
	
	private static int ANCHOVENTANA  = 1500;
	private static int ALTOVENTANA   =  200;
	private static int ANCHOAUTO     =   10;
	private static int ALTOAUTO      =   10;
	private static int ANCHOSEMAFORO =   15;
	private static int ALTOSEMAFORO  =   15;
	private static int ALTOTRAMO     =   30;
	private static int ANCHOSENDA    =    2;
	
	private static int YTRAMO = (ALTOVENTANA/2)-(ALTOTRAMO);
	private static int YAUTO = YTRAMO + (ALTOTRAMO /3);
	private static int YSEMAFORO = YTRAMO - ALTOSEMAFORO;

	private static Font serifFont = new Font("Serif", Font.PLAIN, 15);
	
	public void setAvenida (Avenida a){
	    JFrame frame = new JFrame("Semaforos");
    	frame.add(this);
    	frame.setSize(ANCHOVENTANA,ALTOVENTANA)	;
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.frame = frame;
    	this.avenida = a;
    }

	/**
	* Recibe como parametro el numero de segundos, y el estado del trafico en ese momento.
	* @param ciclo
	* @param trafico
	*/
	public void update(int ciclo, float trafico) {
		this.ciclo = ciclo;
		this.trafico = trafico;
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		int xTramo = 0;
		// Imprimo el numero de Ciclo
		g2d.setFont(serifFont);
	    g.drawString("Segundos transcurridos: "+String.valueOf(ciclo), 15, 15);
	    if(trafico >= 1){
	    	int traficoDis = Math.round(trafico);
	    	g.drawString("Trafico entrada: "+String.valueOf(traficoDis)+" autos/segundo", 15, 30);
	    }else{
	    	int traficoDis = Math.round(1 / trafico);
	    	g.drawString("Trafico entrada: "+String.valueOf(traficoDis)+" segundos/auto", 15, 30);
	    }
	    
		List<Tramo> listaTramos = avenida.getTramos();
		for(Iterator<Tramo> t = listaTramos.iterator(); t.hasNext(); ){
			Tramo tramo = t.next();
			List<Auto> listaAutos = tramo.getAutos();
			
		// Dibujo del tramo
			g2d.setColor(java.awt.Color.GRAY);
			g2d.fillRect(xTramo, YTRAMO, tramo.getLongitud()+ANCHOAUTO, ALTOTRAMO);

		// Escribo el estado del tramo
			int estadoTramo = tramo.estadoTrafico();
			g2d.setFont(serifFont);
		    g.drawString("%"+String.valueOf(estadoTramo), xTramo+tramo.getLongitud()/2, YTRAMO+(ALTOTRAMO*2));
				
		// Dibujo senda peatonal
			g2d.setColor(java.awt.Color.WHITE);
			g2d.fillRect(xTramo, YTRAMO, ANCHOSENDA, ALTOTRAMO);
			g2d.fillRect(xTramo + (ANCHOSENDA*2), YTRAMO, ANCHOSENDA, ALTOTRAMO);
			g2d.fillRect(xTramo + (ANCHOSENDA*4), YTRAMO, ANCHOSENDA, ALTOTRAMO);			
			g2d.fillRect(xTramo + (ANCHOSENDA*6), YTRAMO, ANCHOSENDA, ALTOTRAMO);			
		
		// Dibujo todos los autos del tramo
			for(Iterator<Auto> a = listaAutos.iterator(); a.hasNext(); ){
				Auto auto = a.next();
				int xAuto = auto.getPosicion() + xTramo -ANCHOAUTO;
				int color = Math.abs(auto.getId());
				color = color %7;
				switch (color){
					case 0: g2d.setColor(java.awt.Color.BLUE); break;
					case 1: g2d.setColor(java.awt.Color.BLACK); break;
					case 2: g2d.setColor(java.awt.Color.CYAN); break;
					case 3: g2d.setColor(java.awt.Color.MAGENTA); break;
					case 4: g2d.setColor(java.awt.Color.ORANGE); break;
					case 5: g2d.setColor(java.awt.Color.PINK); break;
					case 6: g2d.setColor(java.awt.Color.GREEN); break;
				}	
				g2d.fillOval(xAuto, YAUTO, ANCHOAUTO, ALTOAUTO);
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
			g2d.fillOval(xSemaforo, YSEMAFORO, ANCHOSEMAFORO, ALTOSEMAFORO);
			
		// Me posiciono en el proximo tramo	
			xTramo = xTramo + tramo.getLongitud();

		}
	}	

}