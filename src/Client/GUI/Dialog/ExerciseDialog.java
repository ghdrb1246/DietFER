package Client.GUI.Dialog;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Common.Exercise;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;
import Client.GUI.Frame.MainFrame;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 운동 입력 Dialog
 */
public class ExerciseDialog extends JDialog {
    // 메인 프레임
    private MainFrame mainFrame;
    // 서버간 통신을 위한 필트
    private ClientSender sender;
    // 클라이언트와 GUI의 제어
    private MessageRouter mr;
    // 사용자 ID
    private String id;
    // 운동한 날짜/시간
    private JTextField txtDateTime;
    // 운동 콤보박스
    private JComboBox<String> cbExercise;
    // 운동한 시간
    private JTextField txtHour;
    // 메시지 생성
    private MessageBuilder mb = new MessageBuilder();

    /**
     * 운동 Dialog 초기화
     * 
     * @param id        사용자 ID
     * @param owner     JDialog의 최상의인 Window
     * @param mainFrame 메인 프래임
     * @param sender    서버간 통신을 위한 sender
     * @param mr        클라이언트와 GUI의 제어
     */
    public ExerciseDialog(String id, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "운동 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.id = id;

        mr.setDialog(this);

        setLayout(new BorderLayout(10, 10));
        
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        
        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);
        
        form.add(new JLabel("운동명"));

        cbExercise = new JComboBox<>(new String[] {
            "걷기",
            "빠르게 걷기",
            "조깅",
            "달리기",
            "런닝머신(걷기)",
            "런닝머신(달리기)",
            "자전거타기",
            "실내자전거타기",
            "스피닝",
            "계단오르기",
            "줄넘기",
            "파워워킹",
            "노르딕워킹",
            "에어로빅(저강도)",
            "에어로빅(중강도)",
            "에어로빅(고강도)",
            "줌바댄스",
            "다이어트 댄스",
            "재즈댄스",
            "방송댄스",
            "수영(자유영)",
            "수영(배영)",
            "수영(평영)",
            "웨이트운동(가볍게)",
            "웨이트운동(보통으로)",
            "웨이트운동(격렬하게)",
            "스쿼트",
            "런지",
            "푸쉬업",
            "플랭크",
            "크런치",
            "레그 프레스",
            "덤벨 운동",
            "케틀벨",
            "하이킹",
            "등산",
            "트래킹"
        });

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
        
        // 저장 이벤트
        btnSave.addActionListener(e -> {
            TimeConversion tc = new TimeConversion();
            LocalDateTime datetime = tc.inputToTimeString(txtDateTime.getText());
            String exercise = (String) cbExercise.getSelectedItem();
            double hours = Double.parseDouble(txtHour.getText());
            Exercise w = new Exercise(datetime, exercise, hours, 0.0);
            
            if (exercise == null || w == null) {
                JOptionPane.showMessageDialog(this, "운동을 선택하세요.");
                return;
            }
            sender.sendMSG(MessageType.EXERCISE_ADD_REQ, mb.exerciseAddReq(id, w));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setSize(350, 210);
        setLocationRelativeTo(owner);
    }

    /**
     * 운동 처리 헨들러
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     */
    public void handleExerciseAddRes(String id, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "운동 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();

            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(id));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "운동 저장 실패");
        }
    }
}