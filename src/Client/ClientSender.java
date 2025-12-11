package Client;

import java.io.*;
import java.net.Socket;

import Common.MessageType;

// 이 클래스 구조는 아래 링크에서 참고함
// https://azamman.tistory.com/200


/**
 * 클라이언트기 서버로 메시지를 보내는 전송 클래스
 */
public class ClientSender {
    // DataOutputStream으로 메시지를 서버로 전송
    private DataOutputStream dataOutStream;
    /**
     * 생성자
     * @param socket 소켓
     * @throws IOException 예외
     */
    public ClientSender(Socket socket) throws IOException {
        // socket.getOutputStream()
        // 연결된 소켓을 통해 데이터를 클라이언트에게 전송하기 위한 OutputStream 객체를 반환하는 메서드
        this.dataOutStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * 클라이언트가 서버로 메시지를 전송하는 메소드
     * 
     * @param _mt 메시지 타입
     * @param _msg 메시지
     */
    public void sendMSG(MessageType _mt, String _msg) {
        try {
            // 메시지 전송
            dataOutStream.writeUTF(_msg);
        }
        catch (IOException e) {
            System.err.println("[Error] 메시지 전송 실패 : " + _mt.getType());
        }
    }
}
