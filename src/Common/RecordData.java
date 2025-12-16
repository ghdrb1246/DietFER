package Common;

import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * 기록 객체
 * 날짜, 음식명, 운동명, 체중
 */
public class RecordData {
    private LocalDateTime date;
    private String mealName;
    private String exerciseName;
    private Double weight;

    /**
     * 
     * @param date
     * @param meal
     * @param exercise
     * @param weight
     */
    public RecordData(LocalDateTime date, String meal, String exercise, Double weight) {
        this.date = date;
        this.mealName = meal;
        this.exerciseName = exercise;
        this.weight = weight;
    }

    // 쓰기
    public LocalDateTime getDate() {
        return date;
    }

    public String getMealName() {
        return mealName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public Double getWeight() {
        return weight;
    }
    
    /**
     * 시간별 아침, 점심, 저녁, 기타 판별하는 메소드
     * 
     * @param time 음식 시간
     * @return 날짜별 음식 타입
     */
    public String getMealType(LocalTime time) {
        if (!time.isBefore(LocalTime.of(5, 0)) && time.isBefore(LocalTime.of(11, 0)))
            return "아침";
        if (!time.isBefore(LocalTime.of(11, 0)) && time.isBefore(LocalTime.of(17, 0)))
            return "점심";
        if (!time.isBefore(LocalTime.of(17, 0)) && time.isBefore(LocalTime.of(23, 0)))
            return "저녁";
        return "기타";
    }
    
    @Override
    public String toString() {
        return ("RecordData [date = " + date.toString() +
                ", mealName = " + mealName +
                ", exerciseName = " + exerciseName +
                ", weight = " + weight +
                "]");
    }
}