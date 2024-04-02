package main.java.server.chat;

import main.java.server.connection.ClientConnection;
import main.java.server.connection.protocol.ChatRoomState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 채팅방을 나타내는 클래스입니다.
 * 채팅방은 여러 클라이언트(ClientConenction)이 연결될 수 있으며, 이들 간의 메시지를 중계합니다.
 */
public class Room {

    private final String roomId; // 채팅방의 고유 ID
    private final List<ClientConnection> users; // 채팅방에 연결된 사용자 목록

    /**
     * 채팅방 객체를 생성합니다.
     *
     * @param roomId 채팅방의 고유 ID
     */
    public Room(String roomId) {
        this.roomId = roomId;
        this.users = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * 새로운 클라이언트를 채팅방에 추가합니다.
     *
     * @param clientId 추가할 클라이언트의 ID
     * @param conn 추가할 클라이언트의 연결 객체
     * @return 클라이언트가 성공적으로 추가되면 true, 이미 존재하는 클라이언트라면 false를 반환합니다.
     */
    public boolean addClient(String clientId, ClientConnection conn) {
        boolean clientExist = users.stream()
                .anyMatch(user -> user.getName().equals(clientId));

        if (!clientExist) {
            conn.setState(new ChatRoomState(this));
            users.add(conn);
            return true;
        }

        return false; // 이용자 추가 실패
    }

    /**
     * 채팅방에 있는 모든 클라이언트에게 메시지를 전송합니다.
     *
     * @param writer 메시지를 작성한 클라이언트의 이름
     * @param msg 전송할 메시지 내용
     */
    public void broadcast(String writer, String msg) {
        String message = "[ " + writer + " ] " + msg;

        synchronized (users) {
            for (ClientConnection user : users) {
                user.printMessage(message);
            }
        }
    }

    /**
     * 채팅방의 고유 ID를 반환합니다.
     *
     * @return 채팅방의 고유 ID
     */
    public String getRoomId() {
        return roomId;
    }

}
