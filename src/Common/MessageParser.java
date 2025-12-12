package Common;

import java.util.StringTokenizer;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 메시지를 분리하는 클래스
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
     * 메시지에서 사용자 PW부분의 토큰 리턴 메소드
     * 
     * @param _msg 메시지
     * @return 사용자 비밀번호 리턴
     */
    public String findPW(String _msg) {
        // 2번째 토큰
        return getToken(_msg, 2);
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
     * 기록 분리 토큰을 리턴 메소드
     * 
     * @param msg 메시지
     * @return 리스트된 RecordData 객체
     */
    public ArrayList<RecordData> findParseRecordList(String msg) {
        ArrayList<RecordData> list = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(msg, "/");
        st.nextToken();       // RECORD_RES
        st.nextToken();       // userId
        String body = st.nextToken();  // FAIL 또는 "2025-12-01(...)+..."

        // FAIL 처리
        if (body.equals("FAIL")) {
            return list;  // 또는 null
        }

        // 날짜 데이터 여러 개 → "+" 기준으로 분리
        StringTokenizer dayTokens = new StringTokenizer(body, "+");

        while (dayTokens.hasMoreTokens()) {

            String block = dayTokens.nextToken();  
            // "2025-12-01(밥,걷기,72)"

            int idx1 = block.indexOf("(");
            int idx2 = block.indexOf(")");

            String dateStr = block.substring(0, idx1);    // "2025-12-01"
            LocalDate date = LocalDate.parse(dateStr);

            String inside = block.substring(idx1 + 1, idx2);  // "밥,걷기,72"

            // "," 기준 분리
            StringTokenizer insideTokens = new StringTokenizer(inside, ",");

            String meal = insideTokens.nextToken();
            String workout = insideTokens.nextToken();
            Double weight = Double.parseDouble(insideTokens.nextToken());

            list.add(new RecordData(date, meal, workout, weight));
        }

        return list;
    }

    /**
     * 토큰이 "+"으로된 메시지 분리
     * @param msg 메시지
     * @return 분리된 메시지
     */
    public ArrayList<String> findTokenUserList(String msg) {
        // 분리한 사용자 id를 리스트화
        ArrayList<String> list = new ArrayList<>();
        // 메시지가 null이 아니면
        if (msg != null) {
            // 토큰 "+"으로 분리
            st = new StringTokenizer(msg, "+");

            // 객체에서 다음에 읽을 토큰이 있으면 true, 없으면 false
            while (st.hasMoreTokens()) {
                // 리스트에 투가
                list.add(st.nextToken());
            }
        }
        // 리스트 리턴
        return list;
    }

    /**
     * 메시지를 피드백 객체로 변환
     * 
     * @param msg 메시지
     * @return FeedbackResult 객체
     */
    public FeedbackResult findFeedback(String msg) {
        ArrayList<String> tokens = messageTokens(msg);
        // FAIL
        if (tokens.get(2).equals("FAIL")) {
            return new FeedbackResult(false);
        }

        // 1. 칼로리 요약
        ArrayList<String> cal = findTokenUserList(tokens.get(2));
        int intake = Integer.parseInt(cal.get(0));
        int burn = Integer.parseInt(cal.get(1));
        int remain = Integer.parseInt(cal.get(2));
        int recommendCal = Integer.parseInt(cal.get(3));

        // 2. 탄수화물
        ArrayList<String> carb = findTokenUserList(tokens.get(3));
        int carbIntake = Integer.parseInt(carb.get(0));
        int carbRecommend = Integer.parseInt(carb.get(1));

        // 3. 단백질
        ArrayList<String> protein = findTokenUserList(tokens.get(4));
        int proteinIntake = Integer.parseInt(protein.get(0));
        int proteinRecommend = Integer.parseInt(protein.get(1));

        // 4. 지방
        ArrayList<String> fat = findTokenUserList(tokens.get(5));
        int fatIntake = Integer.parseInt(fat.get(0));
        int fatRecommend = Integer.parseInt(fat.get(1));

        // 5. 음식 추천
        ArrayList<String> foodList = findTokenUserList(tokens.get(6));

        // 6. 운동 추천
        ArrayList<String> workoutList = findTokenUserList(tokens.get(7));
     
        return new FeedbackResult(
            intake, burn, remain, 
            recommendCal, carbIntake, carbRecommend, 
            proteinIntake, proteinRecommend, fatIntake, 
            fatRecommend, foodList, workoutList
        );
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