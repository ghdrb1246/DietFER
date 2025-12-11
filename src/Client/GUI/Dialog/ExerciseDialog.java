package Client.GUI.Dialog;

import javax.swing.*;

import Client.GUI.Panel.DataInputPanel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 운동 입력 Dialog
public class ExerciseDialog extends JDialog {
    private JTextField txtDateTime;
    private JComboBox<String> cbExercise;
    private JTextField txtHour;

    public ExerciseDialog(Window owner, DataInputPanel panel) {
        super(owner, "운동 입력", ModalityType.APPLICATION_MODAL);

        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));

        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);

        form.add(new JLabel("운동명"));
        cbExercise = new JComboBox<>(new String[] { "걷기", "달리기", "수영", "웨이트" });
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
            String type = "운동";
            String datetime = txtDateTime.getText();
            String exercise = (String) cbExercise.getSelectedItem();
            String hours = txtHour.getText();

            panel.addRow(new Object[] {
                    type,
                    datetime,
                    exercise,
                    "",
                    hours + " h"
            });

            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(350, 210);
        setLocationRelativeTo(owner);
    }
}