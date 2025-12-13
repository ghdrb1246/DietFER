package Server;

import Server.DB.*;
import Common.FeedbackResult;
import Common.User;

import java.time.LocalDate;
import java.util.ArrayList;

/* 
    사용자의 일일 섭취 칼로리, 운동을 통한 소모 칼로리, 
    권장 섭취량 대비 잔여 칼로리, 영양소(탄수화물,단백질,지방)섭취 현황, 
    개인 신체 정보 기반 권장 칼로리를 조회 및 계산해 피드백 객체 생성
*/
public class FeedbackService {
    private MealDAO mealDAO = new MealDAO();
    private WorkoutDAO workoutDAO = new WorkoutDAO();
    private UserDAO userDAO = new UserDAO();

    public FeedbackResult getFeedback(String userId) {

        LocalDate today = LocalDate.now();

        int intake = mealDAO.getDailyIntakeCal(userId, today);
        int burn = workoutDAO.getDailyBurnCal(userId, today);
        int remain = intake - burn;

        User u = userDAO.findById(userId);
        if (u == null)
            return null;

        int recommendCal = calcRecommendCal(u);

        int[] n = mealDAO.getDailyNutrition(userId, today);
        int carb = n[0], protein = n[1], fat = n[2];

        int carbRec = (int) (recommendCal * 0.5 / 4);
        int proteinRec = (int) (recommendCal * 0.3 / 4);
        int fatRec = (int) (recommendCal * 0.2 / 9);

        ArrayList<String> foodRec = recommendFood(carb, protein, fat, carbRec, proteinRec, fatRec);
        ArrayList<String> workoutRec = recommendWorkout(remain, recommendCal);

        return new FeedbackResult(
                intake, burn, remain, recommendCal,
                carb, carbRec,
                protein, proteinRec,
                fat, fatRec,
                foodRec,
                workoutRec);
    }

    private int calcRecommendCal(User u) {
        // 단순 BMR 기반 (Mifflin-St Jeor)
        if (u.getSex()) { // 남
            return (int) (10 * u.getStartWeight() + 6.25 * u.getHeight() - 5 * u.getAge() + 5);
        } else { // 여
            return (int) (10 * u.getStartWeight() + 6.25 * u.getHeight() - 5 * u.getAge() - 161);
        }
    }

    private ArrayList<String> recommendFood(int carb, int protein, int fat,
            int carbRec, int proteinRec, int fatRec) {
        ArrayList<String> list = new ArrayList<>();
        if (protein < proteinRec)
            list.add("단백질 보충 식품");
        if (carb < carbRec)
            list.add("복합 탄수화물 섭취");
        if (fat > fatRec)
            list.add("지방 섭취 줄이기");
        return list;
    }

    private ArrayList<String> recommendWorkout(int remain, int recommend) {
        ArrayList<String> list = new ArrayList<>();
        if (remain > recommend)
            list.add("유산소 운동 30분");
        else
            list.add("가벼운 스트레칭");
        return list;
    }
}
