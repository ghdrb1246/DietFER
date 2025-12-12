package Common;

import java.time.LocalDate;

// 기록 객체
// 날짜, 음식명, 운동명, 체중
public class RecordData {
    public LocalDate date;
    public String mealName;
    public String workoutName;
    public Double weight;

    public RecordData(LocalDate date, String meal, String workout, Double weight) {
        this.date = date;
        this.mealName = meal;
        this.workoutName = workout;
        this.weight = weight;
    }
}