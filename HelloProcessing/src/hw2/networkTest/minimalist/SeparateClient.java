package hw2.networkTest.minimalist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SeparateClient {

	private static final boolean DEBUG1 = false;
	private static final boolean DEBUG2 = false;
	private static final boolean TO_STRING = true;
	private static final int MOVING_PLATFORM_COUNT = 30;
	public static int ITERATION_COUNT = 1000;
			final static int PORT = 9595;
			final static String HOST = "127.0.0.1";
			
	public static void main(String[]args){
		try {
			Socket socket = new Socket(HOST,PORT);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			for(int i=0;i<ITERATION_COUNT;i++){
				if(DEBUG1)System.out.println("Client iteration at :"+i);
				int messageCount = 0;
				Serializable lastMessage = null;
				while(messageCount<MOVING_PLATFORM_COUNT){
					messageCount++;
					if(TO_STRING){
						String string = (String)in.readObject();
						if(DEBUG2)System.out.println(string);
						break;
					}
					else
						in.readObject();
					if(DEBUG2){
						System.out.println("messageCount:"+messageCount);
					}
				}
				out.writeObject(lastMessage);
				out.reset();
			}
			socket.close();
			System.out.println("Client closed socket");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
