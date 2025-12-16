package Server.DB;

import java.sql.*;

import Common.Weight;

// 체중 DAO
public class WeightDAO {
    /**
     * 체중 저장
     * 
     * @param id 사용자 ID
     * @param w 체중 객체
     * @return 저장 여부
     */
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
    
    /**
     * 최신 체중 조회
     * 
     * @param id 사용자 ID
     * @return 조회 여부
     */
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
