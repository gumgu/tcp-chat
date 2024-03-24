package main.java.server.manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    private final Map<String, Map<String, DataOutputStream>> rooms
            = Collections.synchronizedMap(new HashMap<>());

    public Map<String, DataOutputStream> getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public void createRoom(String roomId) {
        if (!rooms.containsKey(roomId)) {
            rooms.put(roomId, Collections.synchronizedMap(new HashMap<>()));
        }
    }

    public void enterRoom(String roomId, String userName, DataOutputStream out) {
        if (rooms.containsKey(roomId)) {
            rooms.get(roomId).put(userName, out);
        }
    }

    public void sendToRoomClients(String roomId, String msg) {
        Map<String, DataOutputStream> roomClients = rooms.get(roomId);

        if(roomClients != null) {
            for (DataOutputStream out : roomClients.values()) {
                try {
                    out.writeUTF(msg);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
