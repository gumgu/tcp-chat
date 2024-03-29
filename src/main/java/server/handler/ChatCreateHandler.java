package main.java.server.handler;

import main.java.server.chat.RoomManager;
import main.java.server.clientHandler.ClientConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    public void process(ClientConnection conn, String content) {
        System.out.println("ChatCreateHandler 실행");

        try {
            roomManager.createRoom(content);
            conn.getOut().writeUTF("채팅방 "+ content + "가 생성 되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
