package Common;

/**
 * 달성률
 */
public class Progress {
    // 초기 체중
    private double initial;
    // 목표 체중
    private double goal;
    // 현재 체중
    private double current;
    // 달성률
    private double progress;

    /**
     * 달성률 생상자
     * 
     * @param initial  초기 체중
     * @param goal     목표 체중
     * @param current  현재 체중
     * @param progress 달성률
     */
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
    // 쓰기
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
