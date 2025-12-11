/**
 * 과제3
 * 개발자 : 윤홍규
 * 개발기간 : 2025년 11월 17일  ~ 2025년 11월 30일
**/

package Client;

import java.io.*;
import java.net.*;

import Client.GUI.Dialog.EntryDialog;
// import Common.MessageParser;

/**
 * 클라이언트 클래스
 */
public class Client {
    // 소켓 선언
    private Socket socket = null;
    // 서버 송신 클래스 선언
    // private ClientSender sender = null;
    // 메시지 처리 선언
    // private MessageListener msgListener = null;
    
    // GUI 선언
    private EntryDialog dialog = null;
    
    
    // 클라이언트 메인 메소드
    public static void main(String[] agse) {
        Client c = new Client();
        c.start();
    }

    /**
     * 클라이언트 시작 메소드
     */
    public void start() {
        try {
            // 만약 서버가 원격에 위치하면 해당 서버컴퓨터의 IP주소
            socket = new Socket("localhost", 55550);
            System.out.println("Client > 서버로 연결되었습니다.");
            
            
            // 메시지 송신기 생성
            // sender = new ClientSender(socket);
            
            // GUI 연결
            dialog = new EntryDialog(null);
           

            // mu = new MenuUI(sender);
            // msgListener = new MessageListener(socket);
            
            // 메시지 처리 스레드 시작
            // msgListener.start();
    
            // 메뉴 표시 시작
            dialog.setVisible(true);
            // mu.start();

            // 클라이언트 스레드를 0.1s 대시
            Thread.sleep(100);
        } 
        catch (IOException e) {
            System.out.println("IOException : ");
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException : ");
            e.printStackTrace();
        }
    }
}   