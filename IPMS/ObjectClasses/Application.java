package IPMS.ObjectClasses;

import IPMS.Enums.*;
import IPMS.Enums.AcceptedByStudentStatus;
import IPMS.UserManagement.IdGenerator;

public class Application {
    private String id;                  
    private String studentId;           
    private String internshipId;        
    private ApplicationStatus status;   
    private AcceptedByStudentStatus acceptedByStudent;
    
    // CONSTRUCTOR FOR CREATING THE OBJ AT THE START
    public Application(String studentId, String internshipId) {
        this.id = IdGenerator.nextAppId();
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = ApplicationStatus.PENDING;
        this.acceptedByStudent = AcceptedByStudentStatus.PENDING;
    }
    // constructor for creating obj from map 
    public Application(String id, String studentId, String internshipId, ApplicationStatus status, AcceptedByStudentStatus acceptedByStudent) {
        this.id = id;
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = status;
        this.acceptedByStudent = AcceptedByStudentStatus.PENDING;

    }

    //getters
    public String getId() {
        return id;}

    public String getStudentId() {
        return studentId;}

    public String getInternshipId() {
        return internshipId;}

    public ApplicationStatus getStatus() {
        return status;}

    public AcceptedByStudentStatus isAcceptedByStudent() {
        return AcceptedByStudentStatus.ACCEPTED;}
        
    //setters
    public void setId(String id) {
        this.id = id;}

    public void setStudentId(String studentId) {
        this.studentId = studentId;}

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;}

    public void setStatus(ApplicationStatus status) {
        this.status = status;}

    public void setAcceptedByStudent(AcceptedByStudentStatus acceptedByStudent) {
        this.acceptedByStudent = acceptedByStudent;}

    public ApplicationStatus isActive() {
        return status = ApplicationStatus.WITHDRAWN;}

    public ApplicationStatus isConfirmedPlacement() {
        return status = ApplicationStatus.SUCCESSFUL ;}

    public ApplicationStatus isUnsuccessful() {
        return status = ApplicationStatus.UNSUCCESSFUL;}

    @Override
    public String toString() {
        return String.format(
            "Application[%s] Student=%s, Internship=%s, Status=%s, Accepted=%s",
            id, studentId, internshipId, status, acceptedByStudent
        );
    }
}
