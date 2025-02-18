package edu.jsu.mcis.cs310.coursedb;

import edu.jsu.mcis.cs310.coursedb.dao.*;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

public class CourseRegistrationDatabaseTest {

    private static final String USERNAME = "nobody@jsu.edu";
    private DAOFactory daoFactory;
    private RegistrationDAO registrationDao;
    private SectionDAO sectionDao;
    private int studentid;

    @Before
    public void setUp() {
        daoFactory = new DAOFactory("coursedb");
        registrationDao = daoFactory.getRegistrationDAO();
        sectionDao = daoFactory.getSectionDAO();
        
        studentid = daoFactory.getStudentDAO().find(USERNAME);
        System.out.println("DEBUG: Student ID retrieved: " + studentid);
        
        assertTrue("ERROR: Student ID not found!", studentid > 0);
    }

    private JsonArray normalizeJson(JsonArray jsonArray) {
        jsonArray.sort((a, b) -> a.toString().compareTo(b.toString()));
        return jsonArray;
    }

    @Test
    public void testRegisterSingle() {
        try {
            JsonArray expected = (JsonArray) Jsoner.deserialize("[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10520\"}]");
            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);
            boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, 10520);
            assertTrue("ERROR: Registration failed!", result);
            JsonArray actual = (JsonArray) Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24));
            System.out.println("DEBUG: Expected: " + expected);
            System.out.println("DEBUG: Actual: " + actual);
            assertEquals("JSON mismatch!", normalizeJson(expected), normalizeJson(actual));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGetSections() {
        try {
            JsonArray expected = (JsonArray) Jsoner.deserialize("[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"08:45:00\",\"days\":\"MWF\",\"section\":\"001\",\"end\":\"09:45:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"10559\",\"subjectid\":\"CS\"}]");
            JsonArray actual = (JsonArray) Jsoner.deserialize(sectionDao.find(1, "CS", "201"));
            System.out.println("DEBUG: Expected: " + expected);
            System.out.println("DEBUG: Actual: " + actual);
            assertEquals("JSON mismatch!", normalizeJson(expected), normalizeJson(actual));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
