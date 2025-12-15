package Server.DB;

import java.sql.*;
import Common.Weight;

public class WeightDAO {
    // 체중 저장
    public boolean insert(String id, Weight w) {
        String sql = """
            INSERT INTO weights (user_id, record_time, weight)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(w.getDate()));
            ps.setDouble(3, w.getWeight());

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
    
    // 최신 체중 조회
    public double getLatestWeight(String id) {
        String sql = """
            SELECT weight
            FROM weights
            WHERE user_id = ?
            ORDER BY record_time DESC
            LIMIT 1
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("weight");
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return 0.0;
    }
}
