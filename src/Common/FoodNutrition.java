package Common;

public class FoodNutrition {
    private String foodName; // FOOD_NM_KR
    private double kcal; // AMT_NUM1
    private double carbohydrate; // AMT_NUM3
    private double protein; // AMT_NUM4
    private double fat; // AMT_NUM6

    public FoodNutrition(String foodName, double kcal,
            double carbohydrate, double protein, double fat) {
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