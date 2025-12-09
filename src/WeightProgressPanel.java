import javax.swing.*;
import java.awt.*;

public class WeightProgressPanel extends JPanel {

    private JProgressBar progressBar;
    private JLabel lblStart, lblCurrent, lblGoal;

    public WeightProgressPanel(double startWeight, double currentWeight, double goalWeight) {

        setLayout(new BorderLayout(10, 10));

        // ----- Weight Labels -----
        lblStart = new JLabel("기초: " + startWeight + "kg");
        lblGoal = new JLabel("목표: " + goalWeight + "kg");
        lblCurrent = new JLabel("현재: " + currentWeight + "kg", SwingConstants.CENTER);

        // ----- ProgressBar 설정 -----
        progressBar = new JProgressBar();

        // 절대값 기반 계산
        int max = (int) Math.abs(startWeight - goalWeight);
        int value = (int) Math.abs(startWeight - currentWeight);

        progressBar.setMinimum(0);
        progressBar.setMaximum(max);
        progressBar.setValue(value);
        progressBar.setStringPainted(false); // 숫자 출력 안함

        // ----- 상단: 기초 / 목표 라벨 -----
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(lblStart, BorderLayout.WEST);
        northPanel.add(lblGoal, BorderLayout.EAST);

        // ----- UI 배치 -----
        add(northPanel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        add(lblCurrent, BorderLayout.SOUTH);
    }

    // 테스트 실행
    public static void main(String[] args) {
        JFrame f = new JFrame("체중 달성률");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 예시 데이터
        WeightProgressPanel panel = new WeightProgressPanel(85, 77, 70);

        f.add(panel);
        f.setSize(400, 150);
        f.setVisible(true);
    }
}
