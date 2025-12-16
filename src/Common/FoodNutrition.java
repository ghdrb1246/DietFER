package Common;

/** 
 * 음식 API에서 가져온 식품 영양 정보 객체
 */
public class FoodNutrition {
    // FOOD_NM_KR
    private String foodName;
    // AMT_NUM1
    private double kcal;
    // AMT_NUM3
    private double carbohydrate;
    // AMT_NUM4
    private double protein;
    // AMT_NUM6
    private double fat; 

    /**
     * 식품 영양 정보 생성지
     * 
     * @param foodName      음식 명
     * @param kcal          칼로리
     * @param carbohydrate  탄수화물
     * @param protein       단백질
     * @param fat           지방 
     */
    public FoodNutrition(String foodName, double kcal, double carbohydrate, double protein, double fat) {
        this.foodName = foodName;
        this.kcal = kcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }

    // getter
    public String getFoodName() {
        return foodName;
    }

    public double getKcal() {
        return kcal;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    // 콤보박스 표시용
    public String toString() {
        return foodName + " (" + kcal + " kcal)";
    }
}