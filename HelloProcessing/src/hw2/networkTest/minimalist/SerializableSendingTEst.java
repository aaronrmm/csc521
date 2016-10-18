package hw2.networkTest.minimalist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import physics.Rectangle;

public class SerializableSendingTEst {

	protected static final boolean TO_STRING = true;
	final static int CLIENT_COUNT = 2;
	final static int ITERATION_COUNT = 100;
	final static int MOVING_PLATFORM_COUNT = 10;
	
	final static boolean DEBUG1 = false;
	final static boolean DEBUG2 = false;
	
	final static int PORT = 9595;
	static boolean running = true;
	final static String HOST = "127.0.0.1";
	protected static final boolean DEBUG_SERVER_ITERATIONS = false;
	static ServerSocket ss;
	static ArrayList<Socket>clientSockets = new ArrayList<Socket>();
	static int finished_sockets = 0;
	static long start_time;
	static int server_iteration = 0;
	
	public static void main(String[]args){
		
		//server thread
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Running with platforms:"+MOVING_PLATFORM_COUNT+", CLIENTS:"+CLIENT_COUNT+", and CLIENT_ITERATIONS:"+ITERATION_COUNT);
				//run thread for accepting clients
				new Thread(new Runnable(){

					@Override
					public void run() {
						try {
							ss = new ServerSocket(PORT);
							while(clientSockets.size()<CLIENT_COUNT){
								clientSockets.add(ss.accept());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}).start();
				
				//create X game objects
				Serializable[] serializables = new Serializable[MOVING_PLATFORM_COUNT];
				for(int i=0;i<MOVING_PLATFORM_COUNT; i++){
					serializables[i] = create_platform();
				}
				
				//wait for all clients to connect
				while(clientSockets.size()<CLIENT_COUNT)
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				System.out.println("All "+CLIENT_COUNT+" clients connected.");
				
				//start timer
				start_time = System.nanoTime();
				
				//create client updating threads
				for(Socket client : clientSockets){
					new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
								ObjectInputStream in = new ObjectInputStream(client.getInputStream());

								//read client responses
								new Thread(new Runnable(){
									@Override
									public void run(){
										int responses = 0;
										while(responses<ITERATION_COUNT && running&& !client.isClosed()){
											responses++;
											try {
												Object o = in.readObject();
												if(DEBUG1){
													System.out.println(o);
												}
											} catch (ClassNotFoundException | IOException e) {
												e.printStackTrace();
												running = false;
											}
										}
									}
								}).start();
								
								for(int i=0;i<ITERATION_COUNT;i++){
									if(DEBUG1)System.out.println("Clientside iteration count:"+i);
									if(!running)break;
									while(i>server_iteration)
										Thread.sleep(10);
									//send all objects
									for(Serializable serializable: serializables){
										if(TO_STRING){
											out.writeObject(serializable.toString());
										}
										else
											out.writeObject(serializable);
									}
									out.reset();
								}
								System.out.println("Done sending to client");
							} catch (IOException | InterruptedException e) {
								e.printStackTrace();
								running = false;
							}
							
						}
					}).start();
						
					//
				}

				//modify all game objects to simulate movement
				while(running){
					for (Serializable serializable : serializables){
						modify(serializable);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					server_iteration ++;
					if(DEBUG_SERVER_ITERATIONS)System.out.println("Server_Iteration: "+server_iteration);
					boolean finished = true;
					for(Socket client:clientSockets){
						if (!client.isClosed()){
							finished = false;
							break;
						}
					}
					if(finished_sockets >= CLIENT_COUNT)
						finished = true;
					if(finished){
						running = false;
						System.out.println(System.nanoTime() - start_time +" nanoseconds");
						break;
					}
				}
			}

			private Serializable create_platform() {
				Rectangle rectangle = new Rectangle();
				return rectangle;
			}

			private void modify(Serializable serializable){
				((Rectangle)serializable).x++;
			}
			
			
			
		}).start();
		
		//client threads
		for(int c = 0; c <CLIENT_COUNT;c++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						Socket socket = new Socket(HOST,PORT);
						ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						for(int i=0;i<ITERATION_COUNT;i++){
							if(DEBUG1)System.out.println("Client iteration at :"+i);
							int messageCount = 0;
							Serializable lastMessage = null;
							while(running && messageCount<MOVING_PLATFORM_COUNT){
								messageCount++;
								if(TO_STRING){
									String string = (String)in.readObject();
									if(DEBUG2)System.out.println(string);
								}
								else
									in.readObject();
								if(DEBUG2){
									System.out.println("messageCount:"+messageCount);
								}
							}
							if(running)out.writeObject(lastMessage);
							out.reset();
						}
						socket.close();
						System.out.println("Client closed socket");
						finished_sockets++;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
