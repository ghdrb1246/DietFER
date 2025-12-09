package GUI.Dialog;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    public RegisterDialog(Dialog owner) {
        super(owner, "회원가입", true);

        JLabel lblId = new JLabel("ID");
        JLabel lblPw = new JLabel("PW");
        JLabel lblGender = new JLabel("성별");
        JLabel lblAge = new JLabel("나이");
        JLabel lblStart = new JLabel("초기 체중");
        JLabel lblGoal = new JLabel("목표 체중");

        JTextField txtId = new JTextField(15);
        JTextField txtPw = new JTextField(15);
        JTextField txtAge = new JTextField(15);
        JTextField txtStart = new JTextField(15);
        JTextField txtGoal = new JTextField(15);

        JRadioButton rbM = new JRadioButton("남");
        JRadioButton rbF = new JRadioButton("여");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbM);
        bg.add(rbF);

        JButton btnJoin = new JButton("회원가입");
        JButton btnCancel = new JButton("취소");

        btnJoin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "가입 정보:\nID=" + txtId.getText() +
                            "\nPW=" + txtPw.getText() +
                            "\n성별=" + (rbM.isSelected() ? "남" : rbF.isSelected() ? "여" : "") +
                            "\n나이=" + txtAge.getText() +
                            "\n초기 체중=" + txtStart.getText() +
                            "\n목표 체중=" + txtGoal.getText());
        });

        btnCancel.addActionListener(e -> dispose());

        // GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblId)
                                .addComponent(txtId))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPw)
                                .addComponent(txtPw))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGender)
                                .addComponent(rbM)
                                .addComponent(rbF))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblAge)
                                .addComponent(txtAge))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStart)
                                .addComponent(txtStart))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGoal)
                                .addComponent(txtGoal))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(btnJoin)
                                .addComponent(btnCancel)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblId)
                                .addComponent(txtId))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblPw)
                                .addComponent(txtPw))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblGender)
                                .addComponent(rbM)
                                .addComponent(rbF))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblAge)
                                .addComponent(txtAge))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblStart)
                                .addComponent(txtStart))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lblGoal)
                                .addComponent(txtGoal))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(btnJoin)
                                .addComponent(btnCancel)));

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }
}