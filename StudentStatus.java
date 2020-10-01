/*
    StudentStatus.java
    Author: Scott Forsyth
    Date: Apri 6th, 2020

    Description
    Models Student Status using six constants
*/

package models;

/**
 *
 * @author Scott Forsyth
 */
public enum StudentStatus {

    GOOD_STANDING("Good Standing", "GS"),
    ACADEMIC_PROBATION("Academic Probation", "AP"),
    ACADEMIC_SUSPENSION("Academic Suspension", "AS"),
    BEHAVIOUR_SUSPENSION("Behaviour Suspension", "BS"),
    EXPELLED("Expelled", "E"),
    GRADUATED("Graduated", "G");
    
    private final String longName;
    private final String shortName;

    private StudentStatus(String longName, String shortName) {
        this.longName = longName;
        this.shortName = shortName;
    }

    /**
     * Accessor for getting the status in the long form
     * @return the status name in long form
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Accessor for getting the status in the short form
     * @return the status name in short form
     */
    public String getShortName() {
        return shortName;
    }
    
    public static StudentStatus valueOfShortName(String shortName) {
        switch(shortName.toUpperCase()) {
            case "GS":
                return GOOD_STANDING;
            case "AP":
                return ACADEMIC_PROBATION;
            case "AS":
                return ACADEMIC_SUSPENSION;
            case "BS":
                return BEHAVIOUR_SUSPENSION;
            case "E":
                return EXPELLED;
            case "G":
                return GRADUATED;
            default:
                throw new IllegalArgumentException(shortName + 
                        " is not a legal status");
        }
    }    
}
