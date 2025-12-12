package Client.GUI.Frame;

import javax.swing.*;

import Client.ClientSender;
import Client.MessageRouter;
import Client.GUI.Dialog.EntryDialog;
import Client.GUI.Panel.AnalysisPanel;
import Client.GUI.Panel.DataInputPanel;
import Common.MessageType;

import java.awt.*;

public class MainFrame extends JFrame {
    private String userId;
    private AnalysisPanel analysisPanel; // 다이어트 분석 패널
    private DataInputPanel dataInputPanel; // 데이터 입력 패널
    private ClientSender sender = null;
    private MessageRouter mr;

    public MainFrame(String userId, ClientSender sender, MessageRouter mr) {
        super("다이어트 분석 시스템 - 메인");
        this.userId = userId;
        this.sender = sender;
        this.mr = mr;

        // 메뉴바 구성
        createMenuBar();

        // 탭 구성
        initTabs();
        
        // 프레임 제목 설정
        setTitle("다이어트 메니저");
        // 닫기 버튼 클릭 시 프로그램 종료로 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 프레임 표시 설정
        setVisible(true);
        // 프레임 크기 설정
        setSize(600, 400);
        // setLocationRelativeTo(null);
    }

    public DataInputPanel getInputPanel() {
        return dataInputPanel;
    }

    public AnalysisPanel getAnalysisPanel() {
        return analysisPanel;
    }

    /** 메뉴바 생성 메소드 */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("메뉴");

        JMenuItem itemEdit = new JMenuItem("회원 정보 수정");
        JMenuItem itemLogout = new JMenuItem("로그아웃");
        JMenuItem itemWithdraw = new JMenuItem("회원탈퇴");

        // 회원 정보 수정 아이템 이벤트
        itemEdit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "회원 정보 수정 클릭됨");
            // TODO: EditDialog 연결 예정
            // TODO : DB에서 사용자 정보 수정
        });

        // 로그아웃 아이템 이벤트
        itemLogout.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "로그아웃 하시겠습니까?",
                    "로그아웃",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                // 로그아웃 요청
                sender.sendMSG(MessageType.SIGNUP_REQ, userId);

                JOptionPane.showMessageDialog(this, "로그아웃 완료");
                dispose(); // 메인 프레임 닫기

                // 다시 EntryDialog 열기
                // EntryDialog entry = new EntryDialog(null, sender, mr);
                // entry.setVisible(true);
            }
        });

        // 회원탈퇴 아이템 이벤트
        itemWithdraw.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "정말 회원탈퇴 하시겠습니까?",
                    "회원탈퇴",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                // TODO : DB에서 사용자 ID으로 삭제

                JOptionPane.showMessageDialog(this, "회원탈퇴 완료");
                dispose();

                // 회원탈퇴 후 EntryDialog로 이동
                EntryDialog entry = new EntryDialog(null, sender, mr);
                entry.setVisible(true);
            }
        });

        // 메뉴 구성
        menu.add(itemEdit);
        menu.addSeparator();
        menu.add(itemLogout);
        menu.add(itemWithdraw);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /** 탭 구성 (분석 / 입력) */
    private void initTabs() {
        JTabbedPane tabs = new JTabbedPane();

        analysisPanel = new AnalysisPanel(userId);
        dataInputPanel = new DataInputPanel();

        tabs.addTab("다이어트 분석", analysisPanel);
        tabs.addTab("데이터 입력", dataInputPanel);

        add(tabs, BorderLayout.CENTER);
    }
}
