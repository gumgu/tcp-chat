package main.java.server.handler;

import main.java.server.clientHandler.ClientConnection;

import java.net.Socket;

public class FileUploadHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process(ClientConnection conn, String content) {


    }
}
