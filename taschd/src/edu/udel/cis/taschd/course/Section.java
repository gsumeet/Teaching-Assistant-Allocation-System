package edu.udel.cis.taschd.course;

import java.util.ListIterator;
import java.util.Objects;
import java.util.ArrayList;

import edu.udel.cis.taschd.ca.CourseAssistant;
import edu.udel.cis.taschd.time.WeeklySchedule;

/**
 * <p>
 * The {@link Section} class contains the attributes for each {@link Section}. There
 * is a relationship between lab and lecture sections for course instances of a
 * given instructor. If a {@link CourseInstance}'s {@link CourseInstance#isHasLab}
 * attribute is TRUE, there is a corresponding lecture section for that lab section.
 * For example, Fall 2018 CISC 675 Software Engineer's lecture section is "010" and
 * the {@link CourseInstance#isHasLab()} is TRUE, therefore, the lab section for this
 * {@link CourseInstance} is "020L". Note that {@link Course}s CISC 106 and CISC 108 are the
 * only 2 Courses that require lab assistants, as specified by Dr. Daniel Chester.
 * </p>
 * 
 * <p>
 * {@link Section}s are belong to a {@link Course}.
 * </p>
 * 
 * @author matthew
 */

public class Section implements Comparable<Section>{
    /**
     * The university department identifier that this {@link Section} has. For example,
     * "L" stands for Lab. This is a String consisting of only capital letters.
     */
    private String sectionType;

    /**
     * The university 3-digit section identifier that this {@link Course} has. This is 
     * a String in the range [000,999]. Note that all sections have leading 0's. 
     * For example, 10 represents 010 and 1 represents 001.
     */
    private String sectionNumber;

    /**
     * The name of the instructor teaching this {@link Section} represented as "LastName,
     *  FirstName". For example, "Siegel, Stephen". This is a comma-separated proper-case String.
     */
    private String instructorName;

    /**
     * The number of students currently enrolled in the {@link Section}. This is an integer 
     * in the range [0,{@link Section#enrollmentCap}]. Note that a {@link Section} needs at least 10
     * students to be offered within a given {@link CourseInstance}. Otherwise, the university may
     * cancel registration for that {@link CourseInstance} for that given term.
     */
    private int currentEnrollment;

    /**
     * The maximum number of students that can enroll in this {@link Section}. This is an integer that
     * is predetermined by the university.
     */
    private int enrollmentCap;

    /**
     * The university 3-character building name and 3-digit room number identifier
     * which is the meeting place for this {@link Section} during the term.
     * For example, "MEM215" stands for Memorial Hall Room 215. This is a String
     * consisting of a 3-character prefix of only capital letters and a 3-digit
     * post-fix of integers in the range of [000-999]. Note that all course codes
     * have leading 0's. For example, 10 represents 010 and 1 represents 001.
     */
    private String location;

    /**
     * The {@link WeeklySchedule} that this {@link Section} will meet.
     * For example, "MWF 11:00AM - 12:15PM" stands for Computer and Information Science.
     * This is an ordered set of {@link edu.udel.cis.taschd.time.TimeInterval}s.
     */
    private WeeklySchedule schedule;

    /**
     * The identifier that this {@link Section} has assigned a Teacher's Assistant. This is
     * only necessary if the {@link Section#isMtac()} is TRUE, hence the {@link Section}
     * requires a Teacher's Assistant present. This is a boolean where TRUE indicates
     * that the {@link CourseInstance} does have an assignment made and FALSE indicates that the
     * {@link CourseInstance} does not have an assignment made.
     */
    private boolean taAssigned;

    /**
     * The mandatory number of Teacher's Assistants that are required for the {@link Section}.
     * This is an integer in the range [0,3] that is predetermined by the university.
     */
    private int numOfTA;

    /**
     * The mandatory number of Lab Assistants that are required for the {@link Section}.
     * This is an integer in the range [0,2] that is predetermined by the university.
     */
    private int numOfLA;

    /**
     * The identifier that this {@link Section} requires a Teacher's Assistant present.
     * This is a boolean where TRUE indicates that the {@link CourseInstance} does require
     * attendance and FALSE indicates that the {@link CourseInstance} does not require attendance.
     */
    private boolean mtac;

    /**
     * The sequence of courseAssistants. They are ordered by Last Name, then First Name,
     * and Id Number. The ordered is determined by the method
     * {@link CourseAssistant#compareTo(CourseAssistant)}. If LastName are equal,
     * the fisrtNames are used to break the tie. If firstName are equal,
     * the Id are used to break the tie.
     */
    private ArrayList<CourseAssistant> assignedCourseAssistants;
    
