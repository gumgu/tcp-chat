package main.java.server.handler;

import main.java.server.clientHandler.ClientConnection;

import java.net.Socket;

public interface MyHandlerAdapter {

    boolean support(Object handler);
    void process(ClientConnection conn, String content);

}
