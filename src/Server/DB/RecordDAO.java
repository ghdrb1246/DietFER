package Server.DB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Common.RecordData;
/* 
public class RecordDAO {
    public ArrayList<RecordData> findRecordsByUser(String userId) {
        ArrayList<RecordData> list = new ArrayList<>();

        String sql = """
            SELECT
                d.record_date,
                IFNULL(m.meals, '') AS meals,
                IFNULL(w.workouts, '') AS workouts,
                wt.weight
            FROM
                (
                    SELECT DISTINCT DATE(meal_time) AS record_date
                    FROM meals
                    WHERE user_id = ?

                    UNION

                    SELECT DISTINCT DATE(workout_time)
                    FROM workouts
                    WHERE user_id = ?

                    UNION

                    SELECT DISTINCT DATE(record_time)
                    FROM weights
                    WHERE user_id = ?
                ) d

            LEFT JOIN
                (
                    SELECT
                        DATE(meal_time) AS mdate,
                        GROUP_CONCAT(food_name SEPARATOR ', ') AS meals
                    FROM meals
                    WHERE user_id = ?
                    GROUP BY DATE(meal_time)
                ) m
                ON d.record_date = m.mdate

            LEFT JOIN
                (
                    SELECT
                        DATE(workout_time) AS wdate,
                        GROUP_CONCAT(exercise_name SEPARATOR ', ') AS workouts
                    FROM workouts
                    WHERE user_id = ?
                    GROUP BY DATE(workout_time)
                ) w
                ON d.record_date = w.wdate

            LEFT JOIN
                (
                    SELECT
                        DATE(record_time) AS wtdate,
                        weight
                    FROM weights w1
                    WHERE user_id = ?
                        AND record_time = (
                            SELECT MAX(record_time)
                            FROM weights w2
                            WHERE w2.user_id = w1.user_id
                            AND DATE(w2.record_time) = DATE(w1.record_time)
                        )
                ) wt
                ON d.record_date = wt.wtdate

            ORDER BY d.record_date DESC;
        """;

        try (Connection conn = DBC.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // user_id 바인딩
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, userId);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                LocalDateTime date =
                        rs.getDate("record_date").toLocalDate().atStartOfDay();

                String meals = rs.getString("meals");
                String workouts = rs.getString("workouts");
                Double weight = rs.getObject("weight") != null
                        ? rs.getDouble("weight")
                        : null;

                list.add(new RecordData(
                        date,
                        meals,
                        workouts,
                        weight
                ));
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }

        return list;
    }
} */

public class RecordDAO {
    public ArrayList<RecordData> findRecordsByUser(String userId) {
        ArrayList<RecordData> list = new ArrayList<>();
        String sql = """
                    SELECT
                        m.meal_time AS record_time,
                        m.food_name AS meals,
                        NULL AS workouts,
                        NULL AS weight
                    FROM meals m
                    WHERE m.user_id = ?

                    UNION ALL

                    SELECT
                        w.workout_time,
                        NULL,
                        w.exercise_name,
                        NULL
                    FROM workouts w
                    WHERE w.user_id = ?

                    UNION ALL

                    SELECT
                        wt.record_time,
                        NULL,
                        NULL,
                        wt.weight
                    FROM weights wt
                    WHERE wt.user_id = ?

                    ORDER BY record_time DESC;
                """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // user_id 바인딩을 3번만 수행
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, userId);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                // TIMESTAMP를 사용하여 정밀한 시간을 가져옴
                Timestamp ts = rs.getTimestamp("record_time");
                // 저장한 날짜을 가져옴
                LocalDateTime date = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
                // 음식을 가져옴
                String meals = rs.getObject("meals") != null ? rs.getString("meals") : "-";
                // 운동을 가져옴
                String workouts = rs.getObject("workouts") != null ? rs.getString("workouts") : "-";
                // 체중을 가져옴
                Double weight = rs.getObject("weight") != null ? rs.getDouble("weight") : 0.0;

                list.add(new RecordData(
                    date,
                    meals,
                    workouts,
                    weight
                ));
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }

        return list;
    }
}