    /**
     * The lecture {@link Section} object that this lab {@link Section} references.
     * For example, Section 010 has a lab of 020L.
     */
    private Section correspondingLecture;
    
    /**
     * The lab {@link Section} object that this lecture {@link Section} references.
     * For example, Section 020L has a lab of 010.
     */
    private Section correspondingLab;

    /**
     * Constructs a new {@link Section} with given {@link CourseInstance} for a specified term.
     *
     * @param sectionType
     *                          the type of section which is either lecture (LEC) or lab (LAB),
     *                          a String.
     * @param sectionNumber
     *                          the university 3-digit section identifier,
     *                          a String consisting of only digits with leading zeros (0).
     * @param instructorName
     *                          the instructor's full name who is teaching this {@link Section},
     *                          a comma-separated proper-case String such as "LastName, FirstName".
     * @param currentEnrollment
     *                          the number currently enrolled in this {@link Section},
     *                          an integer in the range [0,{@link Section#enrollmentCap}].
     * @param enrollmentCap
     *                          the max number of students that can enroll in this {@link Section},
     *                          an integer predetermined by the university.
     * @param location
     *                          the university 3-character building name and 3-digit
     *                          room number identifier, a String with a 3-character prefix of
     *                          only capital letters and a 3-digit post-fix of integers in
     *                          the range of [000-999].
     * @param schedule
     *                          the {@link WeeklySchedule} that this {@link Section} will meet,
     *                          an ordered set of {@link edu.udel.cis.taschd.time.TimeInterval}s.
     */
    public Section(String sectionType, String sectionNumber, String instructorName, int currentEnrollment,
                   int enrollmentCap, String location, WeeklySchedule schedule) {
        this.sectionType = sectionType;

        if (sectionNumber.length() != 3 || sectionNumber.matches("[0-9]"))
            throw new IllegalArgumentException(
                    "the section number must be a 3-digit String");
        this.sectionNumber = sectionNumber;

        this.instructorName = instructorName;
        this.currentEnrollment = currentEnrollment;
        this.enrollmentCap = enrollmentCap;
        this.location = location;
        this.schedule = schedule;
        this.numOfTA = 0;
        this.numOfLA = 0;
        this.mtac = false;
        this.taAssigned = false;
        this.assignedCourseAssistants = new ArrayList<>();
    }

    /**
     * A getter to get the university section type initialized, represented as a String.
     *
     * @return the section type for this {@link Section}, a String which is lecture (LEC) or lab (LAB).
     */
    public String getSectionType() {
        return sectionType;
    }

    /**
     * A getter to get the university section number initialized, represented as 
     * a 3-digit String in the range of [000,999], e.g., 
     * <pre>
     * "010".
     * </pre>
     *
     * @return the section number.
     */
    public String getSectionNumber() {
        return sectionNumber;
    }

    /**
     * A getter to get the instructor's full name initialized, represented as 
     * a comma-separated proper-case String.
     *
     * @return the instructor name.
     */
    public String getInstructorName() {
        return instructorName;
    }

    /**
     *  A getter to get the current enrollment initialized, represented as 
     *  an integer in the range of [0, {@link Section#enrollmentCap}]
     *
     * @return the current enrollment.
     */
    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    /**
     * A getter to get the max number of students that can enroll in this {@link Section},
     * represented as an integer predetermined by the university.
     *
     * @return the enrollment capacity.
     */
    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    /**
     * A getter to get the university 3-character building name and 3-digit
     * room number identifier initialized, presented as a String with a 3-character 
     * prefix of only capital letters and a 3-digit post-fix of integers in
     * the range of [000-999]. For example, "MEM215" which represents
     * Memorial Hall Room 215.
     *
     * @return the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * A getter to get the schedule for this {@link Section} which is the set of 
     * {@link edu.udel.cis.taschd.time.TimeInterval}s required for this {@link Section} 
     * initialized, represented as a {@link WeeklySchedule} of {@link edu.udel.cis.taschd.time.TimeInterval} objects.
     *
     * @return the weekly time schedule specification for this section.
     */
    public WeeklySchedule getSchedule() {
        return schedule;
    }

    /**
     * A setter to get the set of {@link edu.udel.cis.taschd.time.TimeInterval}s required for this section, represented as a
     * {@link WeeklySchedule} of {@link edu.udel.cis.taschd.time.TimeInterval} objects.
     *
     * @param schedule
     *                          the schedule associated with this {@link Section}.
     */
    public void setSchedule(WeeklySchedule schedule) {
        this.schedule = schedule;
    }
    
