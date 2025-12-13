package Common;

// CSV 파일 운동 데이터 
public class Exercise {
    private String name;
    private double kcalPerKg;

    public Exercise(String name, double kcalPerKg) {
        this.name = name;
        this.kcalPerKg = kcalPerKg;
    }

    public String getName() {
        return name;
    }

    public double getKcalPerKg() {
        return kcalPerKg;
    }
}
