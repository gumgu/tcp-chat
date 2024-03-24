package main.java.server.handler;

public class ChatListHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {
        System.out.println("ChatListHandler 실행");
    }
}