    /**
     * A getter to get the corresponding Lecture {@link Section} required for this lab section, representing the mapping
     * from {@link Section} to {@link Section} objects.
     *
     * @param correspondingLecture
     *                          the corresponding lecture section with this {@link Section}.
     */
    public Section getCorrespondingLecture() {
    	return correspondingLecture;
    }
    
    /**
     * A setter to set the corresponding Lecture {@link Section} required for this lab section, representing the mapping
     * from {@link Section} to {@link Section} objects.
     *
     * @param correspondingLecture
     *                          the corresponding lecture section with this {@link Section}.
     */
    public void setCorrespondingLecture(Section correspondingLecture) {
    	this.correspondingLecture = correspondingLecture;
    }
    
    /**
     * A getter to get the corresponding Lab {@link Section} required for this lecture section, representing the mapping
     * from {@link Section} to {@link Section} objects.
     *
     * @param correspondingLecture
     *                          the corresponding lab section with this {@link Section}.
     */
    public Section getCorrespondingLab() {
    	return correspondingLab;
    }
    
    /**
     * A setter to set the corresponding Lab {@link Section} required for this lecture section, representing the mapping
     * from {@link Section} to {@link Section} objects.
     *
     * @param correspondingLab
     *                          the corresponding lab section with this {@link Section}.
     */
    public void setCorrespondingLab(Section correspondingLab) {
    	this.correspondingLab = correspondingLab;
    }

    /**
     * A getter to get the taAssigned attribute initialized, represented as a boolean value 
     * where TRUE indicates that the {@link Section} does assigned a teacher's assistant and 
     * FALSE indicates that the {@link Section} does not have a teacher's assistant assigned.
     *
     * @return True if {@link Section} assigned a {@link edu.udel.cis.taschd.ca.CourseAssistant}.
     */
    public boolean isTaAssigned() {
        return taAssigned;
    }

    /**
     * A setter to get the taAssigned attribute for this {@link Section}, 
     * represented as a boolean.
     * 
     * @param taAssigned
     *                          a boolean represented as TRUE if this {@link Section}
     *                          assigned a teacher's assistant.
     */
    public void setTaAssigned(boolean taAssigned) {
        this.taAssigned = taAssigned;
    }

    /**
     * A getter to get the number of teacher's assistants needed for this {@link Section} initialized, 
     * represented as an integer in the range [0, 3]
     *
     * @return the number of teacher's assistants.
     */
    public int getNumOfTA() {
        return numOfTA;
    }

    /**
     * A setter to get the number of teacher's assistants needed for this {@link Section}, an
     * integer in the range [0, 3].
     *
     * @param numOfTA
     *                         the number of teacher's assistants required.
     */
    public void setNumOfTA(int numOfTA) {
        this.numOfTA = numOfTA;
    }

    /**
     * A getter to get the number of lab assistants needed for this {@link Section}, 
     * represented as an integer in the range [0, 2].
     *
     * @return the number of lab assistants.
     */
    public int getNumOfLA() {
        return numOfLA;
    }

    /**
     * A setter to get the number of lab assistants needed for this {@link Section} initialized, 
     * represented as an integer in the range [0, 2].
     * *
     * @param numOfLA
     *                         Number of lab assistants required.
     */
    public void setNumOfLA(int numOfLA) {
        this.numOfLA = numOfLA;
    }

    /**
     * A getter to get the mtac attribute initialized, represented as a boolean value 
     * where TRUE indicates that the {@link Section} requires mandatory attendance of a teacher's assistant and
     * FALSE indicates that the {@link Section} does not require mandatory attendance of a
     * teacher's assistant. Note that if mtac is TRUE, there must be no overlap with
     * the {@link Section#schedule} and the {@link edu.udel.cis.taschd.ca.CourseAssistant#wtps}.
     * On the contrary, if mtac is FALSE, then the {@link Section#schedule} can overlap
     * with the {@link edu.udel.cis.taschd.ca.CourseAssistant#getWtps()}.
     *
     * @return mtac True if this {@link Section} has a mandatory teacher's assistant requirement.
     */
    public boolean isMtac() {
        return mtac;
    }

    /**
     * A setter to get the hasLab attribute for this course instance, 
     * represented as a boolean.
     *
     * @param mtac
     *                         a boolean represented as TRUE if {@link Section} has a
     *                         mandatory teacher's assistant requirement.
     */
    public void setMtac(boolean mtac) {
        this.mtac = mtac;
    }

