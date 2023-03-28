package Stage1;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
            // Initialising a server socket and creating a connection to the server.
            Socket socket = new Socket("127.0.0.1", 50000);
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Sends a "HELO" request to the server side
            out.write(("HELO\n").getBytes());
            out.flush();

            // Reads the message from the server
            String response = in.readLine();
            System.out.println("Server responds with: " + response);

            if ("OK".equals(response)) {
                // Gets the username of the current user or uses a random username
                String username = System.getProperty("user.name");
                if (username == null) {
                    username = "ming";
                }

                // Sends an "AUTH" and a username to the server side
                out.write(("AUTH " + username + "\n").getBytes());
                out.flush();

                // Reads another line of text from the server side
                response = in.readLine();
                System.out.println("Server responds with: " + response);

                if ("OK".equals(response)) {
                    while (true) {
                        // Sends a "REDY" request to the server side
                        out.write(("REDY\n").getBytes());
                        out.flush();

                        // Reads another line of text from the server side
                        response = in.readLine();
                        System.out.println("Server responds with:: " + response);

                        if (response.equals("NONE")) {
                            break;
                        } else if (response.startsWith("JOBN")) {
                            String jobMessage = in.readLine();
                            // Determining the largest server type
                            out.write(("GETS All\n").getBytes());
                            out.flush();


                            response = in.readLine();
                            System.out.println("Server responds with: " );
                            String[] data = response.split("\\s+");
                            int nRecs = Integer.parseInt(data[1]);
                            out.write(("OK\n").getBytes());
                            out.flush();

                            String largestType = "";
                            int largestCount = 0;

                            for (int i = 0; i < nRecs; i++) {
                                response = in.readLine();
                                System.out.println("Server responds with: " + response);

                                data = response.split("\\s+");
                                String serverType = data[0];
                                int serverCount = Integer.parseInt(data[1]);

                                if (serverCount > largestCount) {
                                    largestType = serverType;
                                    largestCount = serverCount;
                                }
                            }

                            out.write(("OK\n").getBytes());
                            out.flush();

                            // Schedule the job
                            String[] jobScheduler = jobMessage.split("\\s+");
                            out.write(("SCHD " + jobScheduler[2] + " " + largestType + " 0\n").getBytes());
                            out.flush();
                            response = in.readLine();
                            System.out.println("Server responds with: " + response);
                        }
                    }

                    // Sends a QUIT message to the server to end the connection.
                    out.write(("QUIT\n").getBytes());
                    out.flush();

                    // Reads a line of text from the server
                    response = in.readLine();
                    System.out.println("Server responds with: " + response);

                    // Check if the response is "ERR"
                    if ("ERR".equals(response)) {
                        System.out.println("An error has occurred with the server...");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error has occured with the server...: " + e.getMessage());
        }
    }
}
