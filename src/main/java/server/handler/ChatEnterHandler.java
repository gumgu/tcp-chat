package main.java.server.handler;

import main.java.server.manager.RoomManager;

public class ChatEnterHandler implements MyHandlerAdapter {

    private RoomManager roomManager;

    public ChatEnterHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {
        System.out.println("ChatEnterHandler 실행");
    }
}

