package Client;

import java.net.*;
import java.util.ArrayList;

import Common.MessageParser;

import java.io.*;

/**
 * 클라이언트 메시지 처리 클래스
 */
class MessageListener extends Thread {
    // 소켓
    private Socket socket;
    // 메뉴 콜백 인테베이스
    private IMessageCallback mc;
    // 메시지를 토큰으로 분리 객체 생성
    private MessageParser mp = new MessageParser();
    // 메시지 수신 루프 On/Off
    private boolean running = true;
    // 입출력 담당
    private InputStream inputStream;
    private DataInputStream dataInputStream;

    /**
     * MessageListener 생성자
     * 
     * @param s  소켓
     * @param mc 메뉴 콜백 인터베이스
     */
    public MessageListener(Socket s, IMessageCallback mc) {
        this.socket = s;
        this.mc = mc;

        // 전송 준비
        try {
            inputStream = this.socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
        }

        catch (IOException e) {
            mc.onMessageError("서버 입력 생성 실패", e);
        }
    }

    /**
     * 메시지 수신 루프 메소드
     */
    @Override
    public void run() {
        // 메시지 수신 반복
        while (running) {
            try {
                // 서버에서 보낸 메시지를 읽음
                while (true) {
                    String msg = dataInputStream.readUTF();
                    if (msg == null || msg.trim().equals("")) continue;
                    // 매시지 처리
                    // msgBasedService(msg);
                }
            
            } 
            // 예외 처리
            catch (Exception e) {
                // mc.onMessageError("메시지 수신 루프중 예외 발생", e);
                running = false;
                break;
            }
            // finally 블록은 예외가 발생하든 안 하든 반드시 실행
            finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                }
                catch (IOException e) { }
            }
        }
    }

    // 메시지 타입에 따라 처리
   /*  public void msgBasedService(String _msg) {
        MessageType tm = mp.getType(_msg);

        // 메시지 타입 판단
        switch (tm) {
            case ID_RES : 
                // id 메시지 처리
                processIDRes(_msg); 
                break;
            case WELCOME : 
                // 웰컴 메시지 처리
                processWelcomeRes(_msg); 
                break;
            case LOGIN_RES :
                // 로그인 메시지 처리 
                processLoginRes(_msg);
                break;
            case JOIN_RES :
                // 회원 가입 메시지 처리
                processJoinRes(_msg);
                break;
            case MSG_RES :
                // 메시지 검사 메시지 처리
                processMsgRes(_msg);
                break;
            case BROADCAST :
                // 전체 메시지 처리
                processBroadcastRes(_msg);
                break;
            case WHISPER :
                // 귓속말 메시지 처리
                processWhisperRes(_msg);
                break;
            case USERLIST :
                // 사용자 리스트 메시지 처리
                processUserListRes(_msg);
                break;
            case EXIT_RES :
                // 종료 메시지 처리
                processExitRes(_msg);
                break;
            default :
                // 메뉴 콜백 인테페이스 호출
                mc.onMessageError(tm + "는 처리할 수 없는 메시지 타입입니다.", null);
                break;
        }
    } */
    /**
     *사용자 id 처리하는 메소드
     * @param _msg 메시지
     */
    private void processIDRes(String _msg) {
        // id 추출
        String id = mp.findID(_msg);
        // 처리 결과 추출
        String ck = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        // mc.onIdRes(id, ck);
    }


    /**
     * 로그인 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param _msg 메시지
     */
    private void processLoginRes(String _msg) {
        // id 추출
        String id = mp.findID(_msg);
        // 처리 결과 추출
        String res = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        mc.onLoginRes(id, res);
    }

    /**
     * 회원가입 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param _msg 메시지
     */
    private void processJoinRes(String _msg) {
        // 처리 결과 추출
        String res = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        // mc.onJoinRes(res);
    }
    
    /**
     * 메시지 전송 결과를 메뉴으로 리턴 메소드
     * 
     * @param _msg 메시지
     */
    private void processMsgRes(String _msg) {
        // 처리 결과 추출
        String res = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        // mc.onNormalMessageRes(res);
    }

    /**
     * 사용자 ID 리스트 요청 결과를 메뉴으로 리턴
     * 
     * @param _msg 메시지
     */
    private void processUserListRes(String _msg) {
        // 시용자 리스트 추출
        // ArrayList<String> ulsit = mp.userList(_msg);
        // 메뉴 콜백 인테페이스 호출
        // mc.onUserListRes(ulsit);
    }

    /**
     * 프로그램 요청 결과를 메뉴으로 리턴
     * 
     * @param _msg
     */
    private void processExitRes(String _msg) {
        String res = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        if (mp.isOk(res)) mc.onTerminateProgramOk();
        else mc.onTerminateProgramFail();
    }
}