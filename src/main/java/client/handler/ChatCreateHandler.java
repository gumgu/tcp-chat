package main.java.client.handler;

import java.util.Scanner;

public class ChatCreateHandler implements Handler {
    @Override
    public String getContent(Scanner scanner) {
        System.out.println("채팅방명을 입력하세요:");
        return scanner.nextLine();
    }
}
