package Server.DB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// JDBC 서버에 DB에 연결
public class DBC {
    // 라이브러리 가져오기
    public static final String databaseDriver = "com.mysql.cj.jdbc.Driver";
    // JDBC 서버 연결
    public static final String databaseUrl = "jdbc:mysql://localhost:3306/UserData?serverTimezone=Asia/Seoul&characterEncoding=UTF8&useSSL=false";
    // 계정 이름
    public static final String databaseUser = "root";
    // 계정 비밀번호
    // TODO : 저장 금지
    public static final String databasePassword = "MQsooT_1246@";
    // JDBC 서버와 연결된 필드
    public static Connection connection = null;
    
    // 테스트용 메인 메소드
    public static void main(String[] args) {
    	connect();
    	close();
    }
    
    //DB 연결 메소드
    public static Connection connect() {
    	try {
    		Class.forName(databaseDriver);
    		connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    		if (connection != null) System.out.println("DB 연결 성공");
    		else System.out.println("DB 연결 실패");
    	}
        catch(Exception e) {
    		// 에러나면 팝업창을 띄움
    		JOptionPane.showMessageDialog(null, "데이터베이스 연결 실패", "경고", JOptionPane.WARNING_MESSAGE);
    		System.err.println("에러 내용: "+ e.getMessage());
    		e.printStackTrace();
    	}
    	return connection;
    }
   
    // DB 연결 해제 메소드
    public static void close() {
    	try {
    		if(connection != null) {
    			System.out.println("DB 연결 종료");
    			connection.close();
    		}
    	}
        catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}