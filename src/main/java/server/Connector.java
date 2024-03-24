package main.java.server;

import main.java.server.dispatch.Dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *             String protocol = in.readUTF();
 *             String[] tokens = protocol.split("/");
 *
 *             String domain = tokens[0];
 *             String action = tokens[1];
 *             String parameter = tokens.length > 2 ? tokens[2] : null;
 */
public class Connector {

    private static final int PORT = 7777;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

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

                //todo protocol 파싱 코드 위치 고민
                this.socket = socket;
                try {
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                } catch(IOException e) {
                    e.printStackTrace();
                }

                String protocol = in.readUTF();

                dispatcher.run(protocol, socket);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
