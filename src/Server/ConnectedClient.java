package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

import Common.MessageBuilder;
import Common.MessageParser;
import Common.MessageType;
import Common.User;

/**
 * 연결된 클라이언트에 대한 보낸 메시지를 타입에 따라 처리 후 전송
 */
class ConnectedClient extends Thread {
    // 로그인한 사용자의 ID
    private String id = null;
    // 소켓 객체 생성
    private Socket socket;
    // 연결된 클라이언트 리스트 객체 생성
    private ArrayList<ConnectedClient> clients;
    // 메시지를 토큰으로 분리 객체 생성
    private MessageParser mp = new MessageParser();
    // 메시지 생성 객체 생성
    private MessageBuilder mb = new MessageBuilder();

    // 입출력 담당
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutStream;

    /**
     * 연결된 클라이언트 생성자
     * 
     * @param _s  소켓
     * @param _cl 연결된 클라이언트 리스트
     */
    public ConnectedClient(Socket _s, ArrayList<ConnectedClient> _cl) {
        this.socket = _s;
        this.clients = _cl;
    }

    /**
     * 연결된 클라이언트 스레드 메소드
     */
    public void run() {
        MessageType typeMSG;

        try {
            normalMessageOutput(this.socket.toString() + "에서의 접속이 연결되었습니다.");

            outputStream = this.socket.getOutputStream();
            dataOutStream = new DataOutputStream(outputStream);
            inputStream = this.socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);

            while (true) {
                String msg = dataInputStream.readUTF();
                System.out.println("Server > " + msg);

                typeMSG = mp.detection(msg);
                // msgBasedService(typeMSG, msg);
                normalMessageOutput(this.socket.toString() + ": " + msg);
            }
        }
        catch (IOException e) {
            normalMessageOutput(this.socket.toString() + ": 연결해제됨");
        }

