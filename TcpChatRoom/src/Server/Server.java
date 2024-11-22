package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

  private ArrayList<ClientConnectionHandler> connections;
  private boolean done;
  private ServerSocket serverSocket;
  private Server server;

  public Server() {
    connections = new ArrayList<>();
    done = false;
  }

  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(9090);
      while (!done) {
        Socket client = serverSocket.accept();
        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(client, server);
        connections.add(clientConnectionHandler);
      }
    } catch (IOException e) {
       shutdown();
    }
  }

  public void broadcast(String message) {
    for (ClientConnectionHandler client : connections) {
      client.sendMessageToClient(message);
    }
  }

  public void shutdown(){
    done = true;
    if(!serverSocket.isClosed()){
      try {
        serverSocket.close();
        for (ClientConnectionHandler client : connections) {
          connections.remove(client);
          client.shutdown();
        }
      } catch (IOException e) {
        //TODO: handle
      }
    }
  }

}
