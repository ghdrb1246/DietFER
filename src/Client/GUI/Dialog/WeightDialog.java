package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Frame.MainFrame;
import Client.GUI.Panel.DataInputPanel;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;
import Common.Weight;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 체중 입력 Dialog
public class WeightDialog extends JDialog {
    private MainFrame mainFrame;
    private ClientSender sender;
    private MessageRouter mr;
    private String userId;
    private JTextField txtDateTime;
    private JTextField txtWeight;
    private MessageBuilder mb = new MessageBuilder();

    public WeightDialog(String userId, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "체중 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.userId = userId;
        
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
            Double weight = Double.parseDouble(txtWeight.getText());

            Weight w = new Weight(datetime, weight);
            System.out.println(w.getDate() + ", " + w.getWeight());

            sender.sendMSG(MessageType.WEIGHT_ADD_REQ, mb.weightAddReq(userId, w));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(320, 180);
        setLocationRelativeTo(owner);
    }

    public void handleWeightAddRes(String userId, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "체중 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();

            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(userId));
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "체중 저장 실패");
        }
    }
}
