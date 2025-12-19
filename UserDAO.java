package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return users;
    }

    public boolean deleteUser(int userId){
        String sql = "DELETE FROM users WHERE user_id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,userId);
            return stmt.executeUpdate()>0;
        }catch(Exception e){ e.printStackTrace(); return false; }
    }

    public boolean updateUser(User user){
        String sql = "UPDATE users SET username=?, email=?, phone=?, password=?, role=? WHERE user_id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getEmail());
            stmt.setString(3,user.getPhone());
            stmt.setString(4,user.getPassword());
            stmt.setString(5,user.getRole());
            stmt.setInt(6,user.getUserId());
            return stmt.executeUpdate()>0;
        }catch(Exception e){ e.printStackTrace(); return false; }
    }

    public boolean addUser(User user){
        String sql = "INSERT INTO users (username,email,phone,password,role) VALUES (?,?,?,?,?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getEmail());
            stmt.setString(3,user.getPhone());
            stmt.setString(4,user.getPassword());
            stmt.setString(5,user.getRole());
            return stmt.executeUpdate()>0;
        }catch(Exception e){ e.printStackTrace(); return false; }
    }
}
