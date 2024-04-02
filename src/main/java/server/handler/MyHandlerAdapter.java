package main.java.server.handler;

import main.java.server.connection.ClientConnection;

public interface MyHandlerAdapter {

    boolean support(Object handler);
    void process(ClientConnection conn, String content);

}
