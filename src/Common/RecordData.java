package Common;

import java.time.LocalDateTime;
import java.time.LocalTime;

// 기록 객체
// 날짜, 음식명, 운동명, 체중
public class RecordData {
    public LocalDateTime date;
    public String mealName;
    public String workoutName;
    public Double weight;

    public RecordData(LocalDateTime date, String meal, String workout, Double weight) {
        this.date = date;
        this.mealName = meal;
        this.workoutName = workout;
        this.weight = weight;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMealName() {
        return mealName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public Double getWeight() {
        return weight;
    }
    
    public String getMealType(LocalTime time) {
        if (!time.isBefore(LocalTime.of(5, 0)) && time.isBefore(LocalTime.of(11, 0)))
            return "아침";
        if (!time.isBefore(LocalTime.of(11, 0)) && time.isBefore(LocalTime.of(17, 0)))
            return "점심";
        if (!time.isBefore(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(23, 0)))
            return "저녁";
        return "기타";
    }
    
}