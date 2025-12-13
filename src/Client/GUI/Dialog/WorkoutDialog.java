package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Frame.MainFrame;
import Client.GUI.Panel.DataInputPanel;
import Common.Exercise;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;
import Common.Workout;
import Server.CSV.ExerciseCSVDAO;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// 운동 입력 Dialog
public class WorkoutDialog extends JDialog {
    private MainFrame mainFrame;
    private ClientSender sender;
    private MessageRouter mr;
    private String userId;
    private JTextField txtDateTime;
    private JComboBox<String> cbExercise;
    private JTextField txtHour;
    private MessageBuilder mb = new MessageBuilder();

    public WorkoutDialog(String userId, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "운동 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.userId = userId;

        mr.setDialog(this);

        setLayout(new BorderLayout(10, 10));
        
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        
        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);
        
        form.add(new JLabel("운동명"));

        ArrayList<Exercise> exercises = new ExerciseCSVDAO().loadExercises();
        cbExercise = new JComboBox<>();

        for (Exercise e : exercises) {
            cbExercise.addItem(e.getName());
        }

        form.add(cbExercise);

        form.add(new JLabel("운동 시간(h)"));
        txtHour = new JTextField();
        form.add(txtHour);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("저장");
        JButton btnCancel = new JButton("취소");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            TimeConversion tc = new TimeConversion();
            LocalDateTime datetime = tc.inputToTimeString(txtDateTime.getText());
            String exercise = (String) cbExercise.getSelectedItem();
            Double hours = Double.parseDouble(txtHour.getText());
            // TODO : kcal 계산 구현
            Workout w = new Workout(datetime, exercise, hours, 0);

            sender.sendMSG(MessageType.WORKOUT_ADD_REQ, mb.workoutAddReq(userId, w));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(350, 210);
        setLocationRelativeTo(owner);
    }

    public void handleWorkoutAddRes(String userId, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "운동 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();

            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(userId));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "운동 저장 실패");
        }
    }
}