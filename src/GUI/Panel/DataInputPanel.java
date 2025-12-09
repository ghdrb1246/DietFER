package GUI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataInputPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;

    public DataInputPanel() {
        setLayout(new BorderLayout());

        // 상단 버튼 영역
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnDiet = new JButton("식단 입력");
        JButton btnExercise = new JButton("운동 입력");
        JButton btnWeight = new JButton("체중 입력");

        topPanel.add(btnDiet);
        topPanel.add(btnExercise);
        topPanel.add(btnWeight);

        add(topPanel, BorderLayout.NORTH);

        // 하단 테이블 영역
        String[] cols = { "타입", "날짜/시간", "이름", "추가정보", "수치" };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 클릭 이벤트 (다음 단계에서 Dialog 연결)
        btnDiet.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "식단 입력 JDialog 다음 단계에서 구현");
        });

        btnExercise.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "운동 입력 JDialog 다음 단계에서 구현");
        });

        btnWeight.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "체중 입력 JDialog 다음 단계에서 구현");
        });
    }
}
