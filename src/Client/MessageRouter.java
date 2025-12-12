package Client;

import java.util.ArrayList;
import Client.GUI.Dialog.*;
import Client.GUI.Frame.MainFrame;
import Common.FeedbackResult;
import Common.Progress;
import Common.RecordData;

public class MessageRouter implements IMessageCallback {
    private EntryDialog entryDialog;
    private RegisterDialog registerDialog;
    private MainFrame mainFrame;

    public void setEntryDialog(EntryDialog ed) {
        this.entryDialog = ed;
        System.out.println("entryDialog set");
    }
    public void setRegisterDialog(RegisterDialog rd) {
        this.registerDialog = rd;
        System.out.println("registerDialog set");
    }

    public void setMainFrame(MainFrame mf) {
        this.mainFrame = mf;
    }

    /**
     * 프로그램 종료 승인 처리
     */
    @Override
    public void onTerminateProgramOk() {}

    /**
     * 프로그램 종료 거부 처리
     */
    @Override
    public void onTerminateProgramFail() {}

    // 로그인
    @Override
    public void onLoginRes(String id, String result) {
        if (entryDialog != null) entryDialog.handleLoginRes(id, result);
        else System.out.println("entryDialog이 Null임");
    }

    // 회원가입
    @Override
    public void onSignupRes(String id, String result) {
        if (registerDialog != null) registerDialog.handleSignupRes(id, result);
        else System.out.println("registerDialog이 Null임");
    }
    
    /**
     * 회원 정보 수정 결과 처리 후 보낸 OK, FAIL 결과를 표시
     * 
     * @param id
     * @param result
     */
    @Override
    public void onUserUpdateRes(String id, String result) {}

    // 로그아웃
    @Override
    public void onLogoutRes(String result) { }

    // 화원 탈퇴
    @Override
    public void onUserDeleteRes(String userId, String result) {}

    // 식단 입력
    @Override
    public void onMealAddRes(String userId, String result) {}

    // 운동 입력
    @Override
    public void onWorkoutAddRes(String userId, String result) {}

    // 체중 입력
    @Override
    public void onWeightAddRes(String userId, String result) {}

    // 기록
    @Override
    public void onRecordRes(String userId, ArrayList<RecordData> list) {}

    // 진행률
    @Override
    public void onProgressRes(String userId, Progress p) {}

    // 피드백
    @Override
    public void onFeedbackRes(String userId, FeedbackResult fr) {}

    // 메시지
    @Override
    public void onNormalMessageRes(String result) {}

    /**
     * 메시지 처리 에러 출력
     * 
     * @param _msg 예러 출력 메시지
     * @param _e   예외
     */
    public void onMessageError(String _msg, Exception _e) {}

    /**
     * 메시지 처리 출력
     * 
     * @param _msg 출력 메시지
     */
    public void onMessage(String _msg) {}
    

    public interface LoginHandler {
        void handleLoginRes(String id, String result);
    }

    public interface SignupHandler {
        void handleSignupRes(String id, String result);
    }
    
    public interface MealHandler {
        void handleMealAddRes(String userId, String result);
    }

    public interface WorkoutHandler {
        void handleWorkoutAddRes(String userId, String result);
    }

    public interface WeightHandler {
        void handleWeightAddRes(String userId, String result);
    }
    
    public interface FeedbackHandler {
        void handleFeedbackRes(String userId, FeedbackResult result);
    }

    public interface ProgressHandler {
        void handleProgressRes(String userId, Progress progress);
    }
    
    public interface RecordHandler {
        void handleRecordRes(String userId, ArrayList<RecordData> list);
    }

}