public class WithdrawalRequest {
    private String id;
    private String studentId;
    private String applicationId;
    private String reason;
    private String decision; // "Pending", "Approved", "Rejected"
    private boolean postPlacement;

    public WithdrawalRequest(String id, String studentId, String applicationId, String reason, boolean postPlacement) {
        this.id = id;
        this.studentId = studentId;
        this.applicationId = applicationId;
        this.reason = reason;
        this.postPlacement = postPlacement;
        this.decision = "Pending";
    }

    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getApplicationId() { return applicationId; }
    public String getReason() { return reason; }
    public boolean isPostPlacement() { return postPlacement; }
    public String getDecision() { return decision; }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}