    /**
     * Returns the assigned course assistants as an iterable sequence of {@link CourseAssistant}s.
     *
     * @return the assignedCourseAssistants
     */
    public Iterable<CourseAssistant> getAssignedCourseAssistants() {
        return assignedCourseAssistants;
    }


    /**
     * Adds a {@link CourseAssistant} to this assigned course assistant list. If the given
     * {@link CourseAssistant} is already in this assignedCourseAssistantSet, this is a no-op.
     *
     *
     * @param ca
     *               a non-{@code null} {@link CourseAssistant} to add to this
     *               assignedCourseAssistant list
     */
    public void addAssignedCourseAssistant(CourseAssistant ca) {

        ListIterator<CourseAssistant> iter = assignedCourseAssistants.listIterator();

        while (iter.hasNext()) {
            CourseAssistant y = iter.next();
            int c = ca.compareTo(y);

            if (c > 0) // ca goes after y
                continue;
            if (c == 0)
                return;
            // ... x y z ...
            // y is the first item in list that comes after ca. You want to
            // insert just before y.
            iter.previous();
            break;
        }
        iter.add(ca);
    }


    /**
     * Compares the prefix of this {@link Section} with that one. 
     * Returns -1, 0, or 1 depending on Alphabetical order of the section type.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if alphabetical order of section type at this ahead of section type of another;
     *         0 if the end section type are the same;
     *         +1 the other one's section type is ahead of this one's section type.
     */
    public int compareSectionType(Section that) {

        int diff = this.getSectionType().compareTo(that.getSectionType());
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }

