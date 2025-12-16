package Server.Service;

import Server.DB.*;

import java.time.LocalDate;
import java.util.ArrayList;

import Common.FeedbackResult;
import Common.User;

/* 
    사용자의 일일 섭취 칼로리, 
    운동을 통한 소모 칼로리, 
    권장 섭취량 대비 잔여 칼로리, 
    영양소(탄수화물,단백질,지방)섭취 현황, 
    개인 신체 정보 기반 권장 칼로리를 조회 및 계산해 피드백 객체 생성
*/
public class FeedbackService {
    // 음식 DAO
    private MealDAO mealDAO = new MealDAO();
    // 운동 DAO
    private ExerciseDAO exerciseDAO = new ExerciseDAO();
    // 체중 DAO
    private UserDAO userDAO = new UserDAO();

    /**
     * 피드백 생성
     * @param id 사용자 id
     * @return 피드백 리턴
     */
    public FeedbackResult getFeedback(String id) {
        // 현재 날짜
        LocalDate today = LocalDate.now();
        // 일일 섭취량 조회
        int intake = mealDAO.getDailyIntakeCal(id, today);
        // 일일 소모 칼로리 조회
        int burn = exerciseDAO.getDailyBurnCal(id, today);
        // 잔여 칼로리
        int remain = intake - burn;

        // 사용자 정보 조회
        User u = userDAO.findById(id);
        if (u == null) return null;

        // 남, 여에 따라 단순 BMR 계산
        int recommendCal = calcRecommendCal(u);

        // 매일 필요한 영양소 섭취
        int[] n = mealDAO.getDailyNutrition(id, today);
        int carb = n[0], protein = n[1], fat = n[2];

        // 탄수화물 현황
        int carbRec = (int) (recommendCal * 0.5 / 4);
        // 단백질 현황
        int proteinRec = (int) (recommendCal * 0.3 / 4);
        // 지방 현황
        int fatRec = (int) (recommendCal * 0.2 / 9);

        // 음식 피드백 
        ArrayList<String> foodRec = recommendFood(carb, protein, fat, carbRec, proteinRec, fatRec);
        // 운동 피드백
        ArrayList<String> exerciseRec = recommendExercise(remain, recommendCal);

        // 모든 피드백 객체 생성 후 리턴
        return new FeedbackResult(
            intake, burn, remain, recommendCal,
            carb, carbRec,
            protein, proteinRec,
            fat, fatRec,
            foodRec,
            exerciseRec
        );
    }

    /**
     * 남, 여에 따라 단순 BMR 계산
     * 
     * @param u 시용자
     * @return BMR 값
     */
    private int calcRecommendCal(User u) {
        if (u.getSex()) {
            // 남
            return (int) (10 * u.getStartWeight() + 6.25 * u.getHeight() - 5 * u.getAge() + 5);
        } 
        else { 
            // 여
            return (int) (10 * u.getStartWeight() + 6.25 * u.getHeight() - 5 * u.getAge() - 161);
        }
    }

    /**
     * 음식 피드백 생성
     * 
     * @param carb       탄수화물
     * @param protein    단백질
     * @param fat        지방
     * @param carbRec    탄수회물 섭취 관장량
     * @param proteinRec 단백질 섭취 관장량
     * @param fatRec     지방 섭취 관장량
     * @return 음식 피드백 list
     */ 
    private ArrayList<String> recommendFood(int carb, int protein, int fat, int carbRec, int proteinRec, int fatRec) {
        ArrayList<String> list = new ArrayList<>();
        if (protein < proteinRec)
            list.add("단백질 보충 식품");
        if (carb < carbRec)
            list.add("복합 탄수화물 섭취");
        if (fat > fatRec)
            list.add("지방 섭취 줄이기");
        return list;
    }

    /**
     * 운동 피드백 생성
     * @param remain 현재 체중
     * @param recommend 목표 체중
     * @return
     */
    private ArrayList<String> recommendExercise(int remain, int recommend) {
        ArrayList<String> list = new ArrayList<>();
        if (remain > recommend)
            list.add("유산소 운동 30분");
        else
            list.add("가벼운 스트레칭");
        return list;
    }
}
