package main.java.server.handler;

import main.java.server.manager.RoomManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class ChatListHandler implements MyHandlerAdapter {

    private RoomManager roomManager;
    private Socket socket;

    public ChatListHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process(Socket socket) {
        System.out.println("ChatListHandler 실행");
        this.socket = socket;

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Set<String> roomNames = roomManager.getRoomNames();
            System.out.println("romNames:" + roomNames);
            String roomsListStr = String.join(", ", roomNames );
            System.out.println("roomsListStr:" + roomsListStr);

            out.writeUTF(roomsListStr);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}

