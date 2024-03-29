package main.java.server.clientHandler;

import main.java.server.dispatch.Dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Dispatcher dispatcher;

    public ClientConnection(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            close();
        }
    }

    @Override
    public void run() {
        while (in != null) {
            try {
                String message = in.readUTF();
                System.out.println("message: " + message);
                String protocol = message.substring(message.indexOf("[") + 1, message.indexOf("]"));
                String content = message.substring(message.indexOf("]") + 1);
                System.out.println("protocol: " + protocol);
                System.out.println("content: " + content);
                dispatcher.run(protocol, content, this);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getIn() {
        return this.in;
    }

    public DataOutputStream getOut() {
        return this.out;
    }

    public Socket getSocket() {
        return this.socket;
    }

}
