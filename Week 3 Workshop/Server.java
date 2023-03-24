import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 50000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                try (OutputStream out = clientSocket.getOutputStream();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Client sent: " + message);

                        if ("HELO".equals(message)) {
                            // Send "OK" response to the client
                            out.write(("OK\n").getBytes());
                            out.flush();
                        } else if (message.startsWith("AUTH")) {
                            // Get the username from the message
                            String[] parts = message.split(" ");
                            String username = parts[1];

                            // Send "OK" response to the client
                            out.write(("OK\n").getBytes());
                            out.flush();
                        } else if ("REDY".equals(message)) {
                            // TODO: generate and send a job to the client
                        } else if ("QUIT".equals(message)) {
                            // Send "OK" response to the client and close the connection
                            out.write(("OK\n").getBytes());
                            out.flush();
                            clientSocket.close();
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error occurred while communicating with client: " + e.getMessage());
                }

                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
