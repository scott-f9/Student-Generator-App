/*
    Student.java
    Author: Scott Forsyth
    Date: April 6th, 2020

    Description
    Models a Student object with studentID, firstName, lastName, gpa and status
    First name an Last name have a maximum lengtj and the student number is 
    made out of 9 digits
 */
package models;

/**
 *
 * @author Scott Forsyth
 */
public class Student {

    private String studentID;
    private String firstName;
    private String lastName;
    private double gpa;
    private StudentStatus status;

    public static final int MAX_FIRSTNAME_LENGTH = 15;
    public static final int MAX_LASTNAME_LENGTH = 20;

    /**
     * Create a new Student
     *
     * @param studentID the student's ID
     * @param sFirstName the student's first name
     * @param sLastName the student's last name
     * @param gpa the student's GPA
     * @param status the student's status
     */
    public Student(String studentID, String sFirstName, String sLastName,
            double gpa, StudentStatus status) {
        
        setStudentID(studentID);
        setFirstName(sFirstName);
        setLastName(sLastName);
        setGpa(gpa);
        setStatus(status);
        
    }

    /**
     * Parses a delimited string of student data. Assuming the delimiter is the
     * comma character ',', the format is:
     * <studentid>,<lastname>,<firstname>,<gpa>, <status>
     * Where <studentid>, <firstname> and <lastname> are strings (which are
     * trimmed at the parsing stage). Where <gpa> is double Where <status> is a
     * string that corresponds to the short name of the student status (GA, AP,
     * AS, BS, E, G)
     *
     * @param data the student data as a delimited string
     * @param delim the delimiter
     * @return a Student object if successfully, otherwise throws an exception.
     */
    public static Student parse(String data, String delim) {       
        String[] parsedData = data.split(delim);
        if (parsedData.length != 5){
            throw new IllegalArgumentException("A Student must have a "
                    + "student id, last name, first name, gpa, and status");
        }
        try{
        String id = parsedData[0].trim();
        String firstName = parsedData[1].trim();
        String lastName = parsedData[2].trim();
        double gpa = Double.parseDouble(parsedData[3].trim());
        StudentStatus status = StudentStatus.valueOfShortName(parsedData[4].trim());
        Student student = new Student(id, firstName, lastName, gpa, status);   
        return student;
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid student entry");
        }
    }

    /**
     * Accessor for the student ID
     *
     * @return the student's ID
     */
    public String getStudentID() {
         
        return studentID;
       
        
    }

    /**
     * Mutator for for the student's ID
     *
     * @param studentID for the student's ID
     * @throws IllegalArgumentException when the student number does not match
     * the format for a student number
     */
    public void setStudentID(String studentID) throws IllegalArgumentException {
        if (studentID.matches("[0-9]{9}")) {
            this.studentID = studentID;
        } else {
            throw new IllegalArgumentException("Incorrect student no format");
        }
    }

    /**
     * Accessor for the student's first name
     *
     * @return the student's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Mutator for the student's first name
     *
     * @param firstName the student's first name
     */
    public void setFirstName(String firstName) {
        try{
        this.firstName = firstName;
        }catch(Exception e){
            throw new IllegalArgumentException("first name");
        }
        
    }

    /**
     * Accessor for the student's last name
     *
     * @return the student's last name
     */
    public String getLastName() {
        return lastName;
        
    }

    /**
     * Mutator for the student's last name
     *
     * @param lastName the student's last name
     */
    public void setLastName(String lastName) {
        try{
        this.lastName = lastName;
        }catch(Exception e){
            throw new IllegalArgumentException("last name");
        }
        
    }

    /**
     * Accessor for the student's gpa
     *
     * @return the student's gpa
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Mutator for the student's gpa
     *
     * @param gpa the student's gpa
     * @throws IllegalArgumentException when gpa values are outside the gpa
     * limits
     */
    public void setGpa(double gpa) throws IllegalArgumentException {
        if (gpa < 0 || gpa > 4.0) {
            throw new IllegalArgumentException("Invalid GPA value");
        } else {
            this.gpa = gpa;
        }
    }

    /**
     * Accessor for the student's status
     *
     * @return the student's status
     */
    public StudentStatus getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(StudentStatus status) {
        try{
        this.status = status;
        }catch(Exception e){
            throw new IllegalArgumentException("status");
        }
    }

    @Override
    public String toString() {

        
        String strStudent = studentID + "," + firstName + "," + lastName
                + "," + gpa + "," + status.getShortName();
        
        return strStudent; 
    }
}
