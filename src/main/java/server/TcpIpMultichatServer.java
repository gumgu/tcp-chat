package _ch16;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TcpIpMultichatServer {

    Map<String, Map<String, DataOutputStream>> rooms;

    TcpIpMultichatServer() {
        rooms = Collections.synchronizedMap(new HashMap<String, Map<String, DataOutputStream>>());
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버가 시작되었습니다.");

            while(true) {
                socket = serverSocket.accept();
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void sendToRoomClients(String roomId, String msg) {
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
    }// sendToAll

    public static void main(String args[]) {
        new TcpIpMultichatServer().start();
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
                String joinMessage = in.readUTF();
                String[] split = joinMessage.split(":");
                roomId = split[1];
                name = split[2];

                synchronized (rooms) {
                    if (!rooms.containsKey(roomId)) {
                        rooms.put(roomId, Collections.synchronizedMap(new HashMap<>()));
                        System.out.println("새 방 " + roomId + "가 생성되었습니다.");
                    }
                    rooms.get(roomId).put(name, out);
                }

                System.out.println("방 [" + roomId + "]의 사용자 수는 " + rooms.get(roomId).size() + "입니다.");

                while(in != null) {
                    String msg = in.readUTF();
                    Map<String, DataOutputStream> roomClients = rooms.get(roomId);
                    for(DataOutputStream out : roomClients.values()) {
                        out.writeUTF(msg);
                    }
                }
            } catch(IOException e) {
                // Ignore
            } finally {
                if (rooms.containsKey(roomId)) {
                    rooms.get(roomId).remove(name);
                    if (rooms.get(roomId).isEmpty()) {
                        rooms.remove(roomId); // 방이 비었다면 방도 제거
                    }
                }
                // 같은 방의 클라이언트들에게 사용자가 나갔음을 알림
                sendToRoomClients(roomId, "#" + name + "님이 나가셨습니다.");
                System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속을 종료하셨습니다.");
                System.out.println("현재 [" + roomId + "] 방의 사용자 수는 " + rooms.get(roomId).size() + "입니다.");
            } // try
        } // run

    } // ReceiverThread
} // class
