package main.java.client;

import main.java.client.handler.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TcpIpMultichatClient {

    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, Handler> handlerMap = new HashMap<>();
    private static Socket socket;
    private static String SERVER_IP = "127.0.0.1";
    private static int PORT = 7777;

    public static void main(String[] args) {
        initHandler();
        String domain;
        String content;

        try {
            socket = new Socket(SERVER_IP, PORT);
            System.out.println("서버에 연결되었습니다.");

            new ClientSender(socket).start();
            new ClientReceiver(socket).start();
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        while(true) {
            domain = promptDomain();
            if ("exit".equalsIgnoreCase(domain)) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            Handler handler = handlerMap.getOrDefault(domain.toUpperCase(), null);
            if (handler != null) {
                content = handler.getContent(scanner);
                connectToServer(domain, content);
            }
        }

    }

    private static void connectToServer(String domain, String content) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("[" + domain + "]" + content); // 최초 컨텐츠 전송

            Handler handler = handlerMap.getOrDefault(domain.toUpperCase(), null);
            if (handler != null) {
                handler.maintainConnection(socket); // 추가 실행 로직 처리
            }
        } catch(ConnectException ce) {
            ce.printStackTrace();
            System.out.println("서버에 연결할 수 없습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void initHandler() {
        handlerMap.put("FILE/LIST", new FileListHandler());
        handlerMap.put("FILE/UPLOAD", new FileUploadHandler());
        handlerMap.put("FILE/DOWNLOAD", new FileDownloadHandler());
        handlerMap.put("CHAT/LIST", new ChatListHandler());
        handlerMap.put("CHAT/CREATE", new ChatCreateHandler());
        handlerMap.put("CHAT/ENTER", new ChatEnterHandler());
    }

    private static String promptDomain() {
        System.out.println("서버에 접근하려면 다음 도메인을 입력하세요. ('exit'을 입력하면 종료됩니다.):");
        System.out.println("파일 목록 조회: 'FILE/LIST'");
        System.out.println("파일 업로드: 'FILE/UPLOAD'");
        System.out.println("파일 다운로드: 'FILE/DOWNLOAD'");
        System.out.println("채팅방 목록 조회: 'CHAT/LIST'");
        System.out.println("채팅방 생성: 'CHAT/CREATE'");
        System.out.println("채팅방 입장: 'CHAT/ENTER'");
        return scanner.nextLine();
    }

    static class ClientSender extends Thread {
        Socket socket;
        DataOutputStream out;

        ClientSender(Socket socket) {
            this.socket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
            }
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {

                while (out != null && socket.isClosed()) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    }
                    out.writeUTF(message);
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
                    break;
                }
            }
        } // run
    } // ClientReceiver

} // class