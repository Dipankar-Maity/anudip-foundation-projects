package com.ghs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ghs.connection.MyConnection;
import com.ghs.model.User;

public class UserDAO {
    
    // Module A: User Registration
	public int registerUser(User user) {
	    int result = 0;
	    // Removed 'email' from the columns and reduced '?' count to 3
	    String query = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
	    
	    try (Connection con = MyConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {
	        
	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getPassword());
	        ps.setInt(3, user.getRoleId());
	        
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}

    // Module A: User Authentication (Login)
	// Module A: User Authentication (Login)
	public User login(String username, String password) {
	    User user = null;
	    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	    
	    try (Connection con = MyConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {
	        
	        ps.setString(1, username);
	        ps.setString(2, password);
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setUsername(rs.getString("username"));
	            user.setRoleId(rs.getInt("role_id"));
	            // LINE 49 REMOVED: user.setEmail is gone because the column doesn't exist
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
}