package Server.Service;

import Server.DB.UserDAO;
import Server.DB.WeightDAO;
import Common.Progress;

// 달성률 계산
public class ProgressService {
    private UserDAO userDAO = new UserDAO();
    private WeightDAO weightDAO = new WeightDAO();

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

    private double calculateProgress(double start, double goal, double current) {

        if (start == goal)
            return 100.0;

        double total = start - goal;
        double done = start - current;

        double rate = (done / total) * 100.0;
        return Math.max(0, Math.min(100, rate));
    }
}
