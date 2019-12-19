import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

// This client thread opens the input and the output streams for a particular client,
// ask the client's name, informs all the clients currently connected to the
// server about the fact that a new client has joined the chat room,
// and as long as it receive data, echos that data back to all other clients.
// When the client leaves the chat room this thread informs also all  the
// clients about that and terminates.

class clientThread extends Thread {

	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	clientThread t[];

	public clientThread(Socket clientSocket, clientThread[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}

	public void run() {
		String line;
		String name;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			os.println("Enter your name.");
			name = is.readLine();
			os.println("Hello " + name + " to our chat room. To leave enter /quit in a new line");
			for (int i = 0; i <= 9; i++)
				if (t[i] != null && t[i] != this)
					t[i].os.println("*** A new user " + name + " entered the chat room  !!! ***");
			while (true) {
				line = is.readLine();
				if (line.startsWith("/quit"))
					break;
				for (int i = 0; i <= 9; i++)
					if (t[i] != null)
						t[i].os.println("<" + name + "> " + line);
			}
			for (int i = 0; i <= 9; i++)
				if (t[i] != null && t[i] != this)
					t[i].os.println("*** The user " + name + " is leaving the chat room  !!! ***");

			os.println("*** Bye " + name + " ***");

			// Clean up:
			// Set to null the current thread variable such that other client could
			// be accepted by the server

			for (int i = 0; i <= 9; i++)
				if (t[i] == this)
					t[i] = null;

			// close the output stream
			// close the input stream
			// close the socket

			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}
