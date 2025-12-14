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
    public void onLogoutRes(String result);

    // 식단 입력
    public void onMealAddRes(String userId, String result);

    // 운동 입력
    public void onWorkoutAddRes(String userId, String result);

    // 현재 체중 입력
    public void onWeightAddRes(String userId, String result);

    // 기록
    public void onRecordRes(String userId, ArrayList<RecordData> list);

    // 진행률
    public void onProgressRes(String userId, Progress p);

    // 피드백
    public void onFeedbackRes(String userId, FeedbackResult fr);
    
    // 음식 검색
    public void onFoodSearchRes(String userId, ArrayList<FoodNutrition> fn);
    
    // 메시지
    // public void onNormalMessageRes(String result);

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