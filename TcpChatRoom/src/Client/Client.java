package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean done;
    public Client(){
        done = false;
    }

    public static void main(String[] args){
        System.out.println("Connecting client...");
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("127.0.0.1", 9090);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            InputConnectionHandler connectionHandler = new InputConnectionHandler();
            Thread thread = new Thread(connectionHandler);
            thread.start();

            //printing messages from the server
            String serverMessage;
            while((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch(Exception e) {
            shutdown();
        }
    }

    public void shutdown(){
        try {
            done = true;
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            //ignore
        }
    }

    public class InputConnectionHandler implements Runnable{
        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if(message.equals("/leave")) {
                        out.println(message);
                        inReader.close();
                        shutdown();
                    } else {
                        out.println(message);
                    }
                }
            } catch (Exception e){
                shutdown();
            }
        }
    }
}
