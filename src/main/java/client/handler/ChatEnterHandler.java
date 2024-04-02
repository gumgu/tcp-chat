package main.java.client.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class ChatEnterHandler implements Handler {

    @Override
    public String getContent(Scanner scanner) {
        return null;
    }

    @Override
    public void maintainConnection(Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        System.out.println("메시지를 입력하세요 ('exit' 입력시 채팅방에서 나갑니다):");
        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            out.writeUTF(message);
        }

        Thread sender = new Thread(new ClientSender(socket));
        Thread receiver = new Thread(new ClientReceiver(socket));

        sender.start();
        receiver.start();

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
}

