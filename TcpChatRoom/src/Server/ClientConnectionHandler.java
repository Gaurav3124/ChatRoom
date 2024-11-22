package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

  private Socket client;
  private Server server;
  private BufferedReader in; //bufferreader is kinda wrapper class around InputStreamReader with a 8KB buffer
  private PrintWriter out; // these can be used to read and write texts only
  private String nickname;

  public ClientConnectionHandler(Socket client, Server server) {
    this.client = client;
    this.server = server;
  }

  @Override
  public void run() {
    try {
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out = new PrintWriter(client.getOutputStream(), true);
      //with autoFlush true, we can send msg to client with
      // out.println("hello")

      out.println("Please enter a valid nickname : ");
      nickname = in.readLine();
//      if (nickname == null || nickname.isEmpty()) {
//        out.println("Please enter valid nickname : ");
//        nickname = in.readLine();
//      }
      System.out.println(nickname + " connected");
      server.broadcast(nickname + " joined the chat!");

      String message;

      while ((message = in.readLine()) != null && !message.isEmpty()) {
        if (message.startsWith("/nick")) {
          String[] splits = message.split(" ", 2);
          if (splits.length == 2) {
            server.broadcast(nickname + " changed their nickname to -> " + splits[1]);
            nickname = splits[1];
          } else {
            out.println("no nickname provided.");
          }
        } else if (message.startsWith("/leave")) {
          server.broadcast(nickname + " left the chat!");
          System.out.println(nickname + " left``````");
          shutdown();
          break;
        } else {
          server.broadcast(nickname + " : " + message);
        }
      }

    } catch (IOException e) {
      shutdown();
      throw new RuntimeException("IOException while connecting to client ", e);
    }
  }

  public void sendMessageToClient(String message) {
    out.println(message);
  }

  public void shutdown() {
    try {
      in.close();
      out.close();
      client.close();
    } catch (IOException e) {
      //ignore
    }
  }
}
