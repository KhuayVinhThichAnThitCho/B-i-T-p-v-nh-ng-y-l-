package dao;
import model.Category;
import until.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

	public List<Category> findByUserId(int userId) {
	    List<Category> list = new ArrayList<>();
	    String sql = "SELECT * FROM categories WHERE user_id = ?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Category c = new Category();
	            c.setId(rs.getInt("id"));
	            c.setName(rs.getString("name"));
	            c.setDescription(rs.getString("description"));
	            c.setUserId(rs.getInt("user_id"));
	            list.add(c);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

    public boolean insert(Category c) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO categories(name, description, user_Id) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Category c) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE categories SET name=?, description=? WHERE id=? AND user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getId());
            ps.setInt(4, c.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id, int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM categories WHERE id=? AND user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
