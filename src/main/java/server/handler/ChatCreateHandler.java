package main.java.server.handler;

import main.java.server.manager.RoomManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatCreateHandler implements MyHandlerAdapter {

    private RoomManager roomManager;
    private Socket socket; // 이거 꼭 해줘야 하나?

    public ChatCreateHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public boolean support(Object handler) {
        return false;
    }

    @Override
    public void process(Socket socket) {
        System.out.println("ChatCreateHandler 실행");
        this.socket = socket;

        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("채팅방 이름을 입력해주세요");
            String roomId = in.readUTF();

            roomManager.createRoom(roomId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
