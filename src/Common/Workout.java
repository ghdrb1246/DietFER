package Common;

import java.time.LocalDateTime;

public class Workout {
    // 사용자 ID
    private String id;
    // 등록 날짜+시간
    private LocalDateTime dateTime;
    // 운동 명
    private String exerciseName;
    // 운동 시간
    private double minutes;
    // 소모 칼로리
    private double kcal;

    // 기본 생성자
    public Workout() {}

    /**
     * 운동 생성자
     * 
     * @param id 사용자 ID
     * @param exerciseName 운동 명
     * @param minutes 운동 시간
     * @param kcal 소모 칼로리
     */
    public Workout(String id, String exerciseName, double minutes, double kcal) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.minutes = minutes;
        this.kcal = kcal;    
    }

    /**
     * 사용자 id getter 메소드
     * 
     * @return 사용자 id 리턴
     */
    public String getId() { return id; }

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
     * 사용자 ID setter 메소드
     * 
     * @param id 수정할 사용자 ID
     */
    public void setId(String id) { this.id = id; }

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
            "Workout [id=" + id + 
            ", dateTime=" + dateTime + 
            ", exerciseName=" + exerciseName + 
            ", minutes=" + minutes + 
            ", kcal=" + kcal + 
            "]"
        );
    }   
}
