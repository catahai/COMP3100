import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        int port = 50000;
        String ip = "127.0.0.1";
        try (
            Socket socket = new Socket(ip, port);
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Sends a "HELO" request to the server side
            out.write(("HELO\n").getBytes());
            out.flush();

            // Reads the message from the server
            String message = in.readLine();
            System.out.println("Server: " + message);

            if ("OK".equals(message)) {
                // Gets the username of the current user or uses a random username
                String username = System.getProperty("user.name");
                if (username == null) {
                    username = "ming";
                }

                // Sends an "AUTH" and a username to the server side
                out.write(("AUTH " + username + "\n").getBytes());
                out.flush();
                
                // Reads another line of text from the server side
                message = in.readLine();
                System.out.println("Server: " + message);

                if ("OK".equals(message)) {
                    // Sends a "REDY" request to the server side
                    out.write(("REDY\n").getBytes());
                    out.flush();

                    // Reads another line of text from the server side
                    message = in.readLine();
                    System.out.println("Server: " + message);

                    if (message.startsWith("JOBN")) {
                        // This part will handle the job process
                    }

                    // Sends a "QUIT" message to terminate the job dispatching simulation
                    out.write(("QUIT\n").getBytes());
                    out.flush();


                    // Reads a line of text from the server
                    message = in.readLine();
                    System.out.println("Server: " + message);
                   

                    // Check if the message is "ERR"
                    if ("ERR".equals(message)) {
                        System.out.println("ERR: An Error has occurred!!!");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("The Error that has occurred is...: " + e.getMessage());
        }
    }
}