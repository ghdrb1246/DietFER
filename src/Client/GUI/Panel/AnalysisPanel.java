package Client.GUI.Panel;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Frame.MainFrame;
import Common.FeedbackResult;
import Common.Progress;

import java.awt.*;

// 다이어트 분석 패널 클래스
public class AnalysisPanel extends JPanel {
    private DailySummaryPanel dailySummaryPanel;
    private WeightAchievementPanel weightAchievementPanel;
    private RecommendationPanel recommendationPanel;
    private NutritionAnalysisPanel nutritionAnalysisPanel;

    private MainFrame mainFrame;
    private String id;
    private ClientSender sender;
    private MessageRouter mr;

    // 패널들 초기화
    public AnalysisPanel(String id, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        this.id = id;
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        
        mr.setFrame(mainFrame);
        
        setLayout(new BorderLayout(10, 10));
        dailySummaryPanel = new DailySummaryPanel();
        weightAchievementPanel = new WeightAchievementPanel();
        recommendationPanel = new RecommendationPanel();
        nutritionAnalysisPanel = new NutritionAnalysisPanel();
        
        // 상단 패널 
        // 일일 요약 + 체중 달성률
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        topPanel.add(dailySummaryPanel);
        topPanel.add(weightAchievementPanel);

        // 상단 높이를 작게 변경
        topPanel.setPreferredSize(new Dimension(0, 60));

        // 하단패널 
        // 추천 + 영양 분석
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        bottomPanel.add(recommendationPanel);
        bottomPanel.add(nutritionAnalysisPanel);

        // 두 패널 배치
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    /* public void handleFeedbackRes(String id, FeedbackResult fr) {
        dailySummaryPanel.handleDailySummary(fr);
        recommendationPanel.handleRecommendation(fr);
        nutritionAnalysisPanel.handleFeedback(id, fr);
    }
    
    public void handleProgressRes(String id, Progress p) {
        System.out.println("handleProgressRes");
        weightAchievementPanel.handleProgress(id, p);
    } */

    // 피드백 패널 업데이트
    public void updateFeedback(FeedbackResult fr) {
        System.out.println("updateFeedback");
        dailySummaryPanel.update(fr);
        recommendationPanel.update(fr);
        nutritionAnalysisPanel.update(fr);
    }

    // 달성률 패널 업데이트
    public void updateProgress(Progress p) {
        weightAchievementPanel.update(p);
    }
}

// 일일 요약 패널
// 표시 내용 -> 섭취 kcal, 잔여 kcal, 소모 kcal, 권장 kcal
class DailySummaryPanel extends JPanel {
    private JLabel lblIntake, lblRemain, lblBurn, lblRecommended;

    public DailySummaryPanel() {
        setBorder(BorderFactory.createTitledBorder("일일 요약"));
        setLayout(new GridLayout(2, 2));

        lblIntake = new JLabel("섭취: 0 kcal");
        lblRemain = new JLabel("잔여: 0 kcal");
        lblBurn = new JLabel("소모: 0 kcal");
        lblRecommended = new JLabel("권장: 2000 kcal");

        add(lblIntake);
        add(lblRemain);
        add(lblBurn);
        add(lblRecommended);
    }

