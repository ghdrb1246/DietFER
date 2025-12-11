package Common;

import java.time.LocalDate;

public class Weight {
    // 사용자 ID
    private String id;
    // 등록 날짜
    private LocalDate date;
    // 현재 체중
    private double weight;
    
    /**
     * 사용자 id getter 메소드
     * 
     * @return 사용자 id 리턴
     */
    public String getId() { return id; }

    /**
     * 등록 날짜 getter 메소드
     * 
     * @return 등록 날짜 리턴
     */
    public LocalDate getDate() { return date; }

    /**
     * 현재 체중 getter 메소드
     * 
     * @return 사용자 id 리턴
     */
    public double getWeight() { return weight; }

    /**
     * 사용자 ID setter 메소드
     * 
     * @param id 수정할 사용자 ID
     */
    public void setId(String id) { this.id = id; }

    /**
     * 등록 날짜 setter 메소드
     * 
     * @param date 수정할 등록 날짜
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * 현재 체중 setter 메소드
     * 
     * @param weight 수정할 현재 체중
     */
    public void setWeight(double weight) { this.weight = weight; }   
}