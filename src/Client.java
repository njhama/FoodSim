import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the program!");
        System.out.print("Enter the server hostname: ");
        String hostname = scanner.nextLine();
        System.out.print("Enter the server port: ");
        int port = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        try {
            socket = new Socket(hostname, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            // Assume the server sends the number of remaining drivers needed as the first message
            int remainingDrivers = (int) input.readObject();
            System.out.println(remainingDrivers + " more drivers are needed before the service can begin.");

            // Wait for the dispatch signal from the server (assuming it's a string message "DISPATCH")
            String dispatchSignal = (String) input.readObject();
            if ("DISPATCH".equals(dispatchSignal)) {
                // Handle the order dispatch and delivery
                // ...
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to server. :(");
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



//there is no definite sequence of read and writes
//should i have a unique identifier forteh object sends to handle?
