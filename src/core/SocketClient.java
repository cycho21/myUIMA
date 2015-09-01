package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		new SocketClient();
	}

	private Socket s;
	
	public SocketClient() throws UnknownHostException, IOException {
		s = new Socket("127.0.0.1", 4344);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while(true){
		try {
			System.out.println(100);
			System.out.println(input.readLine());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
}
