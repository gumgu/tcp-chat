package main.java.server.handler;

import java.net.Socket;

public interface MyHandlerAdapter {

    boolean support(Object handler);
    void process(Socket socket);

}
