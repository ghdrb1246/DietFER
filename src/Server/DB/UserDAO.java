package Server.DB;

import java.sql.*;
import Common.User;

// 사용자 DAO
public class UserDAO {
    /**
     * 회원가입 저장
     * 
     * @param u 사용자 객채
     * @return 저장 여부
     */
    public boolean insert(User u) {
        String sql = """
            INSERT INTO users
            (user_id, password, sex, height, age, start_weight, goal_weight)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getID());
            ps.setString(2, u.getPW());
            ps.setBoolean(3, u.getSex());
            ps.setDouble(4, u.getHeight());
            ps.setInt(5, u.getAge());
            ps.setDouble(6, u.getStartWeight());
            ps.setDouble(7, u.getGoalWeight());

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
     * 로그인 
     * 
     * @param id 사용자 ID
     * @param pw 비밀번호
     * @return 로그인 결과
     */
    public User login(String id, String pw) {
        String sql = "SELECT * FROM users WHERE user_id=? AND password=?";

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getBoolean("sex"),
                        rs.getDouble("height"),
                        rs.getInt("age"),
                        rs.getDouble("start_weight"),
                        rs.getDouble("goal_weight"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return null;
    }

    /**
     * 시작/목표 체중 조회
     * 
     * @param id 사용자
     * @return 조회 여부
     */
    public double[] getStartAndGoalWeight(String id) {
        String sql = """
            SELECT start_weight, goal_weight
            FROM users
            WHERE user_id = ?
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new double[] {
                        rs.getDouble("start_weight"),
                        rs.getDouble("goal_weight")
                };
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return null;
    }

    
    /**
     * 사용자 정보를 조회
     * 
     * @param id 사용자
     * @return 조회 여부
     */
    public User findById(String id) {

        String sql = """
            SELECT user_id, password, sex, height, age, start_weight, goal_weight
            FROM users
            WHERE user_id = ?
        """;

        try (Connection conn = DBC.connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("password"),
                    rs.getBoolean("sex"),
                    rs.getDouble("height"),
                    rs.getInt("age"),
                    rs.getDouble("start_weight"),
                    rs.getDouble("goal_weight")
                );
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DBC.close();
        }
        return null;
    }
}
