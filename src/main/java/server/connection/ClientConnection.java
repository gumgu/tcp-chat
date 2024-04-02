package main.java.server.connection;

import main.java.server.connection.protocol.ClientState;
import main.java.server.connection.protocol.DefaultState;
import main.java.server.dispatch.Dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Dispatcher dispatcher;
    private ClientState state;
    private String name;

    public ClientConnection(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
        initState();
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            close();
        }
    }

    private void initState() {
        state = new DefaultState(dispatcher);
    }

    @Override
    public void run() {
        while (in != null) {
            try {
                String message = in.readUTF();
                if (state != null) {
                    state.handle(message, this);
                }
            }
            catch(SocketException e) {
                System.out.println("클라이언트 연결이 끊어졌습니다: " + e.getMessage());
                break; // 루프 종료
            }
            catch(IOException e) {
                e.printStackTrace();
                break; // 루프 종료
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

    public void printMessage(String message) {
        try {
            out.writeUTF(message);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setState(ClientState state) {
        this.state = state;
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
