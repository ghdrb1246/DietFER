package Client.GUI.Dialog;

import javax.swing.*;
import Client.GUI.Frame.MainFrame;

import java.awt.*;

public class EntryDialog extends JDialog {

    private JTextField txtId;
    private JPasswordField txtPw;

    public EntryDialog(Frame owner) {
        super(owner, "로그인", true);

        setLayout(new BorderLayout(5, 5));

        // 입력 필드 영역 
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        inputPanel.add(new JLabel("ID"));
        txtId = new JTextField(15);
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("비밀번호"));
        txtPw = new JPasswordField(15);
        inputPanel.add(txtPw);

        add(inputPanel, BorderLayout.CENTER);

        // 버튼 영역
        JPanel btnPanel = new JPanel();

        JButton btnLogin = new JButton("로그인");
        JButton btnRegister = new JButton("회원가입");

        btnPanel.add(btnLogin);
        btnPanel.add(btnRegister);

        add(btnPanel, BorderLayout.SOUTH);

        // 로그인 버튼 이벤트
        btnLogin.addActionListener(e -> {
            String id = txtId.getText().trim();
            String pw = new String(txtPw.getPassword());

            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID와 PW를 모두 입력하세요.");
                return;
            }

            // TODO: DB 인증 연결
            JOptionPane.showMessageDialog(this, "로그인 성공!");

            // MainFrame 실행 
            MainFrame main = new MainFrame(id);
            main.setVisible(true);

            dispose();
        });

        // 회원가입 버튼 이벤트
        btnRegister.addActionListener(e -> {
            RegisterDialog register = new RegisterDialog(this);
            register.setVisible(true);
        });

        // 기본 설정 
        setSize(350, 180);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}