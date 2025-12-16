package Common;

import java.time.LocalDateTime;

/**
 * 체중 객체
 */
public class Weight {
    // 등록 날짜
    private LocalDateTime date;
    // 현재 체중
    private double weight;
    
    public Weight(LocalDateTime date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    /**
     * 등록 날짜 getter 메소드
     * 
     * @return 등록 날짜 리턴
     */
    public LocalDateTime getDate() { return date; }

    /**
     * 현재 체중 getter 메소드
     * 
     * @return 사용자 id 리턴
     */
    public double getWeight() { return weight; }
    
    /**
     * 등록 날짜 setter 메소드
     * 
     * @param date 수정할 등록 날짜
     */
    public void setDate(LocalDateTime date) { this.date = date; }

    /**
     * 현재 체중 setter 메소드
     * 
     * @param weight 수정할 현재 체중
     */
    public void setWeight(double weight) { this.weight = weight; }   
}