    public void update(FeedbackResult fr) {
        // Swing은 스레드에서만 UI 갱신해야 함
        SwingUtilities.invokeLater(() -> {
            int intake = fr.getIntake();
            int burn = fr.getBurn();
            int recommended = fr.getRecommendCal();

            lblIntake.setText("섭취: " + intake + " kcal");
            lblBurn.setText("소모: " + burn + " kcal");
            lblRecommended.setText("권장: " + recommended + " kcal");
            lblRemain.setText("잔여: " + (recommended - intake + burn) + " kcal");
        });
    }
    /* 
    public void handleDailySummary(FeedbackResult fr) {
        if (!fr.getSuccess()) {
            lblIntake.setText("-");
            lblBurn.setText("-");
            lblRemain.setText("-");
            lblRecommended.setText("-");
            return;
        }
        // update(fr.getIntake(), fr.getBurn(), fr.getRecommendCal());
        // lblIntake.setText("섭취 : " + fr.getIntake() + " kcal");
        // lblBurn.setText("소모 : " + fr.getBurn() + " kcal");
        // lblRemain.setText("잔여 : " + fr.getRemain() + " kcal");
        // lblRecommended.setText("권장 : " + fr.getRecommendCal() + " kcal");
    } */
}

// 체중 달성률 패널
// ProgressBar (0~100%) 옆에는 초기, 목표, 중앙에는 현재 표시
class WeightAchievementPanel extends JPanel {
    private JProgressBar bar;
    private JLabel lblInfo;

    public WeightAchievementPanel() {
        setBorder(BorderFactory.createTitledBorder("체중 달성률"));
        setLayout(new BorderLayout());

        bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setString("0%");
        lblInfo = new JLabel("초기: 0kg / 현재: 0kg / 목표: 0kg", SwingConstants.CENTER);

        add(lblInfo, BorderLayout.NORTH);
        add(bar, BorderLayout.CENTER);
    }

    public void update(Progress p) {
        // Swing은 스레드에서만 UI 갱신해야 함
        SwingUtilities.invokeLater(() -> {
            double start = p.getInitial(); // 초기
            double goal = p.getGoal(); // 목표
            double current = p.getCurrent(); // 현재
            
            lblInfo.setText("초기: " + start + "kg / 현재: " + current + "kg / 목표: " + goal + "kg");

            double rate = ((start - current) / (start - goal)) * 100;
            rate = Math.max(0, Math.min(100, rate));

            bar.setValue((int) rate);
            bar.setString((int) rate + "%");

            // 디버깅용
            System.out.println("Progress UI updated: " + rate + "%");
        });
    }
/* 
    public void handleProgress(String id, Progress p) {
        // 체중 달성률 UI 갱신
        update(p.getGoal(), p.getInitial(), p.getCurrent());
        
        // weightBar.setMinimum((int) p.getGoal()); // 목표(작은 값)
        // weightBar.setMaximum((int) p.getInitial()); // 초기체중
        // weightBar.setValue((int) p.getCurrent()); // 현재 체중

        repaint();
    } */
}

// 식단/운동 추천 패널
// 추천 텍스트 표시
class RecommendationPanel extends JPanel {
    private JTextArea txtArea;

    public RecommendationPanel() {
        setBorder(BorderFactory.createTitledBorder("식단 / 운동 추천"));
        setLayout(new BorderLayout());

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setText("추천 정보가 여기에 표시됩니다.");

        add(new JScrollPane(txtArea), BorderLayout.CENTER);
    }

    public void update(FeedbackResult fr) {
        // Swing은 스레드에서만 UI 갱신해야 함
        SwingUtilities.invokeLater(() -> {
            if (!fr.getSuccess()) {
                txtArea.setText("추천 없음");
                return;
            }

            // 음식 추천
            StringBuilder foodSb = new StringBuilder();
            for (String food : fr.getFoodRecommend()) {
                foodSb.append("- ").append(food).append("\n");
            }
            txtArea.setText(foodSb.toString());
            // foodArea.setText(foodSb.toString());

            // 운동 추천
            StringBuilder exerciseSb = new StringBuilder();
            for (String exercise : fr.getExerciseRecommend()) {
                exerciseSb.append("- ").append(exercise).append("\n");
            }
            txtArea.setText(foodSb.toString());
        });
    }
}

// 분석 내용
// 탄수화물:currentCarbs/recommendedCarbs
// 단백질:currentProtein/recommendedProtein
// 지방:currentFat/recommendedFat

// 판정 기준
// <90%=부족
// 90%~110%=양호
// 110%=초과
// 영양 분석 패널 (탄·단·지 게이지 + 피드백)
class NutritionAnalysisPanel extends JPanel {
    private JProgressBar barCarbs, barProtein, barFat;
    private JLabel lblCarbsInfo, lblProteinInfo, lblFatInfo;
    private JTextArea txtFeedback;

