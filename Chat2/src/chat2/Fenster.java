package chat2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Fenster extends Frame implements WindowListener {

	private static final long serialVersionUID = -330149246180933535L;

	public static void main(String[] args) {
		new Fenster();
		
	}

	public Fenster() {
		setSize(400, 300);
		setLocation(200, 200);
		setTitle("Chat-AD-HOC");
		setVisible(true);
		
		addWindowListener(this);
		
		setForeground(Color.white);
		setBackground(Color.DARK_GRAY);
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		//(Color.RED);

		Image image = Toolkit.getDefaultToolkit().getImage("res/sun.jpg");
		setIconImage(image);
		
		
		/*try { 
			
			//Thread.sleep(2000); dispose();
			} catch(InterruptedException e) {
			e.printStackTrace(); 
			//System.out.println("Bild nicht gefunden..")
			}; */
		
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("HALLO ", 100, 100);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {

		dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
