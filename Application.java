public class Application {
    private String id;                  
    private String studentId;           
    private String internshipId;        
    private ApplicationStatus status;   
    private boolean acceptedByStudent;  

    public Application(String id, String studentId, String internshipId, ApplicationStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = status;
        this.acceptedByStudent = false;
    }

    public String getId() {
        return id;
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

    public boolean isAcceptedByStudent() {
        return acceptedByStudent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setAcceptedByStudent(boolean acceptedByStudent) {
        this.acceptedByStudent = acceptedByStudent;
    }

    public boolean isActive() {
        return status != ApplicationStatus.WITHDRAWN;
    }

    public boolean isConfirmedPlacement() {
        return status == ApplicationStatus.SUCCESSFUL && acceptedByStudent;
    }

    public boolean isUnsuccessful() {
        return status == ApplicationStatus.UNSUCCESSFUL;
    }

    @Override
    public String toString() {
        return String.format(
            "Application[%s] Student=%s, Internship=%s, Status=%s, Accepted=%s",
            id, studentId, internshipId, status, acceptedByStudent
        );
    }
}
