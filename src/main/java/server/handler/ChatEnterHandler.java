package main.java.server.handler;

import main.java.server.manager.RoomManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    public void process(Socket socket) {
        System.out.println("ChatEnterHandler 실행");

        ServerReceiver thread = new ServerReceiver(socket);
        thread.start();
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch(IOException e) {}
        }

        public void run() {
            String name = "";
            String roomId = "";

            try {
                out.writeUTF("이름을 입력해주세요");
                name = in.readUTF();

                out.writeUTF("참여할 채탕빙의 이름을 입력해주세요");
                roomId = in.readUTF();

                roomManager.enterRoom(roomId, name, out);

                while(in != null) {
                    String msg = in.readUTF();
                    roomManager.sendToRoomClients(roomId, name, msg);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        } // run

    } // ReceiverThread
}

