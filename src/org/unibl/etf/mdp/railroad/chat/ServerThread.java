package org.unibl.etf.mdp.railroad.chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.net.ssl.SSLSocket;


public class ServerThread extends Thread {

	public static final Integer INTRODUCTION = 1;
	public static final Integer TEXT = 2;
	public static final Integer FILE = 3;
	public static final Integer BYE = 4;
	
	private SSLSocket socket;

	private DataInputStream in;
	private DataOutputStream out;
	private boolean active;
//	private static HashMap<Integer, Runnable> actions;
	
//	static {
//		actions = new HashMap<Integer, Runnable>();
//	}
	
	public ServerThread(SSLSocket socket) {
		super();
		this.socket = socket;
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.active = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Integer option = 0;
		try {
		while (active && !socket.isClosed()) {

				option = in.readInt();
				Integer fromLength = in.readInt();
				byte[] fromBuffer = new byte[fromLength];
				in.read(fromBuffer, 0, fromLength);
				String from = new String(fromBuffer);
				if (option.equals(INTRODUCTION) || option.equals(BYE)) {
					Integer stationIdLength = in.readInt();
					byte[] stationIdBuffer = new byte[stationIdLength];
					in.read(stationIdBuffer, 0, stationIdLength);
					String trainStationId = new String(stationIdBuffer);
					if (option.equals(INTRODUCTION)) {
						ChatServer.addUser(from, trainStationId);
						ChatServer.addUserThread(from, this);
					}
					else {
						ChatServer.removeUser(from, trainStationId);
						ChatServer.removeUserThread(from);
						this.active = false;
					}
					
				} else if (option.equals(TEXT) || option.equals(FILE)) {
					Integer toLength = in.readInt();
					byte[] toBuffer = new byte[toLength];
					in.read(toBuffer,0, toLength);
					String to = new String(toBuffer);
					Integer dataLength = in.readInt();
					byte[] dataBuffer = new byte[dataLength];
					Integer dataLoaded = 0;
					Integer loopLoaded = 0;
					while(dataLoaded < dataLength) {
					loopLoaded = in.read(dataBuffer, dataLoaded, dataLength - dataLoaded);
					dataLoaded += loopLoaded;
					}
					if (option.equals(TEXT)) {
					String data = new String(dataBuffer);
					ChatServer.sendMessage(from, to, data);
					} else {
						Integer nameLength = in.readInt();
						byte[] nameBuffer = new byte[nameLength];
						in.read(nameBuffer, 0, nameLength);
						String name = new String(nameBuffer);
						ChatServer.sendFile(from, to, name, dataBuffer);
					}
					
					
				}
			}
		}	catch (IOException e) {
				e.printStackTrace();
			}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String from, String content) {
		try {
			out.writeInt(TEXT);
			byte[] fromBytes = from.getBytes();
			out.writeInt(fromBytes.length);
			out.write(fromBytes);
			byte[] contentBytes = content.getBytes();
			out.writeInt(contentBytes.length);
			out.write(contentBytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(String from, String name, byte[] data) {
		try {
			out.writeInt(FILE);
			byte[] fromBytes = from.getBytes();
			out.writeInt(fromBytes.length);
			out.write(fromBytes);
			out.writeInt(data.length);
			out.write(data);
			byte[] nameBytes = name.getBytes();
			out.writeInt(nameBytes.length);
			out.write(nameBytes);
			out.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
