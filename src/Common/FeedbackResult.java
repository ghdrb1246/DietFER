package Common;

import java.util.ArrayList;

/**
 * 피드백 객체
 */
public class FeedbackResult {
    // FAIL 여부
    private boolean success;
    
    // 섭취+소모+잔여+권장
    private int intake;         // 섭취
    private int burn;           // 소모
    private int remain;         // 잔여
    private int recommendCal;   // 권장 칼로리
    
    // 탄수화물_섭취량+탄수화물_권장량
    private int carbIntake;     // 탄수화물 섭취
    private int carbRecommend;  // 권장 탄수화물 

    // 단백질_섭취량+단백질_권장량
    private int proteinIntake;      // 단백질 섭취
    private int proteinRecommend;   // 권장 단백질 

    // 지방_섭취량+지방_권장량
    private int fatIntake;          // 지방 섭취
    private int fatRecommend;       // 권장 지방 

    private ArrayList<String> foodRecommend;    // 음식 추천 리스트
    private ArrayList<String> exerciseRecommend; // 운동 추천 리스트
  
    // 피드백 생성자
    public FeedbackResult(
        int intake, int burn, int remain, int recommendCal,
        int carbIntake, int carbRecommend,
        int proteinIntake, int proteinRecommend,
        int fatIntake, int fatRecommend,
        ArrayList<String> foodRecommend,
        ArrayList<String> exerciseRecommend) {

        this.success = true;
        this.intake = intake;
        this.burn = burn;
        this.remain = remain;
        this.recommendCal = recommendCal;
        this.carbIntake = carbIntake;
        this.carbRecommend = carbRecommend;
        this.proteinIntake = proteinIntake;
        this.proteinRecommend = proteinRecommend;
        this.fatIntake = fatIntake;
        this.fatRecommend = fatRecommend;
        this.foodRecommend = foodRecommend;
        this.exerciseRecommend = exerciseRecommend;
    }

    // FAIL 생성자
    public FeedbackResult(boolean success) {
        this.success = success;
    }
    
    /* -------------------------------------------------- */
    // 읽기
    public boolean getSuccess() {
        return success;
    }

    public int getIntake() {
        return intake;
    }

    public int getBurn() {
        return burn;
    }

    public int getRemain() {
        return remain;
    }

    public int getRecommendCal() {
        return recommendCal;
    }

    public int getCarbIntake() {
        return carbIntake;
    }

    public int getCarbRecommend() {
        return carbRecommend;
    }

    public int getProteinIntake() {
        return proteinIntake;
    }

    public int getProteinRecommend() {
        return proteinRecommend;
    }

    public int getFatIntake() {
        return fatIntake;
    }

    public int getFatRecommend() {
        return fatRecommend;
    }

    public ArrayList<String> getFoodRecommend() {
        return foodRecommend;
    }

    public ArrayList<String> getExerciseRecommend() {
        return exerciseRecommend;
    }
    /* -------------------------------------------------- */


    
    /* -------------------------------------------------- */
    // 쓰기
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    public void setBurn(int burn) {
        this.burn = burn;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public void setRecommendCal(int recommendCal) {
        this.recommendCal = recommendCal;
    }

    public void setCarbIntake(int carbIntake) {
        this.carbIntake = carbIntake;
    }

    public void setCarbRecommend(int carbRecommend) {
        this.carbRecommend = carbRecommend;
    }

    public void setProteinIntake(int proteinIntake) {
        this.proteinIntake = proteinIntake;
    }

    public void setProteinRecommend(int proteinRecommend) {
        this.proteinRecommend = proteinRecommend;
    }

    public void setFatIntake(int fatIntake) {
        this.fatIntake = fatIntake;
    }

    public void setFatRecommend(int fatRecommend) {
        this.fatRecommend = fatRecommend;
    }

    public void setFoodRecommend(ArrayList<String> foodRecommend) {
        this.foodRecommend = foodRecommend;
    }

    public void setExerciseRecommend(ArrayList<String> exerciseRecommend) {
        this.exerciseRecommend = exerciseRecommend;
    }
    /* -------------------------------------------------- */

    @Override
    public String toString() {
        return ("FeedbackResult [success = " + success +
            ", intake = " + intake +
            ", burn = " + burn +
            ", remain = " + remain +
            ", recommendCal = " + recommendCal +
            ", carbIntake = " + carbIntake +
            ", carbRecommend = " + carbRecommend +
            ", proteinIntake = " + proteinIntake +
            ", proteinRecommend = " + proteinRecommend +
            ", fatIntake = " + fatIntake +
            ", fatRecommend = " + fatRecommend +
            ", foodRecommend = " + foodRecommend.toString() +
            ", exerciseRecommend = " + exerciseRecommend.toString() +
            "]");
    }
}