        // finally 블록은 예외가 발생하든 안 하든 반드시 실행
        // 클라이언트의 연결이 종료되었기 때문에 서버가 관리하는 클라이언트 리스트에서 제거한다.
        finally {
            try {
                if (socket != null) {
                    socket.close();
                    remove(socket);
                }
            }
            catch (IOException e){
                System.out.println("IOException. : ");
                e.printStackTrace();
            }
        }
    }

    // remove() 메소드는 종료한 클라이언트를 clients 리스트에서 제거해야 하지만 제거되지 않는 버그발생 
    // 이를 chatgpt를 활용해 참고하여 작성함.
    // 질문 : 클라이언트가 프로그램을 종료하면 서버에서는 해당 클라이언트를 제거해야하는데 제거 되지 않음
    /**
     * 접속 해제한 클라이언트 제거 메소드
     * 
     * @param _s 제거할 클라이언트 소켓
     */
    private void remove(Socket _s) {
        // 리스트를 반본하며 동일한 소켓을 가진 클라이언트를 찾는다.
        // 이 리스트를 하나씩 꺼내서 검사하기 위해 iterator(반복자)를 생성
        // iterator는 리스트를 안전하게 반복하면서 요소를 삭제할 수 있게 해주는 도구이다
        Iterator<ConnectedClient> iterator = clients.iterator();

        // 리스트에 아직 확인하지 않은 다음 요소가 있을 때 계속 반복함
        // hasNext()는 다음 요소가 있는지 검사하는 메서드
        // 있으면 true, 없으면 false를 변환
        while (iterator.hasNext()) {
            // iterator에서 다음 ConnectedClient 객체 하나를 꺼내어,
            // 클라이언트를 client 변수에 대입한다.
            ConnectedClient client = iterator.next();

            // 현재 client가 가진 소켓과 비교
            // 지금 검사 중인 client가 가진 소켓과 제거하고 싶은 소켓이 같은지 비교한다.
            // 같은 소켓 이면 true, 아니면 false를 대입한다.
            boolean isRemove = (client.socket == _s);

            // 이 클라이언트가 우리가 제거해야 할 대상이라면
            if (isRemove) {
                // iterator가 현재 가리키는 ConnectedClient를 리스트에서 삭제한다.
                iterator.remove();
                normalMessageOutput("id : " + client.getID() + " 제거됨");
                // System.out.println("id : " + client.getID() + " 제거됨");
                
                // 메서드를 즉시 종료한다.
                return; 
            }
        }
        // 제거할 클라이언트가 없으면 
        failMessageOutput("제거 대상 클라이언트 없음");
        // System.out.println("제거 대상 클라이언트 없음");
    }

    /**
     * 클라이언트에서 받은 메시지 타입에 따라 처리 메소드
     * 
     * @param _type 처리할 메지시 타입
     * @param _msg  처리할 메지시
     */
    /* private void msgBasedService(Message _type, String _msg) {
        switch (_type) {
            case MSG : 
                processMgs(_msg); 
                processBroadCast(_msg);
            break;
            case USERLIST_REQ : processUserListReq(); break;
            case WHISPER_REQ : processWhosperReq(_msg); break;
            case EXIT_REQ : processExitReq(_msg); break;
            case JOIN_REQ : processJoinReq(_msg); break;
            case LOGIN_REQ : processLoginReq(_msg); break;
            default : failMessageOutput("처리할 수 없는 메시지 타입입니다."); break;
        }
    } */

    /**
     * 사용자 ID 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processId(String _msg) {
        // 사용자가 보낸 전체메시지에서 사용자 ID 찾기
        // 사용자 ID를 ConnectedClient 객체의 id필드에 대입하는 동직 필요
        this.setID(mp.findID(_msg));
        
        // ID가 있다면 t, 없다면 f
        boolean ok = !(getID().equals("NO_ID"));

        // 사용자 ID 처리 결과 전달 확인용 메시지 생성
        String cmsg = mb.idResMsg(id, ok ? "OK" : "FAIL");

        // 연결된 Clien에게 생성 메시지 전달
        sendMSG(MessageType.ID_RES, cmsg);
        
        // ID 처리가 성공이면 사용자에게 웰컴 메시지를 전송
        if (ok) {
            // 웰컴 메시지 생성
            String wmsg = mb.welcomeMsg(getID());
            // System.out.println(wmsg + " " + getID());
            normalMessageOutput(wmsg + " " + getID());

            // 웰컴 메시지 전달
            sendMSG(MessageType.WELCOME, wmsg);
        }
    } */

    /**
     * 메시지 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processMgs(String _msg) {
        // ID가 있다면 t, 없다면 f?
        boolean ok = !(getID().equals("NO_ID"));
        String id = mp.findID(_msg);
        // 메시지 처리 결과 전달 확인용 메시지 생성
        String cmsg = mb.msgResMsg(id, ok ? "OK" : "FAIL");
        // 연결된 Clien에게 생성 메시지 전달
        sendMSG(MessageType.MSG_RES, cmsg);
    } */

    /**
     * 전체 사용자에서 전송하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
   /*  private void processBroadCast(String _msg) {
        // id 추출
        String id = mp.findID(_msg);
        // 메시지 추출
        String m = mp.findBroadCastingMSG(_msg);
        // 메시지 생성
        String bmsg = mb.broadcasrMsg(id, m);
        // 사용자 리스트 번호 
        int i = 1;

        // 접속한 전체 Client 메시지 생성 메시지 전달 
        for (ConnectedClient cc : clients) {
            System.out.println(i++ + ". " + cc.getID());

            try {
                // 자신 id와 같으면 제외
                if (cc.getID().equals(id)) continue;
                // 클라이안트에서 전체 메시지 전송
                cc.sendMSG(MessageType.BROADCAST, bmsg);
            }

            // 예외 처리
            catch (Exception e) {
                failMessageOutput("접속한" + cc.getID() + "메시지 전송을 실패");
            } 
        }
    } */

    /**
     * 현재 접속한 사용자들의 id 리스트를 처리하는 메소드
     */
    /* private void processUserListReq() {
        // 사용자 ID 리스트
        ArrayList<String> ulist = new ArrayList<>();

        // 현재 접속한 사용자들의 ID를 추가
        for (ConnectedClient cc : clients) {
            ulist.add(cc.id);
        }

        // 사용자 ID 리스트 메시지 생성
        String ulmsg = mb.userListMsg(ulist);

        // 접속한 전체 Clien들의 id 리스트 생성 메시지 전송
        sendMSG(MessageType.USERLIST, ulmsg);
    }
 */
    /**
     * 귓속말 전송하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processWhosperReq(String _msg) {
        // 보낸 사용자 ID
        String from = mp.findID(_msg);
        // 받은 사용자 ID
        String to = mp.findWhisperRID(_msg);
        // 보낼 메시지
        String m = mp.findWhisperMsg(_msg);
        // 귓속말 메시지 생성
        String wmsg = mb.whisperMsg(from, m);
        // 귓속말 전송, 성공 : 1, 실패 0
        ConnectedClient ck = wishper(to, m);

        // 사용자 ID가 "NO_ID"가 이나면 귓속말 대상에게 전송
        if (ck != null) {
            normalMessageOutput(from + "(from) -> " + to + "(to) : [ " + m + " ]");
            // 귓속말 전송
            ck.sendMSG(MessageType.WHISPER, wmsg);
        }
        else {
            failMessageOutput("귓속말 전송 실패");
            // 귓속말 전송 실패 메시지 전송
            sendMSG(MessageType.WHISPER, mb.whisperMsg(to, "FAIL"));
        }
    } */

    /**
     * 프로그램 종료 여부를 판단하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processExitReq(String _msg) {
        // 메시지에서 ID 추출
        String id = mp.findID(_msg);
        // 종료 조건
        boolean ok = !(id.equals("NO_ID")) || id == null ? true : false;
        // 사용자가 프로그램 종료 허용 여부를 메시지 생성
        String emsg = mb.exitResMsg(id, ok ? "OK" : "FAIL");
        // 생성된 메시지를 전송
        sendMSG(MessageType.EXIT_RES, emsg);
        if (ok) normalMessageOutput("클래이언트(" + id + ")가 정성 종료 되었습니다.");
    } */

    /**
     * 사용자가 회원가입 데이터를 파일에서 중복 검사 후 저장 및 처리 결과 메시지 전송하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processJoinReq(String _msg) {
        // 메시지를 "/"토큰으로 분리 -> 리스트에 저장
        ArrayList<String> userData = mp.messageTokens(_msg);

        // 리스트에서 회원 정보를 추가한다.        

        // 파일에서 ID 중복 검사룰 한다.
        // 파일 -> ID 중복 검사
        boolean ok = ufm.saveUser(u);
        System.out.println(ok);
        // System.out.println(u.getID() + "ID TESTING...");
        normalMessageOutput(u.getID() + "ID TESTING...");
        // 회원가입 처리 결과 메시지 생성
        String rmsg = mb.joinResMsg(userData.get(1), ok ? "OK" : "FAIL");
        // 생성된 메시지 전송
        sendMSG(MessageType.JOIN_RES, rmsg);
    } */

    // 로그인
    /**
     * 로그인를 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    /* private void processLoginReq(String _msg) {
        // 파일에서 사용자 정보를 검사
        // id 추출
        String id = mp.findID(_msg);
        // pw 추출
        String pw = mp.findPW(_msg);
        // 결과 추출
        boolean ok = ufm.checkLogin(id, pw);
        // 검사 여부에 따라 OK, FAIL를 클라이언트에게 전송
        String lmsg = mb.loginResMsg(id, ok ? "OK" : "FAIL");
        // 메지시 전송
        sendMSG(MessageType.LOGIN_RES, lmsg);
        // id 처리 결과 전송
        processId(_msg);
    } */

    /**
     * 클라이언트 ID 수정는 메소드
     * 
     * @param _id 수정할 id
     */
    public void setID(String _id) {
        this.id = _id;
    }

    /**
     * 클라이언트 ID를 가져오는 메고드
     * 
     * @return 클라이언트 id 리턴
     */
    public String getID() {
        // 자신 ID기 null이 아니면
        if (this.id != null)
            // 자신 ID를 리턴
            return this.id;

        else
            // ID가 없을 때 "NO_ID"를 리턴
            return "NO_ID";
    }

    /**
     * 사용자 메시지를 전송 메소드
     * 
     * @param _mt 메시지 타입
     * @param _msg 메시지
     */
    public void sendMSG(MessageType _mt, String _msg) {
        // 메시지 타입과 메시지를 출력
        normalMessageOutput("type : " + _mt.getType() + " | msg : " + _msg);
        // System.out.println("type : " + _mt.getType() + " | msg : " + _msg);

        try {
            // 클라이언트에게 전송
            dataOutStream.writeUTF(_msg);
        }
        // 전송 시 예외 처리
        catch (IOException e) {
            failMessageOutput("메시지 전송 실패 : " + _mt.getType());
            // System.err.println("메시지 전송 실패 : " + _mt.getType());
        }
    }

    /**
     * 귓속말 대상을 사용자 리스트에서 찾는 메소드
     * 
     * @param _toID 상대방 id
     * @param _msg  메시지
     * @return 상대방 클라이언트 리턴
     */
    public ConnectedClient wishper(String _toID, String _msg) {
        // 상대방 클라이언트를 반복하여 검사한다.
        for (ConnectedClient cc : clients) {
            // 반복 중 그 클라이언크 ID와 이 클라이언트와 값고, 클라이언트 자신이 아니어야한다.
            if (cc.getID().equals(_toID) && cc.getID() != this.getID()) {
                // 상대방 클라이언트가 있다면 리턴
                return cc;
            }
        }

        // 상대방 클라이언트가 없다면 null를 리턴
        return null;
    }

    /**
     * 정상 메시지 출력 메소드
     * 
     * @param _msg 정상 메시지
     */
    public void normalMessageOutput(String _msg) {
        System.out.println("Server > " + _msg);
    }

    /**
     * 실패 메시지 출력 메소드
     * 
     * @param _msg 실패 메시지
     */
    public void failMessageOutput(String _msg) {
        System.out.println("Server > " + _msg);
    }
} 