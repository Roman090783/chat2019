package chat2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JLabel;

public class Client4 {
	
	JFrame clientFrame;
	JPanel clientPanel;
	JTextArea display;
	JTextField nachricht;
	JButton button_SendMessage;
	JTextField name;
	JScrollPane scrollPane_Messages;
	
	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	private JButton btnLoeschen;
	private JLabel Verbindung;
	
	public static void main(String[] args) {
		Client4 c = new Client4();
		c.createGUI();
	}
	
	public void createGUI() {
		clientFrame = new JFrame("ADHOC-Chat");
		clientFrame.setSize(800, 600);
		
		// Panel erzeugen, welches alle anderen Inhalte enthält
		clientPanel = new JPanel();
		
		display = new JTextArea();
		display.setEditable(false);
		
		nachricht = new JTextField(38);
		nachricht.setText("Nachricht ");
		nachricht.addKeyListener(new SendPressEnterListener());
		
		button_SendMessage = new JButton("Senden");
		button_SendMessage.addActionListener(new SendButtonListener());
		
		name = new JTextField(10);
		name.setText("Name ");
		
		// Scrollbalken zur textArea hinzufügen
		scrollPane_Messages = new JScrollPane(display);
		scrollPane_Messages.setPreferredSize(new Dimension(700, 500));
		scrollPane_Messages.setMinimumSize(new Dimension(700, 500));
		scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		
		
		if(connectToServer()) {
			Verbindung = new JLabel("Netzwerkverbindung hergestellt");
			scrollPane_Messages.setColumnHeaderView(Verbindung);
			//appendTextMessages(Verbindung);
		}
		
		if(!connectToServer()) {
			Verbindung = new JLabel("Keine Verbindung..");
			scrollPane_Messages.setColumnHeaderView(Verbindung);
			//appendTextMessages(Verbindung);
		}
		
		Thread t = new Thread(new MessagesFromServerListener());
		t.start();
		
		clientPanel.add(scrollPane_Messages);
		
		//Verbindung = new JLabel("Verbindung");
		//scrollPane_Messages.setColumnHeaderView(Verbindung);
		clientPanel.add(name);
		clientPanel.add(nachricht);
		clientPanel.add(button_SendMessage);
		
		// Panel zum ContentPane (Inhaltsbereich) hinzufügen
		clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);
		
		btnLoeschen = new JButton("Loeschen");
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Loeschen
				display.setText("");
			}
		});
		clientPanel.add(btnLoeschen);
		
		clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientFrame.setVisible(true);
	}
	
	public boolean connectToServer() {
		try {
			client  = new Socket("192.168.122.54", 50000);
			//client2  = new Socket("192.168.122.54", 54321);
			//if((new Socket("", 5555)) != null) {
				
				System.out.println(client.getLocalPort());
				
			//}
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
			//appendTextMessages("Netzwerkverbindung hergestellt");
			
			return true;
		} catch(Exception e) {
			//appendTextMessages("Netzwerkverbindung konnte nicht hergestellt werden");
			e.printStackTrace();
			
			return false;
		}
	}
	
	public void sendMessageToServer() {
		writer.println(name.getText() + ": " + nachricht.getText());
		writer.flush();
		
		nachricht.setText("");
		nachricht.requestFocus();
	}
	
	public void appendTextMessages(String message) {
		display.append(message + "\n");
	}
	
	// Listener
	public class SendPressEnterListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessageToServer();
			}	
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessageToServer();			
		}
		
	}
	
	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {
			String message;
			
			try {
				while((message = reader.readLine()) != null) {
					appendTextMessages(message);
					display.setCaretPosition(display.getText().length());
				}
			} catch (IOException e) {
				appendTextMessages("Nachricht konnte nicht empfangen werden!");
				e.printStackTrace();
			}
		}
		
	}
}