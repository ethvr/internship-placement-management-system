package IPMS.ObjectClasses;

import java.time.LocalDateTime;
import IPMS.Enums.*;
import IPMS.UserManagement.*;
import java.time.LocalDate;

public class WithdrawalRequest {
    private String id;                      
    private String applicationId;           
    private String studentId;               
    private WithdrawalStatus status;        
    private LocalDate requestTime;      
    private String remarks;                


    public WithdrawalRequest(String applicationId, String studentId, WithdrawalStatus status) {
        this.id = IdGenerator.nextWithdrawalId();
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.status = status;
        this.requestTime = LocalDate.now();
        this.remarks = "";
    }

    public WithdrawalRequest(String id, String applicationId, String studentId, WithdrawalStatus status, LocalDate requestTime, String remarks) {
        this.id = id;
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.status = status;
        this.requestTime = requestTime;
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public WithdrawalStatus getStatus() {
        return status;
    }

    public LocalDate getRequestTime() {
        return requestTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStatus(WithdrawalStatus status) {
        this.status = status;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isPending() {
        return status == WithdrawalStatus.PENDING;
    }

    @Override
    public String toString() {
        return String.format(
            "WithdrawalRequest[%s] Application=%s, Student=%s, Status=%s, Time=%s, Remarks=%s",
            id, applicationId, studentId, status, requestTime, remarks
        );
    }
}
