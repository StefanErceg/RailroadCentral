package org.unibl.etf.mdp.railroad.chat;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.unibl.etf.mdp.railroad.api.TrainStationService;
import org.unibl.etf.mdp.railroad.model.TrainStation;
import org.unibl.etf.mdp.railroad.model.User;

public class ChatServer {
	
	private static final int PORT = 8443;
	private static final String KEYSTORE = System.getProperty("user.home") + File.separator + "Railroad" + File.separator + "SSL" + File.separator + "server_keystore.jks";
	private static final String KEYSTORE_PASS = "railroadserver";
	private static HashMap<String, ArrayList<ChatUser>> onlineUsers = null;
	private static HashMap<String, ServerThread> userThreads = new HashMap<>();
	
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", KEYSTORE);
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);
		
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try {
			ServerSocket serverSocket = serverSocketFactory.createServerSocket(PORT);
			TrainStationService trainStationService = new TrainStationService();
			ArrayList<TrainStation> trainStations = trainStationService.getTrainStations();
			onlineUsers = new HashMap<String, ArrayList<ChatUser>>();
			trainStations.stream().forEach(trainStation -> onlineUsers.put(trainStation.getId(), new ArrayList<ChatUser>()));
			System.out.println("Chat server started");
			while (true) {
				SSLSocket socket = null;
				try {
					socket = (SSLSocket) serverSocket.accept();
					new ServerThread(socket).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static synchronized void addUser(String username, String trainStationId) {
		if (username == null || "".equals(username) || trainStationId == null || "".equals(trainStationId)) return;
		onlineUsers.get(trainStationId).add(new ChatUser(username, trainStationId));
	}
	
	public static synchronized void removeUser(String username, String trainStationId) {
		if (username == null || "".equals(username) || trainStationId == null || "".equals(trainStationId)) return;
		ArrayList<ChatUser> usersInTrainStation = onlineUsers.get(trainStationId);
		usersInTrainStation = (ArrayList<ChatUser>) usersInTrainStation.stream().filter(chatUser -> !chatUser.getTrainStationId().equals(trainStationId) && !chatUser.getUsername().equals(username)).collect(Collectors.toList());
		onlineUsers.replace(trainStationId, usersInTrainStation);
	}
	
	public static synchronized void addUserThread(String username, ServerThread thread) {
		if (username == null || thread == null || "".equals(username)) return;
		userThreads.put(username, thread);
	}
	
	public static synchronized void removeUserThread(String username) {
		if (username == null || "".equals(username)) return;
		userThreads.remove(username);
	}
	
	public static synchronized void sendMessage(String from, String to, String content) {
		ServerThread userThread = userThreads.get(to);
		if (userThread != null)
		userThread.sendMessage(from, content);
		
	}
	
	public static synchronized void sendFile(String from, String to, String name, byte[] data) {
		ServerThread userThread = userThreads.get(to);
		if (userThread != null) {
			userThread.sendFile(from, name, data);
		}
	}
	
	

}
