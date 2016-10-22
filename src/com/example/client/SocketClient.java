package com.example.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	public static void main(String[] args) {
		SocketClient socketClient = new SocketClient();
		socketClient.start();

	}

	public void start() {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		Socket socket = null;
		BufferedReader serverReader = null;
		String in;
		String serverIn;

		try {
			socket = new Socket("127.0.0.1", 9898);
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			startServerReplyListener(serverReader);
			while (!(in = bufferedReader.readLine()).equals("bye")) {
				System.out.println(in);
				bufferedWriter.write(in + "\n");
				bufferedWriter.flush();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (serverReader != null) {
					serverReader.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void startServerReplyListener(final BufferedReader reader) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String response;
				try {
					while ((response = reader.readLine()) != null) {
						System.out.println(response);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
	}

}
