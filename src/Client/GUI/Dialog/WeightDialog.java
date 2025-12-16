package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;
import Common.Weight;
import Client.GUI.Frame.MainFrame;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 체중 입력 Dialog
 */
public class WeightDialog extends JDialog {
    // 메인 프레임
    private MainFrame mainFrame;
    // 서버간 통신을 위한 필트
    private ClientSender sender;
    // 클라이언트와 GUI의 제어
    private MessageRouter mr;
    // 사용자 ID
    private String id;
    // 체중한 날짜/시간
    private JTextField txtDateTime;
    // 현재 체중
    private JTextField txtWeight;
    // 메시지 생성
    private MessageBuilder mb = new MessageBuilder();

    /**
     * 체중 Dialog 초기화
     * 
     * @param id        사용자 ID
     * @param owner     JDialog의 최상의인 Window
     * @param mainFrame 메인 프래임
     * @param sender    서버간 통신을 위한 sender
     * @param mr        클라이언트와 GUI의 제어
     */
    public WeightDialog(String id, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "체중 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.id = id;
        
        mr.setDialog(this);

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
            TimeConversion tc = new TimeConversion();
            LocalDateTime datetime = tc.inputToTimeString(txtDateTime.getText());
            double weight = Double.parseDouble(txtWeight.getText());

            Weight w = new Weight(datetime, weight);
            System.out.println(w.getDate() + ", " + w.getWeight());

            sender.sendMSG(MessageType.WEIGHT_ADD_REQ, mb.weightAddReq(id, w));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(320, 180);
        setLocationRelativeTo(owner);
    }

    /**
     * 체중 처리 헨들러
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     */
    public void handleWeightAddRes(String id, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "체중 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();

            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(id));
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "체중 저장 실패");
        }
    }
}