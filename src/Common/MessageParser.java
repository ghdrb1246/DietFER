package Common;

import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * 서버에서 받은 메시지를 분리하는 클래스
 */
public class MessageParser {
    // 토큰으로 분리하기 위한 StringTokenizer 객체 생성
    private StringTokenizer st;
    // 메시지 타입 열거 객체 생성
    private MessageType mt;

    /**
     * 메시지의 타입을 판단하여 타입을 리턴하는 메소드
     * 
     * @param _msg 메시지
     * @return 메시지 타입 리턴
     */
    public MessageType detection(String _msg) {
        // 메시지 타입
        MessageType result = null;
        String tag;

        // 분리 준비
        st = new StringTokenizer(_msg, "/");
        // 타입 부분 분리
        tag = st.nextToken();
        // 메시지로 대입
        mt = MessageType.valueOf(tag);

        // 메시지 타입이 유효한지 검사
        for (MessageType m : MessageType.values()) {
            if (m == mt) {
                result = m;
                break;
            }
        }
        // 메시지 타입 or null 리턴
        return result;
    }

    /**
     * 메시지를 "/" 토큰으로 분리 후 리스트로 리턴하는 메소드 
     * 
     * @param _msg 메시지
     * @return 타입외 나머지를 분리하서 리스트화
     */
    public ArrayList<String> messageTokens(String _msg) {
        ArrayList<String> tlist = new ArrayList<>();
        st = new StringTokenizer(_msg, "/");

        // 다음 읽어올 토큰이 있는면 t, 아니면 f
        //  t -> 리스트에 추가
        //  f -> 리스트 리턴
        // 분리 준비
        while (st.hasMoreTokens()) tlist.add(st.nextToken());
        // 리스트 리턴
        return tlist;
    }

    /**
     * 메지지에서 n번째 토큰를, 없으면 null 리턴 메소드
     * 
     * @param _msg 메시지
     * @param inx  n번째 토큰 인덱스
     * @return n번째 토큰
     */
    public String getToken(String _msg, int inx) {
        ArrayList<String> tlist = messageTokens(_msg);
        // 인덱스가 0 보다 크고, 인덱스가 tlist의 크기가 작으면 n번째 토큰를 리턴
        if ((inx >= 0) && (inx <= tlist.size())) return tlist.get(inx);
        // 없거나 조건이 거짓이면 null를 리턴
        else return null;
    }

    /**
     * 메시지 타입 리턴 메소드
     * 
     * @param _msg 메시지
     * @return 메시지 타입
     */
    public MessageType getType(String _msg) {
        String tag = null;
        // 분리 준비
        st = new StringTokenizer(_msg, "/");
        // 다음 읽어올 토큰이 없다면 null를 리턴
        if (!(st.hasMoreElements())) {
            return null;
        }
        // 타입을 분리
        tag = st.nextToken();
        // 메시지 타입 리턴
        return MessageType.valueOf(tag);
    }

    /**
     * 메시지에서 사용자 ID부분의 토큰 리턴 메소드
     * 
     * @param _msg 메시지
     * @return 사용자 id를 리턴
     */
    public String findID(String _msg) {    
        // 1번째 토큰
        return getToken(_msg, 1);
    }

    /**
     * 메시지에서 메시지 부분의 토큰을 리턴 메소드
     * 
     * @param _msg 메시지
     * @return 메시지를 리턴
     */
    public String findBroadCastingMSG(String _msg) {
        // 타입/사용자 ID/MSG
        return getToken(_msg, 2);
    }

    /**
     * 마지막 토큰이 "OK"인지 판단하는 메소드
     * 
     * @param _msg 메시지
     * @return "OK"이면 true | 아니면 false
     */
    public boolean isOk(String _msg) {
        // 메시지 리스트 마지막 메시지를 찾음
        String st = getToken(_msg, messageTokens(_msg).size() - 1);
        
        // "OK"인 판단함
        return st.equals("OK");
    }

    /**
     * 마지막 토큰이 "FAIL"인지 판단하는 메소드
     * 
     * @param _msg 메시지
     * @return "FAIL"이면 true | 아니면 false
     */
    public boolean isFail(String _msg) {
        // 메시지 리스트 마지막 메시지를 찾음
        String st = getToken(_msg, messageTokens(_msg).size() - 1);

        // "FAIL"인 판단함
        return st.equals("FAIL");
    }
}   