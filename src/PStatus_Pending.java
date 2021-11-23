package src;
public class PStatus_Pending implements PStatus {
    private String status;
    private static PStatus_Pending instance = new PStatus_Pending();

    private PStatus_Pending() {
        status = "Pending";
    }

    public static PStatus_Pending getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return status;
    }
}
