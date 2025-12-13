package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Frame.MainFrame;
import Common.Meal;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 식단 입력 Dialog
public class DietDialog extends JDialog {
    private MainFrame mainFrame;
    private ClientSender sender;
    private MessageRouter mr;
    private String userId;
    private JTextField txtDateTime;
    private JComboBox<String> cbMealType;
    private JComboBox<String> cbFoodName;
    private JTextField txtGram;
    private MessageBuilder mb = new MessageBuilder();

    public DietDialog(String userId, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "식단 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.userId = userId;
        
        mr.setDialog(this);
        
        setLayout(new BorderLayout(10, 10));

        // 입력폼 
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);

        form.add(new JLabel("식단 종류"));
        // TODO : API에 음식 종류을 구별하여 추가
        cbMealType = new JComboBox<>(new String[] { "아침", "점심", "저녁", "간식" });
        form.add(cbMealType);

        form.add(new JLabel("식단명"));
        // TODO : 서버에게 음식 종류으로 API을 요청 - 조회 후 필터 -> 표시
        cbFoodName = new JComboBox<>(new String[] { "고구마", "닭가슴살", "현미밥", "사과" });
        form.add(cbFoodName);

        form.add(new JLabel("섭취량(g)"));
        txtGram = new JTextField();
        form.add(txtGram);

        add(form, BorderLayout.CENTER);

        // 버튼 
        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("저장");
        JButton btnCancel = new JButton("취소");

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        add(btnPanel, BorderLayout.SOUTH);

        // 저장 이벤트 
        btnSave.addActionListener(e -> {
            TimeConversion tc = new TimeConversion();
            LocalDateTime datetime = tc.inputToTimeString(txtDateTime.getText());

            String mealType = (String) cbMealType.getSelectedItem();
            String food = (String) cbFoodName.getSelectedItem();
            Double gram = Double.parseDouble(txtGram.getText());

            Meal m = new Meal(datetime, food, mealType, gram, 0, 0, 0, 0);

            sender.sendMSG(MessageType.MEAL_ADD_REQ, mb.mealAddReq(userId, m));
            dispose();
        });

        // 취소 이벤트
        btnCancel.addActionListener(e -> dispose());

        setSize(350, 250);
        setLocationRelativeTo(owner);
    }

    public void handleMealAddRes(String userId, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "식단 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();
            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(userId));
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "식단 저장 실패");
        }
    }
}
