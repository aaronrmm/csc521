package hw1section3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	final static int PORT = 9500;
	static ConcurrentHashMap<Socket, ClientListener> clients = new ConcurrentHashMap<Socket, ClientListener>();
	
	public static void main(String[]args){
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(PORT);
			while(true){
				Socket s;
				try {
					s = ss.accept();
					clients.put(s, new ClientListener(s));
					String message = clients.values().size()+" clients connected.";
					
					s.getOutputStream().write(message.getBytes());
					byte[] shoutout = new byte[30];
					s.getInputStream().read(shoutout);
					System.out.println(new String(shoutout,"UTF-8"));
					
					s.getOutputStream().write("See ya, buddy.".getBytes());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
