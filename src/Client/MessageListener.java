package Client;

import java.net.*;
import java.util.ArrayList;

import Common.*;

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
                    msgBasedService(msg);
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
    public void msgBasedService(String _msg) {
        MessageType tm = mp.getType(_msg);

        // 메시지 타입 판단
        switch (tm) {
            case SIGNUP_RES:
                // 회원 가입 메시지 처리
                processSignupRes(_msg);
                break;

            case LOGIN_RES :
                // 로그인 메시지 처리 
                processLoginRes(_msg);
                break;
                
            case LOGOUT_RES : 
                // 로그인 메시지 처리
                processLogoutRes(_msg);
                break;

            case MEAL_ADD_RES :
                // 식단 추가 메시지 처리
                processMealRes(_msg);
                break;

            case EXERCISE_ADD_RES :
                // 운동 추가 메시지 처리
                processExerciseRes(_msg);
                break;

            case WEIGHT_ADD_RES :
                // 체중 추가 메시지 처리
                processWeightRes(_msg);
                break;

            case RECORD_RES :
                // 기록 조회 메시지 처리
                processRecordRes(_msg);
                break;
            
            case PROGRESS_RES :
                // 달성률 메시지 처리
                processProgressRes(_msg);
                break;
            
            case FEEDBACK_RES :
                // 피드백 처리
                processFeedbackRes(_msg);
                break;

            case FOOD_SEARCH_RES :
                // 음식 검색 처리
                processFoodSearchRes(_msg);
                break;

            default :
                // 메뉴 콜백 인테페이스 호출
                mc.onMessageError(tm + "는 처리할 수 없는 메시지 타입입니다.", null);
                break;
        }
    }

    /**
     * 프로그램 요청 결과를 메뉴으로 리턴
     * 
     * @param _msg
     */
/*     private void processExitRes(String _msg) {
        String res = mp.getToken(_msg, 2);
        // 메뉴 콜백 인테페이스 호출
        if (mp.isOk(res))
            mc.onTerminateProgramOk();
        else
            mc.onTerminateProgramFail();
    } */

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
        System.out.println(res);

        // 메뉴 콜백 인테페이스 호출
        mc.onLoginRes(id, res);
        
    }

    /**
     * 회원가입 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param _msg 메시지
     */
    private void processSignupRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 처리 결과 추출
        String res = mp.getToken(msg, 2);
        // 메뉴 콜백 인테페이스 호출
        mc.onSignupRes(id, res);
    }

    /**
     * 로그아웃 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg
     */
    private void processLogoutRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 처리 결과 추출
        String res = mp.getToken(msg, 2);

        // 메뉴 콜백 인테페이스 호출
        mc.onLogoutRes(id, res);
    }

    /**
     * 식단 입력 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지 
     * MEAL_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    private void processMealRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 처리 결과 추출
        String res = mp.getToken(msg, 2);
        
        // 메뉴 콜백 인테페이스 호출
        mc.onMealAddRes(id, res);
    }

    /**
     * 운동 입력 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지
     */
    private void processExerciseRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 처리 결과 추출
        String res = mp.getToken(msg, 2);
        // 메뉴 콜백 인테페이스 호출
        mc.onExerciseAddRes(id, res);
    }
    
    /**
     * 체중 입력 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지
     */
    private void processWeightRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 처리 결과 추출
        String res = mp.getToken(msg, 2);
        // 메뉴 콜백 인테페이스 호출
        mc.onWeightAddRes(id, res);
    }

    /**
     * 기록 조회 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지
     */
    private void processRecordRes(String msg) {
        // id 추출
        String id = mp.findID(msg);

        ArrayList<RecordData> rd = new ArrayList<>();
        // 메시지 뒤 부분이 "FAIL"이니면 
        if (!(mp.isFail(msg))) {
            // RecordData 객체로 분리
            rd = mp.findParseRecordList(msg);
        }
        // "FAIL"이면 NULL
        // 메뉴 콜백 인테페이스 호출
        mc.onRecordRes(id, rd);
    }

    /**
     * 달성률 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지
     
     */
    private void processProgressRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 초기 체중 
        double startWeight = 0.0;
        // 목표 체중
        double goalWeight = 0.0;
        // 현재 체중
        double currentWeight = 0.0;
        // 달성률
        double achievementRate = 0.0;

        // 메시지 뒤 부분이 "FAIL"이니면
        if (!(mp.isFail(msg))) {
            startWeight = Double.parseDouble(mp.getToken(msg, 2));
            goalWeight = Double.parseDouble(mp.getToken(msg, 3));
            currentWeight = Double.parseDouble(mp.getToken(msg, 4));
            achievementRate = Double.parseDouble(mp.getToken(msg, 5));
        }

        Progress p = new Progress(startWeight, goalWeight, currentWeight, achievementRate);
        // 메뉴 콜백 인테페이스 호출
        mc.onProgressRes(id, p);
    }

    /**
     * 피드백 요청 결과를 메뉴으로 리턴 메소드
     * 
     * @param msg 메시지
     * FEEDBACK_RES/사용자 ID/처리 결과(섭취+소모+잔여+권장/탄수화물_섭취량+탄수화물_권장량/단백질_섭취량+단백질_권장량/지방_섭취량+지방_권장량/음식 추천 리스트/운동 추천 리스트 or “FAIL”)
     */
    private void processFeedbackRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        FeedbackResult fr = null;

        if (!(mp.isFail(msg))) {
            fr = mp.findFeedback(msg);
        }
        mc.onFeedbackRes(id, fr);
    }

    private void processFoodSearchRes(String msg) {
        // id 추출
        String id = mp.findID(msg);
        // 메시지 "/"으로 분리
        ArrayList<String> slist = mp.messageTokens(msg);
        // 메시지 "+"으로 분리
        ArrayList<String> plist = new ArrayList<>();
        // 식품 영양 정보 객체
        ArrayList<FoodNutrition> fnlist = new ArrayList<>();

        for (String ss : slist) {
            plist = mp.findTokenUserList(ss);

            if (plist.size() < 5) {
                // 부족한 경우 해당 항목을 건너뛰기
                continue;
            }
            fnlist.add(new FoodNutrition(
                    plist.get(0),
                    Double.parseDouble(plist.get(1)),
                    Double.parseDouble(plist.get(2)),
                    Double.parseDouble(plist.get(3)),
                    Double.parseDouble(plist.get(4))));
        }
        mc.onFoodSearchRes(id, fnlist);
    }
}