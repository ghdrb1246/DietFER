package Common;

import java.time.LocalDateTime;

public class Exercise {
    // 등록 날짜+시간
    private LocalDateTime dateTime;
    // 운동 명
    private String exerciseName;
    // 운동 시간
    private double minutes;
    // 소모 칼로리
    private double kcal;

    // 기본 생성자
    public Exercise() {}

    /**
     * 운동 생성자
     * 
     * @param exerciseName 운동 명
     * @param minutes 운동 시간
     * @param kcal 소모 칼로리
     */
    public Exercise(LocalDateTime dateTime, String exerciseName, double minutes, double kcal) {
        this.dateTime = dateTime;
        this.exerciseName = exerciseName;
        this.minutes = minutes;
        this.kcal = kcal;    
    }

    // 파일 운동 데이터 용 생성자
    public Exercise(String name, double kcal) {
        this.exerciseName = name;
        this.kcal = kcal;
    }

    /**
     * 운동 Kcal 계산 메소드
     * 
     * @param weight  체중
     * @param minutes 운동 시간
     * @return 운동한 Kcal를 리턴
     */ 
    public double calcKcal(double weight, double minutes) {
        return kcal * weight * (minutes / 60.0);
    }

    /**
     * 등록 날짜+시간 getter 메소드
     * 
     * @return 사용자 등록 날짜+시간 리턴
     */
    public LocalDateTime getDateTime() { return dateTime; }

    /**
     * 운동 명 getter 메소드
     * 
     * @return 운동 명 리턴
     */
    public String getExerciseName() { return exerciseName; }

    /**
     * 운동 시간 getter 메소드
     * 
     * @return 운동 시간 리턴
     */
    public double getMinutes() { return minutes; }

    /**
     * 소모 칼로리 getter 메소드
     * 
     * @return 소모 칼로리 리턴
     */
    public double getKcal() { return kcal; }

    /**
     * 등록 날짜+시간 setter 메소드
     * 
     * @param dateTime 수정할 등록 날짜+시간
     */
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    /**
     * 운동 명 setter 메소드
     * 
     * @param exerciseName 수정할 운동명
     */
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    /**
     * 운동 시간 setter 메소드
     * 
     * @param minutes 수정할 운동 시간
     */
    public void setMinutes(double minutes) { this.minutes = minutes; }

    /**
     * 소모 칼로리 setter 메소드
     * 
     * @param kcal 수정할 소모 칼로리
     */
    public void setKcal(double kcal) { this.kcal = kcal; }

    @Override
    public String toString() {
        return (
            "Exercise [" + 
            ", dateTime=" + dateTime + 
            ", exerciseName=" + exerciseName + 
            ", minutes=" + minutes + 
            ", kcal=" + kcal + 
            "]"
        );
    }   
}
