package core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) throws InterruptedException {
		new SocketServer();
	}

	private ServerSocket ss;

	public SocketServer() throws InterruptedException {
		init();
		startSocket();
	}

	private void init() {
		try {
			ss = new ServerSocket(4344);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Make serversocket error / IOException");
		}
	}

	private void startSocket() throws InterruptedException {
		try {
			Socket socket = ss.accept();
			OutputStream os = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			while (true) {
			System.out.println("start server");
			bw.write("this is msg \n");
			bw.flush();
			Thread.sleep(1000);
//			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//			output.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
