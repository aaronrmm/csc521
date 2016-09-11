package hw1section3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	final static int PORT = 9500;
	final static String HOST = "127.0.0.1";
	public static void main(String[]args){
		try {
			Socket socket = new Socket(HOST,PORT);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			byte[] message = new byte[30];
			in.read(message);
			System.out.println(new String(message,"UTF-8"));
			out.write(("Hi, everybody!").getBytes());
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
