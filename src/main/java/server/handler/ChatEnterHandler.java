package main.java.server.handler;

public class ChatEnterHandler implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {
        System.out.println("ChatEnterHandler 실행");
    }
}

