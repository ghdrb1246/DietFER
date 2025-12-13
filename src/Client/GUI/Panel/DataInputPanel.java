package Client.GUI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Dialog.DietDialog;
import Client.GUI.Dialog.WorkoutDialog;
import Client.GUI.Dialog.WeightDialog;
import Client.GUI.Frame.MainFrame;
import Common.RecordData;

import java.awt.*;
import java.util.ArrayList;

public class DataInputPanel extends JPanel {
    private MainFrame mainFrame;
    private DefaultTableModel tableModel;
    private JTable table;
    private String userId;
    private ClientSender sender;
    private MessageRouter mr;

    public DataInputPanel(String userId, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        this.userId = userId;
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
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
            new DietDialog(userId, SwingUtilities.getWindowAncestor(this), mainFrame, sender, mr).setVisible(true);
        });

        btnExercise.addActionListener(e -> {
            new WorkoutDialog(userId, SwingUtilities.getWindowAncestor(this), mainFrame, sender, mr).setVisible(true);
        });

        btnWeight.addActionListener(e -> {
            new WeightDialog(userId, SwingUtilities.getWindowAncestor(this), mainFrame, sender, mr).setVisible(true);
        });
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void handleRecordRes(String userId, ArrayList<RecordData> list) {
        // 기존 테이블 초기화
        tableModel.setRowCount(0);
        //  "타입", "날짜/시간", "이름", "추가정보", "수치" 
        for (RecordData r : list) {
            // 식단
            if (r.getMealName() != null) {
                tableModel.addRow(new Object[]{
                    "식단", 
                    r.getDate(), 
                    r.getMealName(), 
                    r.getMealType(r.getDate().toLocalTime()), 
                    ""           
                });
            }

            // 운동
            if (r.getWorkoutName() != null) {
                tableModel.addRow(new Object[] {
                    "운동", 
                    r.getDate(), 
                    r.getWorkoutName(), 
                    "", 
                    ""
                });
            }

            // 체중
            if (r.getWeight() != null) {
                tableModel.addRow(new Object[] {
                    "체중", 
                    r.getDate(), 
                    "", 
                    "", 
                    r.getWeight()
                });
            }
        }
    }
}