    public NutritionAnalysisPanel() {
        setBorder(BorderFactory.createTitledBorder("영양 분석"));
        setLayout(new GridLayout(4, 1));

        // 탄수화물
        JPanel p1 = new JPanel(new BorderLayout());
        barCarbs = new JProgressBar(0, 100);
        barCarbs.setStringPainted(true);
        lblCarbsInfo = new JLabel("탄수화물: 0g / 0g");
        p1.add(lblCarbsInfo, BorderLayout.NORTH);
        p1.add(barCarbs, BorderLayout.CENTER);

        // 단백질
        JPanel p2 = new JPanel(new BorderLayout());
        barProtein = new JProgressBar(0, 100);
        barProtein.setStringPainted(true);
        lblProteinInfo = new JLabel("단백질: 0g / 0g");
        p2.add(lblProteinInfo, BorderLayout.NORTH);
        p2.add(barProtein, BorderLayout.CENTER);

        // 지방
        JPanel p3 = new JPanel(new BorderLayout());
        barFat = new JProgressBar(0, 100);
        barFat.setStringPainted(true);
        lblFatInfo = new JLabel("지방: 0g / 0g");
        p3.add(lblFatInfo, BorderLayout.NORTH);
        p3.add(barFat, BorderLayout.CENTER);

        // 피드백
        txtFeedback = new JTextArea();
        txtFeedback.setEditable(false);
        txtFeedback.setBorder(BorderFactory.createTitledBorder("피드백"));

        add(p1);
        add(p2);
        add(p3);
        add(new JScrollPane(txtFeedback));
    }

    public void update(FeedbackResult fr) {
        // Swing은 스레드에서만 UI 갱신해야 함
        SwingUtilities.invokeLater(() -> {
            int c = fr.getCarbIntake();
            int cRec = fr.getCarbRecommend();
            int p = fr.getProteinIntake();
            int pRec = fr.getProteinRecommend();
            int f = fr.getFatIntake();
            int fRec = fr.getFatRecommend();
            
            System.out.println("c : " + c + "cRec : " + cRec + "p : " + p + "pRec : " + pRec + "f : " + f + "fRec : " + fRec);

            // 게이지 반영 %
            barCarbs.setValue((int) ((double) c / (cRec * 100)));
            barProtein.setValue((int) ((double) p / (pRec * 100)));
            barFat.setValue((int) ((double) f / (fRec * 100)));

            lblCarbsInfo.setText("탄수화물: " + c + " / " + cRec);
            lblProteinInfo.setText("단백질: " + p + " / " + pRec);
            lblFatInfo.setText("지방: " + f + " / " + fRec);

            // 판정
            StringBuilder fb = new StringBuilder();
            if (c > cRec * 1.1)
                fb.append("탄수화물 초과. ");
            else if (c < cRec * 0.9)
                fb.append("탄수화물 부족. ");

            if (p > pRec * 1.1)
                fb.append("단백질 초과. ");
            else if (p < pRec * 0.9)
                fb.append("단백질 부족. ");

            if (f > fRec * 1.1)
                fb.append("지방 초과. ");
            else if (f < fRec * 0.9)
                fb.append("지방 부족. ");

            if (fb.length() == 0)
                fb.append("영양 섭취가 균형적입니다.");

            // txtFeedback.setText(fb.toString());
            // 추천 식단/운동 갱신
            txtFeedback.setText(String.join("\n", fr.getFoodRecommend()));
            txtFeedback.setText(String.join("\n", fr.getExerciseRecommend()));

            // 영양소 게이지는 패널 구성에 따라 별도 적용
            repaint();
        });
    }
}
