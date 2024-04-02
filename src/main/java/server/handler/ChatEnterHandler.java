package main.java.server.handler;

import main.java.server.chat.RoomManager;
import main.java.server.connection.ClientConnection;

import java.io.IOException;

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
    public void process(ClientConnection conn, String content) {
        System.out.println("ChatEnterHandler 실행");
        String roomId = "";
        String name = "";

        try {
            conn.printMessage("이름을 입력해주세요");
            name = conn.getIn().readUTF();
            conn.setName(name);

            conn.printMessage("참여할 채탕빙의 이름을 입력해주세요");
            roomId = conn.getIn().readUTF();
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        roomManager.enterRoom(roomId, name, conn);

    }

}

