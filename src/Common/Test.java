package Common;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        /* String msg = "RECORD_RES/사용자 ID/2025-12-01(감자,걷기,60)+2025-12-02(고구마,달리기,59.5)+2025-12-03(닭가슴살,A,58)+2025-12-04(밥,B,55)";
        MessageParser mb = new MessageParser();
        ArrayList<RecordData> rd = mb.findParseRecordList(msg);

        for (RecordData rr : rd) {
            System.out.println(
                "날짜 : " + rr.date + ", " + 
                "음식명 : " + rr.mealName + ", " +
                "운동명 : " + rr.workoutName + ", " +
                "체중 : " + rr.weight
            );
        } */

        String t = "2025-12-13 17:30";
        TimeConversion tc = new TimeConversion();
        LocalDateTime ldt = tc.inputToTimeString(t);
        String s = tc.timeToStr(ldt);
        
        System.out.println(s);
    }
}
