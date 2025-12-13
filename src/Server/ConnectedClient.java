package Server;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import Common.*;
import Server.DB.MealDAO;
import Server.DB.RecordDAO;
import Server.DB.UserDAO;
import Server.DB.WeightDAO;
import Server.DB.WorkoutDAO;

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
                msgBasedService(typeMSG, msg);
                normalMessageOutput(this.socket.toString() + ": " + msg);
            }
        }
        catch (IOException e) {
            normalMessageOutput(this.socket.toString() + ": 연결해제됨");
            e.printStackTrace();
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
    private void msgBasedService(MessageType _type, String _msg) {
        switch (_type) {
            case SIGNUP_REQ:
                // 회원 가입 메시지 처리
                processSignupReq(_msg);
                break;

            case LOGIN_REQ:
                processLoginReq(_msg);
                break;
/*             case USER_UPDATE_REQ:
                // 회원 정보 수정 메시지 처리
                System.out.println("보류");
                break;
 */
            case LOGOUT_REQ:
                // 로그아웃 메시지 처리
                processLogoutReq(_msg);
                break;
            case MEAL_ADD_REQ:
                // 식단 추가 메시지 처리
                processMealAddReq(_msg);
                break;
            case WORKOUT_ADD_REQ:
                // 운동 추가 메시지 처리
                processWorkOutAddReq(_msg);
                break;

            case WEIGHT_ADD_REQ:
                // 체중 추가 메시지 처리
                processWeightAddReq(_msg);
                break;

            case RECORD_REQ:
                System.out.println("RECORD_REQ");
                // 기록 조회 메시지 처리
                processRecordReq(_msg);
                break;

            case PROGRESS_REQ:
                // 진행률 메시지 처리
                processProgressReq(_msg);
                break;

            case FEEDBACK_REQ:
                // 피드백 메시지 처리
                processFeedbackReq(_msg);
                break;            
            default : failMessageOutput("처리할 수 없는 메시지 타입입니다."); break;
        }
    }

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
     * 사용자가 회원가입 데이터를 파일에서 중복 검사 후 저장 및 처리 결과 메시지 전송하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    private void processSignupReq(String _msg) {
        // 메시지를 "/"토큰으로 분리 -> 리스트에 저장
        ArrayList<String> userData = mp.messageTokens(_msg);

        // 리스트에서 회원 정보를 추가한다.        
        User u = new User(
            userData.get(1), 
            userData.get(2), 
            Boolean.parseBoolean(userData.get(3)), 
            Double.parseDouble(userData.get(4)),
            Integer.parseInt(userData.get(5)), 
            Double.parseDouble(userData.get(6)),
            Double.parseDouble(userData.get(7))
        );
        // 파일에서 ID 중복 검사룰 한다.
        // 파일 -> ID 중복 검사
        boolean ok = new UserDAO().insert(u);
        System.out.println(userData.get(1));
        System.out.println(u.toString());
        // System.out.println(u.getID() + "ID TESTING...");
        normalMessageOutput(u.getID() + "ID TESTING...");
        // 회원가입 처리 결과 메시지 생성
        String rmsg = mb.signupRes(userData.get(1), ok ? "OK" : "FAIL");
        // 생성된 메시지 전송
        sendMSG(MessageType.SIGNUP_RES, rmsg);
    }

    // 로그인
    /**
     * 로그인를 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    private void processLoginReq(String _msg) {
        // 파일에서 사용자 정보를 검사
        // id 추출
        String id = mp.findID(_msg);
        // pw 추출
        String pw = mp.findPW(_msg);
        // 결과 추출
        // TODO : DB에서 ID, PW 검사
        // ufm.checkLogin(id, pw);
        // boolean ok = (id.equals("id1") && pw.equals("1234"));
        User u = new UserDAO().login(id, pw);
        // 검사 여부에 따라 OK, FAIL를 클라이언트에게 전송
        String lmsg = mb.loginRes(id, (u != null) ? "OK" : "FAIL");
        if (u != null) this.setID(mp.findID(_msg));
        // 메지시 전송
        sendMSG(MessageType.LOGIN_RES, lmsg);
    }

    /**
     * 로그아웃을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     */
    private void processLogoutReq(String _msg) {
        // TODO : 로그아웃을 하는 클라이언트 종료
        String id = mp.findID(_msg);
        
    }

    /**
     * 음식을 처리하는 메소드
     * @param _msg 처리할 메지시
     */
    private void processMealAddReq(String _msg) {
        String id = mp.findID(_msg);
        
        // MEAL_ADD_REQ/사용자 ID/날짜+시간/음식 타입/음식명/섭취량
        TimeConversion tc = new TimeConversion();
        LocalDateTime ldt = tc.strToTime(mp.getToken(_msg, 2));
        
        String foodType = mp.getToken(_msg,3);
        String foodName = mp.getToken(_msg,4);
        Double g = Double.parseDouble(mp.getToken(_msg, 5));

        Meal m = new Meal(ldt, foodType, foodName, g, 0, 0, 0, 0); 
        // TODO : DB에 음식 산입 결과 구현
        boolean ok = new MealDAO().insert(id, m);
        String rmsg = mb.mealAddRes(id, ok ? "OK" : "FAIL");
        
        sendMSG(MessageType.MEAL_ADD_RES, rmsg);
    }

    /**
     * 운동을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     * WORKOUT_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”) 메시지
     * Workout(String id, String exerciseName, double minutes, double kcal) 객체 활용
     */
    private void processWorkOutAddReq(String _msg) {
        // ArrayList<String> tokens = mp.messageTokens(_msg);
        TimeConversion tc = new TimeConversion();

        String id = mp.findID(_msg);
        LocalDateTime datetime = tc.strToTime(mp.getToken(_msg, 2));
        String workoutName = mp.getToken(_msg, 3);
        double minutes = Double.parseDouble(mp.getToken(_msg, 4));

        // Workout 객체 생성
        Workout w = new Workout(datetime, workoutName, minutes, 0); // kcal은 서버에서 계산하면 넣기

        // TODO: DB에 운동 저장 처리
        boolean ok = new WorkoutDAO().insert(id, w);  // DB 처리 결과

        // 응답 메시지 생성
        String rmsg = mb.workoutAddRes(id, ok ? "OK" : "FAIL");
        System.out.println(w.toString());
        // 클라이언트로 전송
        sendMSG(MessageType.WORKOUT_ADD_RES, rmsg);
    }

    /**
     * 체중을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     * WEIGHT_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”) 메시지
     * Weight(String id, LocalDate date, double weight) 객체 활용
     */
    private void processWeightAddReq(String _msg) {
        String id = mp.findID(_msg);
        LocalDateTime date = new TimeConversion().inputToTimeString(mp.getToken(_msg, 2));
        double weight = Double.parseDouble(mp.getToken(_msg, 3));
        Weight w = new Weight(date, weight);

        // TODO: DB에 체중 저장
        boolean ok = new WeightDAO().insert(id, w);

        String rmsg = mb.weightAddRes(id, ok ? "OK" : "FAIL");

        sendMSG(MessageType.WEIGHT_ADD_RES, rmsg);
    }

    /**
     * 기록을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     * RECORD_RES/사용자 ID/처리 결과(날짜1(식단명,운동명,체중)+…+날짜n(식단명,운동명,체중) or “FAIL”) 메시지
     * RecordData(LocalDate date, String meal, String workout, Double weight) 기록 객체
     */
    private void processRecordReq(String _msg) {
        String id = mp.findID(_msg);
        // TODO: DB에서 id의 기록 데이터를 조회
        // ArrayList<RecordData> list = DB.getRecords(id);
        ArrayList<RecordData> list = new RecordDAO().findRecordsByUser(id);

        if (list == null) {
            sendMSG(MessageType.RECORD_RES, id + "/FAIL");
            return;
        }

        // TODO: 임시 샘플 (DB 조회된 값)
        // ArrayList<RecordData> list = new ArrayList<>();
        // list.add(new RecordData(LocalDateTime.now(), "고구마", "걷기", 72.4));
        // list.add(new RecordData(LocalDateTime.now().plusDays(1), "감자", "달리기", 72.3));
        // list.add(new RecordData(LocalDateTime.now().plusDays(2), "시리얼", "기타", 72.2));

        String rmsg = mb.recordRes(id, list);
        System.out.println(rmsg);
        sendMSG(MessageType.RECORD_RES, rmsg);
    }

    /**
     * 진행률을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     * PROGRESS_RES/사용자 ID/처리 결과(초기 체중/목표 체중/현재 체중/달성률 or “FAIL”) 메시지
     */
    private void processProgressReq(String _msg) {
        String id = mp.findID(_msg);

        // TODO: DB에서 초기/목표/현재 체중 조회
        Progress p = new ProgressService().getProgress(id);
        boolean ok = (p != null);

        // double initial = 80;
        // double goal = 70;
        // double current = 75;
        // double progress = (initial - current) / (initial - goal) * 100.0;

        // Progress p = new Progress(initial, goal, current, progress);

        if (!ok) {
            sendMSG(MessageType.PROGRESS_RES, id + "/FAIL");
            return;
        }

        String rmsg = mb.progressRes(id, p);
        // System.out.println(rmsg);
        sendMSG(MessageType.PROGRESS_RES, rmsg);
    }

    /**
     * 피드백을 처리하는 메소드
     * 
     * @param _msg 처리할 메지시
     * FEEDBACK_RES/사용자 ID/처리 결과(섭취+소모+잔여+권장/탄수화물_섭취량+탄수화물_권장량/단백질_섭취량+단백질_권장량/지방_섭취량+지방_권장량/음식 추천 리스트/운동 추천 리스트 or “FAIL”) 메시지
     * FeedbackResult(
     *  int intake, int burn, int remain, 
     *  int recommendCal, int carbIntake, int carbRecommend, 
     *  int proteinIntake, int proteinRecommend, int fatIntake, 
     *  int fatRecommend, ArrayList<String> 
     *  foodRecommend, ArrayList<String> workoutRecommend) 피그백 객체
     */
    private void processFeedbackReq(String _msg) {
        String id = mp.findID(_msg);

        // TODO: DB에서 음식, 운동, 칼로리, 영양소 요약 데이터 조회
        FeedbackResult fr = new FeedbackService().getFeedback(id);
        boolean ok = (fr != null);

        if (!ok) {
            sendMSG(MessageType.FEEDBACK_RES, id + "/FAIL");
            return;
        }
/* 
        // 예시 샘플
        int intake = 1800;
        int burn = 300;
        int remain = intake - burn;
        int recommendCal = 2000;

        int carbIntake = 210, carbRec = 250;
        int proIntake = 55, proRec = 60;
        int fatIntake = 78, fatRec = 55;

        ArrayList<String> foodRecommend = new ArrayList<>();
        foodRecommend.add("닭가슴살");
        foodRecommend.add("현미밥");

        ArrayList<String> workoutRecommend = new ArrayList<>();
        workoutRecommend.add("빠르게 걷기");
        workoutRecommend.add("스쿼트");

        FeedbackResult fr = new FeedbackResult(
                intake, burn, remain, recommendCal,
                carbIntake, carbRec,
                proIntake, proRec,
                fatIntake, fatRec,
                foodRecommend, workoutRecommend); */

        String rmsg = mb.feedbackRes(id, fr);
        sendMSG(MessageType.FEEDBACK_RES, rmsg);
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
            // dataOutStream.flush();
        }
        // 전송 시 예외 처리
        catch (IOException e) {
            failMessageOutput("메시지 전송 실패 : " + _mt.getType());
            e.printStackTrace();
            // System.err.println("메시지 전송 실패 : " + _mt.getType());
        }
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