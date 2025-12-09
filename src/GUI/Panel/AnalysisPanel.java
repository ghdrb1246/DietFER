package GUI.Panel;

import javax.swing.*;
import java.awt.*;

public class AnalysisPanel extends JPanel {

    public AnalysisPanel(String userId) {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("다이어트 분석 화면 (사용자: " + userId + ")", SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(18f));

        add(lbl, BorderLayout.NORTH);

        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setText("여기에 체중 변화 그래프, 피드백, 식단/운동 분석 내용이 표시됩니다.\n(다음 단계에서 구현)");
        add(new JScrollPane(txt), BorderLayout.CENTER);
    }
}
