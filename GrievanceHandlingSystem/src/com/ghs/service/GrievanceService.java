package com.ghs.service;

import com.ghs.dao.GrievanceDAO;
import com.ghs.model.Grievance;
import java.util.List;

public class GrievanceService {
    private GrievanceDAO gDAO = new GrievanceDAO();

    public String raiseGrievance(Grievance g) {
        // Business Rule: Title must be descriptive
        if (g.getTitle().length() < 3) {
            return "Error: Title is too short.";
        }
        
        int result = gDAO.addGrievance(g);
        return (result > 0) ? "Grievance submitted successfully!" : "Submission failed.";
    }

    public List<Grievance> searchGrievances(String keyword) {
        return gDAO.searchByTitle(keyword);
    }

    public String resolveGrievance(int gId) {
        int result = gDAO.updateStatus(gId, "Resolved");
        return (result > 0) ? "Grievance marked as Resolved." : "Update failed.";
    }

    public List<Grievance> fetchAllReports() {
        return gDAO.getAllGrievances();
    }
    public String removeGrievance(int gId) {
        int result = gDAO.deleteGrievance(gId);
        return (result > 0) ? "Grievance # " + gId + " deleted successfully." : "Delete failed: ID not found.";
    }
}