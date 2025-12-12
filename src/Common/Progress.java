package Common;

public class Progress {
    // 초기 체중
    private double initial;
    // 목표 체중
    private double goal;
    // 현재 체중
    private double current;
    // 달성률
    private double progress;

    public Progress(double initial, double goal, double current, double progress) {
        this.initial = initial;
        this.goal = goal;
        this.current = current;
        this.progress = progress;
    }
    
    /* -------------------------------------------- */

    // 읽기
    public double getInitial() {
        return initial;
    }

    public double getGoal() {
        return goal;
    }

    public double getCurrent() {
        return current;
    }

    public double getProgress() {
        return progress;
    }
    /* -------------------------------------------- */
    
    /* -------------------------------------------- */
    // 읽기
    public void setInitial(double initial) {
        this.initial = initial;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }    
}
