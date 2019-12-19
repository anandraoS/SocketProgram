import java.io.*;
import java.net.*;

public class MultiThreadChatServer {

	// Declaration section:
	// declare a server socket and a client socket for the server
	// declare an input and an output stream

	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	// This chat server can accept up to 10 clients' connections

	static clientThread t[] = new clientThread[10];

	public static void main(String args[]) {

		// The default port

		int port_number = 8888;

		if (args.length < 1) {
			System.out.println("Usage: java MultiThreadChatServer " + "Now using port number=" + port_number);
		} else {
			port_number = Integer.valueOf(args[0]).intValue();
		}

		// Initialization section:
		// Try to open a server socket on port port_number (default 8888)
		// Note that we can't choose a port less than 1023 if we are not
		// privileged users (root)

		try {
			serverSocket = new ServerSocket(port_number);
		} // try
		catch (IOException e) {
			System.out.println(e);
		}

		// Create a socket object from the ServerSocket to listen and accept
		// connections.
		// Open input and output streams for this socket will be created in
		// client's thread since every client is served by the server in
		// an individual thread

//can use a for loop to control the number of clients
//I have used the while so that we can have unlimited number of clients
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				new clientThread(clientSocket, t).start();
				break;
			} // try

			catch (IOException e) {
				System.out.println(e);
			}
		}
	}
} // class
