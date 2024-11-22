import Server.Server;

public class Main {
  public static void main(String[] args) {
    System.out.println("Starting Server...");
    Server server = new Server();
    server.run();
  }
}