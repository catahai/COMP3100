import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try {
            // Creates a Socket to connect to the server
            int port = 50000;
            Socket s = new Socket("localhost", port);

            // Creates a BufferedReader, and reads data from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // Creates a DataOutputStream, and sends data to the server
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            // Sends "HELO" to the server
            out.write(("HELO\n").getBytes());
            System.out.println("Client says 'HELO'");
            
            // Reads a line of text from the server
            String word = in.readLine();
            System.out.println("Server responds with: " + word);

            if (word.equals("G'DAY")) {
                // Send "BYE" to the server
                out.write(("BYE\n").getBytes());

                // Reads another line of text from the server
                word = in.readLine();
                System.out.println("Server responds with: " + word);

                if (word.equals("BYE")) {
                    // Closes the connection with the server once we get a response "BYE"
                    s.close();
                }
                
             }
        } catch (IOException e) {
             System.err.println("Failed to connect..." + e.getMessage());
    }
}
}