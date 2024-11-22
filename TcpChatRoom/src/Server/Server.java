package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

  private ArrayList<ClientConnectionHandler> connections;
  private boolean done;
  private ServerSocket serverSocket;
  private ExecutorService pool;

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
        pool = Executors.newCachedThreadPool();
        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(client, this);
        connections.add(clientConnectionHandler);
        pool.execute(clientConnectionHandler);
      }
    } catch (Exception e) {
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
    pool.shutdown();
    if(!serverSocket.isClosed()){
      try {
        serverSocket.close();
        for (ClientConnectionHandler client : connections) {
          connections.remove(client);
          client.shutdown();
        }
      } catch (IOException e) {
        //ignore
      }
    }
  }

}
