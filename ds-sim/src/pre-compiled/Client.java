import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 50000;

        try (
            // Creates a new object socket 
            Socket socket = new Socket(address, port);
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Sends a "HELO" request to the server side
            out.write(("HELO\n").getBytes());
            out.flush();

            // Reads the message from the server
            String message = in.readLine();
            System.out.println("Server responds with: " + message);

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
                System.out.println("Server responds with: " + message);

                if ("OK".equals(message)) {
                    while (true) {
                        // Sends a "REDY" request to the server side
                        out.write(("REDY\n").getBytes());
                        out.flush();

                        // Reads another line of text from the server side
                        message = in.readLine();
                        System.out.println("Server responds with: " + message);

                        //Checks if the message is "NONE"
                        if ("NONE".equals(message)) {
                            break;
                            // Checks if the message is "JOBN"
                        } else if ("JOBN".startsWith(message)) {
                            String jobMessage = message;
                            // Determining the largest server type
                            out.write(("GETS All\n").getBytes());
                            out.flush();


                            message = in.readLine();
                            System.out.println("Server responds with: " );

                            String[] data = message.split("\\s+");
                            int nRecs = Integer.parseInt(data[1]);
                            int type = Integer.parseInt(data[2]);
                            out.write(("OK\n").getBytes());
                            out.flush();

                            String largestType = "";
                            int largestCount = 0;

                            // Loops through the number of servers
                            for (int i = 0; i < nRecs; i++) {
                                message = in.readLine();
                                System.out.println("Server responds with: " + message);

                                data = message.split("\\s+");
                                String serverType = data[0];
                                int serverCount = Integer.parseInt(data[1]);

                                // Checks the servertype and the count 
                                if (serverCount > largestCount) {
                                    largestType = serverType;
                                    largestCount = serverCount;
                                }
                            }

                            out.write(("OK\n").getBytes());
                            out.flush();

                            // Schedules a job from the server
                            String[] jobSchedule = jobMessage.split("\\s+");
                            out.write(("SCHD " + jobSchedule[2] + " " + largestType + " 0\n").getBytes());
                            out.flush();
                            message = in.readLine();
                            System.out.println("Server responds with: " + message);
                        }
                    }

                    // Sends a "QUIT" message to terminate the job dispatching simulation
                    out.write(("QUIT\n").getBytes());
                    out.flush();

                    // Reads a line of text from the server
                    message = in.readLine();
                    System.out.println("Server responds with: " + message);

                    // Check if the message is "ERR"
                    if ("ERR".equals(message)) {
                        System.out.println("An error has occurred...");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error has occured...: " + e.getMessage());
        }
    }
}
