package main.java.server.chat;

import main.java.server.connection.ClientConnection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 채팅방을 관리하는 클래스입니다.
 * 채팅방의 생성, 조회 및 사용자의 채팅방 입장 처리를 담당합니다.
 */
public class RoomManager {

    private final List<Room> rooms; // 관리 중인 채팅방 목록

    /**
     * RoomManager 객체를 생성하고 초기화합니다.
     */
    public RoomManager() {
        System.out.println("roomManager 생성");
        rooms = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * 새로운 채팅방을 생성합니다.
     *
     * @param roomId 생성할 채팅방의 고유 ID
     * @return 채팅방이 성공적으로 생성되었으면 true, 이미 같은 ID의 채팅방이 존재한다면 false를 반환합니다.
     */
    public boolean createRoom(String roomId) {
        if (findByRoomId(roomId) == null) {
            rooms.add(new Room(roomId));
            return true;
        }

        return false; // 방 생성 실패
    }

    /**
     * 사용자를 지정된 채팅방에 입장시킵니다.
     * 채팅방이 존재하지 않는 경우 사용자는 입장할 수 없습니다.
     *
     * @param roomId 입장할 채팅방의 ID
     * @param clientId 입장하는 사용자의 ID
     * @param conn 사용자와의 연결 객체
     */
    public void enterRoom(String roomId, String clientId, ClientConnection conn) {
        Room room = findByRoomId(roomId);
        if (room != null) {
            room.addClient(clientId, conn);
        }
    }

    /**
     * 관리 중인 모든 채팅방의 ID 목록을 반환합니다.
     *
     * @return 채팅방 ID 목록
     */
    public List<String> getRoomIdList() {
        return rooms.stream()
                .map(Room::getRoomId)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 ID에 해당하는 채팅방을 찾습니다.
     *
     * @param roomId 찾고자 하는 채팅방의 ID
     * @return 찾은 채팅방 객체. 존재하지 않을 경우 null을 반환합니다.
     */
    private Room findByRoomId(String roomId) {
        return rooms.stream()
                .filter(room -> room.getRoomId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

}
