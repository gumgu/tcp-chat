package main.java.server.handler;

import java.net.Socket;

public class FileDownloadHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process(Socket socket) {

    }
}
