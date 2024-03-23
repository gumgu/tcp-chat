package main.java.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class TcpIpMultichatClient {

    public static void main(String[] args) {
        String name;
        String roomId;
        Scanner scanner = new Scanner(System.in);

        System.out.println("닉네임을 입력해주세요.");
        name = scanner.nextLine();

        System.out.println("방 번호를 입력해주세요.");
        roomId = scanner.nextLine();

        try {
            String serverIp = "127.0.0.1";
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 7777);
            System.out.println("서버에 연결되었습니다.");

            Thread sender = new Thread(new ClientSender(socket, name, roomId));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        } catch(ConnectException ce) {
            ce.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } // main

    static class ClientSender extends Thread {
        Socket socket;
        DataOutputStream out;
        String joinMessage;
        String name;

        ClientSender(Socket socket, String name, String roomId) {
            this.socket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
                this.joinMessage = getJoinMessage(name, roomId);
            } catch (Exception e) {
            }
        }

        private String getJoinMessage(String name, String roomId) {
            return "JOIN:" + roomId + ":" + name;
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if (out != null) {
                    out.writeUTF(joinMessage);
                }

                while (out != null) {
                    out.writeUTF("[" + name + "]" + scanner.nextLine());
                }
            } catch (IOException e) {
            }
        } // run()
    } // ClientSender

    static class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream in;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
            }
        }

        public void run() {
            while (in != null) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException e) {
                }
            }
        } // run
    } // ClientReceiver
} // class