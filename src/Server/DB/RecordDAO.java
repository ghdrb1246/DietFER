package Server.DB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Common.RecordData;

public class RecordDAO {
    public ArrayList<RecordData> findRecordsByUser(String userId) {
        ArrayList<RecordData> list = new ArrayList<>();
        String sql = """
                    SELECT
                        m.meal_time AS record_time,
                        m.food_name AS meals,
                        NULL AS exercises,
                        NULL AS weight
                    FROM meals m
                    WHERE m.user_id = ?

                    UNION ALL

                    SELECT
                        w.exercise_time,
                        NULL,
                        w.exercise_name,
                        NULL
                    FROM exercises w
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
                LocalDateTime date = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();
                // 음식을 가져옴
                String meals = rs.getObject("meals") != null ? rs.getString("meals") : "-";
                // 운동을 가져옴
                String exercises = rs.getObject("exercises") != null ? rs.getString("exercises") : "-";
                // 체중을 가져옴
                double weight = rs.getObject("weight") != null ? rs.getDouble("weight") : 0.0;

                list.add(new RecordData(
                    date,
                    meals,
                    exercises,
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