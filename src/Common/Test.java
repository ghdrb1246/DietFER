package Common;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Server.CSV.ExerciseCSVDAO;
import Server.DB.RecordDAO;

public class Test {
    public static void main(String[] args) {
        /*
         * String msg =
         * "RECORD_RES/사용자 ID/2025-12-01(감자,걷기,60)+2025-12-02(고구마,달리기,59.5)+2025-12-03(닭가슴살,A,58)+2025-12-04(밥,B,55)"
         * ;
         * MessageParser mb = new MessageParser();
         * ArrayList<RecordData> rd = mb.findParseRecordList(msg);
         * 
         * for (RecordData rr : rd) {
         * System.out.println(
         * "날짜 : " + rr.date + ", " +
         * "음식명 : " + rr.mealName + ", " +
         * "운동명 : " + rr.exerciseName + ", " +
         * "체중 : " + rr.weight
         * );
         * }
         */

        /* String t = "2025-12-13 17:30";
        TimeConversion tc = new TimeConversion();
        LocalDateTime ldt = tc.inputToTimeString(t);
        String s = tc.timeToStr(ldt);
        
        System.out.println(s); */
/* 
        ArrayList<Exercise> exercises = new ExerciseCSVDAO().loadExercises();
        for (Exercise e : exercises) {
            System.out.println(e.getName());
        } */
        /* ArrayList<FoodNutrition> f = new ArrayList<>();
        f.add(new FoodNutrition("A", 100, 3.4, 0, 1));
        f.add(new FoodNutrition("B", 31, 1, 0, 22));
        f.add(new FoodNutrition("C", 67, 30, 5, 11));
        f.add(new FoodNutrition("D", 12, 31, 0, 2.1));
        f.add(new FoodNutrition("F", 64, 3.4, 1, 0.5));
        MessageBuilder b = new MessageBuilder();
        System.out.println(b.foodSearcReq("id1", "고구마"));
        System.out.println(b.foodSearcRes("id1", f));    */
        
        ArrayList<RecordData> list = new RecordDAO().findRecordsByUser("id1");
        
        for (RecordData rd : list) {
            System.out.println(rd.toString());
        }
    }

}
