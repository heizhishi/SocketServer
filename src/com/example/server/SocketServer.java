package com.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServer {
	/**
	 * BufferedWriter bufferedWriter; BufferedReader bufferedReader;
	 * 
	 * 不能设置为全局变量否则不然新的 socket 会替换掉旧的 socket 的 writer 和 reader。
	 * 
	 */

	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer();
		socketServer.startServer();

	}

	public void startServer() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(9898);

			while (true) {

				socket = serverSocket.accept();

				manageConnection(socket);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void manageConnection(Socket socket) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader bufferedReader = null;
				BufferedWriter bufferedWriter = null;
				try {
					System.out.println("client_" + socket.hashCode() + ":has connected");
					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					String receivedMsg;
					/*
					 * new Timer().schedule(new TimerTask(){
					 * 
					 * @Override public void run() { try { System.out.println(
					 * "heart beat once……"); bufferedWriter.write(
					 * "heart beat once……"+"\n"); bufferedWriter.flush(); }
					 * catch (IOException e) { e.printStackTrace(); } }
					 * },3000,3000);
					 */

					while ((receivedMsg = bufferedReader.readLine()) != null) {
						System.out.println("socketClient客户端_" + socket.hashCode() + ":" + receivedMsg);

						/**
						 * 一定要加上换行符“\n”否则readline会一直在读取而无法停止，最终读不出任何数据
						 */

						bufferedWriter.write("server reply:" + receivedMsg + "\n");
						bufferedWriter.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (bufferedWriter != null) {
							bufferedWriter.close();
						}
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
}
