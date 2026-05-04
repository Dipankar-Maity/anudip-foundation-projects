package com.ghs.main;

import java.util.List;
import java.util.Scanner;
import com.ghs.model.*;
import com.ghs.service.*;

public class GrievanceController {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private GrievanceService gService = new GrievanceService();
    private User loggedInUser = null;

    public void startSystem() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   GRIEVANCE HANDLING SYSTEM LOG");
            System.out.println("========================================");
            System.out.println("1. Login\n2. Register\n3. Exit");
            System.out.print("Select Option: ");
            
            // Basic flaw fix: Handle non-integer inputs gracefully
            String input = sc.next();
            sc.nextLine(); 

            switch (input) {
                case "1": login(); break;
                case "2": register(); break;
                case "3": 
                    System.out.println("\nThank you for using the system. Goodbye!");
                    return;
                default: 
                    System.out.println("Invalid input! Please enter 1, 2, or 3.");
            }
        }
    }

    private void login() {
        System.out.println("\n--- User Login ---");
        System.out.print("Username: "); String uname = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();

        loggedInUser = userService.login(uname, pass);

        if (loggedInUser != null) {
            System.out.println("\n>>> Login Successful! Welcome, " + loggedInUser.getUsername());
            showRoleMenu();
        } else {
            System.out.println("\n[!] Access Denied: Invalid Credentials.");
        }
    }

    private void register() {
        System.out.println("\n--- New User Registration ---");
        System.out.print("Username: "); String uname = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        System.out.println("Select Role: 1.Admin | 2.Employee | 3.Manager");
        System.out.print("Choice: ");
        int role = sc.nextInt(); sc.nextLine();

        User u = new User(0, uname, pass, role);
        String result = userService.registerUser(u);
        
        // If the result isn't successful, it's likely a duplicate username
        if (result.contains("successfully")) {
            System.out.println("\n>>> " + result);
        } else {
            System.out.println("\n[!] " + result + " (Username might already be taken)");
        }
    }

    private void showRoleMenu() {
        while (loggedInUser != null) {
            System.out.println("\n----------------------------------------");
            System.out.println("DASHBOARD | User: " + loggedInUser.getUsername());
            System.out.println("----------------------------------------");
            
            if (loggedInUser.getRoleId() == 2) { employeeMenu(); }
            else if (loggedInUser.getRoleId() == 3) { managerMenu(); }
            else { adminMenu(); }
        }
        // Explicit Logout Message
        System.out.println("\n>>> Logged out successfully. Returning to Main Menu...");
    }

   private void employeeMenu() {
        System.out.println("1. Raise Grievance\n2. Search Facility\n3. Logout");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();
        
        if (choice == 1) {
            System.out.print("Subject: "); String t = sc.nextLine();
            System.out.print("Details: "); String d = sc.nextLine();
            Grievance g = new Grievance(0, t, d, "Pending", loggedInUser.getUserId(), null);
            System.out.println("\n>>> " + gService.raiseGrievance(g));
        } else if (choice == 2) {
            System.out.print("Search keyword: ");
            List<Grievance> results = gService.searchGrievances(sc.nextLine());
            System.out.println("\n--- Search Results ---");
            if(results.isEmpty()) {
                System.out.println("No matching grievances found.");
            } else {
                // ADDED: g.getDescription() here to show the details
                results.forEach(g -> System.out.println("ID: " + g.getId() + 
                                                        " | Subject: " + g.getTitle() + 
                                                        " | Details: " + g.getDescription() + 
                                                        " | Status: " + g.getStatus()));
            }
        } else { 
            loggedInUser = null; 
        }
    }
    
    private void managerMenu() {
        System.out.println("1. View All Reports\n2. Resolve Grievance\n3. Logout");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();
        
        if (choice == 1) {
            System.out.println("\n--- All Grievance Reports ---");
            gService.fetchAllReports().forEach(g -> System.out.println("ID: " + g.getId() + " - " + g.getTitle() + " [" + g.getStatus() + "]"));
        } else if (choice == 2) {
            System.out.print("Enter Grievance ID to Resolve: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.println("\n>>> " + gService.resolveGrievance(id));
        } else { 
            loggedInUser = null; 
        }
    }

    private void adminMenu() {
        System.out.println("1. View System Audit\n2. Delete Grievance (Clean-up)\n3. Logout");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();
        
        if (choice == 1) {
            System.out.println("\n--- System Audit ---");
            List<Grievance> logs = gService.fetchAllReports();
            
            // Check if the database returned any records
            if (logs.isEmpty()) {
                System.out.println("[Notice] The grievance log is currently empty. Nothing to display.");
            } else {
                logs.forEach(g -> System.out.println("Ticket #" + g.getId() + " | " + g.getTitle() + " [" + g.getStatus() + "]"));
            }
        } else if (choice == 2) {
            System.out.print("Enter Grievance ID to DELETE: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.println("\n>>> " + gService.removeGrievance(id));
        } else { 
            loggedInUser = null; 
        }
    }
}