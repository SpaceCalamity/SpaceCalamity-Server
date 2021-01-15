package net.conn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

class Client extends Thread {
  private final static int MAX_LOGIN_ATTEMPTS = 5;
  private final static int LOGIN_DELAY = 5000;
  private static ConnectionHandler server;
  
  private Socket socket;
  private PrintWriter writer;

  public Client(Socket socket, ConnectionHandler server) {
    if (server == null) {
      Client.server = server;
    }
    this.socket = socket;
  }

  public void run() {
    String userKey = null;
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
      
      System.out.printf("Received connection from: ", socket.getRemoteSocketAddress().toString(), "\n");

      String clientMessage;

      int failedTries = 0;
      while (userKey == null) {
        clientMessage = reader.readLine();
        Thread.sleep(Client.LOGIN_DELAY);
        userKey = "";
        // login or register
        // add userkey to socket.
      }

      while (!(clientMessage = reader.readLine()).equals("!quit")) {
        // parse clientMessage
      }
      socket.close();

    } catch (Exception e) {
      System.out.printf("Connection failure from: ", socket.getRemoteSocketAddress().toString(), " -> ", e.getMessage(), "\n");
      e.printStackTrace();
    }
    if (userKey != null) {
      server.userThreads.remove(userKey);
      System.out.printf("User thread with key ", userKey, " has disconnected\n");
    }
  }

  void sendMessage(String message) {
    writer.println(message);
  }
}
