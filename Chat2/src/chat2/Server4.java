package chat2;

import java.io.BufferedReader;
//import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.logging.Logger;

public class Server4 {

	public ServerSocket server;
	public ArrayList<PrintWriter> list_clientWriter;

	public final int LEVEL_ERROR = 1;
	public final int LEVEL_NORMAL = 0;

	public static void main(String[] args) {
		Server4 s;
		try {
			s = new Server4();
			if (s.runServer()) 
			s.listenToClients();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ClientHandler implements Runnable {

		public Socket client;
		public BufferedReader reader;

		public ClientHandler(Socket client) {
			try {
				this.client = client;
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String nachricht;
			try {
				while ((nachricht = reader.readLine()) != null) {
					appendTextToConsole("Vom Client4: \n" + nachricht, LEVEL_NORMAL);
					sendToAllClients(nachricht);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void listenToClients() {
		while (true) {
			try {
				Socket client = server.accept();

				PrintWriter writer = new PrintWriter(client.getOutputStream());
				list_clientWriter.add(writer);

				Thread clientThread = new Thread(new ClientHandler(client));
				clientThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean runServer() {
		// if(ServerSocket(55555))
		try {
			 server = new ServerSocket(50000);
			appendTextToConsole("Server4 gestartet!", LEVEL_ERROR);
			list_clientWriter = new ArrayList<PrintWriter>();
			return true;
		} catch (IOException e) {
			// ClientHandler c = new ClientHandler(client);
			appendTextToConsole("Server4 konnte nicht gestartet werden!", LEVEL_ERROR);
			e.printStackTrace();
 /*finally {
				if (!server.isClosed())
					server.getSoTimeout();
			super.finalize();
			
		}*/
		return false;
		}
	}

	public void appendTextToConsole(String message, int level) {
		if (level == LEVEL_ERROR) {
			System.err.println(message + "\n");
		} else {
			System.out.println(message + "\n");
		}
	}

	public void sendToAllClients(String message) {
		Iterator it = list_clientWriter.iterator();

		while (it.hasNext()) {
			PrintWriter writer = (PrintWriter) it.next();
			writer.println(message);
			writer.flush();
		}
	}

}

/*
 * private final Object finalizerGuardian = new Object() {
 * 
 * @Override protected void finalize() throws Throwable {
 * if(!(server.isClosed())) { Logger.log(getAnonymousLogger("SERVER SPINNT.."));
 * //server.close(); } }
 */
