package Client.GUI.Dialog;


import javax.swing.*;
import Client.ClientSender;
import Client.MessageRouter;
import Common.FoodNutrition;
import Common.Meal;
import Common.MessageBuilder;
import Common.MessageType;
import Common.TimeConversion;
import Client.GUI.Frame.MainFrame;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// 식단 입력 Dialog
public class DietDialog extends JDialog {
    // 메인 프레임
    private MainFrame mainFrame;
    // 서버간 통신을 위한 필트
    private ClientSender sender;
    // 클라이언트와 GUI의 제어
    private MessageRouter mr;
    // 사용자 ID
    private String id;
    // 날짜/시간
    private JTextField txtDateTime;
    // 음식 검색 키위드
    private JTextField txtFoodKeyword;
    // 검색 버튼
    private JButton btnSearch;
    // 음식 콤보박스
    private JComboBox<FoodNutrition> cbFoodList;
    // 섭취양
    private JTextField txtGram;
    // 메시지 생성
    private MessageBuilder mb = new MessageBuilder();

    /**
     * 음식 입력 초기화
     * 
     * @param id        사용자 ID
     * @param owner     JDialog의 최상의인 Window
     * @param mainFrame 메인 프래임
     * @param sender    서버간 통신을 위한 sender
     * @param mr        클라이언트와 GUI의 제어
     */
    public DietDialog(String id, Window owner, MainFrame mainFrame, ClientSender sender, MessageRouter mr) {
        super(owner, "식단 입력", ModalityType.APPLICATION_MODAL);
        this.mainFrame = mainFrame;
        this.sender = sender;
        this.mr = mr;
        this.id = id;
        
        mr.setDialog(this);
        
        setLayout(new BorderLayout(10, 10));

        // 입력폼 
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        form.add(new JLabel("날짜/시간"));
        txtDateTime = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        form.add(txtDateTime);

        // API에 음식 종류을 구별하여 추가
        form.add(new JLabel("음식 검색"));
        txtFoodKeyword = new JTextField();
        btnSearch = new JButton("조회");
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.add(txtFoodKeyword, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        form.add(searchPanel);

        form.add(new JLabel("식단명"));
        
        // 서버에게 음식 종류으로 API을 요청 - 조회 후 필터 -> 표시
        cbFoodList = new JComboBox<>();
        form.add(cbFoodList);

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
        
        // 음식 검색 이벤트
        btnSearch.addActionListener(e -> {
            String keyword = txtFoodKeyword.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "음식명을 입력하세요.");
                return;
            }
            // FOOD_SEARCH_REQ/사용자 ID/음식 검색어
            sender.sendMSG(MessageType.FOOD_SEARCH_REQ, mb.foodSearcReq(id, keyword));
        });


        // 저장 이벤트
        btnSave.addActionListener(e -> {
            FoodNutrition food = (FoodNutrition) cbFoodList.getSelectedItem();
            if (food == null) {
                JOptionPane.showMessageDialog(this, "음식을 선택하세요.");
                return;
            }

            double gram;
            try {
                gram = Double.parseDouble(txtGram.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "섭취량을 숫자로 입력하세요.");
                return;
            }

            LocalDateTime datetime = new TimeConversion().inputToTimeString(txtDateTime.getText());

            Meal m = new Meal(datetime, food.getFoodName(), txtFoodKeyword.getText().trim(), gram, food.getKcal(), food.getCarbohydrate(), food.getProtein(), food.getFat());
            System.out.println(m.toString());
            sender.sendMSG(MessageType.MEAL_ADD_REQ, mb.mealAddReq(id, m));
            dispose();
        });

        // 취소 이벤트
        btnCancel.addActionListener(e -> dispose());

        setSize(350, 250);
        setLocationRelativeTo(owner);
    }

    /**
     * 음식 저장 처리 결과를 받아 헨들러
     * 
     * @param id 사용자 ID
     * @param result 결과
     */
    public void handleMealAddRes(String id, String result) {
        if (result.equals("OK")) {
            JOptionPane.showMessageDialog(this, "식단 저장 완료");

            // 입력 후 분석 패널 업데이트 재요청 허용
            mainFrame.onDataInputCompleted();
            sender.sendMSG(MessageType.RECORD_REQ, mb.recordReq(id));
            dispose();
        } 
        else {
            JOptionPane.showMessageDialog(this, "식단 저장 실패");
        }
    }

    /**
     * 음식 종류 콤보박스 아이템 추가
     * 
     * @param id 사용자 ID
     * @param list 음식 리스트를 콤보박스에 표시
     */
    public void handleFoodSearchRes(String id, ArrayList<FoodNutrition> list) {
        SwingUtilities.invokeLater(() -> {
            if (cbFoodList.getItemCount() > 0) cbFoodList.removeAllItems();

            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "조회 결과가 없습니다.");
                return;
            }

            for (FoodNutrition f : list) {
                cbFoodList.addItem(f);
            }
        });
    }
}