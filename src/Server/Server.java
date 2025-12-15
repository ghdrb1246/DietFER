/**
 * 개인 프로젝ㅌ,
 * 개발자 : 윤홍규
 * 개발기간 : 2025년 12월 11일  ~ 2025년 12월 16일
**/

package Server;

import java.net.*;
import java.util.ArrayList;

/**
 * 서버 클래스
 */
public class Server {
	// 서버 소켓
	private ServerSocket ss = null;
	// 클라이언트 리스트 객체 생성
	private ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>(); 

	// 서버 메인 메서드
	public static void main(String[] args) {
		// 서버 객체 생성
		Server server = new Server();
		
		try {
			// 서버 소켓 생성
			server.ss = new ServerSocket(55550);
			System.out.println("Server > Server Socket is created....");
			
			// 서버 메인쓰레드 소켓생성 무한루프
			while(true) { 
				// 소켓 생성
				Socket socket = server.ss.accept();
				ConnectedClient c = new ConnectedClient(socket, server.clients);
				server.clients.add(c);
				c.start();
			}
		}
		catch(SocketException e) { System.out.println("Server > 서버 종료"); }
		catch(Exception e) { e.printStackTrace(); }
	}
}