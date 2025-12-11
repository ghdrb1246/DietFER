package Client;
/**
 * MenuCallback 인터페이스를 구현
 * MessageHandler에 자신을 등록
 * 콜백으로 받은 결과에 따라 화면 메뉴를 변경하거나 출력
 */
public interface IMessageCallback {
    /**
     * 프로그램 종료 승인 처리
    */
    public void onTerminateProgramOk();

    /**
     * 프로그램 종료 거부 처리
     */
    public void onTerminateProgramFail();

    /**
     * 로그인 결과 처리 후 보낸 OK, FAIL 결과를 표시
     * 
     * @param _id  사용자 id
     * @param _res 처리 결과
     */
    public void onLoginRes(String _id, String _res);

    /**
     * 회원가입 결과 처리 후 보낸 OK, FAIL 결과를 표시
     * 
     * @param _res 처리 결과
     */
    public void onSignupRes(String _res);

    /**
     * 회원 정보 수정 결과 처리 후 보낸 OK, FAIL 결과를 표시
     * 
     * @param id
     * @param result
     */
    public void onUserUpdateRes(String id, String result);

    // 로그아웃
    public void logoutRes(String result);

    // 화원 탈퇴
    public void userDeleteRes(String userId, String result);

    // 식단 입력
    public void mealAddRes(String userId, String result);

    // 운동 입력
    public void workoutAddRes(String userId, String result);

    // 현재 체중 입력
    public void weightAddRes(String result);

    // 기록
    public void recordRes(String userId, String result);

    // 진행률
    public void progressRes(String userId, String result);

    // 피드백
    public void feedbackRes(String userId, String result);
    
    /**
     * 메시지 처리 에러 출력
     * 
     * @param _msg 예러 출력 메시지
     * @param _e 예외
     */
    public void onMessageError(String _msg, Exception _e);
    
    /**
     * 메시지 처리 출력
     * 
     * @param _msg 출력 메시지
     */
    public void onMessage(String _msg);
}