    /**
     * Compares the section number of this {@link Section} with that one. 
     * Returns -1, 0, or 1 depending on Alphabetical order of the section number.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if alphabetical order of section number at this ahead of section number of another; 
     *         0 if the end section number are the same; 
     *         +1 the other one's section number is ahead of this one's section number.
     */
    public int compareSectionNumber(Section that) {

        int diff = this.getSectionNumber().compareTo(that.getSectionNumber());
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
     * Compares the instructor's name of this {@link Section} with that one. 
     * Returns -1, 0, or 1 depending on Alphabetical order of the instructor's name.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if alphabetical order of instructor's name at this ahead of instructor's name of another;
     *         0 if the end instructor's name are the same;
     *         +1 the other one's instructor's name is ahead of this one's instructor's name.
     */
    public int compareInstructorName(Section that) {

        int diff = this.getInstructorName().compareTo(that.getInstructorName());
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }

    /**
	 * Compares the current enrollment number of this {@link Section} with that one.
	 * Returns -1, 0, or 1 depending on the difference of the enrollment capacity.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if current enrollment of this is larger than current enrollment number of that object;
	 *         0 if the current enrollments are the same;
	 *         +1 if the current enrollment of this is smaller than the current enrollment of that object.
     */
    public int compareCurrentEnrollment(Section that) {

    	int diff = this.getCurrentEnrollment() - that.getCurrentEnrollment();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
	 * Compares the enrollment cap number of this {@link Section} with that one. 
	 * Returns -1, 0, or 1 depending on the difference of the enrollment capacity.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if enrollment cap of this is larger than enrollment cap number of that object;
	 *         0 if the enrollment caps are the same;
	 *         +1 if the enrollment cap of this is smaller than the enrollment cap of that object.
     */
    public int compareEnrollmentCap(Section that) {

    	int diff = this.getEnrollmentCap() - that.getEnrollmentCap();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
     * Compares the location of this {@link Section} with that one. 
     * Returns -1, 0, or 1 depending on Alphabetical order of the location.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if alphabetical order of location at this ahead of location of another; 
     *         0 if the end location are the same;
     *         +1 the other one's location is ahead of this one's location.
     */
    public int compareLocation(Section that) {

        int diff = this.getLocation().compareTo(that.getLocation());
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
	 * Compares the number of teacher's assistants of this {@link Section} with that one. 
	 * Returns -1, 0, or 1 depending on the difference of the number of teacher's assistants.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if the number of teacher's assistants of this is larger than the number
	 *         of teacher's assistants of that object; 
	 *         0 if the number of teacher's assistants are the same;
	 *         +1 if the number of teacher's assistants of this is smaller than
	 *         the number of teacher's assistants of that object.
     */
    public int compareNumberOfTA(Section that) {

    	int diff = this.getNumOfTA() - that.getNumOfTA();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
	 * Compares the number of lab assistants of this {@link Section} with that one. 
	 * Returns -1, 0, or 1 depending on the difference of the number of lab assistants.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if the number of lab assistants of this is larger than the number
	 *         of lab assistants of that object; 
	 *         0 if the number of lab assistants are the same;
	 *         +1 if the number of lab assistants of this is smaller than
	 *         the number of lab assistants of that object.
     */
    public int compareNumberOfLA(Section that) {

    	int diff = this.getNumOfLA() - that.getNumOfLA();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
	 * Compares the mtac of this {@link Section} with that one. 
	 * Returns -1, 0, or 1 depending on the difference of mtac attribute.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if the mtac of this is larger than the mtac of that object; 
     *         0 if the mtac are the same;
     *         +1 if the mtac of this is smaller than the mtac of that object.
     */
    public int compareMTAC(Section that) {

    	int diff = ((Boolean)(this.isMtac())).hashCode() -
    			((Boolean)(that.isMtac())).hashCode();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
	 * Compares the taAssigned of this {@link Section} with that one. 
	 * Returns -1, 0, or 1 depending on the difference of taAssigned attribute.
     *
     * @param that
     *             another non-{@code null} {@link Section}.
     *             
     * @return -1 if the taAssigned of this is larger than the taAssigned of that object; 
     *         0 if the taAssigned are the same;
     *         +1 if the taAssigned of this is smaller than the taAssigned of that object.
     */
    public int compareTaAssigned(Section that) {

    	int diff = ((Boolean)(this.isTaAssigned())).hashCode() -
    			((Boolean)(that.isTaAssigned())).hashCode();
        if (diff < 0)
            return -1;
        else if (diff == 0)
            return 0;
        else
            return 1;
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>
     * The order is determined by first comparing prefix. If prefix are equal,
     * courseCode is compared to break the tie. If courseCode is equal, courseName
     * is compared to break the tie. If courseName is equal, both course objects are equal.
     * </p>
     */
    @Override
	public int compareTo(Section that) {
        int c = compareSectionType(that);

        if (c != 0) {
            return c;
        }

        c = compareSectionNumber(that);

        if (c != 0)
            return c;
        
        c = compareInstructorName(that);

        if (c != 0)
            return c;
        
        c = compareCurrentEnrollment(that);

        if (c != 0)
            return c;
        
        c = compareEnrollmentCap(that);

        if (c != 0)
            return c;
        
        c = compareLocation(that);

        if (c != 0)
            return c;
        
        c = compareNumberOfTA(that);

        if (c != 0)
            return c;
        
        c = compareNumberOfLA(that);

        if (c != 0)
            return c;
        
        c = compareMTAC(that);

        if (c != 0)
            return c;
        return compareTaAssigned(that);

    }
    
    /**
     * This method is a override for {@link java.lang.Object#equals(Object)}.
     * 
     * @return a boolean value, TRUE if these two objects are equal or in the
     * second if statement their section numbers are the same. FALSE if this 
     * object is not Section type or in the second if statement their section 
     * number are not the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Section) {
        	Section that = (Section) obj;

            return this.sectionNumber == that.sectionNumber;
        }
        return false;
    }

    /**
     * This method is a override for {@link java.lang.Object#hashCode()}.
     * 
     * @return the hash code of the {@link Section}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getSectionType(), this.getSectionNumber(), 
        		this.getCurrentEnrollment(), this.getEnrollmentCap(), this.getLocation(),
        		this.getInstructorName(), this.getSchedule(), this.getNumOfTA(),
        		this.getNumOfLA(), this.isMtac(), this.isTaAssigned());
    }
    
    /**
     * Returns a human-readable representation of this {@link CourseInstance}, in the form of
     * "Prefix CourseCode CourseName".
     *
     * @return the formatted "SectionNumber SectionType, CurrentEnrollment of EnrollmentCap,
     * WeeklySchedule, Location, InstructorName" String.
     */
    @Override
	public String toString() {
    	String result = "",  
    			sectionType = getSectionType(), 
    			sectionNumber = getSectionNumber(),
        		instructor = " - " + getInstructorName();
        
        result += sectionNumber + sectionType + instructor;
        
        return result;
    }
    
    /**
     * Displays all the information of a {@link Section}.
     */
    public void display() {
    	String 
    			sectionType = getSectionType(), 
    			sectionNumber = getSectionNumber(),
    			openSeats =  ", " + getCurrentEnrollment() + " of " + getEnrollmentCap(),
    		    schedule = ", " + getSchedule().toString(), 
    		    location = ", " + getLocation(), 
        		instructor = ", " + getInstructorName();
        
        System.out.println(sectionNumber + sectionType + openSeats + schedule + location + instructor);
    }

}