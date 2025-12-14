package Server.Service;

import Server.DB.UserDAO;
import Server.DB.WeightDAO;
import Common.Progress;

public class ProgressService {
    private UserDAO userDAO = new UserDAO();
    private WeightDAO weightDAO = new WeightDAO();

    public Progress getProgress(String userId) {

        double[] w = userDAO.getStartAndGoalWeight(userId);
        if (w == null)
            return null;

        double initial = w[0];
        double goal = w[1];

        Double current = weightDAO.getLatestWeight(userId);
        if (current == null)
            current = initial; // 체중 기록 없을 경우

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
