package Server.Service;

import Common.Progress;
import Server.DB.UserDAO;
import Server.DB.WeightDAO;


/**
 * 달성률 계산
 */
public class ProgressService {
    // 사용자 DAO
    private UserDAO userDAO = new UserDAO();
    // 체중 DAO
    private WeightDAO weightDAO = new WeightDAO();

    /**
     * 달성률 조회
     * 
     * @param id 사용자 ID
     * @return 달성률 객체 리턴
     */
    public Progress getProgress(String id) {
        double[] w = userDAO.getStartAndGoalWeight(id);
        if (w == null) return null;

        double initial = w[0];
        double goal = w[1];

        double current = weightDAO.getLatestWeight(id);
        if (current <= 0.0) current = initial; // 체중 기록 없을 경우

        double progressRate = calculateProgress(initial, goal, current);

        return new Progress(
            initial,
            goal,
            current,
            progressRate
        );
    }

    /**
     * 진행 상황 계산
     * 
     * @param start     기초 체중
     * @param goal      목표 체중
     * @param current   현재 체중
     * @return          진행 상황 계산 리턴
     */
    private double calculateProgress(double start, double goal, double current) {
        if (start == goal)
            return 100.0;

        double total = start - goal;
        double done = start - current;

        double rate = (done / total) * 100.0;
        return Math.max(0, Math.min(100, rate));
    }
}
