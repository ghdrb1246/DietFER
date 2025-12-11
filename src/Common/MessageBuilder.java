package Common;
/**
 * 메시지 생성 클래스
 */
public class MessageBuilder {
    /**
     * 기본 메시지 생성기
     * 형식: TYPE/arg1/arg2/arg3...
     */
    public String build(MessageType type, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());

        if (args != null) {
            for (String arg : args) {
                sb.append("/");
                sb.append(arg == null ? "" : arg.trim());
            }
        }
        return sb.toString();
    }

    // 개별 메시지 전용 편의 함수

    /**
     * 프로그램 종료 요청
     * 
     * @param userId 사용자 ID
     * @return EXIT_REQ/ID
     */
    public String exitReq(String userId) {
        return build(MessageType.EXIT_REQ, userId);
    }

    /**
     * 프로그램 종료 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return EXIT_RES/ID/(“OK” or “FAIL”)
     */
    public String exitRes(String userId, String result) {
        return build(MessageType.EXIT_RES, userId, result);
    }

    /**
     * 회원가입 요청
     * 
     * @param u 사용자 객체
     * @return SIGNUP_REQ/사용자 ID/PW/성별/키/나이/초기 체중/목표 체중
     */
    public String signupReq(User u) {
        return build(
            MessageType.SIGNUP_REQ, 
            u.getID(),
            u.getPW(), 
            String.valueOf(u.getSex()), 
            String.valueOf(u.getHeight()), 
            String.valueOf(u.getAge()), 
            String.valueOf(u.getStartWeight()), 
            String.valueOf(u.getGoalWeight())
        );
    }

    /**
     * 회원가입 처리 결과
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     * @return       SIGNUP_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String signupRes(String id, String result) {
        return build(MessageType.SIGNUP_RES, id, result);
    }

    /**
     * 로그인 요청
     * 
     * @param id 사용자 ID
     * @param pw 사용자 비밀번호
     * @return LOGIN_REQ/사용자 ID/PW
     */
    public String loginReq(String id, String pw) {
        return build(MessageType.LOGIN_REQ, id, pw);
    }

    /**
     * 로그인 처리 결과
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     * @return      LOGIN_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String loginRes(String id, String result) {
        return build(MessageType.LOGIN_RES, id, result);
    }

    /**
     * 회원 정보 수정 요청
     * 
     * @param u
     * @return USER_UPDATE_REQ/성별/키/나이/초기 체중/목표 체중
     */
    public String userUpdateReq(User u) {
        return build(
            MessageType.USER_UPDATE_REQ, 
            String.valueOf(u.getSex()), 
            String.valueOf(u.getHeight()),
            String.valueOf(u.getAge()), 
            String.valueOf(u.getStartWeight()), 
            String.valueOf(u.getGoalWeight())
        );
    }

    /**
     * 회원 정보 수정 처리 결과
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     * @return USER_UPDATE_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String userUpdateRes(String id, String result) {
        return build(MessageType.USER_UPDATE_RES, id, result);
    }

    /**
     * 로그아웃 요청
     * 
     * @param userId 사용자 ID
     * @return LOGOUT_REQ/사용자 ID
     */
    public String logoutReq(String userId) {
        return build(MessageType.LOGOUT_REQ, userId);
    }

    /**
     * 로그아웃 처리 결과
     * 
     * @param result 처리 결과
     * @return LOGOUT_RES/처리 결과(“OK” or “FAIL”)
     */
    public String logoutRes(String result) {
        return build(MessageType.LOGOUT_RES, result);
    }

    /**
     * 회원 탈퇴 요청
     * 
     * @param userId 사용자 ID
     * @return USER_DELETE_REQ/사용자 ID
     */
    public String userDeleteReq(String userId) {
        return build(MessageType.USER_DELETE_REQ, userId);
    }

    /**
     * 회원 탈퇴 처리 결과
     * 
     * @param result 처리 결과
     * @return USER_DELETE_REQ/처리 결과(“OK” or “FAIL”)
     */
    public String userDeleteRes(String userId, String result) {
        return build(MessageType.USER_DELETE_RES, result);
    }

    /**
     * 식단 입력 추가 요청
     * 
     * @param m 음식
     * @return MEAL_ADD_REQ/사용자 ID/날짜+시간/음식 타입/음식명/섭취량
     */
    public String mealAddReq(Meal m) {
        return build(
            MessageType.MEAL_ADD_REQ, 
            m.getId(), 
            m.getDateTime().toString(),
            m.getFoodName(), 
            String.valueOf(m.getGram())
        );
    }

    /**
     * 식단 입력 추가 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return MEAL_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String mealAddRes(String userId, String result) {
        return build(MessageType.MEAL_ADD_RES, result);
    }

    /**
     * 운동 입력 추가 요청
     * 
     * @param w 운동
     * @return WORKOUT_ADD_REQ/사용자 ID/날짜+시간/운동명/운동 시간
     */
    public String workoutAddReq(Workout wk) {
        return build(
            MessageType.WORKOUT_ADD_REQ, 
            wk.getId(), 
            wk.getDateTime().toString(), 
            wk.getExerciseName(), 
            String.valueOf(wk.getMinutes())
        );
    }

    /**
     * 운동 입력 추가 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return WORKOUT_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String workoutAddRes(String userId, String result) {
        return build(MessageType.WORKOUT_ADD_RES, result);
    }

    /**
     * 현재 체중 입력 추가 요청
     * 
     * @param w 체중
     * @return WEIGHT_ADD_REQ/사용자 ID/날짜/체중
     */
    public String weightAddReq(Weight w) {
        return build(MessageType.WEIGHT_ADD_REQ, w.getId(), w.getDate().toString(), String.valueOf(w.getWeight()));
    }

    /**
     * 현재 체중 입력 추가 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return WEIGHT_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String weightAddRes(String result) {
        return build(MessageType.WEIGHT_ADD_RES, result);
    }

    /**
     * 기록 조회 요청
     * 
     * @param userId 사용자 ID
     * @return RECORD_REQ/사용자 ID
     */
    public String recordReq(String userId) {
        return build(MessageType.RECORD_REQ, userId);
    }

    /**
     * 기록 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return RECORD_RES/사용자 ID/처리 결과(날짜1(식단명,운동명,체중)+…+날짜n(식단명,운동명,체중) or “FAIL”)
     */
    public String recordRes(String userId, String result) {
        return build(MessageType.RECORD_RES, userId, result);
    }

    /**
     * 진행률 요청
     * 
     * @param userId 사용자 ID
     * @return PROGRESS_REQ/사용자 ID
     */
    public String progressReq(String userId) {
        return build(MessageType.PROGRESS_REQ, userId);
    }

    /**
     * 진행률 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return PROGRESS_RES/사용자 ID/처리 결과(초기 체중/목표 체중/현재 체중/달성률 or “FAIL”)
     */
    public String progressRes(String userId, String result) {
        return build(MessageType.PROGRESS_RES, userId, result);
    }

    /**
     * 피드백 요청
     * 
     * @param userId 사용자 ID
     * @return FEEDBACK_REQ/사용자 ID
     */ 
    public String feedbackReq(String userId) {
        return build(MessageType.FEEDBACK_REQ, userId);
    }

    /**
     * 피드백 처리 결과
     * 
     * @param userId 사용자 ID
     * @param result 처리 결과
     * @return FEEDBACK_RES/
     * 사용자 ID/
     * 처리 결과(
     *  섭취+소모+잔여+권장/
     *  탄수화물_섭취량+탄수화물_권장량/
     *  단백질_섭취량+단백질_권장량/
     *  지방_섭취량+지방_권장량/
     *  음식 추천 리스트/
     *  운동 추천 리스트 or “FAIL”
     * )
     */
    public String feedbackRes(String userId, String result) {
        return build(MessageType.FEEDBACK_RES, userId, result);
    }
}
