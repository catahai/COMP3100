import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
            //Initialising the server socket and connection to server
            Socket socket = new Socket("127.0.0.1", 50000);
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Sends HELO request to server
            out.write(("HELO\n").getBytes());
            out.flush();
            System.out.println("Server responds with: " + in.readLine());

            String username = System.getProperty("user.name");
            // Sends AUTH and username to server
            out.write(("AUTH " + username + "\n").getBytes());
            out.flush();
            System.out.println("Server responds with: " + in.readLine());

            // Iterates until it receives the NONE request which will then terminate the loop
            while (true) {
                // Sends REDY request to the server to signal a job dispatch
                out.write(("REDY\n").getBytes());
                out.flush();
                // Reads a line of message from the server
                String response = in.readLine();
                System.out.println("Server responds with: " + response);

                if ("NONE".equals(response)) {
                    break;
                } else if (response.startsWith("JOBN")) {
                    String[] jobMessage = response.split(" ");
                    // Sends GETS Capable message which calls components according to its core memory and disk
                    out.write(("GETS Capable " + jobMessage[4] + " " + jobMessage[5] + " " + jobMessage[6] + "\n").getBytes());
                    out.flush();
                    String message = in.readLine();
                    out.write(("OK\n").getBytes());
                    out.flush();

                    int nRecs = Integer.parseInt(message.split(" ")[1]);

                    String BFServer = "";
                    int bfID = -1;
                    int compareDiff = Integer.MAX_VALUE;
                    
                    String FFServer = "";
                    int ffID = -1;

                    String FCType = "";
                    int fcID = -1;

                    boolean ff = false;
                    boolean fc = false;
                    
                    for (int i = 0; i < nRecs; ++i) {
                        String record = in.readLine(); // Receive each record
                        String[] data = record.split(" ");
                        int cores = Integer.parseInt(data[4]);

                        // Determines the difference between the available CPUs and the number of required CPUs and 
                        // parses the data based on the chosen selected algorithms 
                        int diff = cores - Integer.parseInt(jobMessage[4]);
                        // checks if the server has enough cores to run the and handle the job
                        if (diff >= 0 && diff < compareDiff) {
                            compareDiff = diff;
                            BFServer = data[0];
                            bfID = Integer.parseInt(data[1]);
                        }
                        // Uses the first fit algorithm for the server only if it has enough cores to run it
                        else if (!ff && diff >= 0) {
                            FFServer = data[0];
                            ffID = Integer.parseInt(data[1]);
                            ff = true;
                        }
                        // Uses the first capable algorithm for the server only if it has enough cores to run it
                        else if (!fc) {
                            FCType = data[0];
                            fcID = Integer.parseInt(data[1]);
                            fc = true;
                        }
                    }
                    // Sends OK request to the server
                    out.write(("OK\n").getBytes());
                    out.flush();
                    in.readLine(); 

                    // Uses best fit for the allocated server if available
                    if (!BFServer.equals("")) {
                        out.write(("SCHD " + jobMessage[2] + " " + BFServer + " " + bfID + "\n").getBytes());
                        out.flush();
                        System.out.println("Server: " + in.readLine());
                    // Otherwise we use the first fit algorithm to handle te job handling
                    } 
                    if (!FFServer.equals("")) {
                        out.write(("SCHD " + jobMessage[2] + " " + FFServer + " " + ffID + "\n").getBytes());
                        out.flush();
                        System.out.println("Server: " + in.readLine());
                    // Otherwise we use the first capable algorithm to handle te job handling
                    } 
                    if (!FCType.equals("")) {
                        out.write(("SCHD " + jobMessage[2] + " " + FCType + " " + fcID + "\n").getBytes());
                        out.flush();
                        System.out.println("Server: " + in.readLine());
                    // Otherwise we terminate the process if all jobs are successfully completed or finds a job that is not suitable
                    } else {
                        System.out.println("No job can be completed given this server" + jobMessage[2]);
                        break;
                    }
                }
            }
            // Sends the QUIT message to the server to terminate and quit the process
            out.write(("QUIT\n").getBytes());
            out.flush();
            System.out.println("Server has responded with: " + in.readLine());


        } catch (IOException e) {
            System.err.println("An error has occurred with the server...: " + e.getMessage());
        }
    }
}