package main.java.server.handler;

public class FileDownloadHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {

    }
}
