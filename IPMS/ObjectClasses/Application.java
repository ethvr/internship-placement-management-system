package IPMS.ObjectClasses;

import IPMS.Enums.*;
import IPMS.UserManagement.IdGenerator;

public class Application {
    private String applicationID;                  
    private String studentId;           
    private String internshipId;        
    private ApplicationStatus status; // status on whether the company has accepted 
    private AcceptedByStudentStatus acceptedByStudent; // status of whether student has accepted after company has accepted 
    
    // CONSTRUCTOR FOR CREATING THE OBJ AT THE START
    public Application(String studentId, String internshipId) {
        this.applicationID = IdGenerator.nextAppId();
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = ApplicationStatus.PENDING;
        this.acceptedByStudent = AcceptedByStudentStatus.PENDING;
    }
    // constructor for creating obj from map 
    public Application(String applicationID, String studentId, String internshipId, 
                       ApplicationStatus status, AcceptedByStudentStatus acceptedByStudent) {
        this.applicationID = applicationID;
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = status;
        this.acceptedByStudent = acceptedByStudent;

    }

    //getters
    public String getApplicationID() {
        return applicationID;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getInternshipId() {
        return internshipId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public AcceptedByStudentStatus getAcceptedByStudent() {
        return acceptedByStudent;
    }
        
    //setters
    public void setId(String applicationID) {
        this.applicationID = applicationID;}

    public void setStudentId(String studentId) {
        this.studentId = studentId;}

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;}

    public void setStatus(ApplicationStatus status) {
        this.status = status;}

    public void setAcceptedByStudent(AcceptedByStudentStatus acceptedByStudent) {
        this.acceptedByStudent = acceptedByStudent;}

    public boolean isActive() {
        return status != ApplicationStatus.WITHDRAWN;}

    public boolean isConfirmedPlacement() {
        return (status == ApplicationStatus.SUCCESSFUL && acceptedByStudent == AcceptedByStudentStatus.ACCEPTED);
    }

    public boolean isUnsuccessful() {
        return status == ApplicationStatus.UNSUCCESSFUL;}

    @Override
    public String toString() {
        return String.format(
            "Application[%s] Student=%s, Internship=%s, Status=%s, Accepted=%s",
            applicationID, studentId, internshipId, status, acceptedByStudent
        );
    }
}
