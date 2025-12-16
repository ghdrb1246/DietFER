package Server.DB;

import java.sql.*;
import java.time.LocalDate;

import Common.Meal;

/**
 * 음식 DAO
 */
public class MealDAO {
    /**
     * 음식 저장
     * 
     * @param id 사용자 ID
     * @param m 음식 객체
     * @return 저장 여부
     */
    public boolean insert(String id, Meal m) {
        String sql = """
            INSERT INTO meals
            (user_id, meal_time, food_name, food_type, gram,
                kcal, carbohydrate, protein, fat)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(m.getDateTime()));
            ps.setString(3, m.getFoodName());
            ps.setString(4, m.getFoodTypr());
            ps.setDouble(5, m.getGram());
            ps.setDouble(6, m.getKcal());
            ps.setDouble(7, m.getCarbohydrate());
            ps.setDouble(8, m.getProtein());
            ps.setDouble(9, m.getFat());

            return ps.executeUpdate() == 1;

        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
        finally {
            DBC.close();
        }
    }

    /**
     * 일일 섭취량 조회
     * 
     * @param id    사용자 ID
     * @param date  날짜
     * @return      조회 여부
     */
    public int getDailyIntakeCal(String id, LocalDate date) {
        String sql = """
            SELECT IFNULL(SUM(kcal),0) AS intake
            FROM meals
            WHERE user_id = ? AND DATE(meal_time) = ?
        """;

        try (Connection conn = DBC.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setDate(2, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("intake");

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return 0;
    }

    /**
     * 매일 필요한 영양소 섭취 조회
     * 
     * @param id    사용자 ID
     * @param date  날짜
     * @return      조회 여부
     */
    public int[] getDailyNutrition(String id, LocalDate date) {
        String sql = """
            SELECT
                IFNULL(SUM(carbohydrate),0) AS carb,
                IFNULL(SUM(protein),0) AS protein,
                IFNULL(SUM(fat),0) AS fat
            FROM meals
            WHERE user_id = ? AND DATE(meal_time) = ?
        """;

        try (Connection conn = DBC.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setDate(2, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new int[] {
                    rs.getInt("carb"),
                    rs.getInt("protein"),
                    rs.getInt("fat")
                };
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return new int[] {0,0,0};
    }
}
