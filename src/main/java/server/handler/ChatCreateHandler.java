package main.java.server.handler;

import main.java.server.manager.RoomManager;

public class ChatCreateHandler implements MyHandlerAdapter {

    private RoomManager roomManager;

    public ChatCreateHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process() {

    }
}
