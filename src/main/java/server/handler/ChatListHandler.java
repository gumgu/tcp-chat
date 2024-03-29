package main.java.server.handler;

import main.java.server.chat.RoomManager;
import main.java.server.clientHandler.ClientConnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
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
    public void process(ClientConnection conn, String content) {
        System.out.println("ChatListHandler 실행");

        try {
            List<String> roomIdList = roomManager.getRoomIdList();
            System.out.println("romNames:" + roomIdList);

            String roomListStr = String.join(", ", roomIdList);
            System.out.println("roomsListStr:" + roomListStr);


            DataOutputStream out = new DataOutputStream(conn.getOut());
            out.writeUTF(roomListStr);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}

