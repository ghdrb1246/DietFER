package Server.DB;

import java.sql.*;
import java.time.LocalDate;

import Common.Exercise;

public class ExerciseDAO {
    public boolean insert(String id, Exercise w) {
        String sql = """
            INSERT INTO exercises
            (user_id, exercise_time, exercise_name, minutes, kcal)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(w.getDateTime()));
            ps.setString(3, w.getExerciseName());
            ps.setDouble(4, w.getMinutes());
            ps.setDouble(5, w.getKcal());

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

    // 일일 소모 칼로리 조회
    public int getDailyBurnCal(String id, LocalDate date) {
        String sql = """
            SELECT IFNULL(SUM(kcal),0) AS burn
            FROM exercises
            WHERE user_id = ? AND DATE(exercise_time) = ?
        """;

        try (Connection conn = DBC.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setDate(2, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("burn");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBC.close();
        }
        return 0;
    }
}
