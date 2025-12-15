package Common;

import java.util.ArrayList;

/**
 * 메시지 생성 클래스
 */
public class MessageBuilder {
    /**
     * 기본 메시지 생성기
     * 형식: TYPE/arg1/arg2/arg3...
     */
    public String build(MessageType type, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());

        if (args != null) {
            for (String arg : args) {
                sb.append("/");
                sb.append(arg == null ? "" : arg.trim());
            }
        }
        return sb.toString();
    }

    // 개별 메시지 전용 편의 함수

    /**
     * 회원가입 요청
     * 
     * @param u 사용자 객체
     * @return SIGNUP_REQ/사용자 ID/PW/성별/키/나이/초기 체중/목표 체중
     */
    public String signupReq(User u) {
        return build(
            MessageType.SIGNUP_REQ, 
            u.getID(),
            u.getPW(), 
            String.valueOf(u.getSex()), 
            String.valueOf(u.getHeight()), 
            String.valueOf(u.getAge()), 
            String.valueOf(u.getStartWeight()), 
            String.valueOf(u.getGoalWeight())
        );
    }

    /**
     * 회원가입 처리 결과
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     * @return       SIGNUP_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String signupRes(String id, String result) {
        return build(MessageType.SIGNUP_RES, id, result);
    }

    /**
     * 로그인 요청
     * 
     * @param id 사용자 ID
     * @param pw 사용자 비밀번호
     * @return LOGIN_REQ/사용자 ID/PW
     */
    public String loginReq(String id, String pw) {
        return build(MessageType.LOGIN_REQ, id, pw);
    }

    /**
     * 로그인 처리 결과
     * 
     * @param id     사용자 ID
     * @param result 처리 결과
     * @return      LOGIN_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String loginRes(String id, String result) {
        return build(MessageType.LOGIN_RES, id, result);
    }

    /**
     * 회원 정보 수정 요청
     * 
     * @param u
     * @return USER_UPDATE_REQ/성별/키/나이/초기 체중/목표 체중
     */
    /* public String userUpdateReq(User u) {
        return build(
            MessageType.USER_UPDATE_REQ, 
            String.valueOf(u.getSex()), 
            String.valueOf(u.getHeight()),
            String.valueOf(u.getAge()), 
            String.valueOf(u.getStartWeight()), 
            String.valueOf(u.getGoalWeight())
        );
    } */

    /**
     * 로그아웃 요청
     * 
     * @param id 사용자 ID
     * @return LOGOUT_REQ/사용자 ID
     */
    public String logoutReq(String id) {
        return build(MessageType.LOGOUT_REQ, id);
    }

    /**
     * 로그아웃 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return LOGOUT_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String logoutRes(String id, String result) {
        return build(MessageType.LOGOUT_RES, id, result);
    }
    /**
     * 식단 입력 추가 요청
     * 
     * @param m 음식
     * @return MEAL_ADD_REQ/사용자 ID/날짜+시간/음식 타입/음식명/섭취량/칼로리/탄수회물/단백질/지방
     */
    public String mealAddReq(String id, Meal m) {
        return build(
            MessageType.MEAL_ADD_REQ, 
            id,
            m.getDateTime().toString(),
            m.getFoodName(), 
            m.getFoodTypr(),
            String.valueOf(m.getGram()),
            String.valueOf(m.getKcal()),
            String.valueOf(m.getCarbohydrate()),
            String.valueOf(m.getProtein()),
            String.valueOf(m.getFat())
        );
    }

    /**
     * 식단 입력 추가 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return MEAL_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String mealAddRes(String id, String result) {
        return build(MessageType.MEAL_ADD_RES, id, result);
    }

    /**
     * 운동 입력 추가 요청
     * 
     * @param w 운동
     * @return EXERCISE_ADD_REQ/사용자 ID/날짜+시간/운동명/운동 시간
     */
    public String exerciseAddReq(String id, Exercise ex) {
        return build(
            MessageType.EXERCISE_ADD_REQ, 
            id,
            ex.getDateTime().toString(), 
            ex.getExerciseName(), 
            String.valueOf(ex.getMinutes())
        );
    }

    /**
     * 운동 입력 추가 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return EXERCISE_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String exerciseAddRes(String id, String result) {
        return build(MessageType.EXERCISE_ADD_RES, id, result);
    }

    /**
     * 체중 입력 추가 요청
     * 
     * @param w 체중
     * @return WEIGHT_ADD_REQ/사용자 ID/날짜/체중
     */
    public String weightAddReq(String id, Weight w) {
        TimeConversion tc = new TimeConversion();
        return build(MessageType.WEIGHT_ADD_REQ, id, tc.timeToStr(w.getDate()), String.valueOf(w.getWeight()));
    }

    /**
     * 체중 입력 추가 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return WEIGHT_ADD_RES/사용자 ID/처리 결과(“OK” or “FAIL”)
     */
    public String weightAddRes(String id, String result) {
        return build(MessageType.WEIGHT_ADD_RES, id, result);
    }

    /**
     * 기록 조회 요청
     * 
     * @param id 사용자 ID
     * @return RECORD_REQ/사용자 ID
     */
    public String recordReq(String id) {
        return build(MessageType.RECORD_REQ, id);
    }

    /**
     * 기록 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return RECORD_RES/사용자 ID/처리 결과(날짜1(식단명,운동명,체중)+…+날짜n(식단명,운동명,체중) or “FAIL”)
     */
    public String recordRes(String id, ArrayList<RecordData> result) {
        if (result == null || result.isEmpty()) {
            return MessageType.RECORD_RES + "/" + id + "/FAIL";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.RECORD_RES).append("/");
        sb.append(id).append("/");

        for (int i = 0; i < result.size(); i++) {
            RecordData r = result.get(i);

            sb.append(r.date.toString()) // LocalDate → String
                    .append("(")
                    .append(r.mealName).append(",")
                    .append(r.exerciseName).append(",")
                    .append(r.weight)
                    .append(")");

            if (i < result.size() - 1) {
                sb.append("+");
            }
        }

        return sb.toString();
    }

    /**
     * 달성률 요청
     * 
     * @param id 사용자 ID
     * @return PROGRESS_REQ/사용자 ID
     */
    public String progressReq(String id) {
        return build(MessageType.PROGRESS_REQ, id);
    }

    /**
     * 달성률 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return PROGRESS_RES/사용자 ID/처리 결과(초기 체중/목표 체중/현재 체중/달성률 or “FAIL”)
     */
    public String progressRes(String id, Progress p) {
        return build(MessageType.PROGRESS_RES, id, String.valueOf(p.getInitial()), String.valueOf(p.getGoal()), String.valueOf(p.getCurrent()), String.valueOf(p.getProgress()));
    }

    /**
     * 피드백 요청
     * 
     * @param id 사용자 ID
     * @return FEEDBACK_REQ/사용자 ID
     */ 
    public String feedbackReq(String id) {
        return build(MessageType.FEEDBACK_REQ, id);
    }

    /**
     * 피드백 처리 결과
     * 
     * @param id 사용자 ID
     * @param result 처리 결과
     * @return FEEDBACK_RES/
     *         사용자 ID/
     *         처리 결과(
     *         섭취+소모+잔여+권장/
     *         탄수화물_섭취량+탄수화물_권장량/
     *         단백질_섭취량+단백질_권장량/
     *         지방_섭취량+지방_권장량/
     *         음식 추천 리스트/
     *         운동 추천 리스트 or “FAIL”
     *         )
     */
    public String feedbackRes(String id, FeedbackResult result) {
        if (!result.getSuccess()) {
            return MessageType.FEEDBACK_RES + "/" + id + "/FAIL";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.FEEDBACK_RES).append("/");
        sb.append(id).append("/");

        // 섭취+소모+잔여+권장
        sb.append(result.getIntake()).append("+")
        .append(result.getBurn()).append("+")
        .append(result.getRemain()).append("+")
        .append(result.getRecommendCal()).append("/");

        // 탄수화물
        sb.append(result.getCarbIntake()).append("+")
        .append(result.getCarbRecommend()).append("/");

        // 단백질
        sb.append(result.getProteinIntake()).append("+")
        .append(result.getProteinRecommend()).append("/");

        // 지방
        sb.append(result.getFatIntake()).append("+")
        .append(result.getFatRecommend()).append("/");

        // 음식 추천
        ArrayList<String> food = result.getFoodRecommend();
        for (int i = 0; i < food.size(); i++) {
            sb.append(food.get(i));
            if (i < food.size() - 1) sb.append("+");
        }
        sb.append("/");

        // 운동 추천
        ArrayList<String> exercise = result.getExerciseRecommend();
        for (int i = 0; i < exercise.size(); i++) {
            sb.append(exercise.get(i));
            if (i < exercise.size() - 1) sb.append("+");
        }

        return sb.toString();
    }
    
    // 음식 검색 요청
    public String foodSearcReq(String id, String foodSear) {
        return build(MessageType.FOOD_SEARCH_REQ, id, foodSear);
    }

    // 음식 검색 처리 결과
    public String foodSearcRes(String id, ArrayList<FoodNutrition> fnlList) {
        if (fnlList.size() < 0) {
            return MessageType.FOOD_SEARCH_RES + "/" + id + "/FAIL";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.FOOD_SEARCH_RES).append("/");
        sb.append(id).append("/");

        for (int i = 0; i < fnlList.size(); i++) {
            // 리스트 i번째 FoodNutrition 객체
            FoodNutrition fn = fnlList.get(i);

            // 음식명+칼로리+탄수화물+단백질+지방/.../음식명+칼로리+탄수화물+단백질+지방
            sb.append(fn.getFoodName()).append("+")
                .append(fn.getKcal()).append("+")
                .append(fn.getCarbohydrate()).append("+")
                .append(fn.getProtein()).append("+")
                .append(fn.getFat()).append("/");
        }
        
        // 마지막 "/" 제거
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
