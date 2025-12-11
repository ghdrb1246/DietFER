package Common;

/**
 * <p>
 * 메지시 타입 정의한 열거형
 * </p>
 * 각 메시지 타입의 인덱스 번호, 타입 명
 * </p>
 * 1. 채팅서비스 종료 요청 메시지 
 * Client -> Server
 * 사용자가 종료를 요청 전달
 * EXIT_REQ/사용자 ID
 * 
 * </p>
 * 2. 
 * </p>
 * 3. 
 * </p>
 * 4. 
 * </p>
 * 5. 
 * </p>
 * 6. 
 * </p>
 * 7. 
 * </p>
 * 8. 
 * </p>
 * 9. 
 * </p>
 * 10. 
 * </p>
 * 11. 
 * </p>
 * 12. 
 * </p>
 * 13. 
 * </p>
 * 14. 
 * </p>
 * 15. 
 * </p>
 * 16. 
 * </p>
 */
public enum MessageType {
    // 시스템 종료
    EXIT_REQ (0, "EXIT_REQ"),
    EXIT_RES (1, "EXIT_RES"),

    // 회원가입
    SIGNUP_REQ (2, "SIGNUP_REQ"),
    SIGNUP_RES (3, "SIGNUP_RES"),

    // 로그인
    LOGIN_REQ (4, "LOGIN_REQ"),
    LOGIN_RES (5, "LOGIN_RES"),

    // 사용자 정보 수정
    USER_UPDATE_REQ (6, "USER_UPDATE_REQ"),
    USER_UPDATE_RES (7, "USER_UPDATE_RES"),

    // 로그아웃
    LOGOUT_REQ (8, "LOGOUT_REQ"),
    LOGOUT_RES (9, "LOGOUT_RES"),

    // 회원탈퇴
    USER_DELETE_REQ (10, "USER_DELETE_REQ"),
    USER_DELETE_RES (11, "USER_DELETE_RES"),

    // 식단 추가
    MEAL_ADD_REQ (12, "MEAL_ADD_REQ"),
    MEAL_ADD_RES (13, "MEAL_ADD_RES"),

    // 운동 추가
    WORKOUT_ADD_REQ (14, "WORKOUT_ADD_REQ"),
    WORKOUT_ADD_RES (15, "WORKOUT_ADD_RES"), 

    // 체중 추가
    WEIGHT_ADD_REQ (16, "WEIGHT_ADD_REQ"),
    WEIGHT_ADD_RES (17, "WEIGHT_ADD_RES"),

    // 기록 조회
    RECORD_REQ (18, "RECORD_REQ"),
    RECORD_RES (19, "RECORD_RES"),

    // 진행률
    PROGRESS_REQ (20, "PROGRESS_REQ"),
    PROGRESS_RES (21, "PROGRESS_RES"),

    // 피드백
    FEEDBACK_REQ (22, "FEEDBACK_REQ"),
    FEEDBACK_RES (23, "FEEDBACK_RES");

    // 메시지 인덱스
    private int i = 0;
    // 메시지 타입
    private String type;

    // 생성자
    private MessageType(int i, String type) {
        this.i = i;
        this.type = type;
    }
    
    // 인덱스 리턴
    public int getInx() {
        return i;
    }
    
    // 메시지 타입 리턴
    public String getType() {
        return type;
    }
}