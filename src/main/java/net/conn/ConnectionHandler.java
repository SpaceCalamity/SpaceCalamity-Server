package net.conn;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionHandler
{
  private static ConnectionHandler singleton = null;
  public static ConnectionHandler get() {
    if (ConnectionHandler.singleton == null) {
      ConnectionHandler.singleton = new ConnectionHandler();
    }
    return ConnectionHandler.singleton;
  }

  ConcurrentLinkedQueue<String> queue;
  ConcurrentHashMap<String, Client> userThreads;
  private ConnectionHandler() {
    queue = new ConcurrentLinkedQueue<String>();
    userThreads = new ConcurrentHashMap<String, Client>();
  }
  public void listen(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.printf("Listening on port <%d>\n", port);
      while (true) {
        Socket socket = serverSocket.accept();
        Client newUserThread = new Client(socket, this);
        newUserThread.start();
      }
    } catch (Exception e) {
      System.out.printf("Server Error: ", e.getMessage(), "\n");
      e.printStackTrace();
      System.exit(-1);
    }
  }
  public void sendMessage(String userKey, String message) {
    Client userThread = userThreads.get(userKey);
    if (userThread == null) {
      System.out.printf("User thread key not found: ", userKey, "\n");
      return;
    }
    userThread.sendMessage(userKey);
  }
}
