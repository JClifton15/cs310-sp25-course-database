package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                result = (ps.executeUpdate() > 0);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                result = (ps.executeUpdate() > 0);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                result = (ps.executeUpdate() > 0);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    public String list(int studentid, int termid) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "SELECT studentid, termid, crn FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                rs = ps.executeQuery();
                
                rsmd = rs.getMetaData();
                result = DAOUtility.getResultSetAsJson(rs);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }
}