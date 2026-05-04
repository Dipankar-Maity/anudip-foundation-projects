package com.ghs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ghs.connection.MyConnection;
import com.ghs.model.Grievance;

public class GrievanceDAO {

    // Module B: Grievance Registry
    public int addGrievance(Grievance g) {
        int result = 0;
        String query = "INSERT INTO grievances (title, description, user_id) VALUES (?, ?, ?)";
        
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, g.getTitle());
            ps.setString(2, g.getDescription());
            ps.setInt(3, g.getUserId());
            
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Module C: Search Facility
    public List<Grievance> searchByTitle(String keyword) {
        List<Grievance> list = new ArrayList<>();
        String query = "SELECT * FROM grievances WHERE title LIKE ?";
        
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Grievance g = new Grievance();
                g.setId(rs.getInt("g_id"));
                g.setTitle(rs.getString("title"));
                g.setDescription(rs.getString("description"));
                g.setStatus(rs.getString("status"));
                g.setUserId(rs.getInt("user_id"));
                g.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Module D: Resolve Complaint (The missing method for line 25)
    public int updateStatus(int gId, String status) {
        int result = 0;
        String query = "UPDATE grievances SET status = ? WHERE g_id = ?";
        
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, status);
            ps.setInt(2, gId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Module D: Reports (The missing method for line 30)
    public List<Grievance> getAllGrievances() {
        List<Grievance> list = new ArrayList<>();
        String query = "SELECT * FROM grievances";
        
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
        	while (rs.next()) {
                Grievance g = new Grievance();
                g.setId(rs.getInt("g_id"));
                g.setTitle(rs.getString("title"));
                g.setDescription(rs.getString("description")); // <--- MAKE SURE THIS LINE IS PRESENT
                g.setStatus(rs.getString("status"));
                g.setUserId(rs.getInt("user_id"));
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int deleteGrievance(int gId) {
        int result = 0;
        String query = "DELETE FROM grievances WHERE g_id = ?";
        
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, gId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}