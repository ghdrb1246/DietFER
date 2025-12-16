package Common;

import java.time.LocalDateTime;

/**
 * 음식 객체
 */
public class Meal {
    // 등록 날짜+시간
    private LocalDateTime dateTime;
    // 음식 타입
    private String foodTypr;
    // 음식 명
    private String foodName;
    // 섭취량
    private double gram;
    // 음식 칼로리
    private double kcal;
    // 탄수화물
    private double carbohydrate;
    // 단백질
    private double protein;
    // 지방
    private double fat;

    // 기본 생성자
    public Meal() {}
    
    /**
     * 음식 생성자
     * 
     * @param dateTime     등록 날짜+시간
     * @param foodTypr     음식 타입
     * @param foodName     음식명
     * @param gram         섭취량
     * @param kcal         음식 컬로리
     * @param carbohydrate 탄수화물
     * @param protein      단백질
     * @param fat          지방
     */
    public Meal(LocalDateTime dateTime, String foodName, String foodTypr, double gram, double kcal, double carbohydrate, double protein, double fat) {
        this.dateTime = dateTime;
        this.foodTypr = foodTypr;
        this.foodName = foodName;
        this.gram = gram;
        this.kcal = kcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }

    /**
     * 등록 날짜+시간 getter 메소드
     * 
     * @return 사용자 등록 날짜+시간 리턴
     */
    public LocalDateTime getDateTime() { return dateTime; }

    /**
     * 음식 타입 getter 메소드
     * 
     * @return 사용자 음식 타입 리턴
     */
    public String getFoodTypr() { return foodTypr; }

    /**
     * 음식 명 getter 메소드
     * 
     * @return 사용자 음식 명 리턴
     */
    public String getFoodName() { return foodName; }

    /**
     * 섭취량 getter 메소드
     * 
     * @return 사용자 섭취량 리턴
     */
    public double getGram() { return gram; }

    /**
     * 칼로리 getter 메소드
     * 
     * @return 사용자 칼로리 리턴
     */
    public double getKcal() { return kcal; }

    /**
     * 탄수화물 getter 메소드
     * 
     * @return 사용자 탄수화물 리턴
     */
    public double getCarbohydrate() { return carbohydrate; }

    /**
     * 단백질 getter 메소드
     * 
     * @return 사용자 단백질 리턴
     */
    public double getProtein() { return protein; }

    /**
     * 지방 getter 메소드
     * 
     * @return 사용자 지방 리턴
     */
    public double getFat() { return fat; }

    /**
     * 등록 날짜+시간 setter 메소드
     * 
     * @param dateTime 수정할 등록 날짜+시간
     */
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    /**
     * 음식 타입 setter 메소드
     * 
     * @param foodTypr 수정할 음식 타입
     */
    public void setFoodTypr(String foodTypr) { this.foodTypr = foodTypr; }

    /**
     * 음식 명 setter 메소드
     * 
     * @param foodName 수정할 음식 명
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }

    /**
     * 섭취량 setter 메소드
     * 
     * @param gram 수정할 섭취량
     */
    public void setGram(double gram) { this.gram = gram; }

    /**
     * 칼로리 setter 메소드
     * 
     * @param kcal 수정할 칼로리
     */
    public void setKcal(double kcal) { this.kcal = kcal; }

    /**
     * 탄수화물 setter 메소드
     * 
     * @param carbohydrate 수정할 탄수화물
     */
    public void setCarbohydrate(double carbohydrate) { this.carbohydrate = carbohydrate; }

    /**
     * 단백질 setter 메소드
     * 
     * @param protein 수정할 단백질
     */
    public void setProtein(double protein) { this.protein = protein; }

    /**
     * 지방 setter 메소드
     * 
     * @param fat 수정할 지방
     */
    public void setFat(double fat) { this.fat = fat; }

    @Override
    public String toString() {
        return (
            "Meal [dateTime=" + dateTime + 
            ", foodTypr=" + foodTypr + 
            ", foodName=" + foodName + 
            ", gram=" + gram + 
            ", kcal=" + kcal + 
            ", carbohydrate=" + carbohydrate + 
            ", protein=" + protein + 
            ", province=" + fat + 
            "]"
        );
    }
}