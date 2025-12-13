package Client;

import java.util.ArrayList;
import Client.GUI.Dialog.*;
import Client.GUI.Frame.MainFrame;
import Client.GUI.Panel.AnalysisPanel;
import Client.GUI.Panel.DataInputPanel;
import Common.FeedbackResult;
import Common.Progress;
import Common.RecordData;

public class MessageRouter implements IMessageCallback {
    private EntryDialog entryDialog;
    private RegisterDialog registerDialog;
    private MainFrame mainFrame;
    private DietDialog dietDialog;
    private WorkoutDialog workoutDialog;
    private WeightDialog weightDialog;
    private DataInputPanel dataInputPanel;
    private AnalysisPanel analysisPanel;

    // 로그인
    public void setDialog(EntryDialog ed) {
        this.entryDialog = ed;
        System.out.println("entryDialog set");
    }
    // 회원가입
    public void setDialog(RegisterDialog rd) {
        this.registerDialog = rd;
        System.out.println("registerDialog set");
    }
    // 음식
    public void setDialog(DietDialog dd) {
        this.dietDialog = dd;
        System.out.println("dietDialog set");
    }
    // 운동
    public void setDialog(WorkoutDialog wod) {
        this.workoutDialog = wod;
        System.out.println("dietDialog set");
    }
    // 체중
    public void setDialog(WeightDialog wed) {
        this.weightDialog = wed;
        System.out.println("weightDialog set");
    }
    // 입력 데이터 테이블에 업데이트
    public void setPanel(DataInputPanel dip) {
        this.dataInputPanel = dip;
        System.out.println("dataInputPanel set");
    }
    // 다이어트 분석
    public void setPanel(AnalysisPanel ap) {
        this.analysisPanel = ap;
        System.out.println("analysisPanel set");
    }
    // 메인 프래임
    public void setFrame(MainFrame mf) {
        this.mainFrame = mf;
    }



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
    

    // 회원 정보 수정 결과 처리 후 보낸 OK, FAIL 결과를 표시
    
    // @Override
    // public void onUserUpdateRes(String id, String result) {}

    // 로그아웃
    @Override
    public void onLogoutRes(String result) { }

    // 화원 탈퇴
    // @Override
    // public void onUserDeleteRes(String id, String result) {}

    // 식단 입력
    @Override
    public void onMealAddRes(String id, String result) {
        if (dietDialog != null)
            dietDialog.handleMealAddRes(id, result);
        else
            System.out.println("dietDialog Null임");
    }

    // 운동 입력
    @Override
    public void onWorkoutAddRes(String id, String result) {
        if (workoutDialog != null)
            workoutDialog.handleWorkoutAddRes(id, result);
        else
            System.out.println("workoutDialog Null임");
    }

    // 체중 입력
    @Override
    public void onWeightAddRes(String id, String result) {
        if (weightDialog != null)
            weightDialog.handleWeightAddRes(id, result);
        else
            System.out.println("weightDialog Null임");
    }

    // 기록
    @Override
    public void onRecordRes(String id, ArrayList<RecordData> list) {
        if (dataInputPanel != null)
            dataInputPanel.handleRecordRes(id, list);
        else
            System.out.println("weightDialog Null임");
    }

/*     // 진행률
    @Override
    public void onProgressRes(String id, Progress p) {
        System.out.println("onProgressRes");
        if (analysisPanel != null) {
            System.out.println(p.getInitial());
            analysisPanel.handleProgressRes(id, p);
        }
        else
            System.out.println("analysisPanel Null임");
    }

    // 피드백
    @Override
    public void onFeedbackRes(String id, FeedbackResult fr) {
        if (analysisPanel != null) {
            analysisPanel.handleFeedbackRes(id, fr);
            System.out.println("onFeedbackRes" + id);
        }
        else 
            System.out.println("weightDialog Null임");
    } */

    // 메시지
    // @Override
    // public void onNormalMessageRes(String result) { }
/* 
    // 로그인
    @Override
    public void onLoginRes(String userId, String result) {
        if (mainFrame != null)
            mainFrame.handleLoginRes(userId, result);
    }
    
    // 회원가입
    @Override
    public void onSignupRes(String userId, String result) {
        if (mainFrame != null)
            mainFrame.handleSignupRes(userId, result);
    }

    // 로그아웃 x
    @Override
    public void onLogoutRes(String result) { }

    // 음식 
    @Override
    public void onMealAddRes(String userId, String result) {
        if (mainFrame != null)
            mainFrame.handleMealAddRes(userId, result);
    }

    // 운동
    @Override
    public void onWorkoutAddRes(String userId, String result) {
        if (mainFrame != null)
            mainFrame.handleWorkoutAddRes(userId, result);
    }

    // 체중
    @Override
    public void onWeightAddRes(String userId, String result) {
        if (mainFrame != null)
            mainFrame.handleWeightAddRes(userId, result);
    }

    // 기록
    @Override
    public void onRecordRes(String userId, ArrayList<RecordData> list) {
        if (mainFrame != null)
            mainFrame.handleRecordRes(userId, list);
    }
 */
    // 진행률
    @Override
    public void onProgressRes(String userId, Progress p) {
        if (mainFrame != null) mainFrame.handleProgressRes(userId, p);
        else System.out.println("onProgressRes NULL임");
    }

    // 피드백
    @Override
    public void onFeedbackRes(String userId, FeedbackResult fr) {
        if (mainFrame != null)
            mainFrame.handleFeedbackRes(userId, fr);
    }

    /**
     * 메시지 처리 에러 출력
     * 
     * @param _msg 예러 출력 메시지
     * @param _e   예외
     */
    public void onMessageError(String _msg, Exception _e) {
    }

    /**
     * 메시지 처리 출력
     * 
     * @param _msg 출력 메시지
     */
    public void onMessage(String _msg) {
    }
}