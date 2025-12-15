package Client;

import java.util.ArrayList;

import Common.FeedbackResult;
import Common.FoodNutrition;
import Common.Progress;
import Common.RecordData;


/**
 * MenuCallback 인터페이스를 구현
 * MessageHandler에 자신을 등록
 * 콜백으로 받은 결과에 따라 화면 메뉴를 변경하거나 출력
 */
public interface IMessageCallback {
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
    public void onSignupRes(String _id, String _res);

    /**
     * 회원 정보 수정 결과 처리 후 보낸 OK, FAIL 결과를 표시
     * 
     * @param id
     * @param result
     */
    // public void onUserUpdateRes(String id, String result);

    // 로그아웃
    public void onLogoutRes(String id, String result);

    // 식단 입력
    public void onMealAddRes(String id, String result);

    // 운동 입력
    public void onExerciseAddRes(String id, String result);

    // 현재 체중 입력
    public void onWeightAddRes(String id, String result);

    // 기록
    public void onRecordRes(String id, ArrayList<RecordData> list);

    // 달성률
    public void onProgressRes(String id, Progress p);

    // 피드백
    public void onFeedbackRes(String id, FeedbackResult fr);
    
    // 음식 검색
    public void onFoodSearchRes(String id, ArrayList<FoodNutrition> fn);
    
    /**
     * 메시지 처리 에러 출력
     * 
     * @param msg 예러 출력 메시지
     * @param _e 예외
     */
    public void onMessageError(String msg, Exception _e);
    
    /**
     * 메시지 처리 출력
     * 
     * @param msg 출력 메시지
     */
    public void onMessage(String msg);
}