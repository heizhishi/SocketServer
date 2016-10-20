package com.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer();
		socketServer.startServer();
		
	}

	public void startServer() {
		ServerSocket serverSocket = null;
		BufferedReader bufferedReader = null;
		Socket socket = null;
		BufferedWriter bufferedWriter = null;
		String receivedMsg;
		try {
			serverSocket = new ServerSocket(9898);
			socket = serverSocket.accept();
			System.out.println("client_" + socket.hashCode() + ":has connected");
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while ((receivedMsg = bufferedReader.readLine()) != null) {
				System.out.println(receivedMsg);
				
				/**
				 * һ��Ҫ���ϻ��з���\n������readline��һֱ�ڶ�ȡ���޷�ֹͣ�����ն������κ�����
				 */
				bufferedWriter.write("server reply:" + receivedMsg+"\n");
				
				bufferedWriter.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				bufferedWriter.close();
				bufferedReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
