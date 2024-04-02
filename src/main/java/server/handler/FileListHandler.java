package main.java.server.handler;

import main.java.server.connection.ClientConnection;

public class FileListHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process(ClientConnection conn, String content) {


    }
}
