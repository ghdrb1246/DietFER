package Client.GUI.Dialog;

import javax.swing.*;

import Client.GUI.Panel.DataInputPanel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 체중 입력 Dialog
public class WeightDialog extends JDialog {
    private JTextField txtDateTime;
    private JTextField txtWeight;

    public WeightDialog(Window owner, DataInputPanel panel) {
        super(owner, "체중 입력", ModalityType.APPLICATION_MODAL);

        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));

        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);

        form.add(new JLabel("현재 체중(kg)"));
        txtWeight = new JTextField();
        form.add(txtWeight);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("저장");
        JButton btnCancel = new JButton("취소");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String type = "체중";
            String datetime = txtDateTime.getText();
            String weight = txtWeight.getText();

            panel.addRow(new Object[] {
                    type,
                    datetime,
                    "체중",
                    "",
                    weight + " kg"
            });

            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(320, 180);
        setLocationRelativeTo(owner);
    }
}
