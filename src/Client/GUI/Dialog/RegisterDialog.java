package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Common.MessageBuilder;
import Common.MessageType;
import Common.User;
import java.awt.*;

// TODO : 사용자 정보 DB에 산입, 회원가입 버튼 클릭 시 EntryDialog으로 이동
public class RegisterDialog extends JDialog {
    private ClientSender sender;
    private MessageBuilder mb = new MessageBuilder();
    private MessageRouter mr;

    public RegisterDialog(Dialog owner, ClientSender sender, MessageRouter mr) {
        super(owner, "회원가입", true);
        this.sender = sender;
        this.mr = mr;
        mr.setDialog(this);
        
        JLabel lblId = new JLabel("ID");
        JLabel lblPw = new JLabel("PW");
        JLabel lblGender = new JLabel("성별");
        JLabel lblHeight = new JLabel("키");
        JLabel lblAge = new JLabel("나이");
        JLabel lblStart = new JLabel("초기 체중");
        JLabel lblGoal = new JLabel("목표 체중");

        JTextField txtId = new JTextField(15);
        JTextField txtPw = new JTextField(15);
        JTextField txtHeight = new JTextField(15);
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
            User u = new User(
                txtId.getText(), 
                txtPw.getText(),
                rbM.isSelected(), 
                Double.parseDouble(txtHeight.getText()), 
                Integer.parseInt(txtAge.getText()), 
                Double.parseDouble(txtStart.getText()), 
                Double.parseDouble(txtGoal.getText())
            );

            sender.sendMSG(MessageType.SIGNUP_REQ, mb.signupReq(u));
        });

        // 취소
        btnCancel.addActionListener(e -> dispose());

        // GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblId)
                .addComponent(txtId)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblPw)
                .addComponent(txtPw))

            .addGroup(layout.createSequentialGroup()
                .addComponent(lblGender)
                .addComponent(rbM)
                .addComponent(rbF)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblHeight)
                .addComponent(txtHeight)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblAge)
                .addComponent(txtAge)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblStart)
                .addComponent(txtStart)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(lblGoal)
                .addComponent(txtGoal)
            )
            .addGroup(
                layout.createSequentialGroup()
                .addComponent(btnJoin)
                .addComponent(btnCancel)
            )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblId)
                .addComponent(txtId)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblPw)
                .addComponent(txtPw)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblGender)
                .addComponent(rbM)
                .addComponent(rbF)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblHeight)
                .addComponent(txtHeight)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblAge)
                .addComponent(txtAge)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblStart)
                .addComponent(txtStart)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(lblGoal)
                .addComponent(txtGoal)
            )
            .addGroup(
                layout.createParallelGroup()
                .addComponent(btnJoin)
                .addComponent(btnCancel)
            )
        );

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public void handleSignupRes(String id, String result) {
        System.out.println(this.getName() + " 응답");
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "회원가입 성공");
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "회원가입 실패");
        }
    }
}