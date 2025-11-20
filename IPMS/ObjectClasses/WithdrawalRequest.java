package IPMS.ObjectClasses;

import IPMS.Enums.*;
import IPMS.UserManagement.*;
import java.time.LocalDate;

public class WithdrawalRequest {
    private String id;                      
    private String applicationId;           
    private String studentId;               
    private WithdrawalStatus status;        
    private LocalDate requestDate;      
    private String remarks;                


    public WithdrawalRequest(String applicationId, String studentId, String remarks) {
        this.id = IdGenerator.nextWithdrawalId();
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.status = WithdrawalStatus.PENDING;
        this.requestDate = LocalDate.now();
        this.remarks = remarks;
    }

    public WithdrawalRequest(String id, String applicationId, String studentId, WithdrawalStatus status, LocalDate requestDate, String remarks) {
        this.id = id;
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.status = status;
        this.requestDate = requestDate;
        this.remarks = remarks;
    }

    /** 
     * @return String
     */
    public String getId() {
        return id;
    }

    /** 
     * @return String
     */
    public String getApplicationId() {
        return applicationId;
    }

    /** 
     * @return String
     */
    public String getStudentId() {
        return studentId;
    }

    /** 
     * @return WithdrawalStatus
     */
    public WithdrawalStatus getStatus() {
        return status;
    }

    /** 
     * @return LocalDate
     */
    public LocalDate getRequestDate() {
        return requestDate;
    }

    /** 
     * @return String
     */
    public String getRemarks() {
        return remarks;
    }

    /** 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 
     * @param applicationId
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /** 
     * @param studentId
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /** 
     * @param status
     */
    public void setStatus(WithdrawalStatus status) {
        this.status = status;
    }

    /** 
     * @param requestDate
     */
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    /** 
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** 
     * @return boolean
     */
    public boolean isPending() {
        return status == WithdrawalStatus.PENDING;
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
            "WithdrawalRequest[%s] Application=%s, Student=%s, Status=%s, Time=%s, Remarks=%s",
            id, applicationId, studentId, status, requestDate, remarks
        );
    }

}
