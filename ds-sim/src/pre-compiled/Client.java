import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception {

        try (
                // Initialising a server socket and creating a connection to the server.
                Socket socket = new Socket("127.0.0.1", 50000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream out = socket.getOutputStream()) {

            // Sends a "HELO" request to the server side.
            out.write(("HELO\n").getBytes());
            out.flush();

            // Reads the message from the server.
            System.out.println("Server has responded with: " + in.readLine());

            // Gets the username of the current user or uses a random username
            String username = System.getProperty("user.name");

            if (username == null) {
                username = "46350780 Ming Tan";
            }
            // Sends an "AUTH" and a username to the server side.
            out.write(("AUTH " + username + "\n").getBytes());
            out.flush();
            System.out.println("Server has responded with: " + in.readLine());

            // Creates a collection of key-value pairs. in this case we want the type and
            // cores.
            // We will hold the types (String type, int core) and allocate the job schedule
            // based on
            // these values.
            HashMap<String, Integer> servers = null;

            String response = "";

            // While not receiving the "NONE" request made by the server, this means that
            // this loop will iterate until there
            // are no more jobs to schedule or process.
            while (true) {
                // Sends a "REDY" request to the server side
                out.write(("REDY\n").getBytes());
                out.flush();
                // Reads a line of text from the server
                response = in.readLine();
                System.out.println("Server has responded with: " + response);

                // If servers is null, we want to send a "GETS ALL" request to find all
                // available servers and populate the HashMap.
                if (servers == null) {
                    // Determining the largest server type
                    out.write(("GETS All\n").getBytes());
                    out.flush();
                    // Reads a line of text from the server
                    response = in.readLine();
                    System.out.println("Server has responded with: " + response);
                    String[] data = response.split("\\s+");
                    int nRecs = Integer.parseInt(data[1]);
                    out.write(("OK\n").getBytes());
                    out.flush();
                    servers = new HashMap<>();
                    // server receives DATA nRecs () and the recSize ().
                    for (int i = 0; i < nRecs; i++) {
                        // Reads a line of text from the server
                        response = in.readLine();
                        System.out.println("Server has responded with: " + response);
                        data = response.split("\\s+");
                        // if it contains at least these 5 server types or more, "type", "limit",
                        // "hourlyRate", "cores", "memory" and "disk".
                        if (data.length > 4) {
                            String type = data[0];
                            int cores = Integer.parseInt(data[4]);

                            servers.put(type, cores);
                        }
                    }

                    out.write(("OK\n").getBytes());
                    out.flush();
                    System.out.println("Server has responded with: " + in.readLine()); // Receive .
                }

                if (response.startsWith("JOBN")) {
                    String jobMessage = response;
                    // Finding the largest possible server type by the number of cores.
                    String largestType = Collections.max(servers.entrySet(), HashMap.Entry.comparingByValue()).getKey();
                    // Schedules the job
                    String[] jobScheduler = jobMessage.split("\\s+");
                    out.write(("SCHD " + jobScheduler[2] + " " + largestType + " 0\n").getBytes());
                    out.flush();
                    // Reads a line of text from the server
                    response = in.readLine();
                    System.out.println("Server has responded with: " + response);
                }
                // If the response is "JCPL". We know that the job scheduler has finished and we
                // print this message to confirm.
                else if (response.startsWith("JCPL")) {
                    System.out.println("Job has been scheduled!!!!");
                }
                // Once we reach the "NONE" request. There are no more jobs to be scheduled.
                if ("NONE".equals(response)) {
                    break;
                }

            }
            // Sends a QUIT message to the server to end the connection.
            out.write(("QUIT\n").getBytes());
            out.flush();
            System.out.println("Server has responded with: " + in.readLine());

            // Check if the message is "ERR", if this is the case then there is an error
            // with the client.
            if ("ERR".equals(response)) {
                System.out.println("An error has occured with the server...");
            }
            // Error handling with the client.
        } catch (IOException e) {
            System.err.println("An error has occured with the server...: " + e.getMessage());
        }
    }
}
