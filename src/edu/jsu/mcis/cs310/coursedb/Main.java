package edu.jsu.mcis.cs310.coursedb;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {
    
    private static final String USERNAME = "Jclifton4@stu.jsu.edu";
    
    public static void main(String[] args) {
        
        // Create DAO Objects
        DAOFactory daoFactory = new DAOFactory("coursedb");
        
        RegistrationDAO registrationDao = daoFactory.getRegistrationDAO();
        SectionDAO sectionDao = daoFactory.getSectionDAO();
        StudentDAO studentDao = daoFactory.getStudentDAO();
        
        // **Fix: Get Student ID Correctly**
        int studentid = studentDao.find(USERNAME);
        
        if (studentid == -1) { // **Check if Student ID is invalid**
            System.out.println("Error: Student '" + USERNAME + "' not found in the database.");
            return;
        }

        int termID = 202430; // Example termID for Fall 2024
        int crn = 67890; // Example CRN for testing
        
        // Test Connection
        if (!daoFactory.isClosed()) {
            System.out.println("Connected Successfully!"); 
        } else {
            System.out.println("Connection Failed!");
            return; // Exit if the connection is closed
        }

        // **Fix: Pass termID first in find()**
        System.out.println("\nSearching for Course Sections...");
        String sections = sectionDao.find(termID, "CS", "310");
        System.out.println("Matching Sections: " + sections);

        // **Test create() Method in RegistrationDAO**
        System.out.println("\nRegistering Student for a Course...");
        boolean registered = registrationDao.create(studentid, termID, crn);
        System.out.println("Registration Successful: " + registered);

        // **Test list() Method in RegistrationDAO**
        System.out.println("\nListing Registered Courses...");
        String registeredCourses = registrationDao.list(studentid, termID);
        System.out.println("Registered Courses: " + registeredCourses);

        // **Test delete() Method (Drop Single Course)**
        System.out.println("\nDropping a Course...");
        boolean dropped = registrationDao.delete(studentid, termID, crn);
        System.out.println("Course Dropped: " + dropped);

        // **Test delete() Overload (Withdraw from All Courses)**
        System.out.println("\nWithdrawing from All Courses...");
        boolean withdrawn = registrationDao.delete(studentid, termID);
        System.out.println("Withdraw Successful: " + withdrawn);

        // **Properly Close DAO Factory**
        daoFactory.close();
    }
}