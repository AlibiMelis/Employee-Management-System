package src;
public class PStatus_InProgress implements PStatus {
    private String status;
    private static PStatus_InProgress instance = new PStatus_InProgress();

    private PStatus_InProgress() {
        status = "In-progress";
    }

    public static PStatus_InProgress getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return status;
    }
}