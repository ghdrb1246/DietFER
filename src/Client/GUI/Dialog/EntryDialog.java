package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Common.MessageBuilder;
import Common.MessageType;
import Client.GUI.Frame.MainFrame;

import java.awt.*;

/**
 * 로그인 Dialog
 */
public class EntryDialog extends JDialog {
    // id 입력
    private JTextField txtId;
    // 비밀번호 입력
    private JPasswordField txtPw;
    // 클라이언트와 GUI의 제어
    private MessageRouter mr = new MessageRouter();
    // 서버간 통신을 위한 필트
    private ClientSender sender;
    // 메시지 생성
    private MessageBuilder mb = new MessageBuilder();
    // 회원가입 창
    private RegisterDialog register;
    
    /**
     * 로그인 입력 초기화
     * 
     * @param owner  JDialog의 최상의인 Window
     * @param sender 서버간 통신을 위한 sender
     * @param mr     클라이언트와 GUI의 제어
     */
    public EntryDialog(Frame owner, ClientSender sender, MessageRouter mr) {
        super(owner, "로그인", true);
        this.sender = sender;
        this.mr = mr;

        register = new RegisterDialog(this, sender, mr);

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
            sender.sendMSG(MessageType.LOGIN_REQ, mb.loginReq(id, pw));
        });

        // 회원가입 버튼 이벤트
        btnRegister.addActionListener(e -> {
            // mr.setRegisterDialog(register);
            register.setVisible(true);
        });

        // 기본 설정 
        // 프레임 크기 설정
        setSize(350, 180);
        // 창 크기를 조절 빙지
        setResizable(false);
        // 화면의 정중앙으로 표시
        setLocationRelativeTo(null);
    }
    
    /**
     * 로그인 처리 헨들러
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     */
    public void handleLoginRes(String id, String result) {
        System.out.println(this.getName() + " 응답");
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "로그인 성공!");
            new MainFrame(id, sender, mr).setVisible(true);
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "로그인 실패");
        }
    }
}