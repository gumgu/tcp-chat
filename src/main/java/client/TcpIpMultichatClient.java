package main.java.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class TcpIpMultichatClient {

    public static void main(String[] args) {
        String protocol;
        Scanner scanner = new Scanner(System.in);

        System.out.println("서버에 접근하려면 다음 프로토콜을 사용하세요:");
        System.out.println("파일 목록 조회: 'FILE/LIST'");
        System.out.println("파일 업로드: 'FILE/UPLOAD'");
        System.out.println("파일 다운로드: 'FILE/DOWNLOAD'");
        System.out.println("채팅 목록 조회: 'CHAT/LIST'");
        System.out.println("채팅방 생성: 'CHAT/CREATE'");
        System.out.println("채팅방 입장: 'CHAT/ENTER'");
        protocol = scanner.nextLine();


        try {
            String serverIp = "127.0.0.1";
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 7777);
            System.out.println("서버에 연결되었습니다.");

            Thread sender = new Thread(new ClientSender(socket, protocol));
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
        String protocol;
        String name;

        ClientSender(Socket socket, String protocol) {
            this.socket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
                this.protocol = protocol;
            } catch (Exception e) {
            }
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if (out != null) {
                    out.writeUTF(protocol);
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