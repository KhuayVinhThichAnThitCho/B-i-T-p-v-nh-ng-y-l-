package dao;

import model.User;
import until.DBConnection;
import java.sql.*;

public class UserDAO {

	public User findByUsername(String username) {
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT * FROM users WHERE username=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()){
	            User u = new User();
	            u.setId(rs.getInt("id"));
	            u.setUsername(rs.getString("username"));
	            u.setEmail(rs.getString("email"));
	            u.setPasswordHash(rs.getString("password"));
	            return u;
	        }
	    } catch(Exception e){ 
	        e.printStackTrace(); 
	    }
	    return null;
	}


	public boolean insert(User user) {
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "INSERT INTO users(username, email, password) VALUES (?,?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getEmail());
	        ps.setString(3, user.getPasswordHash()); // hoặc user.getPassword() nếu field trong model tên khác
	        return ps.executeUpdate() > 0;
	    } catch(Exception e){ 
	        e.printStackTrace(); 
	    }
	    return false;
	}

}
