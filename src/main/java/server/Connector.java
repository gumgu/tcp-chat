package main.java.server;

import main.java.server.connection.ClientConnection;
import main.java.server.dispatch.Dispatcher;

import java.net.ServerSocket;
import java.net.Socket;

public class Connector {

    private static final int PORT = 7777;
    private Dispatcher dispatcher;

    public static void main(String[] args) {
        new Connector().start();
    }

    public Connector() {
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("서버가 시작되었습니다.");

            while(true) {
                socket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(socket, dispatcher);
                new Thread(clientConnection).start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
