package Common;

/**
 * <p>
 * 1.회원가입 요청 -> SIGNUP_REQ/사용자 ID/PW/성별/키/나이/초기 체중/목표 체중
 * </p>
 * 
 * <p>
 * 2.회원가입 처리 -> SIGNUP_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
 * </p>
 * 
 * <p>
 * 3.로그인 요청 -> LOGIN_REQ/사용자 ID/PW
 * </p>
 * 
 * <p>
 * 4.로그인 처리 -> LOGIN_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
 * </p>
 * 
 * <p>
 * 5.로그아웃 요청 -> LOGOUT_REQ/사용자 ID
 * </p>
 * 
 * <p>
 * 6.음식 저장 요청 -> MEAL_ADD_REQ/사용자 ID/날짜+시간/음식 타입/음식명/섭취량/칼로리/탄수회물/단백질/지방
 * </p>
 * 
 * <p>
 * 7.음식 저장 처리 -> MEAL_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
 * </p>
 * 
 * <p>
 * 8.운동 저장 요청 -> WORKOUT_ADD_REQ/사용자 ID/날짜-시간/운동명/운동량
 * </p>
 * 
 * <p>
 * 9.운동 저장 처리 -> WORKOUT_ADD_RES/처리 결과(“OK” or “FAIL”)
 * </p>
 * 
 * <p>
 * 10.체중 저장 요청 -> WEIGHT_ADD_REQ/사용자 ID/날짜/체중
 * </p>
 * 
 * <p>
 * 11.체중 저장 처리 -> WEIGHT_ADD_RES/처리 결과(“OK” or “FAIL”)
 * </p>
 * 
 * <p>
 * 12.기록 요청 -> RECORD_REQ/사용자 ID
 * </p>
 * 
 * <p>
 * 13.기록 처리 -> RECORD_RES/사용자 ID/처리 결과(날짜1(식단명,운동명,체중)+…+날짜n(식단명,운동명,체중) or
 * “FAIL”)
 * </p>
 * 
 * <p>
 * 14.진행률 요청 -> PROGRESS_REQ/사용자 ID
 * </p>
 * 
 * <p>
 * 15.진행률 처리 -> PROGRESS_RES/사용자 ID/처리 결과(초기 체중/목표 체중/현재 체중/달성률 or “FAIL”)
 * </p>
 * 
 * <p>
 * 16.피드백 요청 -> FEEDBACK_REQ/사용자 ID
 * </p>
 * 
 * <p>
 * 17.피드백 처리 -> FEEDBACK_RES/사용자 ID/처리
 * </p>
 * 결과(섭취+소모+잔여+권장/탄수화물_섭취량+탄수화물_권장량/단백질_섭취량+단백질_권장량/지방_섭취량+지방_권장량/음식 추천 리스트/운동
 * 추천 리스트 or “FAIL”)
 * </p>
 * 
 * <p>
 * 18.음식 검색 요청 -> FOOD_SEARCH_REQ/사용자 ID/검색어
 * </p>
 * 
 * <p>
 * 19.음식 검색 처리 -> FOOD_SEARCH_RES/사용자 ID/처리 결과(음식명+칼로리+탄수화물+단백질+지방/…/음식명+칼로리+탄수화물+단백질+지방 or “FAIL”)
 * </p>
 */
public enum MessageType {
    // 회원가입
    SIGNUP_REQ (0, "SIGNUP_REQ"),
    SIGNUP_RES (1, "SIGNUP_RES"),

    // 로그인
    LOGIN_REQ (2, "LOGIN_REQ"),
    LOGIN_RES (3, "LOGIN_RES"),

    // 로그아웃
    LOGOUT_REQ (4, "LOGOUT_REQ"),

    // 식단 추가
    MEAL_ADD_REQ (5, "MEAL_ADD_REQ"),
    MEAL_ADD_RES (6, "MEAL_ADD_RES"),

    // 운동 추가
    WORKOUT_ADD_REQ (7, "WORKOUT_ADD_REQ"),
    WORKOUT_ADD_RES (8, "WORKOUT_ADD_RES"), 

    // 체중 추가
    WEIGHT_ADD_REQ (9, "WEIGHT_ADD_REQ"),
    WEIGHT_ADD_RES (10, "WEIGHT_ADD_RES"),

    // 기록 조회
    RECORD_REQ (11, "RECORD_REQ"),
    RECORD_RES (12, "RECORD_RES"),

    // 진행률
    PROGRESS_REQ (13, "PROGRESS_REQ"),
    PROGRESS_RES (14, "PROGRESS_RES"),

    // 피드백
    FEEDBACK_REQ (15, "FEEDBACK_REQ"),
    FEEDBACK_RES (16, "FEEDBACK_RES"),

    // 검색어
    FOOD_SEARCH_REQ(17, "FOOD_SEARCH_REQ"),
    FOOD_SEARCH_RES(18, "FOOD_SEARCH_RES");

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