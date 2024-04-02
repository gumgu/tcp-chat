package main.java.client.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 서버와 통신 시 특정 도메인에 대한 요청을 처리하는 인터페이스.
 * 각 도메인별로 구현할 수 있으며, 클라이언트의 요청을 받아 서버에 전달하고 결과를 처리합니다.
 */
public interface Handler {

    /**
     * 사용자로부터 초기 요청 시 첨부할 데이터(content)를 입력받습니다.
     * 예를 들어, 채팅방 생성 시 채팅방명을 사용자로부터 입력받아 반환합니다.
     *
     * @param scanner 사용자 입력을 받기 위한 Scanner 객체
     * @return content 서버에 전송할 요청 데이터
     */
    String getContent(Scanner scanner);

    /**
     * 서버와 지속적 통신이 필요한 경우 이 메서드를 사용해 지속적인 데이터 송수신을 처리합니다.
     * 기본 구현은 빈 구현으로 구성, 필요한 경우 Handler에서 오버라이드해 구현할 수 있습니다.
     *
     * @param socket 서버와 통신하기 위한 Socket 객체
     * @throws IOException 네트워크 오류 등으로 통신에 실패한 경우 발생하는 예외
     */
    default void maintainConnection(Socket socket) throws IOException {
        // 기본 구현은 빈 구현. 특정 커멘드에서 오버라이드 해야합니다.
    }

}
