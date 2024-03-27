package main.java.server.manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomManager {

    // clients: 쓰레드
    // java의 POJO 클래스???
    private final Map<String, Map<String, DataOutputStream>> rooms
            = Collections.synchronizedMap(new HashMap<>());

    public Map<String, DataOutputStream> getRoom(String roomId) {
        return rooms.get(roomId);
    }

    // 여기에 방 이름을 조회하는 메서드를 추가합니다.
    public Set<String> getRoomNames() {
        return rooms.keySet();
    }

    public void createRoom(String roomId) {
        if (!rooms.containsKey(roomId)) {
            rooms.put(roomId, Collections.synchronizedMap(new HashMap<>()));
            System.out.println("rooms: " + rooms.toString());
        }
    }

    public void enterRoom(String roomId, String userName, DataOutputStream out) {
        if (rooms.containsKey(roomId)) {
            rooms.get(roomId).put(userName, out);
        }
    }

    public void sendToRoomClients(String roomId, String writer, String msg) {
        Map<String, DataOutputStream> roomClients = rooms.get(roomId);

        if(roomClients != null) {
            for (DataOutputStream out : roomClients.values()) {
                try {
                    out.writeUTF("[" + writer + "] " + msg);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
