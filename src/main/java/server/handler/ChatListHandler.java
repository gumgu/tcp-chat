package main.java.server.handler;

import main.java.server.manager.RoomManager;

public class ChatListHandler implements MyHandlerAdapter {

    private RoomManager roomManager;

    public ChatListHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {
        System.out.println("ChatListHandler 실행");
    }
}

