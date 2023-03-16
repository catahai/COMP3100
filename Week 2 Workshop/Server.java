import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        // Creates a ServerSocket to listen for incoming requests made by the client
        int port = 50000;
        ServerSocket ss = new ServerSocket(port);

        // Waits for a client to connect to the server socket
        Socket socket = ss.accept();

        // Create a BufferedReader object to read data from the client
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create a DataOutputStream object to send data to the client
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Read a line of text from the client
        String message = in.readLine();

        if (message.equals("HELO")) {
            // Responds with a "G'DAY" back to the client
            out.write(("G'DAY\n").getBytes());

            // Reads a line of text made by the client
            message = in.readLine();

            if (message.equals("BYE")) {
                // Send "BYE" back to the client
                out.write(("BYE\n").getBytes());

                // Closes the connection made by the client
                socket.close();

                // Closes the ServerSocket
                ss.close();
            }
        }
    }
}