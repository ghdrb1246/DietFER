import javax.swing.*;
import java.awt.*;

// 패널들 테스트용 프래임
public class GUITest {
    public static void main(String[] args) {
        // 테스트 GUId을 선언
        new TestFrame();
    }
}

class TestFrame extends JFrame {
    private WeightProgressPanel p = new WeightProgressPanel(85, 77, 70);

    public TestFrame() {
        Container ct = getContentPane();
        
        ct.add(p);

        // 프레임 제목 설정
        setTitle("Test GUI");
        // 프레임 크기 설정
        setSize(800, 500);
        // 닫기 버튼 클릭 시 프로그램 종료로 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 프레임 표시 설정
        setVisible(true);
    }
}